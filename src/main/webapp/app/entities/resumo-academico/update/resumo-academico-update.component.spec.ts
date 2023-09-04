import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResumoAcademicoFormService } from './resumo-academico-form.service';
import { ResumoAcademicoService } from '../service/resumo-academico.service';
import { IResumoAcademico } from '../resumo-academico.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from 'app/entities/estado-disciplina-curricular/service/estado-disciplina-curricular.service';

import { ResumoAcademicoUpdateComponent } from './resumo-academico-update.component';

describe('ResumoAcademico Management Update Component', () => {
  let comp: ResumoAcademicoUpdateComponent;
  let fixture: ComponentFixture<ResumoAcademicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let resumoAcademicoFormService: ResumoAcademicoFormService;
  let resumoAcademicoService: ResumoAcademicoService;
  let userService: UserService;
  let turmaService: TurmaService;
  let discenteService: DiscenteService;
  let estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResumoAcademicoUpdateComponent],
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
      .overrideTemplate(ResumoAcademicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumoAcademicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    resumoAcademicoFormService = TestBed.inject(ResumoAcademicoFormService);
    resumoAcademicoService = TestBed.inject(ResumoAcademicoService);
    userService = TestBed.inject(UserService);
    turmaService = TestBed.inject(TurmaService);
    discenteService = TestBed.inject(DiscenteService);
    estadoDisciplinaCurricularService = TestBed.inject(EstadoDisciplinaCurricularService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const resumoAcademico: IResumoAcademico = { id: 456 };
      const utilizador: IUser = { id: 59506 };
      resumoAcademico.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 63607 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumoAcademico });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const resumoAcademico: IResumoAcademico = { id: 456 };
      const ultimaTurmaMatriculada: ITurma = { id: 86990 };
      resumoAcademico.ultimaTurmaMatriculada = ultimaTurmaMatriculada;

      const turmaCollection: ITurma[] = [{ id: 33319 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [ultimaTurmaMatriculada];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumoAcademico });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Discente query and add missing value', () => {
      const resumoAcademico: IResumoAcademico = { id: 456 };
      const discente: IDiscente = { id: 19606 };
      resumoAcademico.discente = discente;

      const discenteCollection: IDiscente[] = [{ id: 994 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [discente];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumoAcademico });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EstadoDisciplinaCurricular query and add missing value', () => {
      const resumoAcademico: IResumoAcademico = { id: 456 };
      const situacao: IEstadoDisciplinaCurricular = { id: 13148 };
      resumoAcademico.situacao = situacao;

      const estadoDisciplinaCurricularCollection: IEstadoDisciplinaCurricular[] = [{ id: 59579 }];
      jest
        .spyOn(estadoDisciplinaCurricularService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: estadoDisciplinaCurricularCollection })));
      const additionalEstadoDisciplinaCurriculars = [situacao];
      const expectedCollection: IEstadoDisciplinaCurricular[] = [
        ...additionalEstadoDisciplinaCurriculars,
        ...estadoDisciplinaCurricularCollection,
      ];
      jest
        .spyOn(estadoDisciplinaCurricularService, 'addEstadoDisciplinaCurricularToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ resumoAcademico });
      comp.ngOnInit();

      expect(estadoDisciplinaCurricularService.query).toHaveBeenCalled();
      expect(estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        estadoDisciplinaCurricularCollection,
        ...additionalEstadoDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.estadoDisciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const resumoAcademico: IResumoAcademico = { id: 456 };
      const utilizador: IUser = { id: 8237 };
      resumoAcademico.utilizador = utilizador;
      const ultimaTurmaMatriculada: ITurma = { id: 65977 };
      resumoAcademico.ultimaTurmaMatriculada = ultimaTurmaMatriculada;
      const discente: IDiscente = { id: 53924 };
      resumoAcademico.discente = discente;
      const situacao: IEstadoDisciplinaCurricular = { id: 27478 };
      resumoAcademico.situacao = situacao;

      activatedRoute.data = of({ resumoAcademico });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.turmasSharedCollection).toContain(ultimaTurmaMatriculada);
      expect(comp.discentesSharedCollection).toContain(discente);
      expect(comp.estadoDisciplinaCurricularsSharedCollection).toContain(situacao);
      expect(comp.resumoAcademico).toEqual(resumoAcademico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResumoAcademico>>();
      const resumoAcademico = { id: 123 };
      jest.spyOn(resumoAcademicoFormService, 'getResumoAcademico').mockReturnValue(resumoAcademico);
      jest.spyOn(resumoAcademicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumoAcademico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumoAcademico }));
      saveSubject.complete();

      // THEN
      expect(resumoAcademicoFormService.getResumoAcademico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(resumoAcademicoService.update).toHaveBeenCalledWith(expect.objectContaining(resumoAcademico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResumoAcademico>>();
      const resumoAcademico = { id: 123 };
      jest.spyOn(resumoAcademicoFormService, 'getResumoAcademico').mockReturnValue({ id: null });
      jest.spyOn(resumoAcademicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumoAcademico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: resumoAcademico }));
      saveSubject.complete();

      // THEN
      expect(resumoAcademicoFormService.getResumoAcademico).toHaveBeenCalled();
      expect(resumoAcademicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResumoAcademico>>();
      const resumoAcademico = { id: 123 };
      jest.spyOn(resumoAcademicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ resumoAcademico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(resumoAcademicoService.update).toHaveBeenCalled();
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

    describe('compareDiscente', () => {
      it('Should forward to discenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(discenteService, 'compareDiscente');
        comp.compareDiscente(entity, entity2);
        expect(discenteService.compareDiscente).toHaveBeenCalledWith(entity, entity2);
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
