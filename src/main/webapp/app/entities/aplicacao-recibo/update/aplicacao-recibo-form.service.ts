import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAplicacaoRecibo, NewAplicacaoRecibo } from '../aplicacao-recibo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAplicacaoRecibo for edit and NewAplicacaoReciboFormGroupInput for create.
 */
type AplicacaoReciboFormGroupInput = IAplicacaoRecibo | PartialWithRequiredKeyOf<NewAplicacaoRecibo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAplicacaoRecibo | NewAplicacaoRecibo> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type AplicacaoReciboFormRawValue = FormValueOf<IAplicacaoRecibo>;

type NewAplicacaoReciboFormRawValue = FormValueOf<NewAplicacaoRecibo>;

type AplicacaoReciboFormDefaults = Pick<NewAplicacaoRecibo, 'id' | 'timestamp'>;

type AplicacaoReciboFormGroupContent = {
  id: FormControl<AplicacaoReciboFormRawValue['id'] | NewAplicacaoRecibo['id']>;
  totalFactura: FormControl<AplicacaoReciboFormRawValue['totalFactura']>;
  totalPago: FormControl<AplicacaoReciboFormRawValue['totalPago']>;
  totalDiferenca: FormControl<AplicacaoReciboFormRawValue['totalDiferenca']>;
  timestamp: FormControl<AplicacaoReciboFormRawValue['timestamp']>;
  itemFactura: FormControl<AplicacaoReciboFormRawValue['itemFactura']>;
  factura: FormControl<AplicacaoReciboFormRawValue['factura']>;
  recibo: FormControl<AplicacaoReciboFormRawValue['recibo']>;
};

export type AplicacaoReciboFormGroup = FormGroup<AplicacaoReciboFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AplicacaoReciboFormService {
  createAplicacaoReciboFormGroup(aplicacaoRecibo: AplicacaoReciboFormGroupInput = { id: null }): AplicacaoReciboFormGroup {
    const aplicacaoReciboRawValue = this.convertAplicacaoReciboToAplicacaoReciboRawValue({
      ...this.getFormDefaults(),
      ...aplicacaoRecibo,
    });
    return new FormGroup<AplicacaoReciboFormGroupContent>({
      id: new FormControl(
        { value: aplicacaoReciboRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      totalFactura: new FormControl(aplicacaoReciboRawValue.totalFactura, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalPago: new FormControl(aplicacaoReciboRawValue.totalPago, {
        validators: [Validators.required, Validators.min(0)],
      }),
      totalDiferenca: new FormControl(aplicacaoReciboRawValue.totalDiferenca, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(aplicacaoReciboRawValue.timestamp),
      itemFactura: new FormControl(aplicacaoReciboRawValue.itemFactura),
      factura: new FormControl(aplicacaoReciboRawValue.factura, {
        validators: [Validators.required],
      }),
      recibo: new FormControl(aplicacaoReciboRawValue.recibo, {
        validators: [Validators.required],
      }),
    });
  }

  getAplicacaoRecibo(form: AplicacaoReciboFormGroup): IAplicacaoRecibo | NewAplicacaoRecibo {
    return this.convertAplicacaoReciboRawValueToAplicacaoRecibo(
      form.getRawValue() as AplicacaoReciboFormRawValue | NewAplicacaoReciboFormRawValue
    );
  }

  resetForm(form: AplicacaoReciboFormGroup, aplicacaoRecibo: AplicacaoReciboFormGroupInput): void {
    const aplicacaoReciboRawValue = this.convertAplicacaoReciboToAplicacaoReciboRawValue({ ...this.getFormDefaults(), ...aplicacaoRecibo });
    form.reset(
      {
        ...aplicacaoReciboRawValue,
        id: { value: aplicacaoReciboRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AplicacaoReciboFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertAplicacaoReciboRawValueToAplicacaoRecibo(
    rawAplicacaoRecibo: AplicacaoReciboFormRawValue | NewAplicacaoReciboFormRawValue
  ): IAplicacaoRecibo | NewAplicacaoRecibo {
    return {
      ...rawAplicacaoRecibo,
      timestamp: dayjs(rawAplicacaoRecibo.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertAplicacaoReciboToAplicacaoReciboRawValue(
    aplicacaoRecibo: IAplicacaoRecibo | (Partial<NewAplicacaoRecibo> & AplicacaoReciboFormDefaults)
  ): AplicacaoReciboFormRawValue | PartialWithRequiredKeyOf<NewAplicacaoReciboFormRawValue> {
    return {
      ...aplicacaoRecibo,
      timestamp: aplicacaoRecibo.timestamp ? aplicacaoRecibo.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
