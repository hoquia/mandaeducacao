import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../formacao-docente.test-samples';

import { FormacaoDocenteFormService } from './formacao-docente-form.service';

describe('FormacaoDocente Form Service', () => {
  let service: FormacaoDocenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormacaoDocenteFormService);
  });

  describe('Service methods', () => {
    describe('createFormacaoDocenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFormacaoDocenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            instituicaoEnsino: expect.any(Object),
            areaFormacao: expect.any(Object),
            curso: expect.any(Object),
            especialidade: expect.any(Object),
            grau: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            anexo: expect.any(Object),
            grauAcademico: expect.any(Object),
            docente: expect.any(Object),
          })
        );
      });

      it('passing IFormacaoDocente should create a new form with FormGroup', () => {
        const formGroup = service.createFormacaoDocenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            instituicaoEnsino: expect.any(Object),
            areaFormacao: expect.any(Object),
            curso: expect.any(Object),
            especialidade: expect.any(Object),
            grau: expect.any(Object),
            inicio: expect.any(Object),
            fim: expect.any(Object),
            anexo: expect.any(Object),
            grauAcademico: expect.any(Object),
            docente: expect.any(Object),
          })
        );
      });
    });

    describe('getFormacaoDocente', () => {
      it('should return NewFormacaoDocente for default FormacaoDocente initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFormacaoDocenteFormGroup(sampleWithNewData);

        const formacaoDocente = service.getFormacaoDocente(formGroup) as any;

        expect(formacaoDocente).toMatchObject(sampleWithNewData);
      });

      it('should return NewFormacaoDocente for empty FormacaoDocente initial value', () => {
        const formGroup = service.createFormacaoDocenteFormGroup();

        const formacaoDocente = service.getFormacaoDocente(formGroup) as any;

        expect(formacaoDocente).toMatchObject({});
      });

      it('should return IFormacaoDocente', () => {
        const formGroup = service.createFormacaoDocenteFormGroup(sampleWithRequiredData);

        const formacaoDocente = service.getFormacaoDocente(formGroup) as any;

        expect(formacaoDocente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFormacaoDocente should not enable id FormControl', () => {
        const formGroup = service.createFormacaoDocenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFormacaoDocente should disable id FormControl', () => {
        const formGroup = service.createFormacaoDocenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
