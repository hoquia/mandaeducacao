import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../responsavel-disciplina.test-samples';

import { ResponsavelDisciplinaFormService } from './responsavel-disciplina-form.service';

describe('ResponsavelDisciplina Form Service', () => {
  let service: ResponsavelDisciplinaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResponsavelDisciplinaFormService);
  });

  describe('Service methods', () => {
    describe('createResponsavelDisciplinaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResponsavelDisciplinaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            disciplina: expect.any(Object),
          })
        );
      });

      it('passing IResponsavelDisciplina should create a new form with FormGroup', () => {
        const formGroup = service.createResponsavelDisciplinaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            disciplina: expect.any(Object),
          })
        );
      });
    });

    describe('getResponsavelDisciplina', () => {
      it('should return NewResponsavelDisciplina for default ResponsavelDisciplina initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResponsavelDisciplinaFormGroup(sampleWithNewData);

        const responsavelDisciplina = service.getResponsavelDisciplina(formGroup) as any;

        expect(responsavelDisciplina).toMatchObject(sampleWithNewData);
      });

      it('should return NewResponsavelDisciplina for empty ResponsavelDisciplina initial value', () => {
        const formGroup = service.createResponsavelDisciplinaFormGroup();

        const responsavelDisciplina = service.getResponsavelDisciplina(formGroup) as any;

        expect(responsavelDisciplina).toMatchObject({});
      });

      it('should return IResponsavelDisciplina', () => {
        const formGroup = service.createResponsavelDisciplinaFormGroup(sampleWithRequiredData);

        const responsavelDisciplina = service.getResponsavelDisciplina(formGroup) as any;

        expect(responsavelDisciplina).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResponsavelDisciplina should not enable id FormControl', () => {
        const formGroup = service.createResponsavelDisciplinaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResponsavelDisciplina should disable id FormControl', () => {
        const formGroup = service.createResponsavelDisciplinaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
