import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDisciplinaCurricular, NewDisciplinaCurricular } from '../disciplina-curricular.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDisciplinaCurricular for edit and NewDisciplinaCurricularFormGroupInput for create.
 */
type DisciplinaCurricularFormGroupInput = IDisciplinaCurricular | PartialWithRequiredKeyOf<NewDisciplinaCurricular>;

type DisciplinaCurricularFormDefaults = Pick<NewDisciplinaCurricular, 'id' | 'isTerminal' | 'planosCurriculars' | 'estados'>;

type DisciplinaCurricularFormGroupContent = {
  id: FormControl<IDisciplinaCurricular['id'] | NewDisciplinaCurricular['id']>;
  uniqueDisciplinaCurricular: FormControl<IDisciplinaCurricular['uniqueDisciplinaCurricular']>;
  descricao: FormControl<IDisciplinaCurricular['descricao']>;
  cargaSemanal: FormControl<IDisciplinaCurricular['cargaSemanal']>;
  isTerminal: FormControl<IDisciplinaCurricular['isTerminal']>;
  mediaParaExame: FormControl<IDisciplinaCurricular['mediaParaExame']>;
  mediaParaRecurso: FormControl<IDisciplinaCurricular['mediaParaRecurso']>;
  mediaParaExameEspecial: FormControl<IDisciplinaCurricular['mediaParaExameEspecial']>;
  mediaParaDespensar: FormControl<IDisciplinaCurricular['mediaParaDespensar']>;
  componente: FormControl<IDisciplinaCurricular['componente']>;
  regime: FormControl<IDisciplinaCurricular['regime']>;
  planosCurriculars: FormControl<IDisciplinaCurricular['planosCurriculars']>;
  disciplina: FormControl<IDisciplinaCurricular['disciplina']>;
  referencia: FormControl<IDisciplinaCurricular['referencia']>;
  estados: FormControl<IDisciplinaCurricular['estados']>;
};

export type DisciplinaCurricularFormGroup = FormGroup<DisciplinaCurricularFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DisciplinaCurricularFormService {
  createDisciplinaCurricularFormGroup(
    disciplinaCurricular: DisciplinaCurricularFormGroupInput = { id: null }
  ): DisciplinaCurricularFormGroup {
    const disciplinaCurricularRawValue = {
      ...this.getFormDefaults(),
      ...disciplinaCurricular,
    };
    return new FormGroup<DisciplinaCurricularFormGroupContent>({
      id: new FormControl(
        { value: disciplinaCurricularRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      uniqueDisciplinaCurricular: new FormControl(disciplinaCurricularRawValue.uniqueDisciplinaCurricular),
      descricao: new FormControl(disciplinaCurricularRawValue.descricao, {
        validators: [Validators.required],
      }),
      cargaSemanal: new FormControl(disciplinaCurricularRawValue.cargaSemanal, {
        validators: [Validators.min(0)],
      }),
      isTerminal: new FormControl(disciplinaCurricularRawValue.isTerminal),
      mediaParaExame: new FormControl(disciplinaCurricularRawValue.mediaParaExame, {
        validators: [Validators.required, Validators.min(0)],
      }),
      mediaParaRecurso: new FormControl(disciplinaCurricularRawValue.mediaParaRecurso, {
        validators: [Validators.required, Validators.min(0)],
      }),
      mediaParaExameEspecial: new FormControl(disciplinaCurricularRawValue.mediaParaExameEspecial, {
        validators: [Validators.required, Validators.min(0)],
      }),
      mediaParaDespensar: new FormControl(disciplinaCurricularRawValue.mediaParaDespensar, {
        validators: [Validators.required, Validators.min(0)],
      }),
      componente: new FormControl(disciplinaCurricularRawValue.componente),
      regime: new FormControl(disciplinaCurricularRawValue.regime),
      planosCurriculars: new FormControl(disciplinaCurricularRawValue.planosCurriculars ?? []),
      disciplina: new FormControl(disciplinaCurricularRawValue.disciplina, {
        validators: [Validators.required],
      }),
      referencia: new FormControl(disciplinaCurricularRawValue.referencia),
      estados: new FormControl(disciplinaCurricularRawValue.estados ?? []),
    });
  }

  getDisciplinaCurricular(form: DisciplinaCurricularFormGroup): IDisciplinaCurricular | NewDisciplinaCurricular {
    return form.getRawValue() as IDisciplinaCurricular | NewDisciplinaCurricular;
  }

  resetForm(form: DisciplinaCurricularFormGroup, disciplinaCurricular: DisciplinaCurricularFormGroupInput): void {
    const disciplinaCurricularRawValue = { ...this.getFormDefaults(), ...disciplinaCurricular };
    form.reset(
      {
        ...disciplinaCurricularRawValue,
        id: { value: disciplinaCurricularRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DisciplinaCurricularFormDefaults {
    return {
      id: null,
      isTerminal: false,
      planosCurriculars: [],
      estados: [],
    };
  }
}
