import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../responsavel-curso.test-samples';

import { ResponsavelCursoFormService } from './responsavel-curso-form.service';

describe('ResponsavelCurso Form Service', () => {
  let service: ResponsavelCursoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResponsavelCursoFormService);
  });

  describe('Service methods', () => {
    describe('createResponsavelCursoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResponsavelCursoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            curso: expect.any(Object),
          })
        );
      });

      it('passing IResponsavelCurso should create a new form with FormGroup', () => {
        const formGroup = service.createResponsavelCursoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            de: expect.any(Object),
            ate: expect.any(Object),
            descricao: expect.any(Object),
            timestamp: expect.any(Object),
            utilizador: expect.any(Object),
            curso: expect.any(Object),
          })
        );
      });
    });

    describe('getResponsavelCurso', () => {
      it('should return NewResponsavelCurso for default ResponsavelCurso initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResponsavelCursoFormGroup(sampleWithNewData);

        const responsavelCurso = service.getResponsavelCurso(formGroup) as any;

        expect(responsavelCurso).toMatchObject(sampleWithNewData);
      });

      it('should return NewResponsavelCurso for empty ResponsavelCurso initial value', () => {
        const formGroup = service.createResponsavelCursoFormGroup();

        const responsavelCurso = service.getResponsavelCurso(formGroup) as any;

        expect(responsavelCurso).toMatchObject({});
      });

      it('should return IResponsavelCurso', () => {
        const formGroup = service.createResponsavelCursoFormGroup(sampleWithRequiredData);

        const responsavelCurso = service.getResponsavelCurso(formGroup) as any;

        expect(responsavelCurso).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResponsavelCurso should not enable id FormControl', () => {
        const formGroup = service.createResponsavelCursoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResponsavelCurso should disable id FormControl', () => {
        const formGroup = service.createResponsavelCursoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
