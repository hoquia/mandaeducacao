import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPeriodoLancamentoNota, NewPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPeriodoLancamentoNota for edit and NewPeriodoLancamentoNotaFormGroupInput for create.
 */
type PeriodoLancamentoNotaFormGroupInput = IPeriodoLancamentoNota | PartialWithRequiredKeyOf<NewPeriodoLancamentoNota>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPeriodoLancamentoNota | NewPeriodoLancamentoNota> = Omit<T, 'de' | 'ate' | 'timestamp'> & {
  de?: string | null;
  ate?: string | null;
  timestamp?: string | null;
};

type PeriodoLancamentoNotaFormRawValue = FormValueOf<IPeriodoLancamentoNota>;

type NewPeriodoLancamentoNotaFormRawValue = FormValueOf<NewPeriodoLancamentoNota>;

type PeriodoLancamentoNotaFormDefaults = Pick<NewPeriodoLancamentoNota, 'id' | 'de' | 'ate' | 'timestamp' | 'classes'>;

type PeriodoLancamentoNotaFormGroupContent = {
  id: FormControl<PeriodoLancamentoNotaFormRawValue['id'] | NewPeriodoLancamentoNota['id']>;
  tipoAvaliacao: FormControl<PeriodoLancamentoNotaFormRawValue['tipoAvaliacao']>;
  de: FormControl<PeriodoLancamentoNotaFormRawValue['de']>;
  ate: FormControl<PeriodoLancamentoNotaFormRawValue['ate']>;
  timestamp: FormControl<PeriodoLancamentoNotaFormRawValue['timestamp']>;
  utilizador: FormControl<PeriodoLancamentoNotaFormRawValue['utilizador']>;
  classes: FormControl<PeriodoLancamentoNotaFormRawValue['classes']>;
};

export type PeriodoLancamentoNotaFormGroup = FormGroup<PeriodoLancamentoNotaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PeriodoLancamentoNotaFormService {
  createPeriodoLancamentoNotaFormGroup(
    periodoLancamentoNota: PeriodoLancamentoNotaFormGroupInput = { id: null }
  ): PeriodoLancamentoNotaFormGroup {
    const periodoLancamentoNotaRawValue = this.convertPeriodoLancamentoNotaToPeriodoLancamentoNotaRawValue({
      ...this.getFormDefaults(),
      ...periodoLancamentoNota,
    });
    return new FormGroup<PeriodoLancamentoNotaFormGroupContent>({
      id: new FormControl(
        { value: periodoLancamentoNotaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      tipoAvaliacao: new FormControl(periodoLancamentoNotaRawValue.tipoAvaliacao, {
        validators: [Validators.required],
      }),
      de: new FormControl(periodoLancamentoNotaRawValue.de, {
        validators: [Validators.required],
      }),
      ate: new FormControl(periodoLancamentoNotaRawValue.ate, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(periodoLancamentoNotaRawValue.timestamp),
      utilizador: new FormControl(periodoLancamentoNotaRawValue.utilizador),
      classes: new FormControl(periodoLancamentoNotaRawValue.classes ?? []),
    });
  }

  getPeriodoLancamentoNota(form: PeriodoLancamentoNotaFormGroup): IPeriodoLancamentoNota | NewPeriodoLancamentoNota {
    return this.convertPeriodoLancamentoNotaRawValueToPeriodoLancamentoNota(
      form.getRawValue() as PeriodoLancamentoNotaFormRawValue | NewPeriodoLancamentoNotaFormRawValue
    );
  }

  resetForm(form: PeriodoLancamentoNotaFormGroup, periodoLancamentoNota: PeriodoLancamentoNotaFormGroupInput): void {
    const periodoLancamentoNotaRawValue = this.convertPeriodoLancamentoNotaToPeriodoLancamentoNotaRawValue({
      ...this.getFormDefaults(),
      ...periodoLancamentoNota,
    });
    form.reset(
      {
        ...periodoLancamentoNotaRawValue,
        id: { value: periodoLancamentoNotaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PeriodoLancamentoNotaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      de: currentTime,
      ate: currentTime,
      timestamp: currentTime,
      classes: [],
    };
  }

  private convertPeriodoLancamentoNotaRawValueToPeriodoLancamentoNota(
    rawPeriodoLancamentoNota: PeriodoLancamentoNotaFormRawValue | NewPeriodoLancamentoNotaFormRawValue
  ): IPeriodoLancamentoNota | NewPeriodoLancamentoNota {
    return {
      ...rawPeriodoLancamentoNota,
      de: dayjs(rawPeriodoLancamentoNota.de, DATE_TIME_FORMAT),
      ate: dayjs(rawPeriodoLancamentoNota.ate, DATE_TIME_FORMAT),
      timestamp: dayjs(rawPeriodoLancamentoNota.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertPeriodoLancamentoNotaToPeriodoLancamentoNotaRawValue(
    periodoLancamentoNota: IPeriodoLancamentoNota | (Partial<NewPeriodoLancamentoNota> & PeriodoLancamentoNotaFormDefaults)
  ): PeriodoLancamentoNotaFormRawValue | PartialWithRequiredKeyOf<NewPeriodoLancamentoNotaFormRawValue> {
    return {
      ...periodoLancamentoNota,
      de: periodoLancamentoNota.de ? periodoLancamentoNota.de.format(DATE_TIME_FORMAT) : undefined,
      ate: periodoLancamentoNota.ate ? periodoLancamentoNota.ate.format(DATE_TIME_FORMAT) : undefined,
      timestamp: periodoLancamentoNota.timestamp ? periodoLancamentoNota.timestamp.format(DATE_TIME_FORMAT) : undefined,
      classes: periodoLancamentoNota.classes ?? [],
    };
  }
}
