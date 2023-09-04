import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRecibo, NewRecibo } from '../recibo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRecibo for edit and NewReciboFormGroupInput for create.
 */
type ReciboFormGroupInput = IRecibo | PartialWithRequiredKeyOf<NewRecibo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRecibo | NewRecibo> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ReciboFormRawValue = FormValueOf<IRecibo>;

type NewReciboFormRawValue = FormValueOf<NewRecibo>;

type ReciboFormDefaults = Pick<NewRecibo, 'id' | 'isNovo' | 'timestamp' | 'isFiscalizado'>;

type ReciboFormGroupContent = {
  id: FormControl<ReciboFormRawValue['id'] | NewRecibo['id']>;
  data: FormControl<ReciboFormRawValue['data']>;
  vencimento: FormControl<ReciboFormRawValue['vencimento']>;
  numero: FormControl<ReciboFormRawValue['numero']>;
  totalSemImposto: FormControl<ReciboFormRawValue['totalSemImposto']>;
  totalComImposto: FormControl<ReciboFormRawValue['totalComImposto']>;
  totalDescontoComercial: FormControl<ReciboFormRawValue['totalDescontoComercial']>;
  totalDescontoFinanceiro: FormControl<ReciboFormRawValue['totalDescontoFinanceiro']>;
  totalIVA: FormControl<ReciboFormRawValue['totalIVA']>;
  totalRetencao: FormControl<ReciboFormRawValue['totalRetencao']>;
  totalJuro: FormControl<ReciboFormRawValue['totalJuro']>;
  cambio: FormControl<ReciboFormRawValue['cambio']>;
  totalMoedaEstrangeira: FormControl<ReciboFormRawValue['totalMoedaEstrangeira']>;
  totalPagar: FormControl<ReciboFormRawValue['totalPagar']>;
  totalPago: FormControl<ReciboFormRawValue['totalPago']>;
  totalFalta: FormControl<ReciboFormRawValue['totalFalta']>;
  totalTroco: FormControl<ReciboFormRawValue['totalTroco']>;
  isNovo: FormControl<ReciboFormRawValue['isNovo']>;
  timestamp: FormControl<ReciboFormRawValue['timestamp']>;
  descricao: FormControl<ReciboFormRawValue['descricao']>;
  debito: FormControl<ReciboFormRawValue['debito']>;
  credito: FormControl<ReciboFormRawValue['credito']>;
  isFiscalizado: FormControl<ReciboFormRawValue['isFiscalizado']>;
  signText: FormControl<ReciboFormRawValue['signText']>;
  hash: FormControl<ReciboFormRawValue['hash']>;
  hashShort: FormControl<ReciboFormRawValue['hashShort']>;
  hashControl: FormControl<ReciboFormRawValue['hashControl']>;
  keyVersion: FormControl<ReciboFormRawValue['keyVersion']>;
  estado: FormControl<ReciboFormRawValue['estado']>;
  origem: FormControl<ReciboFormRawValue['origem']>;
  utilizador: FormControl<ReciboFormRawValue['utilizador']>;
  matricula: FormControl<ReciboFormRawValue['matricula']>;
  documentoComercial: FormControl<ReciboFormRawValue['documentoComercial']>;
  transacao: FormControl<ReciboFormRawValue['transacao']>;
};

export type ReciboFormGroup = FormGroup<ReciboFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReciboFormService {
  createReciboFormGroup(recibo: ReciboFormGroupInput = { id: null }): ReciboFormGroup {
    const reciboRawValue = this.convertReciboToReciboRawValue({
      ...this.getFormDefaults(),
      ...recibo,
    });
    return new FormGroup<ReciboFormGroupContent>({
      id: new FormControl(
        { value: reciboRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      data: new FormControl(reciboRawValue.data, {
        validators: [Validators.required],
      }),
      vencimento: new FormControl(reciboRawValue.vencimento),
      numero: new FormControl(reciboRawValue.numero, {
        validators: [Validators.required],
      }),
      totalSemImposto: new FormControl(reciboRawValue.totalSemImposto, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalComImposto: new FormControl(reciboRawValue.totalComImposto, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalDescontoComercial: new FormControl(reciboRawValue.totalDescontoComercial, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalDescontoFinanceiro: new FormControl(reciboRawValue.totalDescontoFinanceiro, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalIVA: new FormControl(reciboRawValue.totalIVA, {
        validators: [Validators.required],
      }),
      totalRetencao: new FormControl(reciboRawValue.totalRetencao, {
        validators: [Validators.required],
      }),
      totalJuro: new FormControl(reciboRawValue.totalJuro, {
        validators: [Validators.required],
      }),
      cambio: new FormControl(reciboRawValue.cambio, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalMoedaEstrangeira: new FormControl(reciboRawValue.totalMoedaEstrangeira, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalPagar: new FormControl(reciboRawValue.totalPagar, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalPago: new FormControl(reciboRawValue.totalPago, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalFalta: new FormControl(reciboRawValue.totalFalta, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalTroco: new FormControl(reciboRawValue.totalTroco, {
        validators: [Validators.required, Validators.min(0)],
      }),
      isNovo: new FormControl(reciboRawValue.isNovo),
      timestamp: new FormControl(reciboRawValue.timestamp, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(reciboRawValue.descricao),
      debito: new FormControl(reciboRawValue.debito, {
        validators: [Validators.min(0)],
      }),
      credito: new FormControl(reciboRawValue.credito, {
        validators: [Validators.min(0)],
      }),
      isFiscalizado: new FormControl(reciboRawValue.isFiscalizado),
      signText: new FormControl(reciboRawValue.signText),
      hash: new FormControl(reciboRawValue.hash, {
        validators: [Validators.maxLength(172)],
      }),
      hashShort: new FormControl(reciboRawValue.hashShort),
      hashControl: new FormControl(reciboRawValue.hashControl, {
        validators: [Validators.maxLength(70)],
      }),
      keyVersion: new FormControl(reciboRawValue.keyVersion),
      estado: new FormControl(reciboRawValue.estado, {
        validators: [Validators.required],
      }),
      origem: new FormControl(reciboRawValue.origem, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(reciboRawValue.utilizador),
      matricula: new FormControl(reciboRawValue.matricula, {
        validators: [Validators.required],
      }),
      documentoComercial: new FormControl(reciboRawValue.documentoComercial, {
        validators: [Validators.required],
      }),
      transacao: new FormControl(reciboRawValue.transacao, {
        validators: [Validators.required],
      }),
    });
  }

  getRecibo(form: ReciboFormGroup): IRecibo | NewRecibo {
    return this.convertReciboRawValueToRecibo(form.getRawValue() as ReciboFormRawValue | NewReciboFormRawValue);
  }

  resetForm(form: ReciboFormGroup, recibo: ReciboFormGroupInput): void {
    const reciboRawValue = this.convertReciboToReciboRawValue({ ...this.getFormDefaults(), ...recibo });
    form.reset(
      {
        ...reciboRawValue,
        id: { value: reciboRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReciboFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isNovo: false,
      timestamp: currentTime,
      isFiscalizado: false,
    };
  }

  private convertReciboRawValueToRecibo(rawRecibo: ReciboFormRawValue | NewReciboFormRawValue): IRecibo | NewRecibo {
    return {
      ...rawRecibo,
      timestamp: dayjs(rawRecibo.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertReciboToReciboRawValue(
    recibo: IRecibo | (Partial<NewRecibo> & ReciboFormDefaults)
  ): ReciboFormRawValue | PartialWithRequiredKeyOf<NewReciboFormRawValue> {
    return {
      ...recibo,
      timestamp: recibo.timestamp ? recibo.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
