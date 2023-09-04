import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategoriaEmolumentoFormService } from './categoria-emolumento-form.service';
import { CategoriaEmolumentoService } from '../service/categoria-emolumento.service';
import { ICategoriaEmolumento } from '../categoria-emolumento.model';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';
import { PlanoMultaService } from 'app/entities/plano-multa/service/plano-multa.service';

import { CategoriaEmolumentoUpdateComponent } from './categoria-emolumento-update.component';

describe('CategoriaEmolumento Management Update Component', () => {
  let comp: CategoriaEmolumentoUpdateComponent;
  let fixture: ComponentFixture<CategoriaEmolumentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoriaEmolumentoFormService: CategoriaEmolumentoFormService;
  let categoriaEmolumentoService: CategoriaEmolumentoService;
  let planoMultaService: PlanoMultaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategoriaEmolumentoUpdateComponent],
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
      .overrideTemplate(CategoriaEmolumentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoriaEmolumentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoriaEmolumentoFormService = TestBed.inject(CategoriaEmolumentoFormService);
    categoriaEmolumentoService = TestBed.inject(CategoriaEmolumentoService);
    planoMultaService = TestBed.inject(PlanoMultaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlanoMulta query and add missing value', () => {
      const categoriaEmolumento: ICategoriaEmolumento = { id: 456 };
      const planoMulta: IPlanoMulta = { id: 38495 };
      categoriaEmolumento.planoMulta = planoMulta;

      const planoMultaCollection: IPlanoMulta[] = [{ id: 39257 }];
      jest.spyOn(planoMultaService, 'query').mockReturnValue(of(new HttpResponse({ body: planoMultaCollection })));
      const additionalPlanoMultas = [planoMulta];
      const expectedCollection: IPlanoMulta[] = [...additionalPlanoMultas, ...planoMultaCollection];
      jest.spyOn(planoMultaService, 'addPlanoMultaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ categoriaEmolumento });
      comp.ngOnInit();

      expect(planoMultaService.query).toHaveBeenCalled();
      expect(planoMultaService.addPlanoMultaToCollectionIfMissing).toHaveBeenCalledWith(
        planoMultaCollection,
        ...additionalPlanoMultas.map(expect.objectContaining)
      );
      expect(comp.planoMultasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const categoriaEmolumento: ICategoriaEmolumento = { id: 456 };
      const planoMulta: IPlanoMulta = { id: 12345 };
      categoriaEmolumento.planoMulta = planoMulta;

      activatedRoute.data = of({ categoriaEmolumento });
      comp.ngOnInit();

      expect(comp.planoMultasSharedCollection).toContain(planoMulta);
      expect(comp.categoriaEmolumento).toEqual(categoriaEmolumento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaEmolumento>>();
      const categoriaEmolumento = { id: 123 };
      jest.spyOn(categoriaEmolumentoFormService, 'getCategoriaEmolumento').mockReturnValue(categoriaEmolumento);
      jest.spyOn(categoriaEmolumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaEmolumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaEmolumento }));
      saveSubject.complete();

      // THEN
      expect(categoriaEmolumentoFormService.getCategoriaEmolumento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoriaEmolumentoService.update).toHaveBeenCalledWith(expect.objectContaining(categoriaEmolumento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaEmolumento>>();
      const categoriaEmolumento = { id: 123 };
      jest.spyOn(categoriaEmolumentoFormService, 'getCategoriaEmolumento').mockReturnValue({ id: null });
      jest.spyOn(categoriaEmolumentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaEmolumento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaEmolumento }));
      saveSubject.complete();

      // THEN
      expect(categoriaEmolumentoFormService.getCategoriaEmolumento).toHaveBeenCalled();
      expect(categoriaEmolumentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaEmolumento>>();
      const categoriaEmolumento = { id: 123 };
      jest.spyOn(categoriaEmolumentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaEmolumento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoriaEmolumentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
