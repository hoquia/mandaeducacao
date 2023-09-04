import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../periodo-horario.test-samples';

import { PeriodoHorarioFormService } from './periodo-horario-form.service';

describe('PeriodoHorario Form Service', () => {
  let service: PeriodoHorarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeriodoHorarioFormService);
  });

  describe('Service methods', () => {
    describe('createPeriodoHorarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPeriodoHorarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            tempo: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            turno: expect.any(Object),
          })
        );
      });

      it('passing IPeriodoHorario should create a new form with FormGroup', () => {
        const formGroup = service.createPeriodoHorarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            tempo: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            turno: expect.any(Object),
          })
        );
      });
    });

    describe('getPeriodoHorario', () => {
      it('should return NewPeriodoHorario for default PeriodoHorario initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPeriodoHorarioFormGroup(sampleWithNewData);

        const periodoHorario = service.getPeriodoHorario(formGroup) as any;

        expect(periodoHorario).toMatchObject(sampleWithNewData);
      });

      it('should return NewPeriodoHorario for empty PeriodoHorario initial value', () => {
        const formGroup = service.createPeriodoHorarioFormGroup();

        const periodoHorario = service.getPeriodoHorario(formGroup) as any;

        expect(periodoHorario).toMatchObject({});
      });

      it('should return IPeriodoHorario', () => {
        const formGroup = service.createPeriodoHorarioFormGroup(sampleWithRequiredData);

        const periodoHorario = service.getPeriodoHorario(formGroup) as any;

        expect(periodoHorario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPeriodoHorario should not enable id FormControl', () => {
        const formGroup = service.createPeriodoHorarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPeriodoHorario should disable id FormControl', () => {
        const formGroup = service.createPeriodoHorarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
