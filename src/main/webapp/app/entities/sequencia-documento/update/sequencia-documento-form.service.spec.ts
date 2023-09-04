import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sequencia-documento.test-samples';

import { SequenciaDocumentoFormService } from './sequencia-documento-form.service';

describe('SequenciaDocumento Form Service', () => {
  let service: SequenciaDocumentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SequenciaDocumentoFormService);
  });

  describe('Service methods', () => {
    describe('createSequenciaDocumentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSequenciaDocumentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sequencia: expect.any(Object),
            data: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            serie: expect.any(Object),
          })
        );
      });

      it('passing ISequenciaDocumento should create a new form with FormGroup', () => {
        const formGroup = service.createSequenciaDocumentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            sequencia: expect.any(Object),
            data: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            serie: expect.any(Object),
          })
        );
      });
    });

    describe('getSequenciaDocumento', () => {
      it('should return NewSequenciaDocumento for default SequenciaDocumento initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSequenciaDocumentoFormGroup(sampleWithNewData);

        const sequenciaDocumento = service.getSequenciaDocumento(formGroup) as any;

        expect(sequenciaDocumento).toMatchObject(sampleWithNewData);
      });

      it('should return NewSequenciaDocumento for empty SequenciaDocumento initial value', () => {
        const formGroup = service.createSequenciaDocumentoFormGroup();

        const sequenciaDocumento = service.getSequenciaDocumento(formGroup) as any;

        expect(sequenciaDocumento).toMatchObject({});
      });

      it('should return ISequenciaDocumento', () => {
        const formGroup = service.createSequenciaDocumentoFormGroup(sampleWithRequiredData);

        const sequenciaDocumento = service.getSequenciaDocumento(formGroup) as any;

        expect(sequenciaDocumento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISequenciaDocumento should not enable id FormControl', () => {
        const formGroup = service.createSequenciaDocumentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSequenciaDocumento should disable id FormControl', () => {
        const formGroup = service.createSequenciaDocumentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
