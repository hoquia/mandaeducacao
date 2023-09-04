import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DocenteFormService } from './docente-form.service';
import { DocenteService } from '../service/docente.service';
import { IDocente } from '../docente.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IResponsavelTurno } from 'app/entities/responsavel-turno/responsavel-turno.model';
import { ResponsavelTurnoService } from 'app/entities/responsavel-turno/service/responsavel-turno.service';
import { IResponsavelAreaFormacao } from 'app/entities/responsavel-area-formacao/responsavel-area-formacao.model';
import { ResponsavelAreaFormacaoService } from 'app/entities/responsavel-area-formacao/service/responsavel-area-formacao.service';
import { IResponsavelCurso } from 'app/entities/responsavel-curso/responsavel-curso.model';
import { ResponsavelCursoService } from 'app/entities/responsavel-curso/service/responsavel-curso.service';
import { IResponsavelDisciplina } from 'app/entities/responsavel-disciplina/responsavel-disciplina.model';
import { ResponsavelDisciplinaService } from 'app/entities/responsavel-disciplina/service/responsavel-disciplina.service';
import { IResponsavelTurma } from 'app/entities/responsavel-turma/responsavel-turma.model';
import { ResponsavelTurmaService } from 'app/entities/responsavel-turma/service/responsavel-turma.service';

import { DocenteUpdateComponent } from './docente-update.component';

