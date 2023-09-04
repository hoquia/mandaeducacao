import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../responsavel-turma.test-samples';

import { ResponsavelTurmaFormService } from './responsavel-turma-form.service';

describe('ResponsavelTurma Form Service', () => {
  let service: ResponsavelTurmaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResponsavelTurmaFormService);
  });

  describe('Service methods', () => {
    describe('createResponsavelTurmaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResponsavelTurmaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
          })
        );
      });

      it('passing IResponsavelTurma should create a new form with FormGroup', () => {
        const formGroup = service.createResponsavelTurmaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            turma: expect.any(Object),
          })
        );
      });
    });

    describe('getResponsavelTurma', () => {
      it('should return NewResponsavelTurma for default ResponsavelTurma initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResponsavelTurmaFormGroup(sampleWithNewData);

        const responsavelTurma = service.getResponsavelTurma(formGroup) as any;

        expect(responsavelTurma).toMatchObject(sampleWithNewData);
      });

      it('should return NewResponsavelTurma for empty ResponsavelTurma initial value', () => {
        const formGroup = service.createResponsavelTurmaFormGroup();

        const responsavelTurma = service.getResponsavelTurma(formGroup) as any;

        expect(responsavelTurma).toMatchObject({});
      });

      it('should return IResponsavelTurma', () => {
        const formGroup = service.createResponsavelTurmaFormGroup(sampleWithRequiredData);

        const responsavelTurma = service.getResponsavelTurma(formGroup) as any;

        expect(responsavelTurma).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResponsavelTurma should not enable id FormControl', () => {
        const formGroup = service.createResponsavelTurmaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResponsavelTurma should disable id FormControl', () => {
        const formGroup = service.createResponsavelTurmaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
