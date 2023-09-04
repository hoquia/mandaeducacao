import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransferenciaTurma, NewTransferenciaTurma } from '../transferencia-turma.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITransferenciaTurma for edit and NewTransferenciaTurmaFormGroupInput for create.
 */
type TransferenciaTurmaFormGroupInput = ITransferenciaTurma | PartialWithRequiredKeyOf<NewTransferenciaTurma>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITransferenciaTurma | NewTransferenciaTurma> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type TransferenciaTurmaFormRawValue = FormValueOf<ITransferenciaTurma>;

type NewTransferenciaTurmaFormRawValue = FormValueOf<NewTransferenciaTurma>;

type TransferenciaTurmaFormDefaults = Pick<NewTransferenciaTurma, 'id' | 'timestamp'>;

type TransferenciaTurmaFormGroupContent = {
  id: FormControl<TransferenciaTurmaFormRawValue['id'] | NewTransferenciaTurma['id']>;
  timestamp: FormControl<TransferenciaTurmaFormRawValue['timestamp']>;
  de: FormControl<TransferenciaTurmaFormRawValue['de']>;
  para: FormControl<TransferenciaTurmaFormRawValue['para']>;
  utilizador: FormControl<TransferenciaTurmaFormRawValue['utilizador']>;
  motivoTransferencia: FormControl<TransferenciaTurmaFormRawValue['motivoTransferencia']>;
  matricula: FormControl<TransferenciaTurmaFormRawValue['matricula']>;
};

export type TransferenciaTurmaFormGroup = FormGroup<TransferenciaTurmaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TransferenciaTurmaFormService {
  createTransferenciaTurmaFormGroup(transferenciaTurma: TransferenciaTurmaFormGroupInput = { id: null }): TransferenciaTurmaFormGroup {
    const transferenciaTurmaRawValue = this.convertTransferenciaTurmaToTransferenciaTurmaRawValue({
      ...this.getFormDefaults(),
      ...transferenciaTurma,
    });
    return new FormGroup<TransferenciaTurmaFormGroupContent>({
      id: new FormControl(
        { value: transferenciaTurmaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      timestamp: new FormControl(transferenciaTurmaRawValue.timestamp),
      de: new FormControl(transferenciaTurmaRawValue.de, {
        validators: [Validators.required],
      }),
      para: new FormControl(transferenciaTurmaRawValue.para, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(transferenciaTurmaRawValue.utilizador),
      motivoTransferencia: new FormControl(transferenciaTurmaRawValue.motivoTransferencia),
      matricula: new FormControl(transferenciaTurmaRawValue.matricula, {
        validators: [Validators.required],
      }),
    });
  }

  getTransferenciaTurma(form: TransferenciaTurmaFormGroup): ITransferenciaTurma | NewTransferenciaTurma {
    return this.convertTransferenciaTurmaRawValueToTransferenciaTurma(
      form.getRawValue() as TransferenciaTurmaFormRawValue | NewTransferenciaTurmaFormRawValue
    );
  }

  resetForm(form: TransferenciaTurmaFormGroup, transferenciaTurma: TransferenciaTurmaFormGroupInput): void {
    const transferenciaTurmaRawValue = this.convertTransferenciaTurmaToTransferenciaTurmaRawValue({
      ...this.getFormDefaults(),
      ...transferenciaTurma,
    });
    form.reset(
      {
        ...transferenciaTurmaRawValue,
        id: { value: transferenciaTurmaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TransferenciaTurmaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertTransferenciaTurmaRawValueToTransferenciaTurma(
    rawTransferenciaTurma: TransferenciaTurmaFormRawValue | NewTransferenciaTurmaFormRawValue
  ): ITransferenciaTurma | NewTransferenciaTurma {
    return {
      ...rawTransferenciaTurma,
      timestamp: dayjs(rawTransferenciaTurma.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertTransferenciaTurmaToTransferenciaTurmaRawValue(
    transferenciaTurma: ITransferenciaTurma | (Partial<NewTransferenciaTurma> & TransferenciaTurmaFormDefaults)
  ): TransferenciaTurmaFormRawValue | PartialWithRequiredKeyOf<NewTransferenciaTurmaFormRawValue> {
    return {
      ...transferenciaTurma,
      timestamp: transferenciaTurma.timestamp ? transferenciaTurma.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
