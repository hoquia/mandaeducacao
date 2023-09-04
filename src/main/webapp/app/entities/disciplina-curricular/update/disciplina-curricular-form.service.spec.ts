import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../disciplina-curricular.test-samples';

import { DisciplinaCurricularFormService } from './disciplina-curricular-form.service';

describe('DisciplinaCurricular Form Service', () => {
  let service: DisciplinaCurricularFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DisciplinaCurricularFormService);
  });

  describe('Service methods', () => {
    describe('createDisciplinaCurricularFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDisciplinaCurricularFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uniqueDisciplinaCurricular: expect.any(Object),
            descricao: expect.any(Object),
            cargaSemanal: expect.any(Object),
            isTerminal: expect.any(Object),
            mediaParaExame: expect.any(Object),
            mediaParaRecurso: expect.any(Object),
            mediaParaExameEspecial: expect.any(Object),
            mediaParaDespensar: expect.any(Object),
            componente: expect.any(Object),
            regime: expect.any(Object),
            planosCurriculars: expect.any(Object),
            disciplina: expect.any(Object),
            referencia: expect.any(Object),
            estados: expect.any(Object),
          })
        );
      });

      it('passing IDisciplinaCurricular should create a new form with FormGroup', () => {
        const formGroup = service.createDisciplinaCurricularFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uniqueDisciplinaCurricular: expect.any(Object),
            descricao: expect.any(Object),
            cargaSemanal: expect.any(Object),
            isTerminal: expect.any(Object),
            mediaParaExame: expect.any(Object),
            mediaParaRecurso: expect.any(Object),
            mediaParaExameEspecial: expect.any(Object),
            mediaParaDespensar: expect.any(Object),
            componente: expect.any(Object),
            regime: expect.any(Object),
            planosCurriculars: expect.any(Object),
            disciplina: expect.any(Object),
            referencia: expect.any(Object),
            estados: expect.any(Object),
          })
        );
      });
    });

    describe('getDisciplinaCurricular', () => {
      it('should return NewDisciplinaCurricular for default DisciplinaCurricular initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDisciplinaCurricularFormGroup(sampleWithNewData);

        const disciplinaCurricular = service.getDisciplinaCurricular(formGroup) as any;

        expect(disciplinaCurricular).toMatchObject(sampleWithNewData);
      });

      it('should return NewDisciplinaCurricular for empty DisciplinaCurricular initial value', () => {
        const formGroup = service.createDisciplinaCurricularFormGroup();

        const disciplinaCurricular = service.getDisciplinaCurricular(formGroup) as any;

        expect(disciplinaCurricular).toMatchObject({});
      });

      it('should return IDisciplinaCurricular', () => {
        const formGroup = service.createDisciplinaCurricularFormGroup(sampleWithRequiredData);

        const disciplinaCurricular = service.getDisciplinaCurricular(formGroup) as any;

        expect(disciplinaCurricular).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDisciplinaCurricular should not enable id FormControl', () => {
        const formGroup = service.createDisciplinaCurricularFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDisciplinaCurricular should disable id FormControl', () => {
        const formGroup = service.createDisciplinaCurricularFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
