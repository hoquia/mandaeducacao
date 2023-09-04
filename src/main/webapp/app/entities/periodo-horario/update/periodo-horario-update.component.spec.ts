import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PeriodoHorarioFormService } from './periodo-horario-form.service';
import { PeriodoHorarioService } from '../service/periodo-horario.service';
import { IPeriodoHorario } from '../periodo-horario.model';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';

import { PeriodoHorarioUpdateComponent } from './periodo-horario-update.component';

describe('PeriodoHorario Management Update Component', () => {
  let comp: PeriodoHorarioUpdateComponent;
  let fixture: ComponentFixture<PeriodoHorarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let periodoHorarioFormService: PeriodoHorarioFormService;
  let periodoHorarioService: PeriodoHorarioService;
  let turnoService: TurnoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PeriodoHorarioUpdateComponent],
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
      .overrideTemplate(PeriodoHorarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PeriodoHorarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    periodoHorarioFormService = TestBed.inject(PeriodoHorarioFormService);
    periodoHorarioService = TestBed.inject(PeriodoHorarioService);
    turnoService = TestBed.inject(TurnoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Turno query and add missing value', () => {
      const periodoHorario: IPeriodoHorario = { id: 456 };
      const turno: ITurno = { id: 32947 };
      periodoHorario.turno = turno;

      const turnoCollection: ITurno[] = [{ id: 99054 }];
      jest.spyOn(turnoService, 'query').mockReturnValue(of(new HttpResponse({ body: turnoCollection })));
      const additionalTurnos = [turno];
      const expectedCollection: ITurno[] = [...additionalTurnos, ...turnoCollection];
      jest.spyOn(turnoService, 'addTurnoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ periodoHorario });
      comp.ngOnInit();

      expect(turnoService.query).toHaveBeenCalled();
      expect(turnoService.addTurnoToCollectionIfMissing).toHaveBeenCalledWith(
        turnoCollection,
        ...additionalTurnos.map(expect.objectContaining)
      );
      expect(comp.turnosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const periodoHorario: IPeriodoHorario = { id: 456 };
      const turno: ITurno = { id: 94461 };
      periodoHorario.turno = turno;

      activatedRoute.data = of({ periodoHorario });
      comp.ngOnInit();

      expect(comp.turnosSharedCollection).toContain(turno);
      expect(comp.periodoHorario).toEqual(periodoHorario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoHorario>>();
      const periodoHorario = { id: 123 };
      jest.spyOn(periodoHorarioFormService, 'getPeriodoHorario').mockReturnValue(periodoHorario);
      jest.spyOn(periodoHorarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoHorario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodoHorario }));
      saveSubject.complete();

      // THEN
      expect(periodoHorarioFormService.getPeriodoHorario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(periodoHorarioService.update).toHaveBeenCalledWith(expect.objectContaining(periodoHorario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoHorario>>();
      const periodoHorario = { id: 123 };
      jest.spyOn(periodoHorarioFormService, 'getPeriodoHorario').mockReturnValue({ id: null });
      jest.spyOn(periodoHorarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoHorario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodoHorario }));
      saveSubject.complete();

      // THEN
      expect(periodoHorarioFormService.getPeriodoHorario).toHaveBeenCalled();
      expect(periodoHorarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodoHorario>>();
      const periodoHorario = { id: 123 };
      jest.spyOn(periodoHorarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodoHorario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(periodoHorarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
