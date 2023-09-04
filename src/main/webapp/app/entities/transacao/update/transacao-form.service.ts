import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransacao, NewTransacao } from '../transacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITransacao for edit and NewTransacaoFormGroupInput for create.
 */
type TransacaoFormGroupInput = ITransacao | PartialWithRequiredKeyOf<NewTransacao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITransacao | NewTransacao> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type TransacaoFormRawValue = FormValueOf<ITransacao>;

type NewTransacaoFormRawValue = FormValueOf<NewTransacao>;

type TransacaoFormDefaults = Pick<NewTransacao, 'id' | 'timestamp' | 'transferenciaSaldos'>;

type TransacaoFormGroupContent = {
  id: FormControl<TransacaoFormRawValue['id'] | NewTransacao['id']>;
  montante: FormControl<TransacaoFormRawValue['montante']>;
  data: FormControl<TransacaoFormRawValue['data']>;
  referencia: FormControl<TransacaoFormRawValue['referencia']>;
  estado: FormControl<TransacaoFormRawValue['estado']>;
  saldo: FormControl<TransacaoFormRawValue['saldo']>;
  anexo: FormControl<TransacaoFormRawValue['anexo']>;
  anexoContentType: FormControl<TransacaoFormRawValue['anexoContentType']>;
  timestamp: FormControl<TransacaoFormRawValue['timestamp']>;
  utilizador: FormControl<TransacaoFormRawValue['utilizador']>;
  moeda: FormControl<TransacaoFormRawValue['moeda']>;
  matricula: FormControl<TransacaoFormRawValue['matricula']>;
  meioPagamento: FormControl<TransacaoFormRawValue['meioPagamento']>;
  conta: FormControl<TransacaoFormRawValue['conta']>;
  transferenciaSaldos: FormControl<TransacaoFormRawValue['transferenciaSaldos']>;
};

export type TransacaoFormGroup = FormGroup<TransacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TransacaoFormService {
  createTransacaoFormGroup(transacao: TransacaoFormGroupInput = { id: null }): TransacaoFormGroup {
    const transacaoRawValue = this.convertTransacaoToTransacaoRawValue({
      ...this.getFormDefaults(),
      ...transacao,
    });
    return new FormGroup<TransacaoFormGroupContent>({
      id: new FormControl(
        { value: transacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      montante: new FormControl(transacaoRawValue.montante, {
        validators: [Validators.required, Validators.min(0)],
      }),
      data: new FormControl(transacaoRawValue.data, {
        validators: [Validators.required],
      }),
      referencia: new FormControl(transacaoRawValue.referencia, {
        validators: [Validators.required],
      }),
      estado: new FormControl(transacaoRawValue.estado, {
        validators: [Validators.required],
      }),
      saldo: new FormControl(transacaoRawValue.saldo, {
        validators: [Validators.required, Validators.min(0)],
      }),
      anexo: new FormControl(transacaoRawValue.anexo),
      anexoContentType: new FormControl(transacaoRawValue.anexoContentType),
      timestamp: new FormControl(transacaoRawValue.timestamp),
      utilizador: new FormControl(transacaoRawValue.utilizador),
      moeda: new FormControl(transacaoRawValue.moeda),
      matricula: new FormControl(transacaoRawValue.matricula, {
        validators: [Validators.required],
      }),
      meioPagamento: new FormControl(transacaoRawValue.meioPagamento, {
        validators: [Validators.required],
      }),
      conta: new FormControl(transacaoRawValue.conta, {
        validators: [Validators.required],
      }),
      transferenciaSaldos: new FormControl(transacaoRawValue.transferenciaSaldos ?? []),
    });
  }

  getTransacao(form: TransacaoFormGroup): ITransacao | NewTransacao {
    return this.convertTransacaoRawValueToTransacao(form.getRawValue() as TransacaoFormRawValue | NewTransacaoFormRawValue);
  }

  resetForm(form: TransacaoFormGroup, transacao: TransacaoFormGroupInput): void {
    const transacaoRawValue = this.convertTransacaoToTransacaoRawValue({ ...this.getFormDefaults(), ...transacao });
    form.reset(
      {
        ...transacaoRawValue,
        id: { value: transacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TransacaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
      transferenciaSaldos: [],
    };
  }

  private convertTransacaoRawValueToTransacao(rawTransacao: TransacaoFormRawValue | NewTransacaoFormRawValue): ITransacao | NewTransacao {
    return {
      ...rawTransacao,
      timestamp: dayjs(rawTransacao.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertTransacaoToTransacaoRawValue(
    transacao: ITransacao | (Partial<NewTransacao> & TransacaoFormDefaults)
  ): TransacaoFormRawValue | PartialWithRequiredKeyOf<NewTransacaoFormRawValue> {
    return {
      ...transacao,
      timestamp: transacao.timestamp ? transacao.timestamp.format(DATE_TIME_FORMAT) : undefined,
      transferenciaSaldos: transacao.transferenciaSaldos ?? [],
    };
  }
}
