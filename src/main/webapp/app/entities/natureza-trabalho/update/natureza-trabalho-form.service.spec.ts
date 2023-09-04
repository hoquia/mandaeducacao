import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../natureza-trabalho.test-samples';

import { NaturezaTrabalhoFormService } from './natureza-trabalho-form.service';

describe('NaturezaTrabalho Form Service', () => {
  let service: NaturezaTrabalhoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NaturezaTrabalhoFormService);
  });

  describe('Service methods', () => {
    describe('createNaturezaTrabalhoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNaturezaTrabalhoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            isActivo: expect.any(Object),
          })
        );
      });

      it('passing INaturezaTrabalho should create a new form with FormGroup', () => {
        const formGroup = service.createNaturezaTrabalhoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            isActivo: expect.any(Object),
          })
        );
      });
    });

    describe('getNaturezaTrabalho', () => {
      it('should return NewNaturezaTrabalho for default NaturezaTrabalho initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNaturezaTrabalhoFormGroup(sampleWithNewData);

        const naturezaTrabalho = service.getNaturezaTrabalho(formGroup) as any;

        expect(naturezaTrabalho).toMatchObject(sampleWithNewData);
      });

      it('should return NewNaturezaTrabalho for empty NaturezaTrabalho initial value', () => {
        const formGroup = service.createNaturezaTrabalhoFormGroup();

        const naturezaTrabalho = service.getNaturezaTrabalho(formGroup) as any;

        expect(naturezaTrabalho).toMatchObject({});
      });

      it('should return INaturezaTrabalho', () => {
        const formGroup = service.createNaturezaTrabalhoFormGroup(sampleWithRequiredData);

        const naturezaTrabalho = service.getNaturezaTrabalho(formGroup) as any;

        expect(naturezaTrabalho).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INaturezaTrabalho should not enable id FormControl', () => {
        const formGroup = service.createNaturezaTrabalhoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNaturezaTrabalho should disable id FormControl', () => {
        const formGroup = service.createNaturezaTrabalhoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
