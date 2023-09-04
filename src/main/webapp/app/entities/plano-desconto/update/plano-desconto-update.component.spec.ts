import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlanoDescontoFormService } from './plano-desconto-form.service';
import { PlanoDescontoService } from '../service/plano-desconto.service';
import { IPlanoDesconto } from '../plano-desconto.model';
import { ICategoriaEmolumento } from 'app/entities/categoria-emolumento/categoria-emolumento.model';
import { CategoriaEmolumentoService } from 'app/entities/categoria-emolumento/service/categoria-emolumento.service';

import { PlanoDescontoUpdateComponent } from './plano-desconto-update.component';

describe('PlanoDesconto Management Update Component', () => {
  let comp: PlanoDescontoUpdateComponent;
  let fixture: ComponentFixture<PlanoDescontoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoDescontoFormService: PlanoDescontoFormService;
  let planoDescontoService: PlanoDescontoService;
  let categoriaEmolumentoService: CategoriaEmolumentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlanoDescontoUpdateComponent],
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
      .overrideTemplate(PlanoDescontoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoDescontoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoDescontoFormService = TestBed.inject(PlanoDescontoFormService);
    planoDescontoService = TestBed.inject(PlanoDescontoService);
    categoriaEmolumentoService = TestBed.inject(CategoriaEmolumentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CategoriaEmolumento query and add missing value', () => {
      const planoDesconto: IPlanoDesconto = { id: 456 };
      const categoriasEmolumentos: ICategoriaEmolumento[] = [{ id: 28266 }];
      planoDesconto.categoriasEmolumentos = categoriasEmolumentos;

      const categoriaEmolumentoCollection: ICategoriaEmolumento[] = [{ id: 44415 }];
      jest.spyOn(categoriaEmolumentoService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaEmolumentoCollection })));
      const additionalCategoriaEmolumentos = [...categoriasEmolumentos];
      const expectedCollection: ICategoriaEmolumento[] = [...additionalCategoriaEmolumentos, ...categoriaEmolumentoCollection];
      jest.spyOn(categoriaEmolumentoService, 'addCategoriaEmolumentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoDesconto });
      comp.ngOnInit();

      expect(categoriaEmolumentoService.query).toHaveBeenCalled();
      expect(categoriaEmolumentoService.addCategoriaEmolumentoToCollectionIfMissing).toHaveBeenCalledWith(
        categoriaEmolumentoCollection,
        ...additionalCategoriaEmolumentos.map(expect.objectContaining)
      );
      expect(comp.categoriaEmolumentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const planoDesconto: IPlanoDesconto = { id: 456 };
      const categoriasEmolumento: ICategoriaEmolumento = { id: 95043 };
      planoDesconto.categoriasEmolumentos = [categoriasEmolumento];

      activatedRoute.data = of({ planoDesconto });
      comp.ngOnInit();

      expect(comp.categoriaEmolumentosSharedCollection).toContain(categoriasEmolumento);
      expect(comp.planoDesconto).toEqual(planoDesconto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoDesconto>>();
      const planoDesconto = { id: 123 };
      jest.spyOn(planoDescontoFormService, 'getPlanoDesconto').mockReturnValue(planoDesconto);
      jest.spyOn(planoDescontoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoDesconto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoDesconto }));
      saveSubject.complete();

      // THEN
      expect(planoDescontoFormService.getPlanoDesconto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoDescontoService.update).toHaveBeenCalledWith(expect.objectContaining(planoDesconto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoDesconto>>();
      const planoDesconto = { id: 123 };
      jest.spyOn(planoDescontoFormService, 'getPlanoDesconto').mockReturnValue({ id: null });
      jest.spyOn(planoDescontoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoDesconto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoDesconto }));
      saveSubject.complete();

      // THEN
      expect(planoDescontoFormService.getPlanoDesconto).toHaveBeenCalled();
      expect(planoDescontoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoDesconto>>();
      const planoDesconto = { id: 123 };
      jest.spyOn(planoDescontoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoDesconto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoDescontoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategoriaEmolumento', () => {
      it('Should forward to categoriaEmolumentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoriaEmolumentoService, 'compareCategoriaEmolumento');
        comp.compareCategoriaEmolumento(entity, entity2);
        expect(categoriaEmolumentoService.compareCategoriaEmolumento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
