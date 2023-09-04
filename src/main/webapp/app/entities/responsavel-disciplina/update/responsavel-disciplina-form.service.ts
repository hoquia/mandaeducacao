import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IResponsavelDisciplina, NewResponsavelDisciplina } from '../responsavel-disciplina.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResponsavelDisciplina for edit and NewResponsavelDisciplinaFormGroupInput for create.
 */
type ResponsavelDisciplinaFormGroupInput = IResponsavelDisciplina | PartialWithRequiredKeyOf<NewResponsavelDisciplina>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IResponsavelDisciplina | NewResponsavelDisciplina> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ResponsavelDisciplinaFormRawValue = FormValueOf<IResponsavelDisciplina>;

type NewResponsavelDisciplinaFormRawValue = FormValueOf<NewResponsavelDisciplina>;

type ResponsavelDisciplinaFormDefaults = Pick<NewResponsavelDisciplina, 'id' | 'timestamp'>;

type ResponsavelDisciplinaFormGroupContent = {
  id: FormControl<ResponsavelDisciplinaFormRawValue['id'] | NewResponsavelDisciplina['id']>;
  de: FormControl<ResponsavelDisciplinaFormRawValue['de']>;
  ate: FormControl<ResponsavelDisciplinaFormRawValue['ate']>;
  descricao: FormControl<ResponsavelDisciplinaFormRawValue['descricao']>;
  timestamp: FormControl<ResponsavelDisciplinaFormRawValue['timestamp']>;
  utilizador: FormControl<ResponsavelDisciplinaFormRawValue['utilizador']>;
  disciplina: FormControl<ResponsavelDisciplinaFormRawValue['disciplina']>;
};

export type ResponsavelDisciplinaFormGroup = FormGroup<ResponsavelDisciplinaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResponsavelDisciplinaFormService {
  createResponsavelDisciplinaFormGroup(
    responsavelDisciplina: ResponsavelDisciplinaFormGroupInput = { id: null }
  ): ResponsavelDisciplinaFormGroup {
    const responsavelDisciplinaRawValue = this.convertResponsavelDisciplinaToResponsavelDisciplinaRawValue({
      ...this.getFormDefaults(),
      ...responsavelDisciplina,
    });
    return new FormGroup<ResponsavelDisciplinaFormGroupContent>({
      id: new FormControl(
        { value: responsavelDisciplinaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      de: new FormControl(responsavelDisciplinaRawValue.de, {
        validators: [Validators.required],
      }),
      ate: new FormControl(responsavelDisciplinaRawValue.ate, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(responsavelDisciplinaRawValue.descricao),
      timestamp: new FormControl(responsavelDisciplinaRawValue.timestamp),
      utilizador: new FormControl(responsavelDisciplinaRawValue.utilizador),
      disciplina: new FormControl(responsavelDisciplinaRawValue.disciplina, {
        validators: [Validators.required],
      }),
    });
  }

  getResponsavelDisciplina(form: ResponsavelDisciplinaFormGroup): IResponsavelDisciplina | NewResponsavelDisciplina {
    return this.convertResponsavelDisciplinaRawValueToResponsavelDisciplina(
      form.getRawValue() as ResponsavelDisciplinaFormRawValue | NewResponsavelDisciplinaFormRawValue
    );
  }

  resetForm(form: ResponsavelDisciplinaFormGroup, responsavelDisciplina: ResponsavelDisciplinaFormGroupInput): void {
    const responsavelDisciplinaRawValue = this.convertResponsavelDisciplinaToResponsavelDisciplinaRawValue({
      ...this.getFormDefaults(),
      ...responsavelDisciplina,
    });
    form.reset(
      {
        ...responsavelDisciplinaRawValue,
        id: { value: responsavelDisciplinaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResponsavelDisciplinaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertResponsavelDisciplinaRawValueToResponsavelDisciplina(
    rawResponsavelDisciplina: ResponsavelDisciplinaFormRawValue | NewResponsavelDisciplinaFormRawValue
  ): IResponsavelDisciplina | NewResponsavelDisciplina {
    return {
      ...rawResponsavelDisciplina,
      timestamp: dayjs(rawResponsavelDisciplina.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertResponsavelDisciplinaToResponsavelDisciplinaRawValue(
    responsavelDisciplina: IResponsavelDisciplina | (Partial<NewResponsavelDisciplina> & ResponsavelDisciplinaFormDefaults)
  ): ResponsavelDisciplinaFormRawValue | PartialWithRequiredKeyOf<NewResponsavelDisciplinaFormRawValue> {
    return {
      ...responsavelDisciplina,
      timestamp: responsavelDisciplina.timestamp ? responsavelDisciplina.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
