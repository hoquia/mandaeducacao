import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ocorrencia.test-samples';

import { OcorrenciaFormService } from './ocorrencia-form.service';

describe('Ocorrencia Form Service', () => {
  let service: OcorrenciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OcorrenciaFormService);
  });

  describe('Service methods', () => {
    describe('createOcorrenciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOcorrenciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uniqueOcorrencia: expect.any(Object),
            descricao: expect.any(Object),
            evidencia: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            referencia: expect.any(Object),
            docente: expect.any(Object),
            matricula: expect.any(Object),
            estado: expect.any(Object),
            licao: expect.any(Object),
          })
        );
      });

      it('passing IOcorrencia should create a new form with FormGroup', () => {
        const formGroup = service.createOcorrenciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uniqueOcorrencia: expect.any(Object),
            descricao: expect.any(Object),
            evidencia: expect.any(Object),
            hash: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            referencia: expect.any(Object),
            docente: expect.any(Object),
            matricula: expect.any(Object),
            estado: expect.any(Object),
            licao: expect.any(Object),
          })
        );
      });
    });

    describe('getOcorrencia', () => {
      it('should return NewOcorrencia for default Ocorrencia initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOcorrenciaFormGroup(sampleWithNewData);

        const ocorrencia = service.getOcorrencia(formGroup) as any;

        expect(ocorrencia).toMatchObject(sampleWithNewData);
      });

      it('should return NewOcorrencia for empty Ocorrencia initial value', () => {
        const formGroup = service.createOcorrenciaFormGroup();

        const ocorrencia = service.getOcorrencia(formGroup) as any;

        expect(ocorrencia).toMatchObject({});
      });

      it('should return IOcorrencia', () => {
        const formGroup = service.createOcorrenciaFormGroup(sampleWithRequiredData);

        const ocorrencia = service.getOcorrencia(formGroup) as any;

        expect(ocorrencia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOcorrencia should not enable id FormControl', () => {
        const formGroup = service.createOcorrenciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOcorrencia should disable id FormControl', () => {
        const formGroup = service.createOcorrenciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
