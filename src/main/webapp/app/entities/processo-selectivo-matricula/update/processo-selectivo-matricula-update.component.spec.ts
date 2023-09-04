import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProcessoSelectivoMatriculaFormService } from './processo-selectivo-matricula-form.service';
import { ProcessoSelectivoMatriculaService } from '../service/processo-selectivo-matricula.service';
import { IProcessoSelectivoMatricula } from '../processo-selectivo-matricula.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

import { ProcessoSelectivoMatriculaUpdateComponent } from './processo-selectivo-matricula-update.component';

describe('ProcessoSelectivoMatricula Management Update Component', () => {
  let comp: ProcessoSelectivoMatriculaUpdateComponent;
  let fixture: ComponentFixture<ProcessoSelectivoMatriculaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let processoSelectivoMatriculaFormService: ProcessoSelectivoMatriculaFormService;
  let processoSelectivoMatriculaService: ProcessoSelectivoMatriculaService;
  let userService: UserService;
  let turmaService: TurmaService;
  let discenteService: DiscenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProcessoSelectivoMatriculaUpdateComponent],
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
      .overrideTemplate(ProcessoSelectivoMatriculaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProcessoSelectivoMatriculaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    processoSelectivoMatriculaFormService = TestBed.inject(ProcessoSelectivoMatriculaFormService);
    processoSelectivoMatriculaService = TestBed.inject(ProcessoSelectivoMatriculaService);
    userService = TestBed.inject(UserService);
    turmaService = TestBed.inject(TurmaService);
    discenteService = TestBed.inject(DiscenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const processoSelectivoMatricula: IProcessoSelectivoMatricula = { id: 456 };
      const utilizador: IUser = { id: 98304 };
      processoSelectivoMatricula.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 41156 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ processoSelectivoMatricula });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const processoSelectivoMatricula: IProcessoSelectivoMatricula = { id: 456 };
      const turma: ITurma = { id: 71071 };
      processoSelectivoMatricula.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 59161 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ processoSelectivoMatricula });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Discente query and add missing value', () => {
      const processoSelectivoMatricula: IProcessoSelectivoMatricula = { id: 456 };
      const discente: IDiscente = { id: 42470 };
      processoSelectivoMatricula.discente = discente;

      const discenteCollection: IDiscente[] = [{ id: 81554 }];
      jest.spyOn(discenteService, 'query').mockReturnValue(of(new HttpResponse({ body: discenteCollection })));
      const additionalDiscentes = [discente];
      const expectedCollection: IDiscente[] = [...additionalDiscentes, ...discenteCollection];
      jest.spyOn(discenteService, 'addDiscenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ processoSelectivoMatricula });
      comp.ngOnInit();

      expect(discenteService.query).toHaveBeenCalled();
      expect(discenteService.addDiscenteToCollectionIfMissing).toHaveBeenCalledWith(
        discenteCollection,
        ...additionalDiscentes.map(expect.objectContaining)
      );
      expect(comp.discentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const processoSelectivoMatricula: IProcessoSelectivoMatricula = { id: 456 };
      const utilizador: IUser = { id: 74591 };
      processoSelectivoMatricula.utilizador = utilizador;
      const turma: ITurma = { id: 33656 };
      processoSelectivoMatricula.turma = turma;
      const discente: IDiscente = { id: 36571 };
      processoSelectivoMatricula.discente = discente;

      activatedRoute.data = of({ processoSelectivoMatricula });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.discentesSharedCollection).toContain(discente);
      expect(comp.processoSelectivoMatricula).toEqual(processoSelectivoMatricula);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcessoSelectivoMatricula>>();
      const processoSelectivoMatricula = { id: 123 };
      jest.spyOn(processoSelectivoMatriculaFormService, 'getProcessoSelectivoMatricula').mockReturnValue(processoSelectivoMatricula);
      jest.spyOn(processoSelectivoMatriculaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ processoSelectivoMatricula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: processoSelectivoMatricula }));
      saveSubject.complete();

      // THEN
      expect(processoSelectivoMatriculaFormService.getProcessoSelectivoMatricula).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(processoSelectivoMatriculaService.update).toHaveBeenCalledWith(expect.objectContaining(processoSelectivoMatricula));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcessoSelectivoMatricula>>();
      const processoSelectivoMatricula = { id: 123 };
      jest.spyOn(processoSelectivoMatriculaFormService, 'getProcessoSelectivoMatricula').mockReturnValue({ id: null });
      jest.spyOn(processoSelectivoMatriculaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ processoSelectivoMatricula: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: processoSelectivoMatricula }));
      saveSubject.complete();

      // THEN
      expect(processoSelectivoMatriculaFormService.getProcessoSelectivoMatricula).toHaveBeenCalled();
      expect(processoSelectivoMatriculaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProcessoSelectivoMatricula>>();
      const processoSelectivoMatricula = { id: 123 };
      jest.spyOn(processoSelectivoMatriculaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ processoSelectivoMatricula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(processoSelectivoMatriculaService.update).toHaveBeenCalled();
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
  });
});
