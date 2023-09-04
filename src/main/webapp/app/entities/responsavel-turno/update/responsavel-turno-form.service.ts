import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IResponsavelTurno, NewResponsavelTurno } from '../responsavel-turno.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResponsavelTurno for edit and NewResponsavelTurnoFormGroupInput for create.
 */
type ResponsavelTurnoFormGroupInput = IResponsavelTurno | PartialWithRequiredKeyOf<NewResponsavelTurno>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IResponsavelTurno | NewResponsavelTurno> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ResponsavelTurnoFormRawValue = FormValueOf<IResponsavelTurno>;

type NewResponsavelTurnoFormRawValue = FormValueOf<NewResponsavelTurno>;

type ResponsavelTurnoFormDefaults = Pick<NewResponsavelTurno, 'id' | 'timestamp'>;

type ResponsavelTurnoFormGroupContent = {
  id: FormControl<ResponsavelTurnoFormRawValue['id'] | NewResponsavelTurno['id']>;
  de: FormControl<ResponsavelTurnoFormRawValue['de']>;
  ate: FormControl<ResponsavelTurnoFormRawValue['ate']>;
  descricao: FormControl<ResponsavelTurnoFormRawValue['descricao']>;
  timestamp: FormControl<ResponsavelTurnoFormRawValue['timestamp']>;
  utilizador: FormControl<ResponsavelTurnoFormRawValue['utilizador']>;
  turno: FormControl<ResponsavelTurnoFormRawValue['turno']>;
};

export type ResponsavelTurnoFormGroup = FormGroup<ResponsavelTurnoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResponsavelTurnoFormService {
  createResponsavelTurnoFormGroup(responsavelTurno: ResponsavelTurnoFormGroupInput = { id: null }): ResponsavelTurnoFormGroup {
    const responsavelTurnoRawValue = this.convertResponsavelTurnoToResponsavelTurnoRawValue({
      ...this.getFormDefaults(),
      ...responsavelTurno,
    });
    return new FormGroup<ResponsavelTurnoFormGroupContent>({
      id: new FormControl(
        { value: responsavelTurnoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      de: new FormControl(responsavelTurnoRawValue.de, {
        validators: [Validators.required],
      }),
      ate: new FormControl(responsavelTurnoRawValue.ate, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(responsavelTurnoRawValue.descricao),
      timestamp: new FormControl(responsavelTurnoRawValue.timestamp),
      utilizador: new FormControl(responsavelTurnoRawValue.utilizador),
      turno: new FormControl(responsavelTurnoRawValue.turno, {
        validators: [Validators.required],
      }),
    });
  }

  getResponsavelTurno(form: ResponsavelTurnoFormGroup): IResponsavelTurno | NewResponsavelTurno {
    return this.convertResponsavelTurnoRawValueToResponsavelTurno(
      form.getRawValue() as ResponsavelTurnoFormRawValue | NewResponsavelTurnoFormRawValue
    );
  }

  resetForm(form: ResponsavelTurnoFormGroup, responsavelTurno: ResponsavelTurnoFormGroupInput): void {
    const responsavelTurnoRawValue = this.convertResponsavelTurnoToResponsavelTurnoRawValue({
      ...this.getFormDefaults(),
      ...responsavelTurno,
    });
    form.reset(
      {
        ...responsavelTurnoRawValue,
        id: { value: responsavelTurnoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResponsavelTurnoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertResponsavelTurnoRawValueToResponsavelTurno(
    rawResponsavelTurno: ResponsavelTurnoFormRawValue | NewResponsavelTurnoFormRawValue
  ): IResponsavelTurno | NewResponsavelTurno {
    return {
      ...rawResponsavelTurno,
      timestamp: dayjs(rawResponsavelTurno.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertResponsavelTurnoToResponsavelTurnoRawValue(
    responsavelTurno: IResponsavelTurno | (Partial<NewResponsavelTurno> & ResponsavelTurnoFormDefaults)
  ): ResponsavelTurnoFormRawValue | PartialWithRequiredKeyOf<NewResponsavelTurnoFormRawValue> {
    return {
      ...responsavelTurno,
      timestamp: responsavelTurno.timestamp ? responsavelTurno.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
