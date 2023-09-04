import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INaturezaTrabalho, NewNaturezaTrabalho } from '../natureza-trabalho.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INaturezaTrabalho for edit and NewNaturezaTrabalhoFormGroupInput for create.
 */
type NaturezaTrabalhoFormGroupInput = INaturezaTrabalho | PartialWithRequiredKeyOf<NewNaturezaTrabalho>;

type NaturezaTrabalhoFormDefaults = Pick<NewNaturezaTrabalho, 'id' | 'isActivo'>;

type NaturezaTrabalhoFormGroupContent = {
  id: FormControl<INaturezaTrabalho['id'] | NewNaturezaTrabalho['id']>;
  nome: FormControl<INaturezaTrabalho['nome']>;
  descricao: FormControl<INaturezaTrabalho['descricao']>;
  isActivo: FormControl<INaturezaTrabalho['isActivo']>;
};

export type NaturezaTrabalhoFormGroup = FormGroup<NaturezaTrabalhoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NaturezaTrabalhoFormService {
  createNaturezaTrabalhoFormGroup(naturezaTrabalho: NaturezaTrabalhoFormGroupInput = { id: null }): NaturezaTrabalhoFormGroup {
    const naturezaTrabalhoRawValue = {
      ...this.getFormDefaults(),
      ...naturezaTrabalho,
    };
    return new FormGroup<NaturezaTrabalhoFormGroupContent>({
      id: new FormControl(
        { value: naturezaTrabalhoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nome: new FormControl(naturezaTrabalhoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(naturezaTrabalhoRawValue.descricao),
      isActivo: new FormControl(naturezaTrabalhoRawValue.isActivo),
    });
  }

  getNaturezaTrabalho(form: NaturezaTrabalhoFormGroup): INaturezaTrabalho | NewNaturezaTrabalho {
    return form.getRawValue() as INaturezaTrabalho | NewNaturezaTrabalho;
  }

  resetForm(form: NaturezaTrabalhoFormGroup, naturezaTrabalho: NaturezaTrabalhoFormGroupInput): void {
    const naturezaTrabalhoRawValue = { ...this.getFormDefaults(), ...naturezaTrabalho };
    form.reset(
      {
        ...naturezaTrabalhoRawValue,
        id: { value: naturezaTrabalhoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NaturezaTrabalhoFormDefaults {
    return {
      id: null,
      isActivo: false,
    };
  }
}
