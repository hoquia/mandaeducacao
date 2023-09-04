import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TurmaFormService } from './turma-form.service';
import { TurmaService } from '../service/turma.service';
import { ITurma } from '../turma.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPlanoCurricular } from 'app/entities/plano-curricular/plano-curricular.model';
import { PlanoCurricularService } from 'app/entities/plano-curricular/service/plano-curricular.service';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';

import { TurmaUpdateComponent } from './turma-update.component';

describe('Turma Management Update Component', () => {
  let comp: TurmaUpdateComponent;
  let fixture: ComponentFixture<TurmaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let turmaFormService: TurmaFormService;
  let turmaService: TurmaService;
  let userService: UserService;
  let planoCurricularService: PlanoCurricularService;
  let turnoService: TurnoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TurmaUpdateComponent],
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
      .overrideTemplate(TurmaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TurmaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    turmaFormService = TestBed.inject(TurmaFormService);
    turmaService = TestBed.inject(TurmaService);
    userService = TestBed.inject(UserService);
    planoCurricularService = TestBed.inject(PlanoCurricularService);
    turnoService = TestBed.inject(TurnoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Turma query and add missing value', () => {
      const turma: ITurma = { id: 456 };
      const referencia: ITurma = { id: 88859 };
      turma.referencia = referencia;

      const turmaCollection: ITurma[] = [{ id: 41984 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [referencia];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const turma: ITurma = { id: 456 };
      const utilizador: IUser = { id: 4182 };
      turma.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 19263 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoCurricular query and add missing value', () => {
      const turma: ITurma = { id: 456 };
      const planoCurricular: IPlanoCurricular = { id: 48621 };
      turma.planoCurricular = planoCurricular;

      const planoCurricularCollection: IPlanoCurricular[] = [{ id: 37553 }];
      jest.spyOn(planoCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: planoCurricularCollection })));
      const additionalPlanoCurriculars = [planoCurricular];
      const expectedCollection: IPlanoCurricular[] = [...additionalPlanoCurriculars, ...planoCurricularCollection];
      jest.spyOn(planoCurricularService, 'addPlanoCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      expect(planoCurricularService.query).toHaveBeenCalled();
      expect(planoCurricularService.addPlanoCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        planoCurricularCollection,
        ...additionalPlanoCurriculars.map(expect.objectContaining)
      );
      expect(comp.planoCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turno query and add missing value', () => {
      const turma: ITurma = { id: 456 };
      const turno: ITurno = { id: 54520 };
      turma.turno = turno;

      const turnoCollection: ITurno[] = [{ id: 20254 }];
      jest.spyOn(turnoService, 'query').mockReturnValue(of(new HttpResponse({ body: turnoCollection })));
      const additionalTurnos = [turno];
      const expectedCollection: ITurno[] = [...additionalTurnos, ...turnoCollection];
      jest.spyOn(turnoService, 'addTurnoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      expect(turnoService.query).toHaveBeenCalled();
      expect(turnoService.addTurnoToCollectionIfMissing).toHaveBeenCalledWith(
        turnoCollection,
        ...additionalTurnos.map(expect.objectContaining)
      );
      expect(comp.turnosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const turma: ITurma = { id: 456 };
      const referencia: ITurma = { id: 59562 };
      turma.referencia = referencia;
      const utilizador: IUser = { id: 74036 };
      turma.utilizador = utilizador;
      const planoCurricular: IPlanoCurricular = { id: 23859 };
      turma.planoCurricular = planoCurricular;
      const turno: ITurno = { id: 70328 };
      turma.turno = turno;

      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      expect(comp.turmasSharedCollection).toContain(referencia);
      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.planoCurricularsSharedCollection).toContain(planoCurricular);
      expect(comp.turnosSharedCollection).toContain(turno);
      expect(comp.turma).toEqual(turma);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITurma>>();
      const turma = { id: 123 };
      jest.spyOn(turmaFormService, 'getTurma').mockReturnValue(turma);
      jest.spyOn(turmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: turma }));
      saveSubject.complete();

      // THEN
      expect(turmaFormService.getTurma).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(turmaService.update).toHaveBeenCalledWith(expect.objectContaining(turma));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITurma>>();
      const turma = { id: 123 };
      jest.spyOn(turmaFormService, 'getTurma').mockReturnValue({ id: null });
      jest.spyOn(turmaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turma: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: turma }));
      saveSubject.complete();

      // THEN
      expect(turmaFormService.getTurma).toHaveBeenCalled();
      expect(turmaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITurma>>();
      const turma = { id: 123 };
      jest.spyOn(turmaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turma });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(turmaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTurma', () => {
      it('Should forward to turmaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turmaService, 'compareTurma');
        comp.compareTurma(entity, entity2);
        expect(turmaService.compareTurma).toHaveBeenCalledWith(entity, entity2);
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

    describe('comparePlanoCurricular', () => {
      it('Should forward to planoCurricularService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoCurricularService, 'comparePlanoCurricular');
        comp.comparePlanoCurricular(entity, entity2);
        expect(planoCurricularService.comparePlanoCurricular).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTurno', () => {
      it('Should forward to turnoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(turnoService, 'compareTurno');
        comp.compareTurno(entity, entity2);
        expect(turnoService.compareTurno).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
