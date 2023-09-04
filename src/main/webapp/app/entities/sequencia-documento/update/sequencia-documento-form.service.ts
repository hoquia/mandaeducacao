import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISequenciaDocumento, NewSequenciaDocumento } from '../sequencia-documento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISequenciaDocumento for edit and NewSequenciaDocumentoFormGroupInput for create.
 */
type SequenciaDocumentoFormGroupInput = ISequenciaDocumento | PartialWithRequiredKeyOf<NewSequenciaDocumento>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISequenciaDocumento | NewSequenciaDocumento> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type SequenciaDocumentoFormRawValue = FormValueOf<ISequenciaDocumento>;

type NewSequenciaDocumentoFormRawValue = FormValueOf<NewSequenciaDocumento>;

type SequenciaDocumentoFormDefaults = Pick<NewSequenciaDocumento, 'id' | 'timestamp'>;

type SequenciaDocumentoFormGroupContent = {
  id: FormControl<SequenciaDocumentoFormRawValue['id'] | NewSequenciaDocumento['id']>;
  sequencia: FormControl<SequenciaDocumentoFormRawValue['sequencia']>;
  data: FormControl<SequenciaDocumentoFormRawValue['data']>;
  hash: FormControl<SequenciaDocumentoFormRawValue['hash']>;
  timestamp: FormControl<SequenciaDocumentoFormRawValue['timestamp']>;
  serie: FormControl<SequenciaDocumentoFormRawValue['serie']>;
};

export type SequenciaDocumentoFormGroup = FormGroup<SequenciaDocumentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SequenciaDocumentoFormService {
  createSequenciaDocumentoFormGroup(sequenciaDocumento: SequenciaDocumentoFormGroupInput = { id: null }): SequenciaDocumentoFormGroup {
    const sequenciaDocumentoRawValue = this.convertSequenciaDocumentoToSequenciaDocumentoRawValue({
      ...this.getFormDefaults(),
      ...sequenciaDocumento,
    });
    return new FormGroup<SequenciaDocumentoFormGroupContent>({
      id: new FormControl(
        { value: sequenciaDocumentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sequencia: new FormControl(sequenciaDocumentoRawValue.sequencia, {
        validators: [Validators.required, Validators.min(1)],
      }),
      data: new FormControl(sequenciaDocumentoRawValue.data, {
        validators: [Validators.required],
      }),
      hash: new FormControl(sequenciaDocumentoRawValue.hash, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(sequenciaDocumentoRawValue.timestamp, {
        validators: [Validators.required],
      }),
      serie: new FormControl(sequenciaDocumentoRawValue.serie, {
        validators: [Validators.required],
      }),
    });
  }

  getSequenciaDocumento(form: SequenciaDocumentoFormGroup): ISequenciaDocumento | NewSequenciaDocumento {
    return this.convertSequenciaDocumentoRawValueToSequenciaDocumento(
      form.getRawValue() as SequenciaDocumentoFormRawValue | NewSequenciaDocumentoFormRawValue
    );
  }

  resetForm(form: SequenciaDocumentoFormGroup, sequenciaDocumento: SequenciaDocumentoFormGroupInput): void {
    const sequenciaDocumentoRawValue = this.convertSequenciaDocumentoToSequenciaDocumentoRawValue({
      ...this.getFormDefaults(),
      ...sequenciaDocumento,
    });
    form.reset(
      {
        ...sequenciaDocumentoRawValue,
        id: { value: sequenciaDocumentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SequenciaDocumentoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertSequenciaDocumentoRawValueToSequenciaDocumento(
    rawSequenciaDocumento: SequenciaDocumentoFormRawValue | NewSequenciaDocumentoFormRawValue
  ): ISequenciaDocumento | NewSequenciaDocumento {
    return {
      ...rawSequenciaDocumento,
      timestamp: dayjs(rawSequenciaDocumento.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertSequenciaDocumentoToSequenciaDocumentoRawValue(
    sequenciaDocumento: ISequenciaDocumento | (Partial<NewSequenciaDocumento> & SequenciaDocumentoFormDefaults)
  ): SequenciaDocumentoFormRawValue | PartialWithRequiredKeyOf<NewSequenciaDocumentoFormRawValue> {
    return {
      ...sequenciaDocumento,
      timestamp: sequenciaDocumento.timestamp ? sequenciaDocumento.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
