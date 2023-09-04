import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategoriaEmolumento, NewCategoriaEmolumento } from '../categoria-emolumento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoriaEmolumento for edit and NewCategoriaEmolumentoFormGroupInput for create.
 */
type CategoriaEmolumentoFormGroupInput = ICategoriaEmolumento | PartialWithRequiredKeyOf<NewCategoriaEmolumento>;

type CategoriaEmolumentoFormDefaults = Pick<
  NewCategoriaEmolumento,
  'id' | 'isServico' | 'isIsentoMulta' | 'isIsentoJuro' | 'planosDescontos'
>;

type CategoriaEmolumentoFormGroupContent = {
  id: FormControl<ICategoriaEmolumento['id'] | NewCategoriaEmolumento['id']>;
  nome: FormControl<ICategoriaEmolumento['nome']>;
  isServico: FormControl<ICategoriaEmolumento['isServico']>;
  cor: FormControl<ICategoriaEmolumento['cor']>;
  descricao: FormControl<ICategoriaEmolumento['descricao']>;
  isIsentoMulta: FormControl<ICategoriaEmolumento['isIsentoMulta']>;
  isIsentoJuro: FormControl<ICategoriaEmolumento['isIsentoJuro']>;
  planoMulta: FormControl<ICategoriaEmolumento['planoMulta']>;
  planosDescontos: FormControl<ICategoriaEmolumento['planosDescontos']>;
};

export type CategoriaEmolumentoFormGroup = FormGroup<CategoriaEmolumentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoriaEmolumentoFormService {
  createCategoriaEmolumentoFormGroup(categoriaEmolumento: CategoriaEmolumentoFormGroupInput = { id: null }): CategoriaEmolumentoFormGroup {
    const categoriaEmolumentoRawValue = {
      ...this.getFormDefaults(),
      ...categoriaEmolumento,
    };
    return new FormGroup<CategoriaEmolumentoFormGroupContent>({
      id: new FormControl(
        { value: categoriaEmolumentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nome: new FormControl(categoriaEmolumentoRawValue.nome, {
        validators: [Validators.required],
      }),
      isServico: new FormControl(categoriaEmolumentoRawValue.isServico),
      cor: new FormControl(categoriaEmolumentoRawValue.cor),
      descricao: new FormControl(categoriaEmolumentoRawValue.descricao),
      isIsentoMulta: new FormControl(categoriaEmolumentoRawValue.isIsentoMulta),
      isIsentoJuro: new FormControl(categoriaEmolumentoRawValue.isIsentoJuro),
      planoMulta: new FormControl(categoriaEmolumentoRawValue.planoMulta),
      planosDescontos: new FormControl(categoriaEmolumentoRawValue.planosDescontos ?? []),
    });
  }

  getCategoriaEmolumento(form: CategoriaEmolumentoFormGroup): ICategoriaEmolumento | NewCategoriaEmolumento {
    return form.getRawValue() as ICategoriaEmolumento | NewCategoriaEmolumento;
  }

  resetForm(form: CategoriaEmolumentoFormGroup, categoriaEmolumento: CategoriaEmolumentoFormGroupInput): void {
    const categoriaEmolumentoRawValue = { ...this.getFormDefaults(), ...categoriaEmolumento };
    form.reset(
      {
        ...categoriaEmolumentoRawValue,
        id: { value: categoriaEmolumentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategoriaEmolumentoFormDefaults {
    return {
      id: null,
      isServico: false,
      isIsentoMulta: false,
      isIsentoJuro: false,
      planosDescontos: [],
    };
  }
}
