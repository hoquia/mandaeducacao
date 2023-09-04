import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DisciplinaCurricularFormService } from './disciplina-curricular-form.service';
import { DisciplinaCurricularService } from '../service/disciplina-curricular.service';
import { IDisciplinaCurricular } from '../disciplina-curricular.model';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IPlanoCurricular } from 'app/entities/plano-curricular/plano-curricular.model';
import { PlanoCurricularService } from 'app/entities/plano-curricular/service/plano-curricular.service';
import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/service/disciplina.service';

import { DisciplinaCurricularUpdateComponent } from './disciplina-curricular-update.component';

describe('DisciplinaCurricular Management Update Component', () => {
  let comp: DisciplinaCurricularUpdateComponent;
  let fixture: ComponentFixture<DisciplinaCurricularUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let disciplinaCurricularFormService: DisciplinaCurricularFormService;
  let disciplinaCurricularService: DisciplinaCurricularService;
  let lookupItemService: LookupItemService;
  let planoCurricularService: PlanoCurricularService;
  let disciplinaService: DisciplinaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DisciplinaCurricularUpdateComponent],
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
      .overrideTemplate(DisciplinaCurricularUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DisciplinaCurricularUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    disciplinaCurricularFormService = TestBed.inject(DisciplinaCurricularFormService);
    disciplinaCurricularService = TestBed.inject(DisciplinaCurricularService);
    lookupItemService = TestBed.inject(LookupItemService);
    planoCurricularService = TestBed.inject(PlanoCurricularService);
    disciplinaService = TestBed.inject(DisciplinaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DisciplinaCurricular query and add missing value', () => {
      const disciplinaCurricular: IDisciplinaCurricular = { id: 456 };
      const referencia: IDisciplinaCurricular = { id: 34920 };
      disciplinaCurricular.referencia = referencia;

      const disciplinaCurricularCollection: IDisciplinaCurricular[] = [{ id: 92068 }];
      jest.spyOn(disciplinaCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCurricularCollection })));
      const additionalDisciplinaCurriculars = [referencia];
      const expectedCollection: IDisciplinaCurricular[] = [...additionalDisciplinaCurriculars, ...disciplinaCurricularCollection];
      jest.spyOn(disciplinaCurricularService, 'addDisciplinaCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disciplinaCurricular });
      comp.ngOnInit();

      expect(disciplinaCurricularService.query).toHaveBeenCalled();
      expect(disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCurricularCollection,
        ...additionalDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.disciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LookupItem query and add missing value', () => {
      const disciplinaCurricular: IDisciplinaCurricular = { id: 456 };
      const componente: ILookupItem = { id: 66417 };
      disciplinaCurricular.componente = componente;
      const regime: ILookupItem = { id: 67225 };
      disciplinaCurricular.regime = regime;

      const lookupItemCollection: ILookupItem[] = [{ id: 25943 }];
      jest.spyOn(lookupItemService, 'query').mockReturnValue(of(new HttpResponse({ body: lookupItemCollection })));
      const additionalLookupItems = [componente, regime];
      const expectedCollection: ILookupItem[] = [...additionalLookupItems, ...lookupItemCollection];
      jest.spyOn(lookupItemService, 'addLookupItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disciplinaCurricular });
      comp.ngOnInit();

      expect(lookupItemService.query).toHaveBeenCalled();
      expect(lookupItemService.addLookupItemToCollectionIfMissing).toHaveBeenCalledWith(
        lookupItemCollection,
        ...additionalLookupItems.map(expect.objectContaining)
      );
      expect(comp.lookupItemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoCurricular query and add missing value', () => {
      const disciplinaCurricular: IDisciplinaCurricular = { id: 456 };
      const planosCurriculars: IPlanoCurricular[] = [{ id: 92283 }];
      disciplinaCurricular.planosCurriculars = planosCurriculars;

      const planoCurricularCollection: IPlanoCurricular[] = [{ id: 28104 }];
      jest.spyOn(planoCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: planoCurricularCollection })));
      const additionalPlanoCurriculars = [...planosCurriculars];
      const expectedCollection: IPlanoCurricular[] = [...additionalPlanoCurriculars, ...planoCurricularCollection];
      jest.spyOn(planoCurricularService, 'addPlanoCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disciplinaCurricular });
      comp.ngOnInit();

      expect(planoCurricularService.query).toHaveBeenCalled();
      expect(planoCurricularService.addPlanoCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        planoCurricularCollection,
        ...additionalPlanoCurriculars.map(expect.objectContaining)
      );
      expect(comp.planoCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Disciplina query and add missing value', () => {
      const disciplinaCurricular: IDisciplinaCurricular = { id: 456 };
      const disciplina: IDisciplina = { id: 63181 };
      disciplinaCurricular.disciplina = disciplina;

      const disciplinaCollection: IDisciplina[] = [{ id: 36619 }];
      jest.spyOn(disciplinaService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCollection })));
      const additionalDisciplinas = [disciplina];
      const expectedCollection: IDisciplina[] = [...additionalDisciplinas, ...disciplinaCollection];
      jest.spyOn(disciplinaService, 'addDisciplinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disciplinaCurricular });
      comp.ngOnInit();

      expect(disciplinaService.query).toHaveBeenCalled();
      expect(disciplinaService.addDisciplinaToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCollection,
        ...additionalDisciplinas.map(expect.objectContaining)
      );
      expect(comp.disciplinasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const disciplinaCurricular: IDisciplinaCurricular = { id: 456 };
      const referencia: IDisciplinaCurricular = { id: 6829 };
      disciplinaCurricular.referencia = referencia;
      const componente: ILookupItem = { id: 95537 };
      disciplinaCurricular.componente = componente;
      const regime: ILookupItem = { id: 25719 };
      disciplinaCurricular.regime = regime;
      const planosCurricular: IPlanoCurricular = { id: 65709 };
      disciplinaCurricular.planosCurriculars = [planosCurricular];
      const disciplina: IDisciplina = { id: 37522 };
      disciplinaCurricular.disciplina = disciplina;

      activatedRoute.data = of({ disciplinaCurricular });
      comp.ngOnInit();

      expect(comp.disciplinaCurricularsSharedCollection).toContain(referencia);
      expect(comp.lookupItemsSharedCollection).toContain(componente);
      expect(comp.lookupItemsSharedCollection).toContain(regime);
      expect(comp.planoCurricularsSharedCollection).toContain(planosCurricular);
      expect(comp.disciplinasSharedCollection).toContain(disciplina);
      expect(comp.disciplinaCurricular).toEqual(disciplinaCurricular);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDisciplinaCurricular>>();
      const disciplinaCurricular = { id: 123 };
      jest.spyOn(disciplinaCurricularFormService, 'getDisciplinaCurricular').mockReturnValue(disciplinaCurricular);
      jest.spyOn(disciplinaCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disciplinaCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disciplinaCurricular }));
      saveSubject.complete();

      // THEN
      expect(disciplinaCurricularFormService.getDisciplinaCurricular).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(disciplinaCurricularService.update).toHaveBeenCalledWith(expect.objectContaining(disciplinaCurricular));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDisciplinaCurricular>>();
      const disciplinaCurricular = { id: 123 };
      jest.spyOn(disciplinaCurricularFormService, 'getDisciplinaCurricular').mockReturnValue({ id: null });
      jest.spyOn(disciplinaCurricularService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disciplinaCurricular: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disciplinaCurricular }));
      saveSubject.complete();

      // THEN
      expect(disciplinaCurricularFormService.getDisciplinaCurricular).toHaveBeenCalled();
      expect(disciplinaCurricularService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDisciplinaCurricular>>();
      const disciplinaCurricular = { id: 123 };
      jest.spyOn(disciplinaCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disciplinaCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(disciplinaCurricularService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDisciplinaCurricular', () => {
      it('Should forward to disciplinaCurricularService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(disciplinaCurricularService, 'compareDisciplinaCurricular');
        comp.compareDisciplinaCurricular(entity, entity2);
        expect(disciplinaCurricularService.compareDisciplinaCurricular).toHaveBeenCalledWith(entity, entity2);
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

    describe('comparePlanoCurricular', () => {
      it('Should forward to planoCurricularService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoCurricularService, 'comparePlanoCurricular');
        comp.comparePlanoCurricular(entity, entity2);
        expect(planoCurricularService.comparePlanoCurricular).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDisciplina', () => {
      it('Should forward to disciplinaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(disciplinaService, 'compareDisciplina');
        comp.compareDisciplina(entity, entity2);
        expect(disciplinaService.compareDisciplina).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
