import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../responsavel-turno.test-samples';

import { ResponsavelTurnoFormService } from './responsavel-turno-form.service';

describe('ResponsavelTurno Form Service', () => {
  let service: ResponsavelTurnoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResponsavelTurnoFormService);
  });

  describe('Service methods', () => {
    describe('createResponsavelTurnoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResponsavelTurnoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            turno: expect.any(Object),
          })
        );
      });

      it('passing IResponsavelTurno should create a new form with FormGroup', () => {
        const formGroup = service.createResponsavelTurnoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            turno: expect.any(Object),
          })
        );
      });
    });

    describe('getResponsavelTurno', () => {
      it('should return NewResponsavelTurno for default ResponsavelTurno initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResponsavelTurnoFormGroup(sampleWithNewData);

        const responsavelTurno = service.getResponsavelTurno(formGroup) as any;

        expect(responsavelTurno).toMatchObject(sampleWithNewData);
      });

      it('should return NewResponsavelTurno for empty ResponsavelTurno initial value', () => {
        const formGroup = service.createResponsavelTurnoFormGroup();

        const responsavelTurno = service.getResponsavelTurno(formGroup) as any;

        expect(responsavelTurno).toMatchObject({});
      });

      it('should return IResponsavelTurno', () => {
        const formGroup = service.createResponsavelTurnoFormGroup(sampleWithRequiredData);

        const responsavelTurno = service.getResponsavelTurno(formGroup) as any;

        expect(responsavelTurno).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResponsavelTurno should not enable id FormControl', () => {
        const formGroup = service.createResponsavelTurnoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResponsavelTurno should disable id FormControl', () => {
        const formGroup = service.createResponsavelTurnoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
