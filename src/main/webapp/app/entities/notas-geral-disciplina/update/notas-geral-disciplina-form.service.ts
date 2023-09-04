import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { INotasGeralDisciplina, NewNotasGeralDisciplina } from '../notas-geral-disciplina.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotasGeralDisciplina for edit and NewNotasGeralDisciplinaFormGroupInput for create.
 */
type NotasGeralDisciplinaFormGroupInput = INotasGeralDisciplina | PartialWithRequiredKeyOf<NewNotasGeralDisciplina>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends INotasGeralDisciplina | NewNotasGeralDisciplina> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type NotasGeralDisciplinaFormRawValue = FormValueOf<INotasGeralDisciplina>;

type NewNotasGeralDisciplinaFormRawValue = FormValueOf<NewNotasGeralDisciplina>;

type NotasGeralDisciplinaFormDefaults = Pick<NewNotasGeralDisciplina, 'id' | 'timestamp'>;

type NotasGeralDisciplinaFormGroupContent = {
  id: FormControl<NotasGeralDisciplinaFormRawValue['id'] | NewNotasGeralDisciplina['id']>;
  chaveComposta: FormControl<NotasGeralDisciplinaFormRawValue['chaveComposta']>;
  periodoLancamento: FormControl<NotasGeralDisciplinaFormRawValue['periodoLancamento']>;
  media1: FormControl<NotasGeralDisciplinaFormRawValue['media1']>;
  media2: FormControl<NotasGeralDisciplinaFormRawValue['media2']>;
  media3: FormControl<NotasGeralDisciplinaFormRawValue['media3']>;
  exame: FormControl<NotasGeralDisciplinaFormRawValue['exame']>;
  recurso: FormControl<NotasGeralDisciplinaFormRawValue['recurso']>;
  exameEspecial: FormControl<NotasGeralDisciplinaFormRawValue['exameEspecial']>;
  notaConselho: FormControl<NotasGeralDisciplinaFormRawValue['notaConselho']>;
  mediaFinalDisciplina: FormControl<NotasGeralDisciplinaFormRawValue['mediaFinalDisciplina']>;
  timestamp: FormControl<NotasGeralDisciplinaFormRawValue['timestamp']>;
  hash: FormControl<NotasGeralDisciplinaFormRawValue['hash']>;
  faltaJusticada: FormControl<NotasGeralDisciplinaFormRawValue['faltaJusticada']>;
  faltaInjustificada: FormControl<NotasGeralDisciplinaFormRawValue['faltaInjustificada']>;
  utilizador: FormControl<NotasGeralDisciplinaFormRawValue['utilizador']>;
  docente: FormControl<NotasGeralDisciplinaFormRawValue['docente']>;
  disciplinaCurricular: FormControl<NotasGeralDisciplinaFormRawValue['disciplinaCurricular']>;
  matricula: FormControl<NotasGeralDisciplinaFormRawValue['matricula']>;
  estado: FormControl<NotasGeralDisciplinaFormRawValue['estado']>;
};

