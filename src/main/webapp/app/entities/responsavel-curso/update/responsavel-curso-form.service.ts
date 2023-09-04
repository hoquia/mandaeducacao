import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IResponsavelCurso, NewResponsavelCurso } from '../responsavel-curso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResponsavelCurso for edit and NewResponsavelCursoFormGroupInput for create.
 */
type ResponsavelCursoFormGroupInput = IResponsavelCurso | PartialWithRequiredKeyOf<NewResponsavelCurso>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IResponsavelCurso | NewResponsavelCurso> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ResponsavelCursoFormRawValue = FormValueOf<IResponsavelCurso>;

type NewResponsavelCursoFormRawValue = FormValueOf<NewResponsavelCurso>;

type ResponsavelCursoFormDefaults = Pick<NewResponsavelCurso, 'id' | 'timestamp'>;

type ResponsavelCursoFormGroupContent = {
  id: FormControl<ResponsavelCursoFormRawValue['id'] | NewResponsavelCurso['id']>;
  de: FormControl<ResponsavelCursoFormRawValue['de']>;
  ate: FormControl<ResponsavelCursoFormRawValue['ate']>;
  descricao: FormControl<ResponsavelCursoFormRawValue['descricao']>;
  timestamp: FormControl<ResponsavelCursoFormRawValue['timestamp']>;
  utilizador: FormControl<ResponsavelCursoFormRawValue['utilizador']>;
  curso: FormControl<ResponsavelCursoFormRawValue['curso']>;
};

export type ResponsavelCursoFormGroup = FormGroup<ResponsavelCursoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResponsavelCursoFormService {
  createResponsavelCursoFormGroup(responsavelCurso: ResponsavelCursoFormGroupInput = { id: null }): ResponsavelCursoFormGroup {
    const responsavelCursoRawValue = this.convertResponsavelCursoToResponsavelCursoRawValue({
      ...this.getFormDefaults(),
      ...responsavelCurso,
    });
    return new FormGroup<ResponsavelCursoFormGroupContent>({
      id: new FormControl(
        { value: responsavelCursoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      de: new FormControl(responsavelCursoRawValue.de, {
        validators: [Validators.required],
      }),
      ate: new FormControl(responsavelCursoRawValue.ate, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(responsavelCursoRawValue.descricao),
      timestamp: new FormControl(responsavelCursoRawValue.timestamp),
      utilizador: new FormControl(responsavelCursoRawValue.utilizador),
      curso: new FormControl(responsavelCursoRawValue.curso, {
        validators: [Validators.required],
      }),
    });
  }

  getResponsavelCurso(form: ResponsavelCursoFormGroup): IResponsavelCurso | NewResponsavelCurso {
    return this.convertResponsavelCursoRawValueToResponsavelCurso(
      form.getRawValue() as ResponsavelCursoFormRawValue | NewResponsavelCursoFormRawValue
    );
  }

  resetForm(form: ResponsavelCursoFormGroup, responsavelCurso: ResponsavelCursoFormGroupInput): void {
    const responsavelCursoRawValue = this.convertResponsavelCursoToResponsavelCursoRawValue({
      ...this.getFormDefaults(),
      ...responsavelCurso,
    });
    form.reset(
      {
        ...responsavelCursoRawValue,
        id: { value: responsavelCursoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResponsavelCursoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertResponsavelCursoRawValueToResponsavelCurso(
    rawResponsavelCurso: ResponsavelCursoFormRawValue | NewResponsavelCursoFormRawValue
  ): IResponsavelCurso | NewResponsavelCurso {
    return {
      ...rawResponsavelCurso,
      timestamp: dayjs(rawResponsavelCurso.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertResponsavelCursoToResponsavelCursoRawValue(
    responsavelCurso: IResponsavelCurso | (Partial<NewResponsavelCurso> & ResponsavelCursoFormDefaults)
  ): ResponsavelCursoFormRawValue | PartialWithRequiredKeyOf<NewResponsavelCursoFormRawValue> {
    return {
      ...responsavelCurso,
      timestamp: responsavelCurso.timestamp ? responsavelCurso.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
