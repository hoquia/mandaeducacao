import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NivelEnsinoFormService } from './nivel-ensino-form.service';
import { NivelEnsinoService } from '../service/nivel-ensino.service';
import { INivelEnsino } from '../nivel-ensino.model';

import { NivelEnsinoUpdateComponent } from './nivel-ensino-update.component';

describe('NivelEnsino Management Update Component', () => {
  let comp: NivelEnsinoUpdateComponent;
  let fixture: ComponentFixture<NivelEnsinoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nivelEnsinoFormService: NivelEnsinoFormService;
  let nivelEnsinoService: NivelEnsinoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NivelEnsinoUpdateComponent],
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
      .overrideTemplate(NivelEnsinoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NivelEnsinoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nivelEnsinoFormService = TestBed.inject(NivelEnsinoFormService);
    nivelEnsinoService = TestBed.inject(NivelEnsinoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NivelEnsino query and add missing value', () => {
      const nivelEnsino: INivelEnsino = { id: 456 };
      const referencia: INivelEnsino = { id: 36477 };
      nivelEnsino.referencia = referencia;

      const nivelEnsinoCollection: INivelEnsino[] = [{ id: 85875 }];
      jest.spyOn(nivelEnsinoService, 'query').mockReturnValue(of(new HttpResponse({ body: nivelEnsinoCollection })));
      const additionalNivelEnsinos = [referencia];
      const expectedCollection: INivelEnsino[] = [...additionalNivelEnsinos, ...nivelEnsinoCollection];
      jest.spyOn(nivelEnsinoService, 'addNivelEnsinoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nivelEnsino });
      comp.ngOnInit();

      expect(nivelEnsinoService.query).toHaveBeenCalled();
      expect(nivelEnsinoService.addNivelEnsinoToCollectionIfMissing).toHaveBeenCalledWith(
        nivelEnsinoCollection,
        ...additionalNivelEnsinos.map(expect.objectContaining)
      );
      expect(comp.nivelEnsinosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const nivelEnsino: INivelEnsino = { id: 456 };
      const referencia: INivelEnsino = { id: 95893 };
      nivelEnsino.referencia = referencia;

      activatedRoute.data = of({ nivelEnsino });
      comp.ngOnInit();

      expect(comp.nivelEnsinosSharedCollection).toContain(referencia);
      expect(comp.nivelEnsino).toEqual(nivelEnsino);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INivelEnsino>>();
      const nivelEnsino = { id: 123 };
      jest.spyOn(nivelEnsinoFormService, 'getNivelEnsino').mockReturnValue(nivelEnsino);
      jest.spyOn(nivelEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nivelEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nivelEnsino }));
      saveSubject.complete();

      // THEN
      expect(nivelEnsinoFormService.getNivelEnsino).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nivelEnsinoService.update).toHaveBeenCalledWith(expect.objectContaining(nivelEnsino));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INivelEnsino>>();
      const nivelEnsino = { id: 123 };
      jest.spyOn(nivelEnsinoFormService, 'getNivelEnsino').mockReturnValue({ id: null });
      jest.spyOn(nivelEnsinoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nivelEnsino: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nivelEnsino }));
      saveSubject.complete();

      // THEN
      expect(nivelEnsinoFormService.getNivelEnsino).toHaveBeenCalled();
      expect(nivelEnsinoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INivelEnsino>>();
      const nivelEnsino = { id: 123 };
      jest.spyOn(nivelEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nivelEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nivelEnsinoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNivelEnsino', () => {
      it('Should forward to nivelEnsinoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nivelEnsinoService, 'compareNivelEnsino');
        comp.compareNivelEnsino(entity, entity2);
        expect(nivelEnsinoService.compareNivelEnsino).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
