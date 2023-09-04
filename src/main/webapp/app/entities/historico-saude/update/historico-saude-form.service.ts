import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHistoricoSaude, NewHistoricoSaude } from '../historico-saude.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHistoricoSaude for edit and NewHistoricoSaudeFormGroupInput for create.
 */
type HistoricoSaudeFormGroupInput = IHistoricoSaude | PartialWithRequiredKeyOf<NewHistoricoSaude>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IHistoricoSaude | NewHistoricoSaude> = Omit<T, 'inicio' | 'fim' | 'timestamp'> & {
  inicio?: string | null;
  fim?: string | null;
  timestamp?: string | null;
};

type HistoricoSaudeFormRawValue = FormValueOf<IHistoricoSaude>;

type NewHistoricoSaudeFormRawValue = FormValueOf<NewHistoricoSaude>;

type HistoricoSaudeFormDefaults = Pick<NewHistoricoSaude, 'id' | 'inicio' | 'fim' | 'timestamp'>;

type HistoricoSaudeFormGroupContent = {
  id: FormControl<HistoricoSaudeFormRawValue['id'] | NewHistoricoSaude['id']>;
  nome: FormControl<HistoricoSaudeFormRawValue['nome']>;
  descricao: FormControl<HistoricoSaudeFormRawValue['descricao']>;
  inicio: FormControl<HistoricoSaudeFormRawValue['inicio']>;
  fim: FormControl<HistoricoSaudeFormRawValue['fim']>;
  situacaoPrescricao: FormControl<HistoricoSaudeFormRawValue['situacaoPrescricao']>;
  timestamp: FormControl<HistoricoSaudeFormRawValue['timestamp']>;
  hash: FormControl<HistoricoSaudeFormRawValue['hash']>;
  utilizador: FormControl<HistoricoSaudeFormRawValue['utilizador']>;
  discente: FormControl<HistoricoSaudeFormRawValue['discente']>;
};

export type HistoricoSaudeFormGroup = FormGroup<HistoricoSaudeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HistoricoSaudeFormService {
  createHistoricoSaudeFormGroup(historicoSaude: HistoricoSaudeFormGroupInput = { id: null }): HistoricoSaudeFormGroup {
    const historicoSaudeRawValue = this.convertHistoricoSaudeToHistoricoSaudeRawValue({
      ...this.getFormDefaults(),
      ...historicoSaude,
    });
    return new FormGroup<HistoricoSaudeFormGroupContent>({
      id: new FormControl(
        { value: historicoSaudeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nome: new FormControl(historicoSaudeRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(historicoSaudeRawValue.descricao),
      inicio: new FormControl(historicoSaudeRawValue.inicio, {
        validators: [Validators.required],
      }),
      fim: new FormControl(historicoSaudeRawValue.fim),
      situacaoPrescricao: new FormControl(historicoSaudeRawValue.situacaoPrescricao),
      timestamp: new FormControl(historicoSaudeRawValue.timestamp, {
        validators: [Validators.required],
      }),
      hash: new FormControl(historicoSaudeRawValue.hash),
      utilizador: new FormControl(historicoSaudeRawValue.utilizador),
      discente: new FormControl(historicoSaudeRawValue.discente, {
        validators: [Validators.required],
      }),
    });
  }

  getHistoricoSaude(form: HistoricoSaudeFormGroup): IHistoricoSaude | NewHistoricoSaude {
    return this.convertHistoricoSaudeRawValueToHistoricoSaude(
      form.getRawValue() as HistoricoSaudeFormRawValue | NewHistoricoSaudeFormRawValue
    );
  }

  resetForm(form: HistoricoSaudeFormGroup, historicoSaude: HistoricoSaudeFormGroupInput): void {
    const historicoSaudeRawValue = this.convertHistoricoSaudeToHistoricoSaudeRawValue({ ...this.getFormDefaults(), ...historicoSaude });
    form.reset(
      {
        ...historicoSaudeRawValue,
        id: { value: historicoSaudeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HistoricoSaudeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      inicio: currentTime,
      fim: currentTime,
      timestamp: currentTime,
    };
  }

  private convertHistoricoSaudeRawValueToHistoricoSaude(
    rawHistoricoSaude: HistoricoSaudeFormRawValue | NewHistoricoSaudeFormRawValue
  ): IHistoricoSaude | NewHistoricoSaude {
    return {
      ...rawHistoricoSaude,
      inicio: dayjs(rawHistoricoSaude.inicio, DATE_TIME_FORMAT),
      fim: dayjs(rawHistoricoSaude.fim, DATE_TIME_FORMAT),
      timestamp: dayjs(rawHistoricoSaude.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertHistoricoSaudeToHistoricoSaudeRawValue(
    historicoSaude: IHistoricoSaude | (Partial<NewHistoricoSaude> & HistoricoSaudeFormDefaults)
  ): HistoricoSaudeFormRawValue | PartialWithRequiredKeyOf<NewHistoricoSaudeFormRawValue> {
    return {
      ...historicoSaude,
      inicio: historicoSaude.inicio ? historicoSaude.inicio.format(DATE_TIME_FORMAT) : undefined,
      fim: historicoSaude.fim ? historicoSaude.fim.format(DATE_TIME_FORMAT) : undefined,
      timestamp: historicoSaude.timestamp ? historicoSaude.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
