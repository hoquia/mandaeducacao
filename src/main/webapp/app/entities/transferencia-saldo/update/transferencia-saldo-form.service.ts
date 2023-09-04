import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransferenciaSaldo, NewTransferenciaSaldo } from '../transferencia-saldo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITransferenciaSaldo for edit and NewTransferenciaSaldoFormGroupInput for create.
 */
type TransferenciaSaldoFormGroupInput = ITransferenciaSaldo | PartialWithRequiredKeyOf<NewTransferenciaSaldo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITransferenciaSaldo | NewTransferenciaSaldo> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type TransferenciaSaldoFormRawValue = FormValueOf<ITransferenciaSaldo>;

type NewTransferenciaSaldoFormRawValue = FormValueOf<NewTransferenciaSaldo>;

type TransferenciaSaldoFormDefaults = Pick<NewTransferenciaSaldo, 'id' | 'isMesmaConta' | 'timestamp' | 'transacoes'>;

type TransferenciaSaldoFormGroupContent = {
  id: FormControl<TransferenciaSaldoFormRawValue['id'] | NewTransferenciaSaldo['id']>;
  montante: FormControl<TransferenciaSaldoFormRawValue['montante']>;
  isMesmaConta: FormControl<TransferenciaSaldoFormRawValue['isMesmaConta']>;
  descricao: FormControl<TransferenciaSaldoFormRawValue['descricao']>;
  timestamp: FormControl<TransferenciaSaldoFormRawValue['timestamp']>;
  de: FormControl<TransferenciaSaldoFormRawValue['de']>;
  para: FormControl<TransferenciaSaldoFormRawValue['para']>;
  utilizador: FormControl<TransferenciaSaldoFormRawValue['utilizador']>;
  motivoTransferencia: FormControl<TransferenciaSaldoFormRawValue['motivoTransferencia']>;
  transacoes: FormControl<TransferenciaSaldoFormRawValue['transacoes']>;
};

export type TransferenciaSaldoFormGroup = FormGroup<TransferenciaSaldoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TransferenciaSaldoFormService {
  createTransferenciaSaldoFormGroup(transferenciaSaldo: TransferenciaSaldoFormGroupInput = { id: null }): TransferenciaSaldoFormGroup {
    const transferenciaSaldoRawValue = this.convertTransferenciaSaldoToTransferenciaSaldoRawValue({
      ...this.getFormDefaults(),
      ...transferenciaSaldo,
    });
    return new FormGroup<TransferenciaSaldoFormGroupContent>({
      id: new FormControl(
        { value: transferenciaSaldoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      montante: new FormControl(transferenciaSaldoRawValue.montante, {
        validators: [Validators.min(0)],
      }),
      isMesmaConta: new FormControl(transferenciaSaldoRawValue.isMesmaConta),
      descricao: new FormControl(transferenciaSaldoRawValue.descricao),
      timestamp: new FormControl(transferenciaSaldoRawValue.timestamp),
      de: new FormControl(transferenciaSaldoRawValue.de, {
        validators: [Validators.required],
      }),
      para: new FormControl(transferenciaSaldoRawValue.para, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(transferenciaSaldoRawValue.utilizador),
      motivoTransferencia: new FormControl(transferenciaSaldoRawValue.motivoTransferencia),
      transacoes: new FormControl(transferenciaSaldoRawValue.transacoes ?? []),
    });
  }

  getTransferenciaSaldo(form: TransferenciaSaldoFormGroup): ITransferenciaSaldo | NewTransferenciaSaldo {
    return this.convertTransferenciaSaldoRawValueToTransferenciaSaldo(
      form.getRawValue() as TransferenciaSaldoFormRawValue | NewTransferenciaSaldoFormRawValue
    );
  }

  resetForm(form: TransferenciaSaldoFormGroup, transferenciaSaldo: TransferenciaSaldoFormGroupInput): void {
    const transferenciaSaldoRawValue = this.convertTransferenciaSaldoToTransferenciaSaldoRawValue({
      ...this.getFormDefaults(),
      ...transferenciaSaldo,
    });
    form.reset(
      {
        ...transferenciaSaldoRawValue,
        id: { value: transferenciaSaldoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TransferenciaSaldoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isMesmaConta: false,
      timestamp: currentTime,
      transacoes: [],
    };
  }

  private convertTransferenciaSaldoRawValueToTransferenciaSaldo(
    rawTransferenciaSaldo: TransferenciaSaldoFormRawValue | NewTransferenciaSaldoFormRawValue
  ): ITransferenciaSaldo | NewTransferenciaSaldo {
    return {
      ...rawTransferenciaSaldo,
      timestamp: dayjs(rawTransferenciaSaldo.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertTransferenciaSaldoToTransferenciaSaldoRawValue(
    transferenciaSaldo: ITransferenciaSaldo | (Partial<NewTransferenciaSaldo> & TransferenciaSaldoFormDefaults)
  ): TransferenciaSaldoFormRawValue | PartialWithRequiredKeyOf<NewTransferenciaSaldoFormRawValue> {
    return {
      ...transferenciaSaldo,
      timestamp: transferenciaSaldo.timestamp ? transferenciaSaldo.timestamp.format(DATE_TIME_FORMAT) : undefined,
      transacoes: transferenciaSaldo.transacoes ?? [],
    };
  }
}
