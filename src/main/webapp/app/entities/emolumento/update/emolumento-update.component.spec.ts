import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmolumentoFormService } from './emolumento-form.service';
import { EmolumentoService } from '../service/emolumento.service';
import { IEmolumento } from '../emolumento.model';
import { ICategoriaEmolumento } from 'app/entities/categoria-emolumento/categoria-emolumento.model';
import { CategoriaEmolumentoService } from 'app/entities/categoria-emolumento/service/categoria-emolumento.service';
import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';
import { PlanoMultaService } from 'app/entities/plano-multa/service/plano-multa.service';

import { EmolumentoUpdateComponent } from './emolumento-update.component';

describe('Emolumento Management Update Component', () => {
  let comp: EmolumentoUpdateComponent;
  let fixture: ComponentFixture<EmolumentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emolumentoFormService: EmolumentoFormService;
  let emolumentoService: EmolumentoService;
  let categoriaEmolumentoService: CategoriaEmolumentoService;
  let impostoService: ImpostoService;
  let planoMultaService: PlanoMultaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmolumentoUpdateComponent],
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
      .overrideTemplate(EmolumentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmolumentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emolumentoFormService = TestBed.inject(EmolumentoFormService);
    emolumentoService = TestBed.inject(EmolumentoService);
    categoriaEmolumentoService = TestBed.inject(CategoriaEmolumentoService);
    impostoService = TestBed.inject(ImpostoService);
    planoMultaService = TestBed.inject(PlanoMultaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Emolumento query and add missing value', () => {
      const emolumento: IEmolumento = { id: 456 };
      const referencia: IEmolumento = { id: 85029 };
      emolumento.referencia = referencia;

      const emolumentoCollection: IEmolumento[] = [{ id: 11902 }];
      jest.spyOn(emolumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: emolumentoCollection })));
      const additionalEmolumentos = [referencia];
      const expectedCollection: IEmolumento[] = [...additionalEmolumentos, ...emolumentoCollection];
      jest.spyOn(emolumentoService, 'addEmolumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ emolumento });
      comp.ngOnInit();

      expect(emolumentoService.query).toHaveBeenCalled();
      expect(emolumentoService.addEmolumentoToCollectionIfMissing).toHaveBeenCalledWith(
        emolumentoCollection,
        ...additionalEmolumentos.map(expect.objectContaining)
      );
      expect(comp.emolumentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CategoriaEmolumento query and add missing value', () => {
      const emolumento: IEmolumento = { id: 456 };
      const categoria: ICategoriaEmolumento = { id: 11844 };
      emolumento.categoria = categoria;

      const categoriaEmolumentoCollection: ICategoriaEmolumento[] = [{ id: 55716 }];
      jest.spyOn(categoriaEmolumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaEmolumentoCollection })));
      const additionalCategoriaEmolumentos = [categoria];
      const expectedCollection: ICategoriaEmolumento[] = [...additionalCategoriaEmolumentos, ...categoriaEmolumentoCollection];
      jest.spyOn(categoriaEmolumentoService, 'addCategoriaEmolumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ emolumento });
      comp.ngOnInit();

      expect(categoriaEmolumentoService.query).toHaveBeenCalled();
      expect(categoriaEmolumentoService.addCategoriaEmolumentoToCollectionIfMissing).toHaveBeenCalledWith(
        categoriaEmolumentoCollection,
        ...additionalCategoriaEmolumentos.map(expect.objectContaining)
      );
      expect(comp.categoriaEmolumentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Imposto query and add missing value', () => {
      const emolumento: IEmolumento = { id: 456 };
      const imposto: IImposto = { id: 39689 };
      emolumento.imposto = imposto;

      const impostoCollection: IImposto[] = [{ id: 95909 }];
      jest.spyOn(impostoService, 'query').mockReturnValue(of(new HttpResponse({ body: impostoCollection })));
      const additionalImpostos = [imposto];
      const expectedCollection: IImposto[] = [...additionalImpostos, ...impostoCollection];
      jest.spyOn(impostoService, 'addImpostoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ emolumento });
      comp.ngOnInit();

      expect(impostoService.query).toHaveBeenCalled();
      expect(impostoService.addImpostoToCollectionIfMissing).toHaveBeenCalledWith(
        impostoCollection,
        ...additionalImpostos.map(expect.objectContaining)
      );
      expect(comp.impostosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoMulta query and add missing value', () => {
      const emolumento: IEmolumento = { id: 456 };
      const planoMulta: IPlanoMulta = { id: 35465 };
      emolumento.planoMulta = planoMulta;

      const planoMultaCollection: IPlanoMulta[] = [{ id: 88180 }];
      jest.spyOn(planoMultaService, 'query').mockReturnValue(of(new HttpResponse({ body: planoMultaCollection })));
      const additionalPlanoMultas = [planoMulta];
      const expectedCollection: IPlanoMulta[] = [...additionalPlanoMultas, ...planoMultaCollection];
      jest.spyOn(planoMultaService, 'addPlanoMultaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ emolumento });
      comp.ngOnInit();

      expect(planoMultaService.query).toHaveBeenCalled();
      expect(planoMultaService.addPlanoMultaToCollectionIfMissing).toHaveBeenCalledWith(
        planoMultaCollection,
        ...additionalPlanoMultas.map(expect.objectContaining)
      );
      expect(comp.planoMultasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const emolumento: IEmolumento = { id: 456 };
      const referencia: IEmolumento = { id: 60363 };
      emolumento.referencia = referencia;
      const categoria: ICategoriaEmolumento = { id: 72538 };
      emolumento.categoria = categoria;
      const imposto: IImposto = { id: 15208 };
      emolumento.imposto = imposto;
      const planoMulta: IPlanoMulta = { id: 99112 };
      emolumento.planoMulta = planoMulta;

      activatedRoute.data = of({ emolumento });
      comp.ngOnInit();

      expect(comp.emolumentosSharedCollection).toContain(referencia);
      expect(comp.categoriaEmolumentosSharedCollection).toContain(categoria);
      expect(comp.impostosSharedCollection).toContain(imposto);
      expect(comp.planoMultasSharedCollection).toContain(planoMulta);
      expect(comp.emolumento).toEqual(emolumento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmolumento>>();
      const emolumento = { id: 123 };
      jest.spyOn(emolumentoFormService, 'getEmolumento').mockReturnValue(emolumento);
      jest.spyOn(emolumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emolumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emolumento }));
      saveSubject.complete();

      // THEN
      expect(emolumentoFormService.getEmolumento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(emolumentoService.update).toHaveBeenCalledWith(expect.objectContaining(emolumento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmolumento>>();
      const emolumento = { id: 123 };
      jest.spyOn(emolumentoFormService, 'getEmolumento').mockReturnValue({ id: null });
      jest.spyOn(emolumentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emolumento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emolumento }));
      saveSubject.complete();

      // THEN
      expect(emolumentoFormService.getEmolumento).toHaveBeenCalled();
      expect(emolumentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmolumento>>();
      const emolumento = { id: 123 };
      jest.spyOn(emolumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emolumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emolumentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmolumento', () => {
      it('Should forward to emolumentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(emolumentoService, 'compareEmolumento');
        comp.compareEmolumento(entity, entity2);
        expect(emolumentoService.compareEmolumento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCategoriaEmolumento', () => {
      it('Should forward to categoriaEmolumentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoriaEmolumentoService, 'compareCategoriaEmolumento');
        comp.compareCategoriaEmolumento(entity, entity2);
        expect(categoriaEmolumentoService.compareCategoriaEmolumento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareImposto', () => {
      it('Should forward to impostoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(impostoService, 'compareImposto');
        comp.compareImposto(entity, entity2);
        expect(impostoService.compareImposto).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoMulta', () => {
      it('Should forward to planoMultaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoMultaService, 'comparePlanoMulta');
        comp.comparePlanoMulta(entity, entity2);
        expect(planoMultaService.comparePlanoMulta).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
