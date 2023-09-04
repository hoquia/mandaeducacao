import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILookupItem, NewLookupItem } from '../lookup-item.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILookupItem for edit and NewLookupItemFormGroupInput for create.
 */
type LookupItemFormGroupInput = ILookupItem | PartialWithRequiredKeyOf<NewLookupItem>;

type LookupItemFormDefaults = Pick<NewLookupItem, 'id' | 'isSistema'>;

type LookupItemFormGroupContent = {
  id: FormControl<ILookupItem['id'] | NewLookupItem['id']>;
  codigo: FormControl<ILookupItem['codigo']>;
  ordem: FormControl<ILookupItem['ordem']>;
  isSistema: FormControl<ILookupItem['isSistema']>;
  descricao: FormControl<ILookupItem['descricao']>;
  lookup: FormControl<ILookupItem['lookup']>;
};

export type LookupItemFormGroup = FormGroup<LookupItemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LookupItemFormService {
  createLookupItemFormGroup(lookupItem: LookupItemFormGroupInput = { id: null }): LookupItemFormGroup {
    const lookupItemRawValue = {
      ...this.getFormDefaults(),
      ...lookupItem,
    };
    return new FormGroup<LookupItemFormGroupContent>({
      id: new FormControl(
        { value: lookupItemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codigo: new FormControl(lookupItemRawValue.codigo),
      ordem: new FormControl(lookupItemRawValue.ordem, {
        validators: [Validators.min(0)],
      }),
      isSistema: new FormControl(lookupItemRawValue.isSistema),
      descricao: new FormControl(lookupItemRawValue.descricao, {
        validators: [Validators.required],
      }),
      lookup: new FormControl(lookupItemRawValue.lookup, {
        validators: [Validators.required],
      }),
    });
  }

  getLookupItem(form: LookupItemFormGroup): ILookupItem | NewLookupItem {
    return form.getRawValue() as ILookupItem | NewLookupItem;
  }

  resetForm(form: LookupItemFormGroup, lookupItem: LookupItemFormGroupInput): void {
    const lookupItemRawValue = { ...this.getFormDefaults(), ...lookupItem };
    form.reset(
      {
        ...lookupItemRawValue,
        id: { value: lookupItemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LookupItemFormDefaults {
    return {
      id: null,
      isSistema: false,
    };
  }
}
