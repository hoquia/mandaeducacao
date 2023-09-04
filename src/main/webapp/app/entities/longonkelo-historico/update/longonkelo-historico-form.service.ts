import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILongonkeloHistorico, NewLongonkeloHistorico } from '../longonkelo-historico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILongonkeloHistorico for edit and NewLongonkeloHistoricoFormGroupInput for create.
 */
type LongonkeloHistoricoFormGroupInput = ILongonkeloHistorico | PartialWithRequiredKeyOf<NewLongonkeloHistorico>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILongonkeloHistorico | NewLongonkeloHistorico> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type LongonkeloHistoricoFormRawValue = FormValueOf<ILongonkeloHistorico>;

type NewLongonkeloHistoricoFormRawValue = FormValueOf<NewLongonkeloHistorico>;

type LongonkeloHistoricoFormDefaults = Pick<NewLongonkeloHistorico, 'id' | 'timestamp'>;

type LongonkeloHistoricoFormGroupContent = {
  id: FormControl<LongonkeloHistoricoFormRawValue['id'] | NewLongonkeloHistorico['id']>;
  operacao: FormControl<LongonkeloHistoricoFormRawValue['operacao']>;
  entidadeNome: FormControl<LongonkeloHistoricoFormRawValue['entidadeNome']>;
  entidadeCodigo: FormControl<LongonkeloHistoricoFormRawValue['entidadeCodigo']>;
  payload: FormControl<LongonkeloHistoricoFormRawValue['payload']>;
  host: FormControl<LongonkeloHistoricoFormRawValue['host']>;
  hash: FormControl<LongonkeloHistoricoFormRawValue['hash']>;
  timestamp: FormControl<LongonkeloHistoricoFormRawValue['timestamp']>;
  utilizador: FormControl<LongonkeloHistoricoFormRawValue['utilizador']>;
};

export type LongonkeloHistoricoFormGroup = FormGroup<LongonkeloHistoricoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LongonkeloHistoricoFormService {
  createLongonkeloHistoricoFormGroup(longonkeloHistorico: LongonkeloHistoricoFormGroupInput = { id: null }): LongonkeloHistoricoFormGroup {
    const longonkeloHistoricoRawValue = this.convertLongonkeloHistoricoToLongonkeloHistoricoRawValue({
      ...this.getFormDefaults(),
      ...longonkeloHistorico,
    });
    return new FormGroup<LongonkeloHistoricoFormGroupContent>({
      id: new FormControl(
        { value: longonkeloHistoricoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      operacao: new FormControl(longonkeloHistoricoRawValue.operacao, {
        validators: [Validators.required],
      }),
      entidadeNome: new FormControl(longonkeloHistoricoRawValue.entidadeNome, {
        validators: [Validators.required],
      }),
      entidadeCodigo: new FormControl(longonkeloHistoricoRawValue.entidadeCodigo, {
        validators: [Validators.required],
      }),
      payload: new FormControl(longonkeloHistoricoRawValue.payload, {
        validators: [Validators.required],
      }),
      host: new FormControl(longonkeloHistoricoRawValue.host, {
        validators: [Validators.required],
      }),
      hash: new FormControl(longonkeloHistoricoRawValue.hash),
      timestamp: new FormControl(longonkeloHistoricoRawValue.timestamp, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(longonkeloHistoricoRawValue.utilizador),
    });
  }

  getLongonkeloHistorico(form: LongonkeloHistoricoFormGroup): ILongonkeloHistorico | NewLongonkeloHistorico {
    return this.convertLongonkeloHistoricoRawValueToLongonkeloHistorico(
      form.getRawValue() as LongonkeloHistoricoFormRawValue | NewLongonkeloHistoricoFormRawValue
    );
  }

  resetForm(form: LongonkeloHistoricoFormGroup, longonkeloHistorico: LongonkeloHistoricoFormGroupInput): void {
    const longonkeloHistoricoRawValue = this.convertLongonkeloHistoricoToLongonkeloHistoricoRawValue({
      ...this.getFormDefaults(),
      ...longonkeloHistorico,
    });
    form.reset(
      {
        ...longonkeloHistoricoRawValue,
        id: { value: longonkeloHistoricoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LongonkeloHistoricoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertLongonkeloHistoricoRawValueToLongonkeloHistorico(
    rawLongonkeloHistorico: LongonkeloHistoricoFormRawValue | NewLongonkeloHistoricoFormRawValue
  ): ILongonkeloHistorico | NewLongonkeloHistorico {
    return {
      ...rawLongonkeloHistorico,
      timestamp: dayjs(rawLongonkeloHistorico.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertLongonkeloHistoricoToLongonkeloHistoricoRawValue(
    longonkeloHistorico: ILongonkeloHistorico | (Partial<NewLongonkeloHistorico> & LongonkeloHistoricoFormDefaults)
  ): LongonkeloHistoricoFormRawValue | PartialWithRequiredKeyOf<NewLongonkeloHistoricoFormRawValue> {
    return {
      ...longonkeloHistorico,
      timestamp: longonkeloHistorico.timestamp ? longonkeloHistorico.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
