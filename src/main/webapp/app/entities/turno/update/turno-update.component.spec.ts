import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TurnoFormService } from './turno-form.service';
import { TurnoService } from '../service/turno.service';
import { ITurno } from '../turno.model';

import { TurnoUpdateComponent } from './turno-update.component';

describe('Turno Management Update Component', () => {
  let comp: TurnoUpdateComponent;
  let fixture: ComponentFixture<TurnoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let turnoFormService: TurnoFormService;
  let turnoService: TurnoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TurnoUpdateComponent],
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
      .overrideTemplate(TurnoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TurnoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    turnoFormService = TestBed.inject(TurnoFormService);
    turnoService = TestBed.inject(TurnoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const turno: ITurno = { id: 456 };

      activatedRoute.data = of({ turno });
      comp.ngOnInit();

      expect(comp.turno).toEqual(turno);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITurno>>();
      const turno = { id: 123 };
      jest.spyOn(turnoFormService, 'getTurno').mockReturnValue(turno);
      jest.spyOn(turnoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turno });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: turno }));
      saveSubject.complete();

      // THEN
      expect(turnoFormService.getTurno).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(turnoService.update).toHaveBeenCalledWith(expect.objectContaining(turno));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITurno>>();
      const turno = { id: 123 };
      jest.spyOn(turnoFormService, 'getTurno').mockReturnValue({ id: null });
      jest.spyOn(turnoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turno: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: turno }));
      saveSubject.complete();

      // THEN
      expect(turnoFormService.getTurno).toHaveBeenCalled();
      expect(turnoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITurno>>();
      const turno = { id: 123 };
      jest.spyOn(turnoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ turno });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(turnoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
