import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HorarioFormService } from './horario-form.service';
import { HorarioService } from '../service/horario.service';
import { IHorario } from '../horario.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IPeriodoHorario } from 'app/entities/periodo-horario/periodo-horario.model';
import { PeriodoHorarioService } from 'app/entities/periodo-horario/service/periodo-horario.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';

import { HorarioUpdateComponent } from './horario-update.component';

describe('Horario Management Update Component', () => {
  let comp: HorarioUpdateComponent;
  let fixture: ComponentFixture<HorarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let horarioFormService: HorarioFormService;
  let horarioService: HorarioService;
  let userService: UserService;
  let turmaService: TurmaService;
  let periodoHorarioService: PeriodoHorarioService;
  let docenteService: DocenteService;
  let disciplinaCurricularService: DisciplinaCurricularService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HorarioUpdateComponent],
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
      .overrideTemplate(HorarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HorarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    horarioFormService = TestBed.inject(HorarioFormService);
    horarioService = TestBed.inject(HorarioService);
    userService = TestBed.inject(UserService);
    turmaService = TestBed.inject(TurmaService);
    periodoHorarioService = TestBed.inject(PeriodoHorarioService);
    docenteService = TestBed.inject(DocenteService);
    disciplinaCurricularService = TestBed.inject(DisciplinaCurricularService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Horario query and add missing value', () => {
      const horario: IHorario = { id: 456 };
      const referencia: IHorario = { id: 75047 };
      horario.referencia = referencia;

      const horarioCollection: IHorario[] = [{ id: 0 }];
      jest.spyOn(horarioService, 'query').mockReturnValue(of(new HttpResponse({ body: horarioCollection })));
      const additionalHorarios = [referencia];
      const expectedCollection: IHorario[] = [...additionalHorarios, ...horarioCollection];
      jest.spyOn(horarioService, 'addHorarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      expect(horarioService.query).toHaveBeenCalled();
      expect(horarioService.addHorarioToCollectionIfMissing).toHaveBeenCalledWith(
        horarioCollection,
        ...additionalHorarios.map(expect.objectContaining)
      );
      expect(comp.horariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const horario: IHorario = { id: 456 };
      const utilizador: IUser = { id: 43495 };
      horario.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 9028 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const horario: IHorario = { id: 456 };
      const turma: ITurma = { id: 74728 };
      horario.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 37391 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PeriodoHorario query and add missing value', () => {
      const horario: IHorario = { id: 456 };
      const periodo: IPeriodoHorario = { id: 48185 };
      horario.periodo = periodo;

      const periodoHorarioCollection: IPeriodoHorario[] = [{ id: 3132 }];
      jest.spyOn(periodoHorarioService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoHorarioCollection })));
      const additionalPeriodoHorarios = [periodo];
      const expectedCollection: IPeriodoHorario[] = [...additionalPeriodoHorarios, ...periodoHorarioCollection];
      jest.spyOn(periodoHorarioService, 'addPeriodoHorarioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      expect(periodoHorarioService.query).toHaveBeenCalled();
      expect(periodoHorarioService.addPeriodoHorarioToCollectionIfMissing).toHaveBeenCalledWith(
        periodoHorarioCollection,
        ...additionalPeriodoHorarios.map(expect.objectContaining)
      );
      expect(comp.periodoHorariosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const horario: IHorario = { id: 456 };
      const docente: IDocente = { id: 95665 };
      horario.docente = docente;

      const docenteCollection: IDocente[] = [{ id: 56584 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [docente];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DisciplinaCurricular query and add missing value', () => {
      const horario: IHorario = { id: 456 };
      const disciplinaCurricular: IDisciplinaCurricular = { id: 95809 };
      horario.disciplinaCurricular = disciplinaCurricular;

      const disciplinaCurricularCollection: IDisciplinaCurricular[] = [{ id: 24934 }];
      jest.spyOn(disciplinaCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCurricularCollection })));
      const additionalDisciplinaCurriculars = [disciplinaCurricular];
      const expectedCollection: IDisciplinaCurricular[] = [...additionalDisciplinaCurriculars, ...disciplinaCurricularCollection];
      jest.spyOn(disciplinaCurricularService, 'addDisciplinaCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      expect(disciplinaCurricularService.query).toHaveBeenCalled();
      expect(disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCurricularCollection,
        ...additionalDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.disciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const horario: IHorario = { id: 456 };
      const referencia: IHorario = { id: 12035 };
      horario.referencia = referencia;
      const utilizador: IUser = { id: 42226 };
      horario.utilizador = utilizador;
      const turma: ITurma = { id: 81366 };
      horario.turma = turma;
      const periodo: IPeriodoHorario = { id: 68229 };
      horario.periodo = periodo;
      const docente: IDocente = { id: 74316 };
      horario.docente = docente;
      const disciplinaCurricular: IDisciplinaCurricular = { id: 19730 };
      horario.disciplinaCurricular = disciplinaCurricular;

      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      expect(comp.horariosSharedCollection).toContain(referencia);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.periodoHorariosSharedCollection).toContain(periodo);
      expect(comp.docentesSharedCollection).toContain(docente);
      expect(comp.disciplinaCurricularsSharedCollection).toContain(disciplinaCurricular);
      expect(comp.horario).toEqual(horario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHorario>>();
      const horario = { id: 123 };
      jest.spyOn(horarioFormService, 'getHorario').mockReturnValue(horario);
      jest.spyOn(horarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: horario }));
      saveSubject.complete();

      // THEN
      expect(horarioFormService.getHorario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(horarioService.update).toHaveBeenCalledWith(expect.objectContaining(horario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHorario>>();
      const horario = { id: 123 };
      jest.spyOn(horarioFormService, 'getHorario').mockReturnValue({ id: null });
      jest.spyOn(horarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ horario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: horario }));
      saveSubject.complete();

      // THEN
      expect(horarioFormService.getHorario).toHaveBeenCalled();
      expect(horarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHorario>>();
      const horario = { id: 123 };
      jest.spyOn(horarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ horario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(horarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHorario', () => {
      it('Should forward to horarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(horarioService, 'compareHorario');
        comp.compareHorario(entity, entity2);
        expect(horarioService.compareHorario).toHaveBeenCalledWith(entity, entity2);
      });
    });

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

    describe('comparePeriodoHorario', () => {
      it('Should forward to periodoHorarioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(periodoHorarioService, 'comparePeriodoHorario');
        comp.comparePeriodoHorario(entity, entity2);
        expect(periodoHorarioService.comparePeriodoHorario).toHaveBeenCalledWith(entity, entity2);
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
  });
});
