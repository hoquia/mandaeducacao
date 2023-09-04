import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategoriaOcorrenciaFormService } from './categoria-ocorrencia-form.service';
import { CategoriaOcorrenciaService } from '../service/categoria-ocorrencia.service';
import { ICategoriaOcorrencia } from '../categoria-ocorrencia.model';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IMedidaDisciplinar } from 'app/entities/medida-disciplinar/medida-disciplinar.model';
import { MedidaDisciplinarService } from 'app/entities/medida-disciplinar/service/medida-disciplinar.service';

import { CategoriaOcorrenciaUpdateComponent } from './categoria-ocorrencia-update.component';

describe('CategoriaOcorrencia Management Update Component', () => {
  let comp: CategoriaOcorrenciaUpdateComponent;
  let fixture: ComponentFixture<CategoriaOcorrenciaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoriaOcorrenciaFormService: CategoriaOcorrenciaFormService;
  let categoriaOcorrenciaService: CategoriaOcorrenciaService;
  let docenteService: DocenteService;
  let medidaDisciplinarService: MedidaDisciplinarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategoriaOcorrenciaUpdateComponent],
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
      .overrideTemplate(CategoriaOcorrenciaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoriaOcorrenciaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoriaOcorrenciaFormService = TestBed.inject(CategoriaOcorrenciaFormService);
    categoriaOcorrenciaService = TestBed.inject(CategoriaOcorrenciaService);
    docenteService = TestBed.inject(DocenteService);
    medidaDisciplinarService = TestBed.inject(MedidaDisciplinarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CategoriaOcorrencia query and add missing value', () => {
      const categoriaOcorrencia: ICategoriaOcorrencia = { id: 456 };
      const referencia: ICategoriaOcorrencia = { id: 56897 };
      categoriaOcorrencia.referencia = referencia;

      const categoriaOcorrenciaCollection: ICategoriaOcorrencia[] = [{ id: 54512 }];
      jest.spyOn(categoriaOcorrenciaService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriaOcorrenciaCollection })));
      const additionalCategoriaOcorrencias = [referencia];
      const expectedCollection: ICategoriaOcorrencia[] = [...additionalCategoriaOcorrencias, ...categoriaOcorrenciaCollection];
      jest.spyOn(categoriaOcorrenciaService, 'addCategoriaOcorrenciaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ categoriaOcorrencia });
      comp.ngOnInit();

      expect(categoriaOcorrenciaService.query).toHaveBeenCalled();
      expect(categoriaOcorrenciaService.addCategoriaOcorrenciaToCollectionIfMissing).toHaveBeenCalledWith(
        categoriaOcorrenciaCollection,
        ...additionalCategoriaOcorrencias.map(expect.objectContaining)
      );
      expect(comp.categoriaOcorrenciasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const categoriaOcorrencia: ICategoriaOcorrencia = { id: 456 };
      const encaminhar: IDocente = { id: 95801 };
      categoriaOcorrencia.encaminhar = encaminhar;

      const docenteCollection: IDocente[] = [{ id: 83924 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [encaminhar];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ categoriaOcorrencia });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MedidaDisciplinar query and add missing value', () => {
      const categoriaOcorrencia: ICategoriaOcorrencia = { id: 456 };
      const medidaDisciplinar: IMedidaDisciplinar = { id: 59284 };
      categoriaOcorrencia.medidaDisciplinar = medidaDisciplinar;

      const medidaDisciplinarCollection: IMedidaDisciplinar[] = [{ id: 6252 }];
      jest.spyOn(medidaDisciplinarService, 'query').mockReturnValue(of(new HttpResponse({ body: medidaDisciplinarCollection })));
      const additionalMedidaDisciplinars = [medidaDisciplinar];
      const expectedCollection: IMedidaDisciplinar[] = [...additionalMedidaDisciplinars, ...medidaDisciplinarCollection];
      jest.spyOn(medidaDisciplinarService, 'addMedidaDisciplinarToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ categoriaOcorrencia });
      comp.ngOnInit();

      expect(medidaDisciplinarService.query).toHaveBeenCalled();
      expect(medidaDisciplinarService.addMedidaDisciplinarToCollectionIfMissing).toHaveBeenCalledWith(
        medidaDisciplinarCollection,
        ...additionalMedidaDisciplinars.map(expect.objectContaining)
      );
      expect(comp.medidaDisciplinarsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const categoriaOcorrencia: ICategoriaOcorrencia = { id: 456 };
      const referencia: ICategoriaOcorrencia = { id: 34752 };
      categoriaOcorrencia.referencia = referencia;
      const encaminhar: IDocente = { id: 26181 };
      categoriaOcorrencia.encaminhar = encaminhar;
      const medidaDisciplinar: IMedidaDisciplinar = { id: 73474 };
      categoriaOcorrencia.medidaDisciplinar = medidaDisciplinar;

      activatedRoute.data = of({ categoriaOcorrencia });
      comp.ngOnInit();

      expect(comp.categoriaOcorrenciasSharedCollection).toContain(referencia);
      expect(comp.docentesSharedCollection).toContain(encaminhar);
      expect(comp.medidaDisciplinarsSharedCollection).toContain(medidaDisciplinar);
      expect(comp.categoriaOcorrencia).toEqual(categoriaOcorrencia);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaOcorrencia>>();
      const categoriaOcorrencia = { id: 123 };
      jest.spyOn(categoriaOcorrenciaFormService, 'getCategoriaOcorrencia').mockReturnValue(categoriaOcorrencia);
      jest.spyOn(categoriaOcorrenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaOcorrencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaOcorrencia }));
      saveSubject.complete();

      // THEN
      expect(categoriaOcorrenciaFormService.getCategoriaOcorrencia).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoriaOcorrenciaService.update).toHaveBeenCalledWith(expect.objectContaining(categoriaOcorrencia));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaOcorrencia>>();
      const categoriaOcorrencia = { id: 123 };
      jest.spyOn(categoriaOcorrenciaFormService, 'getCategoriaOcorrencia').mockReturnValue({ id: null });
      jest.spyOn(categoriaOcorrenciaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaOcorrencia: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriaOcorrencia }));
      saveSubject.complete();

      // THEN
      expect(categoriaOcorrenciaFormService.getCategoriaOcorrencia).toHaveBeenCalled();
      expect(categoriaOcorrenciaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriaOcorrencia>>();
      const categoriaOcorrencia = { id: 123 };
      jest.spyOn(categoriaOcorrenciaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriaOcorrencia });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoriaOcorrenciaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategoriaOcorrencia', () => {
      it('Should forward to categoriaOcorrenciaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoriaOcorrenciaService, 'compareCategoriaOcorrencia');
        comp.compareCategoriaOcorrencia(entity, entity2);
        expect(categoriaOcorrenciaService.compareCategoriaOcorrencia).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDocente', () => {
      it('Should forward to docenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(docenteService, 'compareDocente');
        comp.compareDocente(entity, entity2);
        expect(docenteService.compareDocente).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMedidaDisciplinar', () => {
      it('Should forward to medidaDisciplinarService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(medidaDisciplinarService, 'compareMedidaDisciplinar');
        comp.compareMedidaDisciplinar(entity, entity2);
        expect(medidaDisciplinarService.compareMedidaDisciplinar).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