export type NotasGeralDisciplinaFormGroup = FormGroup<NotasGeralDisciplinaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotasGeralDisciplinaFormService {
  createNotasGeralDisciplinaFormGroup(
    notasGeralDisciplina: NotasGeralDisciplinaFormGroupInput = { id: null }
  ): NotasGeralDisciplinaFormGroup {
    const notasGeralDisciplinaRawValue = this.convertNotasGeralDisciplinaToNotasGeralDisciplinaRawValue({
      ...this.getFormDefaults(),
      ...notasGeralDisciplina,
    });
    return new FormGroup<NotasGeralDisciplinaFormGroupContent>({
      id: new FormControl(
        { value: notasGeralDisciplinaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chaveComposta: new FormControl(notasGeralDisciplinaRawValue.chaveComposta),
      periodoLancamento: new FormControl(notasGeralDisciplinaRawValue.periodoLancamento, {
        validators: [Validators.min(0), Validators.max(3)],
      }),
      media1: new FormControl(notasGeralDisciplinaRawValue.media1, {
        validators: [Validators.min(0)],
      }),
      media2: new FormControl(notasGeralDisciplinaRawValue.media2, {
        validators: [Validators.min(0)],
      }),
      media3: new FormControl(notasGeralDisciplinaRawValue.media3, {
        validators: [Validators.min(0)],
      }),
      exame: new FormControl(notasGeralDisciplinaRawValue.exame, {
        validators: [Validators.min(0)],
      }),
      recurso: new FormControl(notasGeralDisciplinaRawValue.recurso, {
        validators: [Validators.min(0)],
      }),
      exameEspecial: new FormControl(notasGeralDisciplinaRawValue.exameEspecial, {
        validators: [Validators.min(0)],
      }),
      notaConselho: new FormControl(notasGeralDisciplinaRawValue.notaConselho, {
        validators: [Validators.min(0)],
      }),
      mediaFinalDisciplina: new FormControl(notasGeralDisciplinaRawValue.mediaFinalDisciplina, {
        validators: [Validators.min(0)],
      }),
      timestamp: new FormControl(notasGeralDisciplinaRawValue.timestamp, {
        validators: [Validators.required],
      }),
      hash: new FormControl(notasGeralDisciplinaRawValue.hash),
      faltaJusticada: new FormControl(notasGeralDisciplinaRawValue.faltaJusticada, {
        validators: [Validators.min(0)],
      }),
      faltaInjustificada: new FormControl(notasGeralDisciplinaRawValue.faltaInjustificada, {
        validators: [Validators.min(0)],
      }),
      utilizador: new FormControl(notasGeralDisciplinaRawValue.utilizador),
      docente: new FormControl(notasGeralDisciplinaRawValue.docente, {
        validators: [Validators.required],
      }),
      disciplinaCurricular: new FormControl(notasGeralDisciplinaRawValue.disciplinaCurricular, {
        validators: [Validators.required],
      }),
      matricula: new FormControl(notasGeralDisciplinaRawValue.matricula, {
        validators: [Validators.required],
      }),
      estado: new FormControl(notasGeralDisciplinaRawValue.estado),
    });
  }

  getNotasGeralDisciplina(form: NotasGeralDisciplinaFormGroup): INotasGeralDisciplina | NewNotasGeralDisciplina {
    return this.convertNotasGeralDisciplinaRawValueToNotasGeralDisciplina(
      form.getRawValue() as NotasGeralDisciplinaFormRawValue | NewNotasGeralDisciplinaFormRawValue
    );
  }

  resetForm(form: NotasGeralDisciplinaFormGroup, notasGeralDisciplina: NotasGeralDisciplinaFormGroupInput): void {
    const notasGeralDisciplinaRawValue = this.convertNotasGeralDisciplinaToNotasGeralDisciplinaRawValue({
      ...this.getFormDefaults(),
      ...notasGeralDisciplina,
    });
    form.reset(
      {
        ...notasGeralDisciplinaRawValue,
        id: { value: notasGeralDisciplinaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotasGeralDisciplinaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertNotasGeralDisciplinaRawValueToNotasGeralDisciplina(
    rawNotasGeralDisciplina: NotasGeralDisciplinaFormRawValue | NewNotasGeralDisciplinaFormRawValue
  ): INotasGeralDisciplina | NewNotasGeralDisciplina {
    return {
      ...rawNotasGeralDisciplina,
      timestamp: dayjs(rawNotasGeralDisciplina.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertNotasGeralDisciplinaToNotasGeralDisciplinaRawValue(
    notasGeralDisciplina: INotasGeralDisciplina | (Partial<NewNotasGeralDisciplina> & NotasGeralDisciplinaFormDefaults)
  ): NotasGeralDisciplinaFormRawValue | PartialWithRequiredKeyOf<NewNotasGeralDisciplinaFormRawValue> {
    return {
      ...notasGeralDisciplina,
      timestamp: notasGeralDisciplina.timestamp ? notasGeralDisciplina.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
