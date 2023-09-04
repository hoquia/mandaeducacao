import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProcessoSelectivoMatricula, NewProcessoSelectivoMatricula } from '../processo-selectivo-matricula.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProcessoSelectivoMatricula for edit and NewProcessoSelectivoMatriculaFormGroupInput for create.
 */
type ProcessoSelectivoMatriculaFormGroupInput = IProcessoSelectivoMatricula | PartialWithRequiredKeyOf<NewProcessoSelectivoMatricula>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProcessoSelectivoMatricula | NewProcessoSelectivoMatricula> = Omit<T, 'dataTeste'> & {
  dataTeste?: string | null;
};

type ProcessoSelectivoMatriculaFormRawValue = FormValueOf<IProcessoSelectivoMatricula>;

type NewProcessoSelectivoMatriculaFormRawValue = FormValueOf<NewProcessoSelectivoMatricula>;

type ProcessoSelectivoMatriculaFormDefaults = Pick<NewProcessoSelectivoMatricula, 'id' | 'dataTeste' | 'isAdmitido'>;

type ProcessoSelectivoMatriculaFormGroupContent = {
  id: FormControl<ProcessoSelectivoMatriculaFormRawValue['id'] | NewProcessoSelectivoMatricula['id']>;
  localTeste: FormControl<ProcessoSelectivoMatriculaFormRawValue['localTeste']>;
  dataTeste: FormControl<ProcessoSelectivoMatriculaFormRawValue['dataTeste']>;
  notaTeste: FormControl<ProcessoSelectivoMatriculaFormRawValue['notaTeste']>;
  isAdmitido: FormControl<ProcessoSelectivoMatriculaFormRawValue['isAdmitido']>;
  utilizador: FormControl<ProcessoSelectivoMatriculaFormRawValue['utilizador']>;
  turma: FormControl<ProcessoSelectivoMatriculaFormRawValue['turma']>;
  discente: FormControl<ProcessoSelectivoMatriculaFormRawValue['discente']>;
};

export type ProcessoSelectivoMatriculaFormGroup = FormGroup<ProcessoSelectivoMatriculaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProcessoSelectivoMatriculaFormService {
  createProcessoSelectivoMatriculaFormGroup(
    processoSelectivoMatricula: ProcessoSelectivoMatriculaFormGroupInput = { id: null }
  ): ProcessoSelectivoMatriculaFormGroup {
    const processoSelectivoMatriculaRawValue = this.convertProcessoSelectivoMatriculaToProcessoSelectivoMatriculaRawValue({
      ...this.getFormDefaults(),
      ...processoSelectivoMatricula,
    });
    return new FormGroup<ProcessoSelectivoMatriculaFormGroupContent>({
      id: new FormControl(
        { value: processoSelectivoMatriculaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      localTeste: new FormControl(processoSelectivoMatriculaRawValue.localTeste),
      dataTeste: new FormControl(processoSelectivoMatriculaRawValue.dataTeste),
      notaTeste: new FormControl(processoSelectivoMatriculaRawValue.notaTeste, {
        validators: [Validators.min(0)],
      }),
      isAdmitido: new FormControl(processoSelectivoMatriculaRawValue.isAdmitido),
      utilizador: new FormControl(processoSelectivoMatriculaRawValue.utilizador),
      turma: new FormControl(processoSelectivoMatriculaRawValue.turma, {
        validators: [Validators.required],
      }),
      discente: new FormControl(processoSelectivoMatriculaRawValue.discente, {
        validators: [Validators.required],
      }),
    });
  }

  getProcessoSelectivoMatricula(form: ProcessoSelectivoMatriculaFormGroup): IProcessoSelectivoMatricula | NewProcessoSelectivoMatricula {
    return this.convertProcessoSelectivoMatriculaRawValueToProcessoSelectivoMatricula(
      form.getRawValue() as ProcessoSelectivoMatriculaFormRawValue | NewProcessoSelectivoMatriculaFormRawValue
    );
  }

  resetForm(form: ProcessoSelectivoMatriculaFormGroup, processoSelectivoMatricula: ProcessoSelectivoMatriculaFormGroupInput): void {
    const processoSelectivoMatriculaRawValue = this.convertProcessoSelectivoMatriculaToProcessoSelectivoMatriculaRawValue({
      ...this.getFormDefaults(),
      ...processoSelectivoMatricula,
    });
    form.reset(
      {
        ...processoSelectivoMatriculaRawValue,
        id: { value: processoSelectivoMatriculaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProcessoSelectivoMatriculaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataTeste: currentTime,
      isAdmitido: false,
    };
  }

  private convertProcessoSelectivoMatriculaRawValueToProcessoSelectivoMatricula(
    rawProcessoSelectivoMatricula: ProcessoSelectivoMatriculaFormRawValue | NewProcessoSelectivoMatriculaFormRawValue
  ): IProcessoSelectivoMatricula | NewProcessoSelectivoMatricula {
    return {
      ...rawProcessoSelectivoMatricula,
      dataTeste: dayjs(rawProcessoSelectivoMatricula.dataTeste, DATE_TIME_FORMAT),
    };
  }

  private convertProcessoSelectivoMatriculaToProcessoSelectivoMatriculaRawValue(
    processoSelectivoMatricula:
      | IProcessoSelectivoMatricula
      | (Partial<NewProcessoSelectivoMatricula> & ProcessoSelectivoMatriculaFormDefaults)
  ): ProcessoSelectivoMatriculaFormRawValue | PartialWithRequiredKeyOf<NewProcessoSelectivoMatriculaFormRawValue> {
    return {
      ...processoSelectivoMatricula,
      dataTeste: processoSelectivoMatricula.dataTeste ? processoSelectivoMatricula.dataTeste.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
