import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IResponsavelAreaFormacao, NewResponsavelAreaFormacao } from '../responsavel-area-formacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResponsavelAreaFormacao for edit and NewResponsavelAreaFormacaoFormGroupInput for create.
 */
type ResponsavelAreaFormacaoFormGroupInput = IResponsavelAreaFormacao | PartialWithRequiredKeyOf<NewResponsavelAreaFormacao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IResponsavelAreaFormacao | NewResponsavelAreaFormacao> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ResponsavelAreaFormacaoFormRawValue = FormValueOf<IResponsavelAreaFormacao>;

type NewResponsavelAreaFormacaoFormRawValue = FormValueOf<NewResponsavelAreaFormacao>;

type ResponsavelAreaFormacaoFormDefaults = Pick<NewResponsavelAreaFormacao, 'id' | 'timestamp'>;

type ResponsavelAreaFormacaoFormGroupContent = {
  id: FormControl<ResponsavelAreaFormacaoFormRawValue['id'] | NewResponsavelAreaFormacao['id']>;
  de: FormControl<ResponsavelAreaFormacaoFormRawValue['de']>;
  ate: FormControl<ResponsavelAreaFormacaoFormRawValue['ate']>;
  descricao: FormControl<ResponsavelAreaFormacaoFormRawValue['descricao']>;
  timestamp: FormControl<ResponsavelAreaFormacaoFormRawValue['timestamp']>;
  utilizador: FormControl<ResponsavelAreaFormacaoFormRawValue['utilizador']>;
  areaFormacao: FormControl<ResponsavelAreaFormacaoFormRawValue['areaFormacao']>;
};

export type ResponsavelAreaFormacaoFormGroup = FormGroup<ResponsavelAreaFormacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResponsavelAreaFormacaoFormService {
  createResponsavelAreaFormacaoFormGroup(
    responsavelAreaFormacao: ResponsavelAreaFormacaoFormGroupInput = { id: null }
  ): ResponsavelAreaFormacaoFormGroup {
    const responsavelAreaFormacaoRawValue = this.convertResponsavelAreaFormacaoToResponsavelAreaFormacaoRawValue({
      ...this.getFormDefaults(),
      ...responsavelAreaFormacao,
    });
    return new FormGroup<ResponsavelAreaFormacaoFormGroupContent>({
      id: new FormControl(
        { value: responsavelAreaFormacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      de: new FormControl(responsavelAreaFormacaoRawValue.de, {
        validators: [Validators.required],
      }),
      ate: new FormControl(responsavelAreaFormacaoRawValue.ate, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(responsavelAreaFormacaoRawValue.descricao),
      timestamp: new FormControl(responsavelAreaFormacaoRawValue.timestamp),
      utilizador: new FormControl(responsavelAreaFormacaoRawValue.utilizador),
      areaFormacao: new FormControl(responsavelAreaFormacaoRawValue.areaFormacao, {
        validators: [Validators.required],
      }),
    });
  }

  getResponsavelAreaFormacao(form: ResponsavelAreaFormacaoFormGroup): IResponsavelAreaFormacao | NewResponsavelAreaFormacao {
    return this.convertResponsavelAreaFormacaoRawValueToResponsavelAreaFormacao(
      form.getRawValue() as ResponsavelAreaFormacaoFormRawValue | NewResponsavelAreaFormacaoFormRawValue
    );
  }

  resetForm(form: ResponsavelAreaFormacaoFormGroup, responsavelAreaFormacao: ResponsavelAreaFormacaoFormGroupInput): void {
    const responsavelAreaFormacaoRawValue = this.convertResponsavelAreaFormacaoToResponsavelAreaFormacaoRawValue({
      ...this.getFormDefaults(),
      ...responsavelAreaFormacao,
    });
    form.reset(
      {
        ...responsavelAreaFormacaoRawValue,
        id: { value: responsavelAreaFormacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResponsavelAreaFormacaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertResponsavelAreaFormacaoRawValueToResponsavelAreaFormacao(
    rawResponsavelAreaFormacao: ResponsavelAreaFormacaoFormRawValue | NewResponsavelAreaFormacaoFormRawValue
  ): IResponsavelAreaFormacao | NewResponsavelAreaFormacao {
    return {
      ...rawResponsavelAreaFormacao,
      timestamp: dayjs(rawResponsavelAreaFormacao.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertResponsavelAreaFormacaoToResponsavelAreaFormacaoRawValue(
    responsavelAreaFormacao: IResponsavelAreaFormacao | (Partial<NewResponsavelAreaFormacao> & ResponsavelAreaFormacaoFormDefaults)
  ): ResponsavelAreaFormacaoFormRawValue | PartialWithRequiredKeyOf<NewResponsavelAreaFormacaoFormRawValue> {
    return {
      ...responsavelAreaFormacao,
      timestamp: responsavelAreaFormacao.timestamp ? responsavelAreaFormacao.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
