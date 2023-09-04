import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlanoCurricularFormService } from './plano-curricular-form.service';
import { PlanoCurricularService } from '../service/plano-curricular.service';
import { IPlanoCurricular } from '../plano-curricular.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';

import { PlanoCurricularUpdateComponent } from './plano-curricular-update.component';

describe('PlanoCurricular Management Update Component', () => {
  let comp: PlanoCurricularUpdateComponent;
  let fixture: ComponentFixture<PlanoCurricularUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoCurricularFormService: PlanoCurricularFormService;
  let planoCurricularService: PlanoCurricularService;
  let userService: UserService;
  let classeService: ClasseService;
  let cursoService: CursoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlanoCurricularUpdateComponent],
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
      .overrideTemplate(PlanoCurricularUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoCurricularUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoCurricularFormService = TestBed.inject(PlanoCurricularFormService);
    planoCurricularService = TestBed.inject(PlanoCurricularService);
    userService = TestBed.inject(UserService);
    classeService = TestBed.inject(ClasseService);
    cursoService = TestBed.inject(CursoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const planoCurricular: IPlanoCurricular = { id: 456 };
      const utilizador: IUser = { id: 76064 };
      planoCurricular.utilizador = utilizador;

      const userCollection: IUser[] = [{ id: 24263 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [utilizador];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoCurricular });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Classe query and add missing value', () => {
      const planoCurricular: IPlanoCurricular = { id: 456 };
      const classe: IClasse = { id: 76771 };
      planoCurricular.classe = classe;

      const classeCollection: IClasse[] = [{ id: 24256 }];
      jest.spyOn(classeService, 'query').mockReturnValue(of(new HttpResponse({ body: classeCollection })));
      const additionalClasses = [classe];
      const expectedCollection: IClasse[] = [...additionalClasses, ...classeCollection];
      jest.spyOn(classeService, 'addClasseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoCurricular });
      comp.ngOnInit();

      expect(classeService.query).toHaveBeenCalled();
      expect(classeService.addClasseToCollectionIfMissing).toHaveBeenCalledWith(
        classeCollection,
        ...additionalClasses.map(expect.objectContaining)
      );
      expect(comp.classesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Curso query and add missing value', () => {
      const planoCurricular: IPlanoCurricular = { id: 456 };
      const curso: ICurso = { id: 11941 };
      planoCurricular.curso = curso;

      const cursoCollection: ICurso[] = [{ id: 35569 }];
      jest.spyOn(cursoService, 'query').mockReturnValue(of(new HttpResponse({ body: cursoCollection })));
      const additionalCursos = [curso];
      const expectedCollection: ICurso[] = [...additionalCursos, ...cursoCollection];
      jest.spyOn(cursoService, 'addCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoCurricular });
      comp.ngOnInit();

      expect(cursoService.query).toHaveBeenCalled();
      expect(cursoService.addCursoToCollectionIfMissing).toHaveBeenCalledWith(
        cursoCollection,
        ...additionalCursos.map(expect.objectContaining)
      );
      expect(comp.cursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const planoCurricular: IPlanoCurricular = { id: 456 };
      const utilizador: IUser = { id: 50359 };
      planoCurricular.utilizador = utilizador;
      const classe: IClasse = { id: 37583 };
      planoCurricular.classe = classe;
      const curso: ICurso = { id: 48900 };
      planoCurricular.curso = curso;

      activatedRoute.data = of({ planoCurricular });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(utilizador);
      expect(comp.classesSharedCollection).toContain(classe);
      expect(comp.cursosSharedCollection).toContain(curso);
      expect(comp.planoCurricular).toEqual(planoCurricular);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoCurricular>>();
      const planoCurricular = { id: 123 };
      jest.spyOn(planoCurricularFormService, 'getPlanoCurricular').mockReturnValue(planoCurricular);
      jest.spyOn(planoCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoCurricular }));
      saveSubject.complete();

      // THEN
      expect(planoCurricularFormService.getPlanoCurricular).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoCurricularService.update).toHaveBeenCalledWith(expect.objectContaining(planoCurricular));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoCurricular>>();
      const planoCurricular = { id: 123 };
      jest.spyOn(planoCurricularFormService, 'getPlanoCurricular').mockReturnValue({ id: null });
      jest.spyOn(planoCurricularService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoCurricular: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoCurricular }));
      saveSubject.complete();

      // THEN
      expect(planoCurricularFormService.getPlanoCurricular).toHaveBeenCalled();
      expect(planoCurricularService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoCurricular>>();
      const planoCurricular = { id: 123 };
      jest.spyOn(planoCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoCurricularService.update).toHaveBeenCalled();
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

    describe('compareClasse', () => {
      it('Should forward to classeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(classeService, 'compareClasse');
        comp.compareClasse(entity, entity2);
        expect(classeService.compareClasse).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
