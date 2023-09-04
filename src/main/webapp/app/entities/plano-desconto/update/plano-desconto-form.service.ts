import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanoDesconto, NewPlanoDesconto } from '../plano-desconto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoDesconto for edit and NewPlanoDescontoFormGroupInput for create.
 */
type PlanoDescontoFormGroupInput = IPlanoDesconto | PartialWithRequiredKeyOf<NewPlanoDesconto>;

type PlanoDescontoFormDefaults = Pick<NewPlanoDesconto, 'id' | 'isIsentoMulta' | 'isIsentoJuro' | 'categoriasEmolumentos' | 'matriculas'>;

type PlanoDescontoFormGroupContent = {
  id: FormControl<IPlanoDesconto['id'] | NewPlanoDesconto['id']>;
  codigo: FormControl<IPlanoDesconto['codigo']>;
  nome: FormControl<IPlanoDesconto['nome']>;
  isIsentoMulta: FormControl<IPlanoDesconto['isIsentoMulta']>;
  isIsentoJuro: FormControl<IPlanoDesconto['isIsentoJuro']>;
  desconto: FormControl<IPlanoDesconto['desconto']>;
  categoriasEmolumentos: FormControl<IPlanoDesconto['categoriasEmolumentos']>;
  matriculas: FormControl<IPlanoDesconto['matriculas']>;
};

export type PlanoDescontoFormGroup = FormGroup<PlanoDescontoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoDescontoFormService {
  createPlanoDescontoFormGroup(planoDesconto: PlanoDescontoFormGroupInput = { id: null }): PlanoDescontoFormGroup {
    const planoDescontoRawValue = {
      ...this.getFormDefaults(),
      ...planoDesconto,
    };
    return new FormGroup<PlanoDescontoFormGroupContent>({
      id: new FormControl(
        { value: planoDescontoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codigo: new FormControl(planoDescontoRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(planoDescontoRawValue.nome, {
        validators: [Validators.required],
      }),
      isIsentoMulta: new FormControl(planoDescontoRawValue.isIsentoMulta),
      isIsentoJuro: new FormControl(planoDescontoRawValue.isIsentoJuro),
      desconto: new FormControl(planoDescontoRawValue.desconto, {
        validators: [Validators.required, Validators.min(0)],
      }),
      categoriasEmolumentos: new FormControl(planoDescontoRawValue.categoriasEmolumentos ?? []),
      matriculas: new FormControl(planoDescontoRawValue.matriculas ?? []),
    });
  }

  getPlanoDesconto(form: PlanoDescontoFormGroup): IPlanoDesconto | NewPlanoDesconto {
    return form.getRawValue() as IPlanoDesconto | NewPlanoDesconto;
  }

  resetForm(form: PlanoDescontoFormGroup, planoDesconto: PlanoDescontoFormGroupInput): void {
    const planoDescontoRawValue = { ...this.getFormDefaults(), ...planoDesconto };
    form.reset(
      {
        ...planoDescontoRawValue,
        id: { value: planoDescontoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlanoDescontoFormDefaults {
    return {
      id: null,
      isIsentoMulta: false,
      isIsentoJuro: false,
      categoriasEmolumentos: [],
      matriculas: [],
    };
  }
}
