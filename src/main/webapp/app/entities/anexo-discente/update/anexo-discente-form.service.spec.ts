import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-discente.test-samples';

import { AnexoDiscenteFormService } from './anexo-discente-form.service';

describe('AnexoDiscente Form Service', () => {
  let service: AnexoDiscenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoDiscenteFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoDiscenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoDiscenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categoria: expect.any(Object),
            anexo: expect.any(Object),
            descricao: expect.any(Object),
            validade: expect.any(Object),
            timestamp: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });

      it('passing IAnexoDiscente should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoDiscenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categoria: expect.any(Object),
            anexo: expect.any(Object),
            descricao: expect.any(Object),
            validade: expect.any(Object),
            timestamp: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });
    });

    describe('getAnexoDiscente', () => {
      it('should return NewAnexoDiscente for default AnexoDiscente initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAnexoDiscenteFormGroup(sampleWithNewData);

        const anexoDiscente = service.getAnexoDiscente(formGroup) as any;

        expect(anexoDiscente).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoDiscente for empty AnexoDiscente initial value', () => {
        const formGroup = service.createAnexoDiscenteFormGroup();

        const anexoDiscente = service.getAnexoDiscente(formGroup) as any;

        expect(anexoDiscente).toMatchObject({});
      });

      it('should return IAnexoDiscente', () => {
        const formGroup = service.createAnexoDiscenteFormGroup(sampleWithRequiredData);

        const anexoDiscente = service.getAnexoDiscente(formGroup) as any;

        expect(anexoDiscente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoDiscente should not enable id FormControl', () => {
        const formGroup = service.createAnexoDiscenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoDiscente should disable id FormControl', () => {
        const formGroup = service.createAnexoDiscenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
