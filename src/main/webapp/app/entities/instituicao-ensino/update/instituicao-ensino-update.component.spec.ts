import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InstituicaoEnsinoFormService } from './instituicao-ensino-form.service';
import { InstituicaoEnsinoService } from '../service/instituicao-ensino.service';
import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';

import { InstituicaoEnsinoUpdateComponent } from './instituicao-ensino-update.component';

describe('InstituicaoEnsino Management Update Component', () => {
  let comp: InstituicaoEnsinoUpdateComponent;
  let fixture: ComponentFixture<InstituicaoEnsinoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let instituicaoEnsinoFormService: InstituicaoEnsinoFormService;
  let instituicaoEnsinoService: InstituicaoEnsinoService;
  let lookupItemService: LookupItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InstituicaoEnsinoUpdateComponent],
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
      .overrideTemplate(InstituicaoEnsinoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InstituicaoEnsinoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    instituicaoEnsinoFormService = TestBed.inject(InstituicaoEnsinoFormService);
    instituicaoEnsinoService = TestBed.inject(InstituicaoEnsinoService);
    lookupItemService = TestBed.inject(LookupItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call InstituicaoEnsino query and add missing value', () => {
      const instituicaoEnsino: IInstituicaoEnsino = { id: 456 };
      const sede: IInstituicaoEnsino = { id: 84129 };
      instituicaoEnsino.sede = sede;

      const instituicaoEnsinoCollection: IInstituicaoEnsino[] = [{ id: 98316 }];
      jest.spyOn(instituicaoEnsinoService, 'query').mockReturnValue(of(new HttpResponse({ body: instituicaoEnsinoCollection })));
      const additionalInstituicaoEnsinos = [sede];
      const expectedCollection: IInstituicaoEnsino[] = [...additionalInstituicaoEnsinos, ...instituicaoEnsinoCollection];
      jest.spyOn(instituicaoEnsinoService, 'addInstituicaoEnsinoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      expect(instituicaoEnsinoService.query).toHaveBeenCalled();
      expect(instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing).toHaveBeenCalledWith(
        instituicaoEnsinoCollection,
        ...additionalInstituicaoEnsinos.map(expect.objectContaining)
      );
      expect(comp.instituicaoEnsinosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LookupItem query and add missing value', () => {
      const instituicaoEnsino: IInstituicaoEnsino = { id: 456 };
      const categoriaInstituicao: ILookupItem = { id: 12656 };
      instituicaoEnsino.categoriaInstituicao = categoriaInstituicao;
      const unidadePagadora: ILookupItem = { id: 15519 };
      instituicaoEnsino.unidadePagadora = unidadePagadora;
      const tipoVinculo: ILookupItem = { id: 81799 };
      instituicaoEnsino.tipoVinculo = tipoVinculo;
      const tipoInstalacao: ILookupItem = { id: 46469 };
      instituicaoEnsino.tipoInstalacao = tipoInstalacao;

      const lookupItemCollection: ILookupItem[] = [{ id: 85845 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [categoriaInstituicao, unidadePagadora, tipoVinculo, tipoInstalacao];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const instituicaoEnsino: IInstituicaoEnsino = { id: 456 };
      const sede: IInstituicaoEnsino = { id: 45214 };
      instituicaoEnsino.sede = sede;
      const categoriaInstituicao: ILookupItem = { id: 15608 };
      instituicaoEnsino.categoriaInstituicao = categoriaInstituicao;
      const unidadePagadora: ILookupItem = { id: 37617 };
      instituicaoEnsino.unidadePagadora = unidadePagadora;
      const tipoVinculo: ILookupItem = { id: 76317 };
      instituicaoEnsino.tipoVinculo = tipoVinculo;
      const tipoInstalacao: ILookupItem = { id: 14005 };
      instituicaoEnsino.tipoInstalacao = tipoInstalacao;

      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      expect(comp.instituicaoEnsinosSharedCollection).toContain(sede);
      expect(comp.lookupItemsSharedCollection).toContain(categoriaInstituicao);
      expect(comp.lookupItemsSharedCollection).toContain(unidadePagadora);
      expect(comp.lookupItemsSharedCollection).toContain(tipoVinculo);
      expect(comp.lookupItemsSharedCollection).toContain(tipoInstalacao);
      expect(comp.instituicaoEnsino).toEqual(instituicaoEnsino);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInstituicaoEnsino>>();
      const instituicaoEnsino = { id: 123 };
      jest.spyOn(instituicaoEnsinoFormService, 'getInstituicaoEnsino').mockReturnValue(instituicaoEnsino);
      jest.spyOn(instituicaoEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: instituicaoEnsino }));
      saveSubject.complete();

      // THEN
      expect(instituicaoEnsinoFormService.getInstituicaoEnsino).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(instituicaoEnsinoService.update).toHaveBeenCalledWith(expect.objectContaining(instituicaoEnsino));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInstituicaoEnsino>>();
      const instituicaoEnsino = { id: 123 };
      jest.spyOn(instituicaoEnsinoFormService, 'getInstituicaoEnsino').mockReturnValue({ id: null });
      jest.spyOn(instituicaoEnsinoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ instituicaoEnsino: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: instituicaoEnsino }));
      saveSubject.complete();

      // THEN
      expect(instituicaoEnsinoFormService.getInstituicaoEnsino).toHaveBeenCalled();
      expect(instituicaoEnsinoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInstituicaoEnsino>>();
      const instituicaoEnsino = { id: 123 };
      jest.spyOn(instituicaoEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ instituicaoEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(instituicaoEnsinoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareInstituicaoEnsino', () => {
      it('Should forward to instituicaoEnsinoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(instituicaoEnsinoService, 'compareInstituicaoEnsino');
        comp.compareInstituicaoEnsino(entity, entity2);
        expect(instituicaoEnsinoService.compareInstituicaoEnsino).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
