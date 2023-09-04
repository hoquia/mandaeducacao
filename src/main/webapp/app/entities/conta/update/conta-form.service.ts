import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IConta, NewConta } from '../conta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IConta for edit and NewContaFormGroupInput for create.
 */
type ContaFormGroupInput = IConta | PartialWithRequiredKeyOf<NewConta>;

type ContaFormDefaults = Pick<NewConta, 'id' | 'isPadrao'>;

type ContaFormGroupContent = {
  id: FormControl<IConta['id'] | NewConta['id']>;
  imagem: FormControl<IConta['imagem']>;
  imagemContentType: FormControl<IConta['imagemContentType']>;
  tipo: FormControl<IConta['tipo']>;
  titulo: FormControl<IConta['titulo']>;
  numero: FormControl<IConta['numero']>;
  iban: FormControl<IConta['iban']>;
  titular: FormControl<IConta['titular']>;
  isPadrao: FormControl<IConta['isPadrao']>;
  moeda: FormControl<IConta['moeda']>;
};

export type ContaFormGroup = FormGroup<ContaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContaFormService {
  createContaFormGroup(conta: ContaFormGroupInput = { id: null }): ContaFormGroup {
    const contaRawValue = {
      ...this.getFormDefaults(),
      ...conta,
    };
    return new FormGroup<ContaFormGroupContent>({
      id: new FormControl(
        { value: contaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      imagem: new FormControl(contaRawValue.imagem),
      imagemContentType: new FormControl(contaRawValue.imagemContentType),
      tipo: new FormControl(contaRawValue.tipo, {
        validators: [Validators.required],
      }),
      titulo: new FormControl(contaRawValue.titulo, {
        validators: [Validators.required],
      }),
      numero: new FormControl(contaRawValue.numero, {
        validators: [Validators.required],
      }),
      iban: new FormControl(contaRawValue.iban),
      titular: new FormControl(contaRawValue.titular, {
        validators: [Validators.required],
      }),
      isPadrao: new FormControl(contaRawValue.isPadrao),
      moeda: new FormControl(contaRawValue.moeda),
    });
  }

  getConta(form: ContaFormGroup): IConta | NewConta {
    return form.getRawValue() as IConta | NewConta;
  }

  resetForm(form: ContaFormGroup, conta: ContaFormGroupInput): void {
    const contaRawValue = { ...this.getFormDefaults(), ...conta };
    form.reset(
      {
        ...contaRawValue,
        id: { value: contaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ContaFormDefaults {
    return {
      id: null,
      isPadrao: false,
    };
  }
}
