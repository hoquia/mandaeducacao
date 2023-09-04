import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPeriodoHorario, NewPeriodoHorario } from '../periodo-horario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPeriodoHorario for edit and NewPeriodoHorarioFormGroupInput for create.
 */
type PeriodoHorarioFormGroupInput = IPeriodoHorario | PartialWithRequiredKeyOf<NewPeriodoHorario>;

type PeriodoHorarioFormDefaults = Pick<NewPeriodoHorario, 'id'>;

type PeriodoHorarioFormGroupContent = {
  id: FormControl<IPeriodoHorario['id'] | NewPeriodoHorario['id']>;
  descricao: FormControl<IPeriodoHorario['descricao']>;
  tempo: FormControl<IPeriodoHorario['tempo']>;
  inicio: FormControl<IPeriodoHorario['inicio']>;
  fim: FormControl<IPeriodoHorario['fim']>;
  turno: FormControl<IPeriodoHorario['turno']>;
};

export type PeriodoHorarioFormGroup = FormGroup<PeriodoHorarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PeriodoHorarioFormService {
  createPeriodoHorarioFormGroup(periodoHorario: PeriodoHorarioFormGroupInput = { id: null }): PeriodoHorarioFormGroup {
    const periodoHorarioRawValue = {
      ...this.getFormDefaults(),
      ...periodoHorario,
    };
    return new FormGroup<PeriodoHorarioFormGroupContent>({
      id: new FormControl(
        { value: periodoHorarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descricao: new FormControl(periodoHorarioRawValue.descricao, {
        validators: [Validators.required],
      }),
      tempo: new FormControl(periodoHorarioRawValue.tempo, {
        validators: [Validators.required, Validators.min(1)],
      }),
      inicio: new FormControl(periodoHorarioRawValue.inicio, {
        validators: [Validators.required],
      }),
      fim: new FormControl(periodoHorarioRawValue.fim, {
        validators: [Validators.required],
      }),
      turno: new FormControl(periodoHorarioRawValue.turno, {
        validators: [Validators.required],
      }),
    });
  }

  getPeriodoHorario(form: PeriodoHorarioFormGroup): IPeriodoHorario | NewPeriodoHorario {
    return form.getRawValue() as IPeriodoHorario | NewPeriodoHorario;
  }

  resetForm(form: PeriodoHorarioFormGroup, periodoHorario: PeriodoHorarioFormGroupInput): void {
    const periodoHorarioRawValue = { ...this.getFormDefaults(), ...periodoHorario };
    form.reset(
      {
        ...periodoHorarioRawValue,
        id: { value: periodoHorarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PeriodoHorarioFormDefaults {
    return {
      id: null,
    };
  }
}
