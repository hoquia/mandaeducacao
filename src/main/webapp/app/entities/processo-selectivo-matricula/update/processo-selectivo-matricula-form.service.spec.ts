import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../processo-selectivo-matricula.test-samples';

import { ProcessoSelectivoMatriculaFormService } from './processo-selectivo-matricula-form.service';

describe('ProcessoSelectivoMatricula Form Service', () => {
  let service: ProcessoSelectivoMatriculaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProcessoSelectivoMatriculaFormService);
  });

  describe('Service methods', () => {
    describe('createProcessoSelectivoMatriculaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProcessoSelectivoMatriculaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            localTeste: expect.any(Object),
            dataTeste: expect.any(Object),
            notaTeste: expect.any(Object),
            isAdmitido: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });

      it('passing IProcessoSelectivoMatricula should create a new form with FormGroup', () => {
        const formGroup = service.createProcessoSelectivoMatriculaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            localTeste: expect.any(Object),
            dataTeste: expect.any(Object),
            notaTeste: expect.any(Object),
            isAdmitido: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
            discente: expect.any(Object),
          })
        );
      });
    });

    describe('getProcessoSelectivoMatricula', () => {
      it('should return NewProcessoSelectivoMatricula for default ProcessoSelectivoMatricula initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProcessoSelectivoMatriculaFormGroup(sampleWithNewData);

        const processoSelectivoMatricula = service.getProcessoSelectivoMatricula(formGroup) as any;

        expect(processoSelectivoMatricula).toMatchObject(sampleWithNewData);
      });

      it('should return NewProcessoSelectivoMatricula for empty ProcessoSelectivoMatricula initial value', () => {
        const formGroup = service.createProcessoSelectivoMatriculaFormGroup();

        const processoSelectivoMatricula = service.getProcessoSelectivoMatricula(formGroup) as any;

        expect(processoSelectivoMatricula).toMatchObject({});
      });

      it('should return IProcessoSelectivoMatricula', () => {
        const formGroup = service.createProcessoSelectivoMatriculaFormGroup(sampleWithRequiredData);

        const processoSelectivoMatricula = service.getProcessoSelectivoMatricula(formGroup) as any;

        expect(processoSelectivoMatricula).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProcessoSelectivoMatricula should not enable id FormControl', () => {
        const formGroup = service.createProcessoSelectivoMatriculaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProcessoSelectivoMatricula should disable id FormControl', () => {
        const formGroup = service.createProcessoSelectivoMatriculaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
