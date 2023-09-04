import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOcorrencia, NewOcorrencia } from '../ocorrencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOcorrencia for edit and NewOcorrenciaFormGroupInput for create.
 */
type OcorrenciaFormGroupInput = IOcorrencia | PartialWithRequiredKeyOf<NewOcorrencia>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOcorrencia | NewOcorrencia> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type OcorrenciaFormRawValue = FormValueOf<IOcorrencia>;

type NewOcorrenciaFormRawValue = FormValueOf<NewOcorrencia>;

type OcorrenciaFormDefaults = Pick<NewOcorrencia, 'id' | 'timestamp'>;

type OcorrenciaFormGroupContent = {
  id: FormControl<OcorrenciaFormRawValue['id'] | NewOcorrencia['id']>;
  uniqueOcorrencia: FormControl<OcorrenciaFormRawValue['uniqueOcorrencia']>;
  descricao: FormControl<OcorrenciaFormRawValue['descricao']>;
  evidencia: FormControl<OcorrenciaFormRawValue['evidencia']>;
  evidenciaContentType: FormControl<OcorrenciaFormRawValue['evidenciaContentType']>;
  hash: FormControl<OcorrenciaFormRawValue['hash']>;
  timestamp: FormControl<OcorrenciaFormRawValue['timestamp']>;
  utilizador: FormControl<OcorrenciaFormRawValue['utilizador']>;
  referencia: FormControl<OcorrenciaFormRawValue['referencia']>;
  docente: FormControl<OcorrenciaFormRawValue['docente']>;
  matricula: FormControl<OcorrenciaFormRawValue['matricula']>;
  estado: FormControl<OcorrenciaFormRawValue['estado']>;
  licao: FormControl<OcorrenciaFormRawValue['licao']>;
};

export type OcorrenciaFormGroup = FormGroup<OcorrenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OcorrenciaFormService {
  createOcorrenciaFormGroup(ocorrencia: OcorrenciaFormGroupInput = { id: null }): OcorrenciaFormGroup {
    const ocorrenciaRawValue = this.convertOcorrenciaToOcorrenciaRawValue({
      ...this.getFormDefaults(),
      ...ocorrencia,
    });
    return new FormGroup<OcorrenciaFormGroupContent>({
      id: new FormControl(
        { value: ocorrenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      uniqueOcorrencia: new FormControl(ocorrenciaRawValue.uniqueOcorrencia),
      descricao: new FormControl(ocorrenciaRawValue.descricao, {
        validators: [Validators.required],
      }),
      evidencia: new FormControl(ocorrenciaRawValue.evidencia),
      evidenciaContentType: new FormControl(ocorrenciaRawValue.evidenciaContentType),
      hash: new FormControl(ocorrenciaRawValue.hash),
      timestamp: new FormControl(ocorrenciaRawValue.timestamp, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(ocorrenciaRawValue.utilizador),
      referencia: new FormControl(ocorrenciaRawValue.referencia),
      docente: new FormControl(ocorrenciaRawValue.docente, {
        validators: [Validators.required],
      }),
      matricula: new FormControl(ocorrenciaRawValue.matricula, {
        validators: [Validators.required],
      }),
      estado: new FormControl(ocorrenciaRawValue.estado, {
        validators: [Validators.required],
      }),
      licao: new FormControl(ocorrenciaRawValue.licao, {
        validators: [Validators.required],
      }),
    });
  }

  getOcorrencia(form: OcorrenciaFormGroup): IOcorrencia | NewOcorrencia {
    return this.convertOcorrenciaRawValueToOcorrencia(form.getRawValue() as OcorrenciaFormRawValue | NewOcorrenciaFormRawValue);
  }

  resetForm(form: OcorrenciaFormGroup, ocorrencia: OcorrenciaFormGroupInput): void {
    const ocorrenciaRawValue = this.convertOcorrenciaToOcorrenciaRawValue({ ...this.getFormDefaults(), ...ocorrencia });
    form.reset(
      {
        ...ocorrenciaRawValue,
        id: { value: ocorrenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OcorrenciaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertOcorrenciaRawValueToOcorrencia(
    rawOcorrencia: OcorrenciaFormRawValue | NewOcorrenciaFormRawValue
  ): IOcorrencia | NewOcorrencia {
    return {
      ...rawOcorrencia,
      timestamp: dayjs(rawOcorrencia.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertOcorrenciaToOcorrenciaRawValue(
    ocorrencia: IOcorrencia | (Partial<NewOcorrencia> & OcorrenciaFormDefaults)
  ): OcorrenciaFormRawValue | PartialWithRequiredKeyOf<NewOcorrenciaFormRawValue> {
    return {
      ...ocorrencia,
      timestamp: ocorrencia.timestamp ? ocorrencia.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
