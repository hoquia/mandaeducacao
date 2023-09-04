import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ImpostoFormService } from './imposto-form.service';
import { ImpostoService } from '../service/imposto.service';
import { IImposto } from '../imposto.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';

import { ImpostoUpdateComponent } from './imposto-update.component';

describe('Imposto Management Update Component', () => {
  let comp: ImpostoUpdateComponent;
  let fixture: ComponentFixture<ImpostoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let impostoFormService: ImpostoFormService;
  let impostoService: ImpostoService;
  let lookupItemService: LookupItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ImpostoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ImpostoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ImpostoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    impostoFormService = TestBed.inject(ImpostoFormService);
    impostoService = TestBed.inject(ImpostoService);
    lookupItemService = TestBed.inject(LookupItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LookupItem query and add missing value', () => {
      const imposto: IImposto = { id: 456 };
      const tipoImposto: ILookupItem = { id: 35014 };
      imposto.tipoImposto = tipoImposto;
      const codigoImposto: ILookupItem = { id: 47270 };
      imposto.codigoImposto = codigoImposto;
      const motivoIsencaoCodigo: ILookupItem = { id: 69529 };
      imposto.motivoIsencaoCodigo = motivoIsencaoCodigo;
      const motivoIsencaoDescricao: ILookupItem = { id: 4323 };
      imposto.motivoIsencaoDescricao = motivoIsencaoDescricao;

      const lookupItemCollection: ILookupItem[] = [{ id: 40611 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [tipoImposto, codigoImposto, motivoIsencaoCodigo, motivoIsencaoDescricao];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const imposto: IImposto = { id: 456 };
      const tipoImposto: ILookupItem = { id: 53397 };
      imposto.tipoImposto = tipoImposto;
      const codigoImposto: ILookupItem = { id: 52212 };
      imposto.codigoImposto = codigoImposto;
      const motivoIsencaoCodigo: ILookupItem = { id: 82165 };
      imposto.motivoIsencaoCodigo = motivoIsencaoCodigo;
      const motivoIsencaoDescricao: ILookupItem = { id: 50957 };
      imposto.motivoIsencaoDescricao = motivoIsencaoDescricao;

      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      expect(comp.lookupItemsSharedCollection).toContain(tipoImposto);
      expect(comp.lookupItemsSharedCollection).toContain(codigoImposto);
      expect(comp.lookupItemsSharedCollection).toContain(motivoIsencaoCodigo);
      expect(comp.lookupItemsSharedCollection).toContain(motivoIsencaoDescricao);
      expect(comp.imposto).toEqual(imposto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImposto>>();
      const imposto = { id: 123 };
      jest.spyOn(impostoFormService, 'getImposto').mockReturnValue(imposto);
      jest.spyOn(impostoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: imposto }));
      saveSubject.complete();

      // THEN
      expect(impostoFormService.getImposto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(impostoService.update).toHaveBeenCalledWith(expect.objectContaining(imposto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImposto>>();
      const imposto = { id: 123 };
      jest.spyOn(impostoFormService, 'getImposto').mockReturnValue({ id: null });
      jest.spyOn(impostoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imposto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: imposto }));
      saveSubject.complete();

      // THEN
      expect(impostoFormService.getImposto).toHaveBeenCalled();
      expect(impostoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IImposto>>();
      const imposto = { id: 123 };
      jest.spyOn(impostoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ imposto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(impostoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLookupItem', () => {
      it('Should forward to lookupItemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(lookupItemService, 'compareLookupItem');
        comp.compareLookupItem(entity, entity2);
        expect(lookupItemService.compareLookupItem).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
