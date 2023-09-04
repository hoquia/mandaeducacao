import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../estado-disciplina-curricular.test-samples';

import { EstadoDisciplinaCurricularFormService } from './estado-disciplina-curricular-form.service';

describe('EstadoDisciplinaCurricular Form Service', () => {
  let service: EstadoDisciplinaCurricularFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstadoDisciplinaCurricularFormService);
  });

  describe('Service methods', () => {
    describe('createEstadoDisciplinaCurricularFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEstadoDisciplinaCurricularFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uniqueSituacaoDisciplina: expect.any(Object),
            classificacao: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            cor: expect.any(Object),
            valor: expect.any(Object),
            disciplinasCurriculars: expect.any(Object),
            referencia: expect.any(Object),
          })
        );
      });

      it('passing IEstadoDisciplinaCurricular should create a new form with FormGroup', () => {
        const formGroup = service.createEstadoDisciplinaCurricularFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uniqueSituacaoDisciplina: expect.any(Object),
            classificacao: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            cor: expect.any(Object),
            valor: expect.any(Object),
            disciplinasCurriculars: expect.any(Object),
            referencia: expect.any(Object),
          })
        );
      });
    });

    describe('getEstadoDisciplinaCurricular', () => {
      it('should return NewEstadoDisciplinaCurricular for default EstadoDisciplinaCurricular initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEstadoDisciplinaCurricularFormGroup(sampleWithNewData);

        const estadoDisciplinaCurricular = service.getEstadoDisciplinaCurricular(formGroup) as any;

        expect(estadoDisciplinaCurricular).toMatchObject(sampleWithNewData);
      });

      it('should return NewEstadoDisciplinaCurricular for empty EstadoDisciplinaCurricular initial value', () => {
        const formGroup = service.createEstadoDisciplinaCurricularFormGroup();

        const estadoDisciplinaCurricular = service.getEstadoDisciplinaCurricular(formGroup) as any;

        expect(estadoDisciplinaCurricular).toMatchObject({});
      });

      it('should return IEstadoDisciplinaCurricular', () => {
        const formGroup = service.createEstadoDisciplinaCurricularFormGroup(sampleWithRequiredData);

        const estadoDisciplinaCurricular = service.getEstadoDisciplinaCurricular(formGroup) as any;

        expect(estadoDisciplinaCurricular).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEstadoDisciplinaCurricular should not enable id FormControl', () => {
        const formGroup = service.createEstadoDisciplinaCurricularFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEstadoDisciplinaCurricular should disable id FormControl', () => {
        const formGroup = service.createEstadoDisciplinaCurricularFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
