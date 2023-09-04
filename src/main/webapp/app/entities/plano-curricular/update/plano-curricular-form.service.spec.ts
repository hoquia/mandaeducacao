import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plano-curricular.test-samples';

import { PlanoCurricularFormService } from './plano-curricular-form.service';

describe('PlanoCurricular Form Service', () => {
  let service: PlanoCurricularFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoCurricularFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoCurricularFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoCurricularFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            formulaClassificacaoFinal: expect.any(Object),
            numeroDisciplinaAprova: expect.any(Object),
            numeroDisciplinaReprova: expect.any(Object),
            numeroDisciplinaRecurso: expect.any(Object),
            numeroDisciplinaExame: expect.any(Object),
            numeroDisciplinaExameEspecial: expect.any(Object),
            numeroFaltaReprova: expect.any(Object),
            pesoMedia1: expect.any(Object),
            pesoMedia2: expect.any(Object),
            pesoMedia3: expect.any(Object),
            pesoRecurso: expect.any(Object),
            pesoExame: expect.any(Object),
            pesoExameEspecial: expect.any(Object),
            pesoNotaCoselho: expect.any(Object),
            siglaProva1: expect.any(Object),
            siglaProva2: expect.any(Object),
            siglaProva3: expect.any(Object),
            siglaMedia1: expect.any(Object),
            siglaMedia2: expect.any(Object),
            siglaMedia3: expect.any(Object),
            formulaMedia: expect.any(Object),
            formulaDispensa: expect.any(Object),
            formulaExame: expect.any(Object),
            formulaRecurso: expect.any(Object),
            formulaExameEspecial: expect.any(Object),
            utilizador: expect.any(Object),
            classe: expect.any(Object),
            curso: expect.any(Object),
            disciplinasCurriculars: expect.any(Object),
          })
        );
      });

      it('passing IPlanoCurricular should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoCurricularFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descricao: expect.any(Object),
            formulaClassificacaoFinal: expect.any(Object),
            numeroDisciplinaAprova: expect.any(Object),
            numeroDisciplinaReprova: expect.any(Object),
            numeroDisciplinaRecurso: expect.any(Object),
            numeroDisciplinaExame: expect.any(Object),
            numeroDisciplinaExameEspecial: expect.any(Object),
            numeroFaltaReprova: expect.any(Object),
            pesoMedia1: expect.any(Object),
            pesoMedia2: expect.any(Object),
            pesoMedia3: expect.any(Object),
            pesoRecurso: expect.any(Object),
            pesoExame: expect.any(Object),
            pesoExameEspecial: expect.any(Object),
            pesoNotaCoselho: expect.any(Object),
            siglaProva1: expect.any(Object),
            siglaProva2: expect.any(Object),
            siglaProva3: expect.any(Object),
            siglaMedia1: expect.any(Object),
            siglaMedia2: expect.any(Object),
            siglaMedia3: expect.any(Object),
            formulaMedia: expect.any(Object),
            formulaDispensa: expect.any(Object),
            formulaExame: expect.any(Object),
            formulaRecurso: expect.any(Object),
            formulaExameEspecial: expect.any(Object),
            utilizador: expect.any(Object),
            classe: expect.any(Object),
            curso: expect.any(Object),
            disciplinasCurriculars: expect.any(Object),
          })
        );
      });
    });

    describe('getPlanoCurricular', () => {
      it('should return NewPlanoCurricular for default PlanoCurricular initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPlanoCurricularFormGroup(sampleWithNewData);

        const planoCurricular = service.getPlanoCurricular(formGroup) as any;

        expect(planoCurricular).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoCurricular for empty PlanoCurricular initial value', () => {
        const formGroup = service.createPlanoCurricularFormGroup();

        const planoCurricular = service.getPlanoCurricular(formGroup) as any;

        expect(planoCurricular).toMatchObject({});
      });

      it('should return IPlanoCurricular', () => {
        const formGroup = service.createPlanoCurricularFormGroup(sampleWithRequiredData);

        const planoCurricular = service.getPlanoCurricular(formGroup) as any;

        expect(planoCurricular).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoCurricular should not enable id FormControl', () => {
        const formGroup = service.createPlanoCurricularFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoCurricular should disable id FormControl', () => {
        const formGroup = service.createPlanoCurricularFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
