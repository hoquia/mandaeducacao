import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMedidaDisciplinar, NewMedidaDisciplinar } from '../medida-disciplinar.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMedidaDisciplinar for edit and NewMedidaDisciplinarFormGroupInput for create.
 */
type MedidaDisciplinarFormGroupInput = IMedidaDisciplinar | PartialWithRequiredKeyOf<NewMedidaDisciplinar>;

type MedidaDisciplinarFormDefaults = Pick<NewMedidaDisciplinar, 'id'>;

type MedidaDisciplinarFormGroupContent = {
  id: FormControl<IMedidaDisciplinar['id'] | NewMedidaDisciplinar['id']>;
  descricao: FormControl<IMedidaDisciplinar['descricao']>;
  periodo: FormControl<IMedidaDisciplinar['periodo']>;
  suspensao: FormControl<IMedidaDisciplinar['suspensao']>;
  tempo: FormControl<IMedidaDisciplinar['tempo']>;
};

export type MedidaDisciplinarFormGroup = FormGroup<MedidaDisciplinarFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MedidaDisciplinarFormService {
  createMedidaDisciplinarFormGroup(medidaDisciplinar: MedidaDisciplinarFormGroupInput = { id: null }): MedidaDisciplinarFormGroup {
    const medidaDisciplinarRawValue = {
      ...this.getFormDefaults(),
      ...medidaDisciplinar,
    };
    return new FormGroup<MedidaDisciplinarFormGroupContent>({
      id: new FormControl(
        { value: medidaDisciplinarRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descricao: new FormControl(medidaDisciplinarRawValue.descricao, {
        validators: [Validators.required],
      }),
      periodo: new FormControl(medidaDisciplinarRawValue.periodo, {
        validators: [Validators.required],
      }),
      suspensao: new FormControl(medidaDisciplinarRawValue.suspensao, {
        validators: [Validators.required],
      }),
      tempo: new FormControl(medidaDisciplinarRawValue.tempo, {
        validators: [Validators.min(0)],
      }),
    });
  }

  getMedidaDisciplinar(form: MedidaDisciplinarFormGroup): IMedidaDisciplinar | NewMedidaDisciplinar {
    return form.getRawValue() as IMedidaDisciplinar | NewMedidaDisciplinar;
  }

  resetForm(form: MedidaDisciplinarFormGroup, medidaDisciplinar: MedidaDisciplinarFormGroupInput): void {
    const medidaDisciplinarRawValue = { ...this.getFormDefaults(), ...medidaDisciplinar };
    form.reset(
      {
        ...medidaDisciplinarRawValue,
        id: { value: medidaDisciplinarRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MedidaDisciplinarFormDefaults {
    return {
      id: null,
    };
  }
}
