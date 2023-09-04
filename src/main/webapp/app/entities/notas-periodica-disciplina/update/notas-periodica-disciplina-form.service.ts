import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INotasPeriodicaDisciplina, NewNotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotasPeriodicaDisciplina for edit and NewNotasPeriodicaDisciplinaFormGroupInput for create.
 */
type NotasPeriodicaDisciplinaFormGroupInput = INotasPeriodicaDisciplina | PartialWithRequiredKeyOf<NewNotasPeriodicaDisciplina>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INotasPeriodicaDisciplina | NewNotasPeriodicaDisciplina> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type NotasPeriodicaDisciplinaFormRawValue = FormValueOf<INotasPeriodicaDisciplina>;

type NewNotasPeriodicaDisciplinaFormRawValue = FormValueOf<NewNotasPeriodicaDisciplina>;

type NotasPeriodicaDisciplinaFormDefaults = Pick<NewNotasPeriodicaDisciplina, 'id' | 'timestamp'>;

type NotasPeriodicaDisciplinaFormGroupContent = {
  id: FormControl<NotasPeriodicaDisciplinaFormRawValue['id'] | NewNotasPeriodicaDisciplina['id']>;
  chaveComposta: FormControl<NotasPeriodicaDisciplinaFormRawValue['chaveComposta']>;
  periodoLancamento: FormControl<NotasPeriodicaDisciplinaFormRawValue['periodoLancamento']>;
  nota1: FormControl<NotasPeriodicaDisciplinaFormRawValue['nota1']>;
  nota2: FormControl<NotasPeriodicaDisciplinaFormRawValue['nota2']>;
  nota3: FormControl<NotasPeriodicaDisciplinaFormRawValue['nota3']>;
  media: FormControl<NotasPeriodicaDisciplinaFormRawValue['media']>;
  faltaJusticada: FormControl<NotasPeriodicaDisciplinaFormRawValue['faltaJusticada']>;
  faltaInjustificada: FormControl<NotasPeriodicaDisciplinaFormRawValue['faltaInjustificada']>;
  comportamento: FormControl<NotasPeriodicaDisciplinaFormRawValue['comportamento']>;
  hash: FormControl<NotasPeriodicaDisciplinaFormRawValue['hash']>;
  timestamp: FormControl<NotasPeriodicaDisciplinaFormRawValue['timestamp']>;
  utilizador: FormControl<NotasPeriodicaDisciplinaFormRawValue['utilizador']>;
  turma: FormControl<NotasPeriodicaDisciplinaFormRawValue['turma']>;
  docente: FormControl<NotasPeriodicaDisciplinaFormRawValue['docente']>;
  disciplinaCurricular: FormControl<NotasPeriodicaDisciplinaFormRawValue['disciplinaCurricular']>;
  matricula: FormControl<NotasPeriodicaDisciplinaFormRawValue['matricula']>;
  estado: FormControl<NotasPeriodicaDisciplinaFormRawValue['estado']>;
};

