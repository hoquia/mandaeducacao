import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CampoActuacaoDissertacaoFormService } from './campo-actuacao-dissertacao-form.service';
import { CampoActuacaoDissertacaoService } from '../service/campo-actuacao-dissertacao.service';
import { ICampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';

import { CampoActuacaoDissertacaoUpdateComponent } from './campo-actuacao-dissertacao-update.component';

describe('CampoActuacaoDissertacao Management Update Component', () => {
  let comp: CampoActuacaoDissertacaoUpdateComponent;
  let fixture: ComponentFixture<CampoActuacaoDissertacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let campoActuacaoDissertacaoFormService: CampoActuacaoDissertacaoFormService;
  let campoActuacaoDissertacaoService: CampoActuacaoDissertacaoService;
  let cursoService: CursoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CampoActuacaoDissertacaoUpdateComponent],
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
      .overrideTemplate(CampoActuacaoDissertacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CampoActuacaoDissertacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    campoActuacaoDissertacaoFormService = TestBed.inject(CampoActuacaoDissertacaoFormService);
    campoActuacaoDissertacaoService = TestBed.inject(CampoActuacaoDissertacaoService);
    cursoService = TestBed.inject(CursoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Curso query and add missing value', () => {
      const campoActuacaoDissertacao: ICampoActuacaoDissertacao = { id: 456 };
      const cursos: ICurso[] = [{ id: 3971 }];
      campoActuacaoDissertacao.cursos = cursos;

      const cursoCollection: ICurso[] = [{ id: 19795 }];
      jest.spyOn(cursoService, 'query').mockReturnValue(of(new HttpResponse({ body: cursoCollection })));
      const additionalCursos = [...cursos];
      const expectedCollection: ICurso[] = [...additionalCursos, ...cursoCollection];
      jest.spyOn(cursoService, 'addCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ campoActuacaoDissertacao });
      comp.ngOnInit();

      expect(cursoService.query).toHaveBeenCalled();
      expect(cursoService.addCursoToCollectionIfMissing).toHaveBeenCalledWith(
        cursoCollection,
        ...additionalCursos.map(expect.objectContaining)
      );
      expect(comp.cursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const campoActuacaoDissertacao: ICampoActuacaoDissertacao = { id: 456 };
      const cursos: ICurso = { id: 51876 };
      campoActuacaoDissertacao.cursos = [cursos];

      activatedRoute.data = of({ campoActuacaoDissertacao });
      comp.ngOnInit();

      expect(comp.cursosSharedCollection).toContain(cursos);
      expect(comp.campoActuacaoDissertacao).toEqual(campoActuacaoDissertacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICampoActuacaoDissertacao>>();
      const campoActuacaoDissertacao = { id: 123 };
      jest.spyOn(campoActuacaoDissertacaoFormService, 'getCampoActuacaoDissertacao').mockReturnValue(campoActuacaoDissertacao);
      jest.spyOn(campoActuacaoDissertacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ campoActuacaoDissertacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: campoActuacaoDissertacao }));
      saveSubject.complete();

      // THEN
      expect(campoActuacaoDissertacaoFormService.getCampoActuacaoDissertacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(campoActuacaoDissertacaoService.update).toHaveBeenCalledWith(expect.objectContaining(campoActuacaoDissertacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICampoActuacaoDissertacao>>();
      const campoActuacaoDissertacao = { id: 123 };
      jest.spyOn(campoActuacaoDissertacaoFormService, 'getCampoActuacaoDissertacao').mockReturnValue({ id: null });
      jest.spyOn(campoActuacaoDissertacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ campoActuacaoDissertacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: campoActuacaoDissertacao }));
      saveSubject.complete();

      // THEN
      expect(campoActuacaoDissertacaoFormService.getCampoActuacaoDissertacao).toHaveBeenCalled();
      expect(campoActuacaoDissertacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICampoActuacaoDissertacao>>();
      const campoActuacaoDissertacao = { id: 123 };
      jest.spyOn(campoActuacaoDissertacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ campoActuacaoDissertacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(campoActuacaoDissertacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCurso', () => {
      it('Should forward to cursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cursoService, 'compareCurso');
        comp.compareCurso(entity, entity2);
        expect(cursoService.compareCurso).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
