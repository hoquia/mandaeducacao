import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEnderecoDiscente, NewEnderecoDiscente } from '../endereco-discente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEnderecoDiscente for edit and NewEnderecoDiscenteFormGroupInput for create.
 */
type EnderecoDiscenteFormGroupInput = IEnderecoDiscente | PartialWithRequiredKeyOf<NewEnderecoDiscente>;

type EnderecoDiscenteFormDefaults = Pick<NewEnderecoDiscente, 'id'>;

type EnderecoDiscenteFormGroupContent = {
  id: FormControl<IEnderecoDiscente['id'] | NewEnderecoDiscente['id']>;
  tipo: FormControl<IEnderecoDiscente['tipo']>;
  bairro: FormControl<IEnderecoDiscente['bairro']>;
  rua: FormControl<IEnderecoDiscente['rua']>;
  numeroCasa: FormControl<IEnderecoDiscente['numeroCasa']>;
  codigoPostal: FormControl<IEnderecoDiscente['codigoPostal']>;
  latitude: FormControl<IEnderecoDiscente['latitude']>;
  longitude: FormControl<IEnderecoDiscente['longitude']>;
  pais: FormControl<IEnderecoDiscente['pais']>;
  provincia: FormControl<IEnderecoDiscente['provincia']>;
  municipio: FormControl<IEnderecoDiscente['municipio']>;
  discente: FormControl<IEnderecoDiscente['discente']>;
};

export type EnderecoDiscenteFormGroup = FormGroup<EnderecoDiscenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EnderecoDiscenteFormService {
  createEnderecoDiscenteFormGroup(enderecoDiscente: EnderecoDiscenteFormGroupInput = { id: null }): EnderecoDiscenteFormGroup {
    const enderecoDiscenteRawValue = {
      ...this.getFormDefaults(),
      ...enderecoDiscente,
    };
    return new FormGroup<EnderecoDiscenteFormGroupContent>({
      id: new FormControl(
        { value: enderecoDiscenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      tipo: new FormControl(enderecoDiscenteRawValue.tipo, {
        validators: [Validators.required],
      }),
      bairro: new FormControl(enderecoDiscenteRawValue.bairro),
      rua: new FormControl(enderecoDiscenteRawValue.rua),
      numeroCasa: new FormControl(enderecoDiscenteRawValue.numeroCasa),
      codigoPostal: new FormControl(enderecoDiscenteRawValue.codigoPostal),
      latitude: new FormControl(enderecoDiscenteRawValue.latitude),
      longitude: new FormControl(enderecoDiscenteRawValue.longitude),
      pais: new FormControl(enderecoDiscenteRawValue.pais),
      provincia: new FormControl(enderecoDiscenteRawValue.provincia),
      municipio: new FormControl(enderecoDiscenteRawValue.municipio),
      discente: new FormControl(enderecoDiscenteRawValue.discente, {
        validators: [Validators.required],
      }),
    });
  }

  getEnderecoDiscente(form: EnderecoDiscenteFormGroup): IEnderecoDiscente | NewEnderecoDiscente {
    return form.getRawValue() as IEnderecoDiscente | NewEnderecoDiscente;
  }

  resetForm(form: EnderecoDiscenteFormGroup, enderecoDiscente: EnderecoDiscenteFormGroupInput): void {
    const enderecoDiscenteRawValue = { ...this.getFormDefaults(), ...enderecoDiscente };
    form.reset(
      {
        ...enderecoDiscenteRawValue,
        id: { value: enderecoDiscenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EnderecoDiscenteFormDefaults {
    return {
      id: null,
    };
  }
}
