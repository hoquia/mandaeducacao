import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MedidaDisciplinarFormService } from './medida-disciplinar-form.service';
import { MedidaDisciplinarService } from '../service/medida-disciplinar.service';
import { IMedidaDisciplinar } from '../medida-disciplinar.model';

import { MedidaDisciplinarUpdateComponent } from './medida-disciplinar-update.component';

describe('MedidaDisciplinar Management Update Component', () => {
  let comp: MedidaDisciplinarUpdateComponent;
  let fixture: ComponentFixture<MedidaDisciplinarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let medidaDisciplinarFormService: MedidaDisciplinarFormService;
  let medidaDisciplinarService: MedidaDisciplinarService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MedidaDisciplinarUpdateComponent],
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
      .overrideTemplate(MedidaDisciplinarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MedidaDisciplinarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    medidaDisciplinarFormService = TestBed.inject(MedidaDisciplinarFormService);
    medidaDisciplinarService = TestBed.inject(MedidaDisciplinarService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const medidaDisciplinar: IMedidaDisciplinar = { id: 456 };

      activatedRoute.data = of({ medidaDisciplinar });
      comp.ngOnInit();

      expect(comp.medidaDisciplinar).toEqual(medidaDisciplinar);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedidaDisciplinar>>();
      const medidaDisciplinar = { id: 123 };
      jest.spyOn(medidaDisciplinarFormService, 'getMedidaDisciplinar').mockReturnValue(medidaDisciplinar);
      jest.spyOn(medidaDisciplinarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medidaDisciplinar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medidaDisciplinar }));
      saveSubject.complete();

      // THEN
      expect(medidaDisciplinarFormService.getMedidaDisciplinar).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(medidaDisciplinarService.update).toHaveBeenCalledWith(expect.objectContaining(medidaDisciplinar));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedidaDisciplinar>>();
      const medidaDisciplinar = { id: 123 };
      jest.spyOn(medidaDisciplinarFormService, 'getMedidaDisciplinar').mockReturnValue({ id: null });
      jest.spyOn(medidaDisciplinarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medidaDisciplinar: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medidaDisciplinar }));
      saveSubject.complete();

      // THEN
      expect(medidaDisciplinarFormService.getMedidaDisciplinar).toHaveBeenCalled();
      expect(medidaDisciplinarService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedidaDisciplinar>>();
      const medidaDisciplinar = { id: 123 };
      jest.spyOn(medidaDisciplinarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medidaDisciplinar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(medidaDisciplinarService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