describe('Docente Management Update Component', () => {
  let comp: DocenteUpdateComponent;
  let fixture: ComponentFixture<DocenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let docenteFormService: DocenteFormService;
  let docenteService: DocenteService;
  let lookupItemService: LookupItemService;
  let responsavelTurnoService: ResponsavelTurnoService;
  let responsavelAreaFormacaoService: ResponsavelAreaFormacaoService;
  let responsavelCursoService: ResponsavelCursoService;
  let responsavelDisciplinaService: ResponsavelDisciplinaService;
  let responsavelTurmaService: ResponsavelTurmaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DocenteUpdateComponent],
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
      .overrideTemplate(DocenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DocenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    docenteFormService = TestBed.inject(DocenteFormService);
    docenteService = TestBed.inject(DocenteService);
    lookupItemService = TestBed.inject(LookupItemService);
    responsavelTurnoService = TestBed.inject(ResponsavelTurnoService);
    responsavelAreaFormacaoService = TestBed.inject(ResponsavelAreaFormacaoService);
    responsavelCursoService = TestBed.inject(ResponsavelCursoService);
    responsavelDisciplinaService = TestBed.inject(ResponsavelDisciplinaService);
    responsavelTurmaService = TestBed.inject(ResponsavelTurmaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LookupItem query and add missing value', () => {
      const docente: IDocente = { id: 456 };
      const nacionalidade: ILookupItem = { id: 62330 };
      docente.nacionalidade = nacionalidade;
      const naturalidade: ILookupItem = { id: 80994 };
      docente.naturalidade = naturalidade;
      const tipoDocumento: ILookupItem = { id: 52881 };
      docente.tipoDocumento = tipoDocumento;
      const grauAcademico: ILookupItem = { id: 70885 };
      docente.grauAcademico = grauAcademico;
      const categoriaProfissional: ILookupItem = { id: 99671 };
      docente.categoriaProfissional = categoriaProfissional;
      const unidadeOrganica: ILookupItem = { id: 56976 };
      docente.unidadeOrganica = unidadeOrganica;
      const estadoCivil: ILookupItem = { id: 44542 };
      docente.estadoCivil = estadoCivil;

      const lookupItemCollection: ILookupItem[] = [{ id: 16657 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [
        nacionalidade,
        naturalidade,
        tipoDocumento,
        grauAcademico,
        categoriaProfissional,
        unidadeOrganica,
        estadoCivil,
      ];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelTurno query and add missing value', () => {
      const docente: IDocente = { id: 456 };
      const responsavelTurno: IResponsavelTurno = { id: 68819 };
      docente.responsavelTurno = responsavelTurno;

      const responsavelTurnoCollection: IResponsavelTurno[] = [{ id: 13669 }];
      jest.spyOn(responsavelTurnoService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelTurnoCollection })));
      const additionalResponsavelTurnos = [responsavelTurno];
      const expectedCollection: IResponsavelTurno[] = [...additionalResponsavelTurnos, ...responsavelTurnoCollection];
      jest.spyOn(responsavelTurnoService, 'addResponsavelTurnoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      expect(responsavelTurnoService.query).toHaveBeenCalled();
      expect(responsavelTurnoService.addResponsavelTurnoToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelTurnoCollection,
        ...additionalResponsavelTurnos.map(expect.objectContaining)
      );
      expect(comp.responsavelTurnosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelAreaFormacao query and add missing value', () => {
      const docente: IDocente = { id: 456 };
      const responsavelAreaFormacao: IResponsavelAreaFormacao = { id: 81409 };
      docente.responsavelAreaFormacao = responsavelAreaFormacao;

      const responsavelAreaFormacaoCollection: IResponsavelAreaFormacao[] = [{ id: 62561 }];
      jest
        .spyOn(responsavelAreaFormacaoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: responsavelAreaFormacaoCollection })));
      const additionalResponsavelAreaFormacaos = [responsavelAreaFormacao];
      const expectedCollection: IResponsavelAreaFormacao[] = [...additionalResponsavelAreaFormacaos, ...responsavelAreaFormacaoCollection];
      jest.spyOn(responsavelAreaFormacaoService, 'addResponsavelAreaFormacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      expect(responsavelAreaFormacaoService.query).toHaveBeenCalled();
      expect(responsavelAreaFormacaoService.addResponsavelAreaFormacaoToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelAreaFormacaoCollection,
        ...additionalResponsavelAreaFormacaos.map(expect.objectContaining)
      );
      expect(comp.responsavelAreaFormacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelCurso query and add missing value', () => {
      const docente: IDocente = { id: 456 };
      const responsavelCurso: IResponsavelCurso = { id: 16813 };
      docente.responsavelCurso = responsavelCurso;

      const responsavelCursoCollection: IResponsavelCurso[] = [{ id: 12163 }];
      jest.spyOn(responsavelCursoService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelCursoCollection })));
      const additionalResponsavelCursos = [responsavelCurso];
      const expectedCollection: IResponsavelCurso[] = [...additionalResponsavelCursos, ...responsavelCursoCollection];
      jest.spyOn(responsavelCursoService, 'addResponsavelCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      expect(responsavelCursoService.query).toHaveBeenCalled();
      expect(responsavelCursoService.addResponsavelCursoToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelCursoCollection,
        ...additionalResponsavelCursos.map(expect.objectContaining)
      );
      expect(comp.responsavelCursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelDisciplina query and add missing value', () => {
      const docente: IDocente = { id: 456 };
      const responsavelDisciplina: IResponsavelDisciplina = { id: 60882 };
      docente.responsavelDisciplina = responsavelDisciplina;

      const responsavelDisciplinaCollection: IResponsavelDisciplina[] = [{ id: 34021 }];
      jest.spyOn(responsavelDisciplinaService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelDisciplinaCollection })));
      const additionalResponsavelDisciplinas = [responsavelDisciplina];
      const expectedCollection: IResponsavelDisciplina[] = [...additionalResponsavelDisciplinas, ...responsavelDisciplinaCollection];
      jest.spyOn(responsavelDisciplinaService, 'addResponsavelDisciplinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      expect(responsavelDisciplinaService.query).toHaveBeenCalled();
      expect(responsavelDisciplinaService.addResponsavelDisciplinaToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelDisciplinaCollection,
        ...additionalResponsavelDisciplinas.map(expect.objectContaining)
      );
      expect(comp.responsavelDisciplinasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ResponsavelTurma query and add missing value', () => {
      const docente: IDocente = { id: 456 };
      const responsavelTurma: IResponsavelTurma = { id: 41370 };
      docente.responsavelTurma = responsavelTurma;

      const responsavelTurmaCollection: IResponsavelTurma[] = [{ id: 52755 }];
      jest.spyOn(responsavelTurmaService, 'query').mockReturnValue(of(new HttpResponse({ body: responsavelTurmaCollection })));
      const additionalResponsavelTurmas = [responsavelTurma];
      const expectedCollection: IResponsavelTurma[] = [...additionalResponsavelTurmas, ...responsavelTurmaCollection];
      jest.spyOn(responsavelTurmaService, 'addResponsavelTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      expect(responsavelTurmaService.query).toHaveBeenCalled();
      expect(responsavelTurmaService.addResponsavelTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        responsavelTurmaCollection,
        ...additionalResponsavelTurmas.map(expect.objectContaining)
      );
      expect(comp.responsavelTurmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const docente: IDocente = { id: 456 };
      const nacionalidade: ILookupItem = { id: 61985 };
      docente.nacionalidade = nacionalidade;
      const naturalidade: ILookupItem = { id: 41068 };
      docente.naturalidade = naturalidade;
      const tipoDocumento: ILookupItem = { id: 55177 };
      docente.tipoDocumento = tipoDocumento;
      const grauAcademico: ILookupItem = { id: 85872 };
      docente.grauAcademico = grauAcademico;
      const categoriaProfissional: ILookupItem = { id: 95282 };
      docente.categoriaProfissional = categoriaProfissional;
      const unidadeOrganica: ILookupItem = { id: 47772 };
      docente.unidadeOrganica = unidadeOrganica;
      const estadoCivil: ILookupItem = { id: 65031 };
      docente.estadoCivil = estadoCivil;
      const responsavelTurno: IResponsavelTurno = { id: 72573 };
      docente.responsavelTurno = responsavelTurno;
      const responsavelAreaFormacao: IResponsavelAreaFormacao = { id: 28551 };
      docente.responsavelAreaFormacao = responsavelAreaFormacao;
      const responsavelCurso: IResponsavelCurso = { id: 64345 };
      docente.responsavelCurso = responsavelCurso;
      const responsavelDisciplina: IResponsavelDisciplina = { id: 70221 };
      docente.responsavelDisciplina = responsavelDisciplina;
      const responsavelTurma: IResponsavelTurma = { id: 58303 };
      docente.responsavelTurma = responsavelTurma;

      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      expect(comp.lookupItemsSharedCollection).toContain(nacionalidade);
      expect(comp.lookupItemsSharedCollection).toContain(naturalidade);
      expect(comp.lookupItemsSharedCollection).toContain(tipoDocumento);
      expect(comp.lookupItemsSharedCollection).toContain(grauAcademico);
      expect(comp.lookupItemsSharedCollection).toContain(categoriaProfissional);
      expect(comp.lookupItemsSharedCollection).toContain(unidadeOrganica);
      expect(comp.lookupItemsSharedCollection).toContain(estadoCivil);
      expect(comp.responsavelTurnosSharedCollection).toContain(responsavelTurno);
      expect(comp.responsavelAreaFormacaosSharedCollection).toContain(responsavelAreaFormacao);
      expect(comp.responsavelCursosSharedCollection).toContain(responsavelCurso);
      expect(comp.responsavelDisciplinasSharedCollection).toContain(responsavelDisciplina);
      expect(comp.responsavelTurmasSharedCollection).toContain(responsavelTurma);
      expect(comp.docente).toEqual(docente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocente>>();
      const docente = { id: 123 };
      jest.spyOn(docenteFormService, 'getDocente').mockReturnValue(docente);
      jest.spyOn(docenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docente }));
      saveSubject.complete();

      // THEN
      expect(docenteFormService.getDocente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(docenteService.update).toHaveBeenCalledWith(expect.objectContaining(docente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocente>>();
      const docente = { id: 123 };
      jest.spyOn(docenteFormService, 'getDocente').mockReturnValue({ id: null });
      jest.spyOn(docenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: docente }));
      saveSubject.complete();

      // THEN
      expect(docenteFormService.getDocente).toHaveBeenCalled();
      expect(docenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDocente>>();
      const docente = { id: 123 };
      jest.spyOn(docenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ docente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(docenteService.update).toHaveBeenCalled();
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

    describe('compareResponsavelTurno', () => {
      it('Should forward to responsavelTurnoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelTurnoService, 'compareResponsavelTurno');
        comp.compareResponsavelTurno(entity, entity2);
        expect(responsavelTurnoService.compareResponsavelTurno).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelAreaFormacao', () => {
      it('Should forward to responsavelAreaFormacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelAreaFormacaoService, 'compareResponsavelAreaFormacao');
        comp.compareResponsavelAreaFormacao(entity, entity2);
        expect(responsavelAreaFormacaoService.compareResponsavelAreaFormacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelCurso', () => {
      it('Should forward to responsavelCursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelCursoService, 'compareResponsavelCurso');
        comp.compareResponsavelCurso(entity, entity2);
        expect(responsavelCursoService.compareResponsavelCurso).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelDisciplina', () => {
      it('Should forward to responsavelDisciplinaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelDisciplinaService, 'compareResponsavelDisciplina');
        comp.compareResponsavelDisciplina(entity, entity2);
        expect(responsavelDisciplinaService.compareResponsavelDisciplina).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareResponsavelTurma', () => {
      it('Should forward to responsavelTurmaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(responsavelTurmaService, 'compareResponsavelTurma');
        comp.compareResponsavelTurma(entity, entity2);
        expect(responsavelTurmaService.compareResponsavelTurma).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
