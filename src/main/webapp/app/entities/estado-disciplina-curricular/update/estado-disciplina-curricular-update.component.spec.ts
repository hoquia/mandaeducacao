import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EstadoDisciplinaCurricularFormService } from './estado-disciplina-curricular-form.service';
import { EstadoDisciplinaCurricularService } from '../service/estado-disciplina-curricular.service';
import { IEstadoDisciplinaCurricular } from '../estado-disciplina-curricular.model';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';

import { EstadoDisciplinaCurricularUpdateComponent } from './estado-disciplina-curricular-update.component';

describe('EstadoDisciplinaCurricular Management Update Component', () => {
  let comp: EstadoDisciplinaCurricularUpdateComponent;
  let fixture: ComponentFixture<EstadoDisciplinaCurricularUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estadoDisciplinaCurricularFormService: EstadoDisciplinaCurricularFormService;
  let estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService;
  let disciplinaCurricularService: DisciplinaCurricularService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EstadoDisciplinaCurricularUpdateComponent],
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
      .overrideTemplate(EstadoDisciplinaCurricularUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstadoDisciplinaCurricularUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estadoDisciplinaCurricularFormService = TestBed.inject(EstadoDisciplinaCurricularFormService);
    estadoDisciplinaCurricularService = TestBed.inject(EstadoDisciplinaCurricularService);
    disciplinaCurricularService = TestBed.inject(DisciplinaCurricularService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EstadoDisciplinaCurricular query and add missing value', () => {
      const estadoDisciplinaCurricular: IEstadoDisciplinaCurricular = { id: 456 };
      const referencia: IEstadoDisciplinaCurricular = { id: 56743 };
      estadoDisciplinaCurricular.referencia = referencia;

      const estadoDisciplinaCurricularCollection: IEstadoDisciplinaCurricular[] = [{ id: 38679 }];
      jest
        .spyOn(estadoDisciplinaCurricularService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: estadoDisciplinaCurricularCollection })));
      const additionalEstadoDisciplinaCurriculars = [referencia];
      const expectedCollection: IEstadoDisciplinaCurricular[] = [
        ...additionalEstadoDisciplinaCurriculars,
        ...estadoDisciplinaCurricularCollection,
      ];
      jest
        .spyOn(estadoDisciplinaCurricularService, 'addEstadoDisciplinaCurricularToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estadoDisciplinaCurricular });
      comp.ngOnInit();

      expect(estadoDisciplinaCurricularService.query).toHaveBeenCalled();
      expect(estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        estadoDisciplinaCurricularCollection,
        ...additionalEstadoDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.estadoDisciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DisciplinaCurricular query and add missing value', () => {
      const estadoDisciplinaCurricular: IEstadoDisciplinaCurricular = { id: 456 };
      const disciplinasCurriculars: IDisciplinaCurricular[] = [{ id: 68911 }];
      estadoDisciplinaCurricular.disciplinasCurriculars = disciplinasCurriculars;

      const disciplinaCurricularCollection: IDisciplinaCurricular[] = [{ id: 51291 }];
      jest.spyOn(disciplinaCurricularService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCurricularCollection })));
      const additionalDisciplinaCurriculars = [...disciplinasCurriculars];
      const expectedCollection: IDisciplinaCurricular[] = [...additionalDisciplinaCurriculars, ...disciplinaCurricularCollection];
      jest.spyOn(disciplinaCurricularService, 'addDisciplinaCurricularToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ estadoDisciplinaCurricular });
      comp.ngOnInit();

      expect(disciplinaCurricularService.query).toHaveBeenCalled();
      expect(disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCurricularCollection,
        ...additionalDisciplinaCurriculars.map(expect.objectContaining)
      );
      expect(comp.disciplinaCurricularsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const estadoDisciplinaCurricular: IEstadoDisciplinaCurricular = { id: 456 };
      const referencia: IEstadoDisciplinaCurricular = { id: 35458 };
      estadoDisciplinaCurricular.referencia = referencia;
      const disciplinasCurriculars: IDisciplinaCurricular = { id: 77957 };
      estadoDisciplinaCurricular.disciplinasCurriculars = [disciplinasCurriculars];

      activatedRoute.data = of({ estadoDisciplinaCurricular });
      comp.ngOnInit();

      expect(comp.estadoDisciplinaCurricularsSharedCollection).toContain(referencia);
      expect(comp.disciplinaCurricularsSharedCollection).toContain(disciplinasCurriculars);
      expect(comp.estadoDisciplinaCurricular).toEqual(estadoDisciplinaCurricular);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadoDisciplinaCurricular>>();
      const estadoDisciplinaCurricular = { id: 123 };
      jest.spyOn(estadoDisciplinaCurricularFormService, 'getEstadoDisciplinaCurricular').mockReturnValue(estadoDisciplinaCurricular);
      jest.spyOn(estadoDisciplinaCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoDisciplinaCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadoDisciplinaCurricular }));
      saveSubject.complete();

      // THEN
      expect(estadoDisciplinaCurricularFormService.getEstadoDisciplinaCurricular).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(estadoDisciplinaCurricularService.update).toHaveBeenCalledWith(expect.objectContaining(estadoDisciplinaCurricular));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadoDisciplinaCurricular>>();
      const estadoDisciplinaCurricular = { id: 123 };
      jest.spyOn(estadoDisciplinaCurricularFormService, 'getEstadoDisciplinaCurricular').mockReturnValue({ id: null });
      jest.spyOn(estadoDisciplinaCurricularService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoDisciplinaCurricular: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadoDisciplinaCurricular }));
      saveSubject.complete();

      // THEN
      expect(estadoDisciplinaCurricularFormService.getEstadoDisciplinaCurricular).toHaveBeenCalled();
      expect(estadoDisciplinaCurricularService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadoDisciplinaCurricular>>();
      const estadoDisciplinaCurricular = { id: 123 };
      jest.spyOn(estadoDisciplinaCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadoDisciplinaCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estadoDisciplinaCurricularService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEstadoDisciplinaCurricular', () => {
      it('Should forward to estadoDisciplinaCurricularService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(estadoDisciplinaCurricularService, 'compareEstadoDisciplinaCurricular');
        comp.compareEstadoDisciplinaCurricular(entity, entity2);
        expect(estadoDisciplinaCurricularService.compareEstadoDisciplinaCurricular).toHaveBeenCalledWith(entity, entity2);
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
