import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../medida-disciplinar.test-samples';

import { MedidaDisciplinarFormService } from './medida-disciplinar-form.service';

describe('MedidaDisciplinar Form Service', () => {
  let service: MedidaDisciplinarFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MedidaDisciplinarFormService);
  });

  describe('Service methods', () => {
    describe('createMedidaDisciplinarFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMedidaDisciplinarFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            periodo: expect.any(Object),
            suspensao: expect.any(Object),
            tempo: expect.any(Object),
          })
        );
      });

      it('passing IMedidaDisciplinar should create a new form with FormGroup', () => {
        const formGroup = service.createMedidaDisciplinarFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            periodo: expect.any(Object),
            suspensao: expect.any(Object),
            tempo: expect.any(Object),
          })
        );
      });
    });

    describe('getMedidaDisciplinar', () => {
      it('should return NewMedidaDisciplinar for default MedidaDisciplinar initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMedidaDisciplinarFormGroup(sampleWithNewData);

        const medidaDisciplinar = service.getMedidaDisciplinar(formGroup) as any;

        expect(medidaDisciplinar).toMatchObject(sampleWithNewData);
      });

      it('should return NewMedidaDisciplinar for empty MedidaDisciplinar initial value', () => {
        const formGroup = service.createMedidaDisciplinarFormGroup();

        const medidaDisciplinar = service.getMedidaDisciplinar(formGroup) as any;

        expect(medidaDisciplinar).toMatchObject({});
      });

      it('should return IMedidaDisciplinar', () => {
        const formGroup = service.createMedidaDisciplinarFormGroup(sampleWithRequiredData);

        const medidaDisciplinar = service.getMedidaDisciplinar(formGroup) as any;

        expect(medidaDisciplinar).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMedidaDisciplinar should not enable id FormControl', () => {
        const formGroup = service.createMedidaDisciplinarFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMedidaDisciplinar should disable id FormControl', () => {
        const formGroup = service.createMedidaDisciplinarFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
