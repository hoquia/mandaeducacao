import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlanoAulaFormService } from './plano-aula-form.service';
import { PlanoAulaService } from '../service/plano-aula.service';
import { IPlanoAula } from '../plano-aula.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';

import { PlanoAulaUpdateComponent } from './plano-aula-update.component';

describe('PlanoAula Management Update Component', () => {
  let comp: PlanoAulaUpdateComponent;
  let fixture: ComponentFixture<PlanoAulaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoAulaFormService: PlanoAulaFormService;
  let planoAulaService: PlanoAulaService;
  let userService: UserService;
  let lookupItemService: LookupItemService;
  let turmaService: TurmaService;
  let docenteService: DocenteService;
  let disciplinaCurricularService: DisciplinaCurricularService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlanoAulaUpdateComponent],
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
      .overrideTemplate(PlanoAulaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoAulaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoAulaFormService = TestBed.inject(PlanoAulaFormService);
    planoAulaService = TestBed.inject(PlanoAulaService);
    userService = TestBed.inject(UserService);
    lookupItemService = TestBed.inject(LookupItemService);
    turmaService = TestBed.inject(TurmaService);
    docenteService = TestBed.inject(DocenteService);
    disciplinaCurricularService = TestBed.inject(DisciplinaCurricularService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const planoAula: IPlanoAula = { id: 456 };
      const utilizador: IUser = { id: 2379 };
      planoAula.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 24740 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LookupItem query and add missing value', () => {
      const planoAula: IPlanoAula = { id: 456 };
      const unidadeTematica: ILookupItem = { id: 4127 };
      planoAula.unidadeTematica = unidadeTematica;
      const subUnidadeTematica: ILookupItem = { id: 94185 };
      planoAula.subUnidadeTematica = subUnidadeTematica;

      const lookupItemCollection: ILookupItem[] = [{ id: 83165 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [unidadeTematica, subUnidadeTematica];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Turma query and add missing value', () => {
      const planoAula: IPlanoAula = { id: 456 };
      const turma: ITurma = { id: 230 };
      planoAula.turma = turma;

      const turmaCollection: ITurma[] = [{ id: 18717 }];
      jest.spyOn(turmaService, 'query').mockReturnValue(of(new HttpResponse({ body: turmaCollection })));
      const additionalTurmas = [turma];
      const expectedCollection: ITurma[] = [...additionalTurmas, ...turmaCollection];
      jest.spyOn(turmaService, 'addTurmaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      expect(turmaService.query).toHaveBeenCalled();
      expect(turmaService.addTurmaToCollectionIfMissing).toHaveBeenCalledWith(
        turmaCollection,
        ...additionalTurmas.map(expect.objectContaining)
      );
      expect(comp.turmasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Docente query and add missing value', () => {
      const planoAula: IPlanoAula = { id: 456 };
      const docente: IDocente = { id: 65262 };
      planoAula.docente = docente;

      const docenteCollection: IDocente[] = [{ id: 9179 }];
      jest.spyOn(docenteService, 'query').mockReturnValue(of(new HttpResponse({ body: docenteCollection })));
      const additionalDocentes = [docente];
      const expectedCollection: IDocente[] = [...additionalDocentes, ...docenteCollection];
      jest.spyOn(docenteService, 'addDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      expect(docenteService.query).toHaveBeenCalled();
      expect(docenteService.addDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        docenteCollection,
        ...additionalDocentes.map(expect.objectContaining)
      );
      expect(comp.docentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DisciplinaCurricular query and add missing value', () => {
      const planoAula: IPlanoAula = { id: 456 };
      const disciplinaCurricular: IDisciplinaCurricular = { id: 34606 };
      planoAula.disciplinaCurricular = disciplinaCurricular;

      const disciplinaCurricularCollection: IDisciplinaCurricular[] = [{ id: 74732 }];
      jest.spyOn(disciplinaCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCurricularCollection })));
      const additionalDisciplinaCurriculars = [disciplinaCurricular];
      const expectedCollection: IDisciplinaCurricular[] = [...additionalDisciplinaCurriculars, ...disciplinaCurricularCollection];
      jest.spyOn(disciplinaCurricularService, 'addDisciplinaCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      expect(disciplinaCurricularService.query).toHaveBeenCalled();
      expect(disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCurricularCollection,
        ...additionalDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.disciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const planoAula: IPlanoAula = { id: 456 };
      const utilizador: IUser = { id: 82307 };
      planoAula.utilizador = utilizador;
      const unidadeTematica: ILookupItem = { id: 5800 };
      planoAula.unidadeTematica = unidadeTematica;
      const subUnidadeTematica: ILookupItem = { id: 30012 };
      planoAula.subUnidadeTematica = subUnidadeTematica;
      const turma: ITurma = { id: 92119 };
      planoAula.turma = turma;
      const docente: IDocente = { id: 89074 };
      planoAula.docente = docente;
      const disciplinaCurricular: IDisciplinaCurricular = { id: 15219 };
      planoAula.disciplinaCurricular = disciplinaCurricular;

      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.lookupItemsSharedCollection).toContain(unidadeTematica);
      expect(comp.lookupItemsSharedCollection).toContain(subUnidadeTematica);
      expect(comp.turmasSharedCollection).toContain(turma);
      expect(comp.docentesSharedCollection).toContain(docente);
      expect(comp.disciplinaCurricularsSharedCollection).toContain(disciplinaCurricular);
      expect(comp.planoAula).toEqual(planoAula);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoAula>>();
      const planoAula = { id: 123 };
      jest.spyOn(planoAulaFormService, 'getPlanoAula').mockReturnValue(planoAula);
      jest.spyOn(planoAulaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoAula }));
      saveSubject.complete();

      // THEN
      expect(planoAulaFormService.getPlanoAula).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoAulaService.update).toHaveBeenCalledWith(expect.objectContaining(planoAula));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoAula>>();
      const planoAula = { id: 123 };
      jest.spyOn(planoAulaFormService, 'getPlanoAula').mockReturnValue({ id: null });
      jest.spyOn(planoAulaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoAula: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoAula }));
      saveSubject.complete();

      // THEN
      expect(planoAulaFormService.getPlanoAula).toHaveBeenCalled();
      expect(planoAulaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoAula>>();
      const planoAula = { id: 123 };
      jest.spyOn(planoAulaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoAula });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoAulaService.update).toHaveBeenCalled();
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

    describe('compareLookupItem', () => {
      it('Should forward to lookupItemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(lookupItemService, 'compareLookupItem');
        comp.compareLookupItem(entity, entity2);
        expect(lookupItemService.compareLookupItem).toHaveBeenCalledWith(entity, entity2);
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
  });
});
