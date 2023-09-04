import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NotasGeralDisciplinaFormService } from './notas-geral-disciplina-form.service';
import { NotasGeralDisciplinaService } from '../service/notas-geral-disciplina.service';
import { INotasGeralDisciplina } from '../notas-geral-disciplina.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from 'app/entities/estado-disciplina-curricular/service/estado-disciplina-curricular.service';

import { NotasGeralDisciplinaUpdateComponent } from './notas-geral-disciplina-update.component';

describe('NotasGeralDisciplina Management Update Component', () => {
  let comp: NotasGeralDisciplinaUpdateComponent;
  let fixture: ComponentFixture<NotasGeralDisciplinaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notasGeralDisciplinaFormService: NotasGeralDisciplinaFormService;
  let notasGeralDisciplinaService: NotasGeralDisciplinaService;
  let userService: UserService;
  let docenteService: DocenteService;
  let disciplinaCurricularService: DisciplinaCurricularService;
  let matriculaService: MatriculaService;
  let estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NotasGeralDisciplinaUpdateComponent],
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
      .overrideTemplate(NotasGeralDisciplinaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotasGeralDisciplinaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notasGeralDisciplinaFormService = TestBed.inject(NotasGeralDisciplinaFormService);
    notasGeralDisciplinaService = TestBed.inject(NotasGeralDisciplinaService);
    userService = TestBed.inject(UserService);
    docenteService = TestBed.inject(DocenteService);
    disciplinaCurricularService = TestBed.inject(DisciplinaCurricularService);
    matriculaService = TestBed.inject(MatriculaService);
    estadoDisciplinaCurricularService = TestBed.inject(EstadoDisciplinaCurricularService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 456 };
      const utilizador: IUser = { id: 97750 };
      notasGeralDisciplina.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 58756 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 456 };
      const docente: IDocente = { id: 49619 };
      notasGeralDisciplina.docente = docente;

      const docenteCollection: IDocente[] = [{ id: 31414 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [docente];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DisciplinaCurricular query and add missing value', () => {
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 456 };
      const disciplinaCurricular: IDisciplinaCurricular = { id: 31946 };
      notasGeralDisciplina.disciplinaCurricular = disciplinaCurricular;

      const disciplinaCurricularCollection: IDisciplinaCurricular[] = [{ id: 95333 }];
      jest.spyOn(disciplinaCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCurricularCollection })));
      const additionalDisciplinaCurriculars = [disciplinaCurricular];
      const expectedCollection: IDisciplinaCurricular[] = [...additionalDisciplinaCurriculars, ...disciplinaCurricularCollection];
      jest.spyOn(disciplinaCurricularService, 'addDisciplinaCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      expect(disciplinaCurricularService.query).toHaveBeenCalled();
      expect(disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCurricularCollection,
        ...additionalDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.disciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Matricula query and add missing value', () => {
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 456 };
      const matricula: IMatricula = { id: 53919 };
      notasGeralDisciplina.matricula = matricula;

      const matriculaCollection: IMatricula[] = [{ id: 11889 }];
      jest.spyOn(matriculaService, 'query').mockReturnValue(of(new HttpResponse({ body: matriculaCollection })));
      const additionalMatriculas = [matricula];
      const expectedCollection: IMatricula[] = [...additionalMatriculas, ...matriculaCollection];
      jest.spyOn(matriculaService, 'addMatriculaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      expect(matriculaService.query).toHaveBeenCalled();
      expect(matriculaService.addMatriculaToCollectionIfMissing).toHaveBeenCalledWith(
        matriculaCollection,
        ...additionalMatriculas.map(expect.objectContaining)
      );
      expect(comp.matriculasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EstadoDisciplinaCurricular query and add missing value', () => {
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 456 };
      const estado: IEstadoDisciplinaCurricular = { id: 8214 };
      notasGeralDisciplina.estado = estado;

      const estadoDisciplinaCurricularCollection: IEstadoDisciplinaCurricular[] = [{ id: 28747 }];
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

      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      expect(estadoDisciplinaCurricularService.query).toHaveBeenCalled();
      expect(estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        estadoDisciplinaCurricularCollection,
        ...additionalEstadoDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.estadoDisciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const notasGeralDisciplina: INotasGeralDisciplina = { id: 456 };
      const utilizador: IUser = { id: 40986 };
      notasGeralDisciplina.utilizador = utilizador;
      const docente: IDocente = { id: 28720 };
      notasGeralDisciplina.docente = docente;
      const disciplinaCurricular: IDisciplinaCurricular = { id: 80318 };
      notasGeralDisciplina.disciplinaCurricular = disciplinaCurricular;
      const matricula: IMatricula = { id: 8148 };
      notasGeralDisciplina.matricula = matricula;
      const estado: IEstadoDisciplinaCurricular = { id: 76225 };
      notasGeralDisciplina.estado = estado;

      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.docentesSharedCollection).toContain(docente);
      expect(comp.disciplinaCurricularsSharedCollection).toContain(disciplinaCurricular);
      expect(comp.matriculasSharedCollection).toContain(matricula);
      expect(comp.estadoDisciplinaCurricularsSharedCollection).toContain(estado);
      expect(comp.notasGeralDisciplina).toEqual(notasGeralDisciplina);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotasGeralDisciplina>>();
      const notasGeralDisciplina = { id: 123 };
      jest.spyOn(notasGeralDisciplinaFormService, 'getNotasGeralDisciplina').mockReturnValue(notasGeralDisciplina);
      jest.spyOn(notasGeralDisciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notasGeralDisciplina }));
      saveSubject.complete();

      // THEN
      expect(notasGeralDisciplinaFormService.getNotasGeralDisciplina).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notasGeralDisciplinaService.update).toHaveBeenCalledWith(expect.objectContaining(notasGeralDisciplina));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotasGeralDisciplina>>();
      const notasGeralDisciplina = { id: 123 };
      jest.spyOn(notasGeralDisciplinaFormService, 'getNotasGeralDisciplina').mockReturnValue({ id: null });
      jest.spyOn(notasGeralDisciplinaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notasGeralDisciplina: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notasGeralDisciplina }));
      saveSubject.complete();

      // THEN
      expect(notasGeralDisciplinaFormService.getNotasGeralDisciplina).toHaveBeenCalled();
      expect(notasGeralDisciplinaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotasGeralDisciplina>>();
      const notasGeralDisciplina = { id: 123 };
      jest.spyOn(notasGeralDisciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notasGeralDisciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notasGeralDisciplinaService.update).toHaveBeenCalled();
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
