import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NotasPeriodicaDisciplinaFormService } from './notas-periodica-disciplina-form.service';
import { NotasPeriodicaDisciplinaService } from '../service/notas-periodica-disciplina.service';
import { INotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from 'app/entities/estado-disciplina-curricular/service/estado-disciplina-curricular.service';

import { NotasPeriodicaDisciplinaUpdateComponent } from './notas-periodica-disciplina-update.component';

describe('NotasPeriodicaDisciplina Management Update Component', () => {
  let comp: NotasPeriodicaDisciplinaUpdateComponent;
  let fixture: ComponentFixture<NotasPeriodicaDisciplinaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notasPeriodicaDisciplinaFormService: NotasPeriodicaDisciplinaFormService;
  let notasPeriodicaDisciplinaService: NotasPeriodicaDisciplinaService;
  let userService: UserService;
  let turmaService: TurmaService;
  let docenteService: DocenteService;
  let disciplinaCurricularService: DisciplinaCurricularService;
  let matriculaService: MatriculaService;
  let estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NotasPeriodicaDisciplinaUpdateComponent],
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
      .overrideTemplate(NotasPeriodicaDisciplinaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotasPeriodicaDisciplinaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notasPeriodicaDisciplinaFormService = TestBed.inject(NotasPeriodicaDisciplinaFormService);
    notasPeriodicaDisciplinaService = TestBed.inject(NotasPeriodicaDisciplinaService);
    userService = TestBed.inject(UserService);
    turmaService = TestBed.inject(TurmaService);
    docenteService = TestBed.inject(DocenteService);
    disciplinaCurricularService = TestBed.inject(DisciplinaCurricularService);
    matriculaService = TestBed.inject(MatriculaService);
    estadoDisciplinaCurricularService = TestBed.inject(EstadoDisciplinaCurricularService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 456 };
      const utilizador: IUser = { id: 77223 };
      notasPeriodicaDisciplina.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 39463 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 456 };
      const turma: ITurma = { id: 51824 };
      notasPeriodicaDisciplina.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 67793 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 456 };
      const docente: IDocente = { id: 85309 };
      notasPeriodicaDisciplina.docente = docente;

      const docenteCollection: IDocente[] = [{ id: 4950 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [docente];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DisciplinaCurricular query and add missing value', () => {
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 456 };
      const disciplinaCurricular: IDisciplinaCurricular = { id: 67968 };
      notasPeriodicaDisciplina.disciplinaCurricular = disciplinaCurricular;

      const disciplinaCurricularCollection: IDisciplinaCurricular[] = [{ id: 35660 }];
      jest.spyOn(disciplinaCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCurricularCollection })));
      const additionalDisciplinaCurriculars = [disciplinaCurricular];
      const expectedCollection: IDisciplinaCurricular[] = [...additionalDisciplinaCurriculars, ...disciplinaCurricularCollection];
      jest.spyOn(disciplinaCurricularService, 'addDisciplinaCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      expect(disciplinaCurricularService.query).toHaveBeenCalled();
      expect(disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCurricularCollection,
        ...additionalDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.disciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matricula query and add missing value', () => {
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 456 };
      const matricula: IMatricula = { id: 61866 };
      notasPeriodicaDisciplina.matricula = matricula;

      const matriculaCollection: IMatricula[] = [{ id: 94228 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [matricula];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EstadoDisciplinaCurricular query and add missing value', () => {
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 456 };
      const estado: IEstadoDisciplinaCurricular = { id: 51545 };
      notasPeriodicaDisciplina.estado = estado;

      const estadoDisciplinaCurricularCollection: IEstadoDisciplinaCurricular[] = [{ id: 25711 }];
      jest
        .spyOn(estadoDisciplinaCurricularService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: estadoDisciplinaCurricularCollection })));
      const additionalEstadoDisciplinaCurriculars = [estado];
      const expectedCollection: IEstadoDisciplinaCurricular[] = [
        ...additionalEstadoDisciplinaCurriculars,
        ...estadoDisciplinaCurricularCollection,
      ];
      jest
        .spyOn(estadoDisciplinaCurricularService, 'addEstadoDisciplinaCurricularToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      expect(estadoDisciplinaCurricularService.query).toHaveBeenCalled();
      expect(estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        estadoDisciplinaCurricularCollection,
        ...additionalEstadoDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.estadoDisciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const notasPeriodicaDisciplina: INotasPeriodicaDisciplina = { id: 456 };
      const utilizador: IUser = { id: 21362 };
      notasPeriodicaDisciplina.utilizador = utilizador;
      const turma: ITurma = { id: 79461 };
      notasPeriodicaDisciplina.turma = turma;
      const docente: IDocente = { id: 87160 };
      notasPeriodicaDisciplina.docente = docente;
      const disciplinaCurricular: IDisciplinaCurricular = { id: 55141 };
      notasPeriodicaDisciplina.disciplinaCurricular = disciplinaCurricular;
      const matricula: IMatricula = { id: 62634 };
      notasPeriodicaDisciplina.matricula = matricula;
      const estado: IEstadoDisciplinaCurricular = { id: 96134 };
      notasPeriodicaDisciplina.estado = estado;

      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.docentesSharedCollection).toContain(docente);
      expect(comp.disciplinaCurricularsSharedCollection).toContain(disciplinaCurricular);
      expect(comp.matriculasSharedCollection).toContain(matricula);
      expect(comp.estadoDisciplinaCurricularsSharedCollection).toContain(estado);
      expect(comp.notasPeriodicaDisciplina).toEqual(notasPeriodicaDisciplina);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotasPeriodicaDisciplina>>();
      const notasPeriodicaDisciplina = { id: 123 };
      jest.spyOn(notasPeriodicaDisciplinaFormService, 'getNotasPeriodicaDisciplina').mockReturnValue(notasPeriodicaDisciplina);
      jest.spyOn(notasPeriodicaDisciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notasPeriodicaDisciplina }));
      saveSubject.complete();

      // THEN
      expect(notasPeriodicaDisciplinaFormService.getNotasPeriodicaDisciplina).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notasPeriodicaDisciplinaService.update).toHaveBeenCalledWith(expect.objectContaining(notasPeriodicaDisciplina));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotasPeriodicaDisciplina>>();
      const notasPeriodicaDisciplina = { id: 123 };
      jest.spyOn(notasPeriodicaDisciplinaFormService, 'getNotasPeriodicaDisciplina').mockReturnValue({ id: null });
      jest.spyOn(notasPeriodicaDisciplinaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notasPeriodicaDisciplina: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notasPeriodicaDisciplina }));
      saveSubject.complete();

      // THEN
      expect(notasPeriodicaDisciplinaFormService.getNotasPeriodicaDisciplina).toHaveBeenCalled();
      expect(notasPeriodicaDisciplinaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotasPeriodicaDisciplina>>();
      const notasPeriodicaDisciplina = { id: 123 };
      jest.spyOn(notasPeriodicaDisciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notasPeriodicaDisciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notasPeriodicaDisciplinaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTurma', () => {
      it('Should forward to turmaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turmaService, 'compareTurma');
        comp.compareTurma(entity, entity2);
        expect(turmaService.compareTurma).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareDisciplinaCurricular', () => {
      it('Should forward to disciplinaCurricularService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(disciplinaCurricularService, 'compareDisciplinaCurricular');
        comp.compareDisciplinaCurricular(entity, entity2);
        expect(disciplinaCurricularService.compareDisciplinaCurricular).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMatricula', () => {
      it('Should forward to matriculaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(matriculaService, 'compareMatricula');
        comp.compareMatricula(entity, entity2);
        expect(matriculaService.compareMatricula).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEstadoDisciplinaCurricular', () => {
      it('Should forward to estadoDisciplinaCurricularService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(estadoDisciplinaCurricularService, 'compareEstadoDisciplinaCurricular');
        comp.compareEstadoDisciplinaCurricular(entity, entity2);
        expect(estadoDisciplinaCurricularService.compareEstadoDisciplinaCurricular).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
