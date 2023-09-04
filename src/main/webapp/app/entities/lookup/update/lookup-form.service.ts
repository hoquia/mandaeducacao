import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILookup, NewLookup } from '../lookup.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILookup for edit and NewLookupFormGroupInput for create.
 */
type LookupFormGroupInput = ILookup | PartialWithRequiredKeyOf<NewLookup>;

type LookupFormDefaults = Pick<NewLookup, 'id' | 'isSistema' | 'isModificavel'>;

type LookupFormGroupContent = {
  id: FormControl<ILookup['id'] | NewLookup['id']>;
  codigo: FormControl<ILookup['codigo']>;
  nome: FormControl<ILookup['nome']>;
  descricao: FormControl<ILookup['descricao']>;
  isSistema: FormControl<ILookup['isSistema']>;
  isModificavel: FormControl<ILookup['isModificavel']>;
};

export type LookupFormGroup = FormGroup<LookupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LookupFormService {
  createLookupFormGroup(lookup: LookupFormGroupInput = { id: null }): LookupFormGroup {
    const lookupRawValue = {
      ...this.getFormDefaults(),
      ...lookup,
    };
    return new FormGroup<LookupFormGroupContent>({
      id: new FormControl(
        { value: lookupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codigo: new FormControl(lookupRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(lookupRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(lookupRawValue.descricao),
      isSistema: new FormControl(lookupRawValue.isSistema),
      isModificavel: new FormControl(lookupRawValue.isModificavel),
    });
  }

  getLookup(form: LookupFormGroup): ILookup | NewLookup {
    return form.getRawValue() as ILookup | NewLookup;
  }

  resetForm(form: LookupFormGroup, lookup: LookupFormGroupInput): void {
    const lookupRawValue = { ...this.getFormDefaults(), ...lookup };
    form.reset(
      {
        ...lookupRawValue,
        id: { value: lookupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LookupFormDefaults {
    return {
      id: null,
      isSistema: false,
      isModificavel: false,
    };
  }
}
