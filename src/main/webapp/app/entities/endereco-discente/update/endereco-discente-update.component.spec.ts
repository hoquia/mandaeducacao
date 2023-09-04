import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EnderecoDiscenteFormService } from './endereco-discente-form.service';
import { EnderecoDiscenteService } from '../service/endereco-discente.service';
import { IEnderecoDiscente } from '../endereco-discente.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

import { EnderecoDiscenteUpdateComponent } from './endereco-discente-update.component';

describe('EnderecoDiscente Management Update Component', () => {
  let comp: EnderecoDiscenteUpdateComponent;
  let fixture: ComponentFixture<EnderecoDiscenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enderecoDiscenteFormService: EnderecoDiscenteFormService;
  let enderecoDiscenteService: EnderecoDiscenteService;
  let lookupItemService: LookupItemService;
  let discenteService: DiscenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EnderecoDiscenteUpdateComponent],
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
      .overrideTemplate(EnderecoDiscenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnderecoDiscenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enderecoDiscenteFormService = TestBed.inject(EnderecoDiscenteFormService);
    enderecoDiscenteService = TestBed.inject(EnderecoDiscenteService);
    lookupItemService = TestBed.inject(LookupItemService);
    discenteService = TestBed.inject(DiscenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LookupItem query and add missing value', () => {
      const enderecoDiscente: IEnderecoDiscente = { id: 456 };
      const pais: ILookupItem = { id: 12530 };
      enderecoDiscente.pais = pais;
      const provincia: ILookupItem = { id: 96011 };
      enderecoDiscente.provincia = provincia;
      const municipio: ILookupItem = { id: 55287 };
      enderecoDiscente.municipio = municipio;

      const lookupItemCollection: ILookupItem[] = [{ id: 91083 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [pais, provincia, municipio];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoDiscente });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Discente query and add missing value', () => {
      const enderecoDiscente: IEnderecoDiscente = { id: 456 };
      const discente: IDiscente = { id: 98212 };
      enderecoDiscente.discente = discente;

      const discenteCollection: IDiscente[] = [{ id: 72473 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [discente];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoDiscente });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enderecoDiscente: IEnderecoDiscente = { id: 456 };
      const pais: ILookupItem = { id: 79470 };
      enderecoDiscente.pais = pais;
      const provincia: ILookupItem = { id: 30806 };
      enderecoDiscente.provincia = provincia;
      const municipio: ILookupItem = { id: 14418 };
      enderecoDiscente.municipio = municipio;
      const discente: IDiscente = { id: 4009 };
      enderecoDiscente.discente = discente;

      activatedRoute.data = of({ enderecoDiscente });
      comp.ngOnInit();

      expect(comp.lookupItemsSharedCollection).toContain(pais);
      expect(comp.lookupItemsSharedCollection).toContain(provincia);
      expect(comp.lookupItemsSharedCollection).toContain(municipio);
      expect(comp.discentesSharedCollection).toContain(discente);
      expect(comp.enderecoDiscente).toEqual(enderecoDiscente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoDiscente>>();
      const enderecoDiscente = { id: 123 };
      jest.spyOn(enderecoDiscenteFormService, 'getEnderecoDiscente').mockReturnValue(enderecoDiscente);
      jest.spyOn(enderecoDiscenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoDiscente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoDiscente }));
      saveSubject.complete();

      // THEN
      expect(enderecoDiscenteFormService.getEnderecoDiscente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enderecoDiscenteService.update).toHaveBeenCalledWith(expect.objectContaining(enderecoDiscente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoDiscente>>();
      const enderecoDiscente = { id: 123 };
      jest.spyOn(enderecoDiscenteFormService, 'getEnderecoDiscente').mockReturnValue({ id: null });
      jest.spyOn(enderecoDiscenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoDiscente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoDiscente }));
      saveSubject.complete();

      // THEN
      expect(enderecoDiscenteFormService.getEnderecoDiscente).toHaveBeenCalled();
      expect(enderecoDiscenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoDiscente>>();
      const enderecoDiscente = { id: 123 };
      jest.spyOn(enderecoDiscenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoDiscente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enderecoDiscenteService.update).toHaveBeenCalled();
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

    describe('compareDiscente', () => {
      it('Should forward to discenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(discenteService, 'compareDiscente');
        comp.compareDiscente(entity, entity2);
        expect(discenteService.compareDiscente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
