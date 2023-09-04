import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanoCurricular, NewPlanoCurricular } from '../plano-curricular.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoCurricular for edit and NewPlanoCurricularFormGroupInput for create.
 */
type PlanoCurricularFormGroupInput = IPlanoCurricular | PartialWithRequiredKeyOf<NewPlanoCurricular>;

type PlanoCurricularFormDefaults = Pick<NewPlanoCurricular, 'id' | 'disciplinasCurriculars'>;

type PlanoCurricularFormGroupContent = {
  id: FormControl<IPlanoCurricular['id'] | NewPlanoCurricular['id']>;
  descricao: FormControl<IPlanoCurricular['descricao']>;
  formulaClassificacaoFinal: FormControl<IPlanoCurricular['formulaClassificacaoFinal']>;
  numeroDisciplinaAprova: FormControl<IPlanoCurricular['numeroDisciplinaAprova']>;
  numeroDisciplinaReprova: FormControl<IPlanoCurricular['numeroDisciplinaReprova']>;
  numeroDisciplinaRecurso: FormControl<IPlanoCurricular['numeroDisciplinaRecurso']>;
  numeroDisciplinaExame: FormControl<IPlanoCurricular['numeroDisciplinaExame']>;
  numeroDisciplinaExameEspecial: FormControl<IPlanoCurricular['numeroDisciplinaExameEspecial']>;
  numeroFaltaReprova: FormControl<IPlanoCurricular['numeroFaltaReprova']>;
  pesoMedia1: FormControl<IPlanoCurricular['pesoMedia1']>;
  pesoMedia2: FormControl<IPlanoCurricular['pesoMedia2']>;
  pesoMedia3: FormControl<IPlanoCurricular['pesoMedia3']>;
  pesoRecurso: FormControl<IPlanoCurricular['pesoRecurso']>;
  pesoExame: FormControl<IPlanoCurricular['pesoExame']>;
  pesoExameEspecial: FormControl<IPlanoCurricular['pesoExameEspecial']>;
  pesoNotaCoselho: FormControl<IPlanoCurricular['pesoNotaCoselho']>;
  siglaProva1: FormControl<IPlanoCurricular['siglaProva1']>;
  siglaProva2: FormControl<IPlanoCurricular['siglaProva2']>;
  siglaProva3: FormControl<IPlanoCurricular['siglaProva3']>;
  siglaMedia1: FormControl<IPlanoCurricular['siglaMedia1']>;
  siglaMedia2: FormControl<IPlanoCurricular['siglaMedia2']>;
  siglaMedia3: FormControl<IPlanoCurricular['siglaMedia3']>;
  formulaMedia: FormControl<IPlanoCurricular['formulaMedia']>;
  formulaDispensa: FormControl<IPlanoCurricular['formulaDispensa']>;
  formulaExame: FormControl<IPlanoCurricular['formulaExame']>;
  formulaRecurso: FormControl<IPlanoCurricular['formulaRecurso']>;
  formulaExameEspecial: FormControl<IPlanoCurricular['formulaExameEspecial']>;
  utilizador: FormControl<IPlanoCurricular['utilizador']>;
  classe: FormControl<IPlanoCurricular['classe']>;
  curso: FormControl<IPlanoCurricular['curso']>;
  disciplinasCurriculars: FormControl<IPlanoCurricular['disciplinasCurriculars']>;
};

