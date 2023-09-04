import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanoAula, NewPlanoAula } from '../plano-aula.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoAula for edit and NewPlanoAulaFormGroupInput for create.
 */
type PlanoAulaFormGroupInput = IPlanoAula | PartialWithRequiredKeyOf<NewPlanoAula>;

type PlanoAulaFormDefaults = Pick<NewPlanoAula, 'id'>;

type PlanoAulaFormGroupContent = {
  id: FormControl<IPlanoAula['id'] | NewPlanoAula['id']>;
  tipoAula: FormControl<IPlanoAula['tipoAula']>;
  semanaLectiva: FormControl<IPlanoAula['semanaLectiva']>;
  perfilEntrada: FormControl<IPlanoAula['perfilEntrada']>;
  perfilSaida: FormControl<IPlanoAula['perfilSaida']>;
  assunto: FormControl<IPlanoAula['assunto']>;
  objectivoGeral: FormControl<IPlanoAula['objectivoGeral']>;
  objectivosEspecificos: FormControl<IPlanoAula['objectivosEspecificos']>;
  tempoTotalLicao: FormControl<IPlanoAula['tempoTotalLicao']>;
  estado: FormControl<IPlanoAula['estado']>;
  utilizador: FormControl<IPlanoAula['utilizador']>;
  unidadeTematica: FormControl<IPlanoAula['unidadeTematica']>;
  subUnidadeTematica: FormControl<IPlanoAula['subUnidadeTematica']>;
  turma: FormControl<IPlanoAula['turma']>;
  docente: FormControl<IPlanoAula['docente']>;
  disciplinaCurricular: FormControl<IPlanoAula['disciplinaCurricular']>;
};

export type PlanoAulaFormGroup = FormGroup<PlanoAulaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoAulaFormService {
  createPlanoAulaFormGroup(planoAula: PlanoAulaFormGroupInput = { id: null }): PlanoAulaFormGroup {
    const planoAulaRawValue = {
      ...this.getFormDefaults(),
      ...planoAula,
    };
    return new FormGroup<PlanoAulaFormGroupContent>({
      id: new FormControl(
        { value: planoAulaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      tipoAula: new FormControl(planoAulaRawValue.tipoAula, {
        validators: [Validators.required],
      }),
      semanaLectiva: new FormControl(planoAulaRawValue.semanaLectiva, {
        validators: [Validators.required, Validators.min(0)],
      }),
      perfilEntrada: new FormControl(planoAulaRawValue.perfilEntrada, {
        validators: [Validators.required],
      }),
      perfilSaida: new FormControl(planoAulaRawValue.perfilSaida, {
        validators: [Validators.required],
      }),
      assunto: new FormControl(planoAulaRawValue.assunto, {
        validators: [Validators.required],
      }),
      objectivoGeral: new FormControl(planoAulaRawValue.objectivoGeral, {
        validators: [Validators.required],
      }),
      objectivosEspecificos: new FormControl(planoAulaRawValue.objectivosEspecificos, {
        validators: [Validators.required],
      }),
      tempoTotalLicao: new FormControl(planoAulaRawValue.tempoTotalLicao, {
        validators: [Validators.min(0)],
      }),
      estado: new FormControl(planoAulaRawValue.estado, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(planoAulaRawValue.utilizador),
      unidadeTematica: new FormControl(planoAulaRawValue.unidadeTematica, {
        validators: [Validators.required],
      }),
      subUnidadeTematica: new FormControl(planoAulaRawValue.subUnidadeTematica),
      turma: new FormControl(planoAulaRawValue.turma, {
        validators: [Validators.required],
      }),
      docente: new FormControl(planoAulaRawValue.docente, {
        validators: [Validators.required],
      }),
      disciplinaCurricular: new FormControl(planoAulaRawValue.disciplinaCurricular, {
        validators: [Validators.required],
      }),
    });
  }

  getPlanoAula(form: PlanoAulaFormGroup): IPlanoAula | NewPlanoAula {
    return form.getRawValue() as IPlanoAula | NewPlanoAula;
  }

  resetForm(form: PlanoAulaFormGroup, planoAula: PlanoAulaFormGroupInput): void {
    const planoAulaRawValue = { ...this.getFormDefaults(), ...planoAula };
    form.reset(
      {
        ...planoAulaRawValue,
        id: { value: planoAulaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlanoAulaFormDefaults {
    return {
      id: null,
    };
  }
}