export type NotasPeriodicaDisciplinaFormGroup = FormGroup<NotasPeriodicaDisciplinaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotasPeriodicaDisciplinaFormService {
  createNotasPeriodicaDisciplinaFormGroup(
    notasPeriodicaDisciplina: NotasPeriodicaDisciplinaFormGroupInput = { id: null }
  ): NotasPeriodicaDisciplinaFormGroup {
    const notasPeriodicaDisciplinaRawValue = this.convertNotasPeriodicaDisciplinaToNotasPeriodicaDisciplinaRawValue({
      ...this.getFormDefaults(),
      ...notasPeriodicaDisciplina,
    });
    return new FormGroup<NotasPeriodicaDisciplinaFormGroupContent>({
      id: new FormControl(
        { value: notasPeriodicaDisciplinaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chaveComposta: new FormControl(notasPeriodicaDisciplinaRawValue.chaveComposta),
      periodoLancamento: new FormControl(notasPeriodicaDisciplinaRawValue.periodoLancamento, {
        validators: [Validators.min(0), Validators.max(3)],
      }),
      nota1: new FormControl(notasPeriodicaDisciplinaRawValue.nota1, {
        validators: [Validators.min(0)],
      }),
      nota2: new FormControl(notasPeriodicaDisciplinaRawValue.nota2, {
        validators: [Validators.min(0)],
      }),
      nota3: new FormControl(notasPeriodicaDisciplinaRawValue.nota3, {
        validators: [Validators.min(0)],
      }),
      media: new FormControl(notasPeriodicaDisciplinaRawValue.media, {
        validators: [Validators.required, Validators.min(0)],
      }),
      faltaJusticada: new FormControl(notasPeriodicaDisciplinaRawValue.faltaJusticada, {
        validators: [Validators.min(0)],
      }),
      faltaInjustificada: new FormControl(notasPeriodicaDisciplinaRawValue.faltaInjustificada, {
        validators: [Validators.min(0)],
      }),
      comportamento: new FormControl(notasPeriodicaDisciplinaRawValue.comportamento),
      hash: new FormControl(notasPeriodicaDisciplinaRawValue.hash),
      timestamp: new FormControl(notasPeriodicaDisciplinaRawValue.timestamp, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(notasPeriodicaDisciplinaRawValue.utilizador),
      turma: new FormControl(notasPeriodicaDisciplinaRawValue.turma, {
        validators: [Validators.required],
      }),
      docente: new FormControl(notasPeriodicaDisciplinaRawValue.docente, {
        validators: [Validators.required],
      }),
      disciplinaCurricular: new FormControl(notasPeriodicaDisciplinaRawValue.disciplinaCurricular, {
        validators: [Validators.required],
      }),
      matricula: new FormControl(notasPeriodicaDisciplinaRawValue.matricula, {
        validators: [Validators.required],
      }),
      estado: new FormControl(notasPeriodicaDisciplinaRawValue.estado),
    });
  }

  getNotasPeriodicaDisciplina(form: NotasPeriodicaDisciplinaFormGroup): INotasPeriodicaDisciplina | NewNotasPeriodicaDisciplina {
    return this.convertNotasPeriodicaDisciplinaRawValueToNotasPeriodicaDisciplina(
      form.getRawValue() as NotasPeriodicaDisciplinaFormRawValue | NewNotasPeriodicaDisciplinaFormRawValue
    );
  }

  resetForm(form: NotasPeriodicaDisciplinaFormGroup, notasPeriodicaDisciplina: NotasPeriodicaDisciplinaFormGroupInput): void {
    const notasPeriodicaDisciplinaRawValue = this.convertNotasPeriodicaDisciplinaToNotasPeriodicaDisciplinaRawValue({
      ...this.getFormDefaults(),
      ...notasPeriodicaDisciplina,
    });
    form.reset(
      {
        ...notasPeriodicaDisciplinaRawValue,
        id: { value: notasPeriodicaDisciplinaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotasPeriodicaDisciplinaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertNotasPeriodicaDisciplinaRawValueToNotasPeriodicaDisciplina(
    rawNotasPeriodicaDisciplina: NotasPeriodicaDisciplinaFormRawValue | NewNotasPeriodicaDisciplinaFormRawValue
  ): INotasPeriodicaDisciplina | NewNotasPeriodicaDisciplina {
    return {
      ...rawNotasPeriodicaDisciplina,
      timestamp: dayjs(rawNotasPeriodicaDisciplina.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertNotasPeriodicaDisciplinaToNotasPeriodicaDisciplinaRawValue(
    notasPeriodicaDisciplina: INotasPeriodicaDisciplina | (Partial<NewNotasPeriodicaDisciplina> & NotasPeriodicaDisciplinaFormDefaults)
  ): NotasPeriodicaDisciplinaFormRawValue | PartialWithRequiredKeyOf<NewNotasPeriodicaDisciplinaFormRawValue> {
    return {
      ...notasPeriodicaDisciplina,
      timestamp: notasPeriodicaDisciplina.timestamp ? notasPeriodicaDisciplina.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