export type PlanoCurricularFormGroup = FormGroup<PlanoCurricularFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoCurricularFormService {
  createPlanoCurricularFormGroup(planoCurricular: PlanoCurricularFormGroupInput = { id: null }): PlanoCurricularFormGroup {
    const planoCurricularRawValue = {
      ...this.getFormDefaults(),
      ...planoCurricular,
    };
    return new FormGroup<PlanoCurricularFormGroupContent>({
      id: new FormControl(
        { value: planoCurricularRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descricao: new FormControl(planoCurricularRawValue.descricao, {
        validators: [Validators.required],
      }),
      formulaClassificacaoFinal: new FormControl(planoCurricularRawValue.formulaClassificacaoFinal, {
        validators: [Validators.required],
      }),
      numeroDisciplinaAprova: new FormControl(planoCurricularRawValue.numeroDisciplinaAprova, {
        validators: [Validators.required, Validators.min(0)],
      }),
      numeroDisciplinaReprova: new FormControl(planoCurricularRawValue.numeroDisciplinaReprova, {
        validators: [Validators.required, Validators.min(0)],
      }),
      numeroDisciplinaRecurso: new FormControl(planoCurricularRawValue.numeroDisciplinaRecurso, {
        validators: [Validators.required, Validators.min(0)],
      }),
      numeroDisciplinaExame: new FormControl(planoCurricularRawValue.numeroDisciplinaExame, {
        validators: [Validators.required, Validators.min(0)],
      }),
      numeroDisciplinaExameEspecial: new FormControl(planoCurricularRawValue.numeroDisciplinaExameEspecial, {
        validators: [Validators.required, Validators.min(0)],
      }),
      numeroFaltaReprova: new FormControl(planoCurricularRawValue.numeroFaltaReprova, {
        validators: [Validators.required, Validators.min(0)],
      }),
      pesoMedia1: new FormControl(planoCurricularRawValue.pesoMedia1, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      pesoMedia2: new FormControl(planoCurricularRawValue.pesoMedia2, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      pesoMedia3: new FormControl(planoCurricularRawValue.pesoMedia3, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      pesoRecurso: new FormControl(planoCurricularRawValue.pesoRecurso, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      pesoExame: new FormControl(planoCurricularRawValue.pesoExame, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      pesoExameEspecial: new FormControl(planoCurricularRawValue.pesoExameEspecial, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      pesoNotaCoselho: new FormControl(planoCurricularRawValue.pesoNotaCoselho, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      siglaProva1: new FormControl(planoCurricularRawValue.siglaProva1, {
        validators: [Validators.required],
      }),
      siglaProva2: new FormControl(planoCurricularRawValue.siglaProva2, {
        validators: [Validators.required],
      }),
      siglaProva3: new FormControl(planoCurricularRawValue.siglaProva3, {
        validators: [Validators.required],
      }),
      siglaMedia1: new FormControl(planoCurricularRawValue.siglaMedia1, {
        validators: [Validators.required],
      }),
      siglaMedia2: new FormControl(planoCurricularRawValue.siglaMedia2, {
        validators: [Validators.required],
      }),
      siglaMedia3: new FormControl(planoCurricularRawValue.siglaMedia3, {
        validators: [Validators.required],
      }),
      formulaMedia: new FormControl(planoCurricularRawValue.formulaMedia, {
        validators: [Validators.required],
      }),
      formulaDispensa: new FormControl(planoCurricularRawValue.formulaDispensa, {
        validators: [Validators.required],
      }),
      formulaExame: new FormControl(planoCurricularRawValue.formulaExame, {
        validators: [Validators.required],
      }),
      formulaRecurso: new FormControl(planoCurricularRawValue.formulaRecurso, {
        validators: [Validators.required],
      }),
      formulaExameEspecial: new FormControl(planoCurricularRawValue.formulaExameEspecial, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(planoCurricularRawValue.utilizador),
      classe: new FormControl(planoCurricularRawValue.classe, {
        validators: [Validators.required],
      }),
      curso: new FormControl(planoCurricularRawValue.curso, {
        validators: [Validators.required],
      }),
      disciplinasCurriculars: new FormControl(planoCurricularRawValue.disciplinasCurriculars ?? []),
    });
  }

  getPlanoCurricular(form: PlanoCurricularFormGroup): IPlanoCurricular | NewPlanoCurricular {
    return form.getRawValue() as IPlanoCurricular | NewPlanoCurricular;
  }

  resetForm(form: PlanoCurricularFormGroup, planoCurricular: PlanoCurricularFormGroupInput): void {
    const planoCurricularRawValue = { ...this.getFormDefaults(), ...planoCurricular };
    form.reset(
      {
        ...planoCurricularRawValue,
        id: { value: planoCurricularRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlanoCurricularFormDefaults {
    return {
      id: null,
      disciplinasCurriculars: [],
    };
  }
}
