import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHorario, NewHorario } from '../horario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHorario for edit and NewHorarioFormGroupInput for create.
 */
type HorarioFormGroupInput = IHorario | PartialWithRequiredKeyOf<NewHorario>;

type HorarioFormDefaults = Pick<NewHorario, 'id'>;

type HorarioFormGroupContent = {
  id: FormControl<IHorario['id'] | NewHorario['id']>;
  chaveComposta1: FormControl<IHorario['chaveComposta1']>;
  chaveComposta2: FormControl<IHorario['chaveComposta2']>;
  diaSemana: FormControl<IHorario['diaSemana']>;
  utilizador: FormControl<IHorario['utilizador']>;
  turma: FormControl<IHorario['turma']>;
  referencia: FormControl<IHorario['referencia']>;
  periodo: FormControl<IHorario['periodo']>;
  docente: FormControl<IHorario['docente']>;
  disciplinaCurricular: FormControl<IHorario['disciplinaCurricular']>;
};

export type HorarioFormGroup = FormGroup<HorarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HorarioFormService {
  createHorarioFormGroup(horario: HorarioFormGroupInput = { id: null }): HorarioFormGroup {
    const horarioRawValue = {
      ...this.getFormDefaults(),
      ...horario,
    };
    return new FormGroup<HorarioFormGroupContent>({
      id: new FormControl(
        { value: horarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chaveComposta1: new FormControl(horarioRawValue.chaveComposta1),
      chaveComposta2: new FormControl(horarioRawValue.chaveComposta2),
      diaSemana: new FormControl(horarioRawValue.diaSemana, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(horarioRawValue.utilizador),
      turma: new FormControl(horarioRawValue.turma, {
        validators: [Validators.required],
      }),
      referencia: new FormControl(horarioRawValue.referencia),
      periodo: new FormControl(horarioRawValue.periodo, {
        validators: [Validators.required],
      }),
      docente: new FormControl(horarioRawValue.docente, {
        validators: [Validators.required],
      }),
      disciplinaCurricular: new FormControl(horarioRawValue.disciplinaCurricular, {
        validators: [Validators.required],
      }),
    });
  }

  getHorario(form: HorarioFormGroup): IHorario | NewHorario {
    return form.getRawValue() as IHorario | NewHorario;
  }

  resetForm(form: HorarioFormGroup, horario: HorarioFormGroupInput): void {
    const horarioRawValue = { ...this.getFormDefaults(), ...horario };
    form.reset(
      {
        ...horarioRawValue,
        id: { value: horarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HorarioFormDefaults {
    return {
      id: null,
    };
  }
}
