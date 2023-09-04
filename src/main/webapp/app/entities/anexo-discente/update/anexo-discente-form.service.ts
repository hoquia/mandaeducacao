import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAnexoDiscente, NewAnexoDiscente } from '../anexo-discente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoDiscente for edit and NewAnexoDiscenteFormGroupInput for create.
 */
type AnexoDiscenteFormGroupInput = IAnexoDiscente | PartialWithRequiredKeyOf<NewAnexoDiscente>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAnexoDiscente | NewAnexoDiscente> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type AnexoDiscenteFormRawValue = FormValueOf<IAnexoDiscente>;

type NewAnexoDiscenteFormRawValue = FormValueOf<NewAnexoDiscente>;

type AnexoDiscenteFormDefaults = Pick<NewAnexoDiscente, 'id' | 'timestamp'>;

type AnexoDiscenteFormGroupContent = {
  id: FormControl<AnexoDiscenteFormRawValue['id'] | NewAnexoDiscente['id']>;
  categoria: FormControl<AnexoDiscenteFormRawValue['categoria']>;
  anexo: FormControl<AnexoDiscenteFormRawValue['anexo']>;
  anexoContentType: FormControl<AnexoDiscenteFormRawValue['anexoContentType']>;
  descricao: FormControl<AnexoDiscenteFormRawValue['descricao']>;
  validade: FormControl<AnexoDiscenteFormRawValue['validade']>;
  timestamp: FormControl<AnexoDiscenteFormRawValue['timestamp']>;
  discente: FormControl<AnexoDiscenteFormRawValue['discente']>;
};

export type AnexoDiscenteFormGroup = FormGroup<AnexoDiscenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoDiscenteFormService {
  createAnexoDiscenteFormGroup(anexoDiscente: AnexoDiscenteFormGroupInput = { id: null }): AnexoDiscenteFormGroup {
    const anexoDiscenteRawValue = this.convertAnexoDiscenteToAnexoDiscenteRawValue({
      ...this.getFormDefaults(),
      ...anexoDiscente,
    });
    return new FormGroup<AnexoDiscenteFormGroupContent>({
      id: new FormControl(
        { value: anexoDiscenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      categoria: new FormControl(anexoDiscenteRawValue.categoria, {
        validators: [Validators.required],
      }),
      anexo: new FormControl(anexoDiscenteRawValue.anexo),
      anexoContentType: new FormControl(anexoDiscenteRawValue.anexoContentType),
      descricao: new FormControl(anexoDiscenteRawValue.descricao),
      validade: new FormControl(anexoDiscenteRawValue.validade),
      timestamp: new FormControl(anexoDiscenteRawValue.timestamp),
      discente: new FormControl(anexoDiscenteRawValue.discente, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoDiscente(form: AnexoDiscenteFormGroup): IAnexoDiscente | NewAnexoDiscente {
    return this.convertAnexoDiscenteRawValueToAnexoDiscente(form.getRawValue() as AnexoDiscenteFormRawValue | NewAnexoDiscenteFormRawValue);
  }

  resetForm(form: AnexoDiscenteFormGroup, anexoDiscente: AnexoDiscenteFormGroupInput): void {
    const anexoDiscenteRawValue = this.convertAnexoDiscenteToAnexoDiscenteRawValue({ ...this.getFormDefaults(), ...anexoDiscente });
    form.reset(
      {
        ...anexoDiscenteRawValue,
        id: { value: anexoDiscenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnexoDiscenteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertAnexoDiscenteRawValueToAnexoDiscente(
    rawAnexoDiscente: AnexoDiscenteFormRawValue | NewAnexoDiscenteFormRawValue
  ): IAnexoDiscente | NewAnexoDiscente {
    return {
      ...rawAnexoDiscente,
      timestamp: dayjs(rawAnexoDiscente.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertAnexoDiscenteToAnexoDiscenteRawValue(
    anexoDiscente: IAnexoDiscente | (Partial<NewAnexoDiscente> & AnexoDiscenteFormDefaults)
  ): AnexoDiscenteFormRawValue | PartialWithRequiredKeyOf<NewAnexoDiscenteFormRawValue> {
    return {
      ...anexoDiscente,
      timestamp: anexoDiscente.timestamp ? anexoDiscente.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
