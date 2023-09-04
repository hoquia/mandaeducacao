import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanoMulta, NewPlanoMulta } from '../plano-multa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoMulta for edit and NewPlanoMultaFormGroupInput for create.
 */
type PlanoMultaFormGroupInput = IPlanoMulta | PartialWithRequiredKeyOf<NewPlanoMulta>;

type PlanoMultaFormDefaults = Pick<NewPlanoMulta, 'id' | 'isTaxaMultaPercentual' | 'isTaxaJuroPercentual' | 'isAtivo'>;

type PlanoMultaFormGroupContent = {
  id: FormControl<IPlanoMulta['id'] | NewPlanoMulta['id']>;
  descricao: FormControl<IPlanoMulta['descricao']>;
  diaAplicacaoMulta: FormControl<IPlanoMulta['diaAplicacaoMulta']>;
  metodoAplicacaoMulta: FormControl<IPlanoMulta['metodoAplicacaoMulta']>;
  taxaMulta: FormControl<IPlanoMulta['taxaMulta']>;
  isTaxaMultaPercentual: FormControl<IPlanoMulta['isTaxaMultaPercentual']>;
  diaAplicacaoJuro: FormControl<IPlanoMulta['diaAplicacaoJuro']>;
  metodoAplicacaoJuro: FormControl<IPlanoMulta['metodoAplicacaoJuro']>;
  taxaJuro: FormControl<IPlanoMulta['taxaJuro']>;
  isTaxaJuroPercentual: FormControl<IPlanoMulta['isTaxaJuroPercentual']>;
  aumentarJuroEmDias: FormControl<IPlanoMulta['aumentarJuroEmDias']>;
  isAtivo: FormControl<IPlanoMulta['isAtivo']>;
  utilizador: FormControl<IPlanoMulta['utilizador']>;
};

export type PlanoMultaFormGroup = FormGroup<PlanoMultaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoMultaFormService {
  createPlanoMultaFormGroup(planoMulta: PlanoMultaFormGroupInput = { id: null }): PlanoMultaFormGroup {
    const planoMultaRawValue = {
      ...this.getFormDefaults(),
      ...planoMulta,
    };
    return new FormGroup<PlanoMultaFormGroupContent>({
      id: new FormControl(
        { value: planoMultaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descricao: new FormControl(planoMultaRawValue.descricao, {
        validators: [Validators.required],
      }),
      diaAplicacaoMulta: new FormControl(planoMultaRawValue.diaAplicacaoMulta, {
        validators: [Validators.required, Validators.min(1), Validators.max(31)],
      }),
      metodoAplicacaoMulta: new FormControl(planoMultaRawValue.metodoAplicacaoMulta, {
        validators: [Validators.required],
      }),
      taxaMulta: new FormControl(planoMultaRawValue.taxaMulta, {
        validators: [Validators.required, Validators.min(0)],
      }),
      isTaxaMultaPercentual: new FormControl(planoMultaRawValue.isTaxaMultaPercentual),
      diaAplicacaoJuro: new FormControl(planoMultaRawValue.diaAplicacaoJuro, {
        validators: [Validators.min(1), Validators.max(31)],
      }),
      metodoAplicacaoJuro: new FormControl(planoMultaRawValue.metodoAplicacaoJuro),
      taxaJuro: new FormControl(planoMultaRawValue.taxaJuro, {
        validators: [Validators.min(0)],
      }),
      isTaxaJuroPercentual: new FormControl(planoMultaRawValue.isTaxaJuroPercentual),
      aumentarJuroEmDias: new FormControl(planoMultaRawValue.aumentarJuroEmDias, {
        validators: [Validators.min(0)],
      }),
      isAtivo: new FormControl(planoMultaRawValue.isAtivo),
      utilizador: new FormControl(planoMultaRawValue.utilizador),
    });
  }

  getPlanoMulta(form: PlanoMultaFormGroup): IPlanoMulta | NewPlanoMulta {
    return form.getRawValue() as IPlanoMulta | NewPlanoMulta;
  }

  resetForm(form: PlanoMultaFormGroup, planoMulta: PlanoMultaFormGroupInput): void {
    const planoMultaRawValue = { ...this.getFormDefaults(), ...planoMulta };
    form.reset(
      {
        ...planoMultaRawValue,
        id: { value: planoMultaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlanoMultaFormDefaults {
    return {
      id: null,
      isTaxaMultaPercentual: false,
      isTaxaJuroPercentual: false,
      isAtivo: false,
    };
  }
}
