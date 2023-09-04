import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IResponsavelTurma, NewResponsavelTurma } from '../responsavel-turma.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResponsavelTurma for edit and NewResponsavelTurmaFormGroupInput for create.
 */
type ResponsavelTurmaFormGroupInput = IResponsavelTurma | PartialWithRequiredKeyOf<NewResponsavelTurma>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IResponsavelTurma | NewResponsavelTurma> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ResponsavelTurmaFormRawValue = FormValueOf<IResponsavelTurma>;

type NewResponsavelTurmaFormRawValue = FormValueOf<NewResponsavelTurma>;

type ResponsavelTurmaFormDefaults = Pick<NewResponsavelTurma, 'id' | 'timestamp'>;

type ResponsavelTurmaFormGroupContent = {
  id: FormControl<ResponsavelTurmaFormRawValue['id'] | NewResponsavelTurma['id']>;
  de: FormControl<ResponsavelTurmaFormRawValue['de']>;
  ate: FormControl<ResponsavelTurmaFormRawValue['ate']>;
  descricao: FormControl<ResponsavelTurmaFormRawValue['descricao']>;
  timestamp: FormControl<ResponsavelTurmaFormRawValue['timestamp']>;
  utilizador: FormControl<ResponsavelTurmaFormRawValue['utilizador']>;
  turma: FormControl<ResponsavelTurmaFormRawValue['turma']>;
};

export type ResponsavelTurmaFormGroup = FormGroup<ResponsavelTurmaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResponsavelTurmaFormService {
  createResponsavelTurmaFormGroup(responsavelTurma: ResponsavelTurmaFormGroupInput = { id: null }): ResponsavelTurmaFormGroup {
    const responsavelTurmaRawValue = this.convertResponsavelTurmaToResponsavelTurmaRawValue({
      ...this.getFormDefaults(),
      ...responsavelTurma,
    });
    return new FormGroup<ResponsavelTurmaFormGroupContent>({
      id: new FormControl(
        { value: responsavelTurmaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      de: new FormControl(responsavelTurmaRawValue.de, {
        validators: [Validators.required],
      }),
      ate: new FormControl(responsavelTurmaRawValue.ate, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(responsavelTurmaRawValue.descricao),
      timestamp: new FormControl(responsavelTurmaRawValue.timestamp),
      utilizador: new FormControl(responsavelTurmaRawValue.utilizador),
      turma: new FormControl(responsavelTurmaRawValue.turma, {
        validators: [Validators.required],
      }),
    });
  }

  getResponsavelTurma(form: ResponsavelTurmaFormGroup): IResponsavelTurma | NewResponsavelTurma {
    return this.convertResponsavelTurmaRawValueToResponsavelTurma(
      form.getRawValue() as ResponsavelTurmaFormRawValue | NewResponsavelTurmaFormRawValue
    );
  }

  resetForm(form: ResponsavelTurmaFormGroup, responsavelTurma: ResponsavelTurmaFormGroupInput): void {
    const responsavelTurmaRawValue = this.convertResponsavelTurmaToResponsavelTurmaRawValue({
      ...this.getFormDefaults(),
      ...responsavelTurma,
    });
    form.reset(
      {
        ...responsavelTurmaRawValue,
        id: { value: responsavelTurmaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResponsavelTurmaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertResponsavelTurmaRawValueToResponsavelTurma(
    rawResponsavelTurma: ResponsavelTurmaFormRawValue | NewResponsavelTurmaFormRawValue
  ): IResponsavelTurma | NewResponsavelTurma {
    return {
      ...rawResponsavelTurma,
      timestamp: dayjs(rawResponsavelTurma.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertResponsavelTurmaToResponsavelTurmaRawValue(
    responsavelTurma: IResponsavelTurma | (Partial<NewResponsavelTurma> & ResponsavelTurmaFormDefaults)
  ): ResponsavelTurmaFormRawValue | PartialWithRequiredKeyOf<NewResponsavelTurmaFormRawValue> {
    return {
      ...responsavelTurma,
      timestamp: responsavelTurma.timestamp ? responsavelTurma.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
