import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../turno.test-samples';

import { TurnoFormService } from './turno-form.service';

describe('Turno Form Service', () => {
  let service: TurnoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TurnoFormService);
  });

  describe('Service methods', () => {
    describe('createTurnoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTurnoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
          })
        );
      });

      it('passing ITurno should create a new form with FormGroup', () => {
        const formGroup = service.createTurnoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            nome: expect.any(Object),
          })
        );
      });
    });

    describe('getTurno', () => {
      it('should return NewTurno for default Turno initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTurnoFormGroup(sampleWithNewData);

        const turno = service.getTurno(formGroup) as any;

        expect(turno).toMatchObject(sampleWithNewData);
      });

      it('should return NewTurno for empty Turno initial value', () => {
        const formGroup = service.createTurnoFormGroup();

        const turno = service.getTurno(formGroup) as any;

        expect(turno).toMatchObject({});
      });

      it('should return ITurno', () => {
        const formGroup = service.createTurnoFormGroup(sampleWithRequiredData);

        const turno = service.getTurno(formGroup) as any;

        expect(turno).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITurno should not enable id FormControl', () => {
        const formGroup = service.createTurnoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTurno should disable id FormControl', () => {
        const formGroup = service.createTurnoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
