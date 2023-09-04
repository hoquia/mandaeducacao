import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITurno, NewTurno } from '../turno.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITurno for edit and NewTurnoFormGroupInput for create.
 */
type TurnoFormGroupInput = ITurno | PartialWithRequiredKeyOf<NewTurno>;

type TurnoFormDefaults = Pick<NewTurno, 'id'>;

type TurnoFormGroupContent = {
  id: FormControl<ITurno['id'] | NewTurno['id']>;
  codigo: FormControl<ITurno['codigo']>;
  nome: FormControl<ITurno['nome']>;
};

export type TurnoFormGroup = FormGroup<TurnoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TurnoFormService {
  createTurnoFormGroup(turno: TurnoFormGroupInput = { id: null }): TurnoFormGroup {
    const turnoRawValue = {
      ...this.getFormDefaults(),
      ...turno,
    };
    return new FormGroup<TurnoFormGroupContent>({
      id: new FormControl(
        { value: turnoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codigo: new FormControl(turnoRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(turnoRawValue.nome, {
        validators: [Validators.required],
      }),
    });
  }

  getTurno(form: TurnoFormGroup): ITurno | NewTurno {
    return form.getRawValue() as ITurno | NewTurno;
  }

  resetForm(form: TurnoFormGroup, turno: TurnoFormGroupInput): void {
    const turnoRawValue = { ...this.getFormDefaults(), ...turno };
    form.reset(
      {
        ...turnoRawValue,
        id: { value: turnoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TurnoFormDefaults {
    return {
      id: null,
    };
  }
}
