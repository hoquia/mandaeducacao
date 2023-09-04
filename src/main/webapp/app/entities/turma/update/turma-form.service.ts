import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITurma, NewTurma } from '../turma.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITurma for edit and NewTurmaFormGroupInput for create.
 */
type TurmaFormGroupInput = ITurma | PartialWithRequiredKeyOf<NewTurma>;

type TurmaFormDefaults = Pick<NewTurma, 'id' | 'fazInscricaoDepoisMatricula' | 'isDisponivel'>;

type TurmaFormGroupContent = {
  id: FormControl<ITurma['id'] | NewTurma['id']>;
  chaveComposta: FormControl<ITurma['chaveComposta']>;
  tipoTurma: FormControl<ITurma['tipoTurma']>;
  sala: FormControl<ITurma['sala']>;
  descricao: FormControl<ITurma['descricao']>;
  lotacao: FormControl<ITurma['lotacao']>;
  confirmado: FormControl<ITurma['confirmado']>;
  abertura: FormControl<ITurma['abertura']>;
  encerramento: FormControl<ITurma['encerramento']>;
  criterioDescricao: FormControl<ITurma['criterioDescricao']>;
  criterioOrdenacaoNumero: FormControl<ITurma['criterioOrdenacaoNumero']>;
  fazInscricaoDepoisMatricula: FormControl<ITurma['fazInscricaoDepoisMatricula']>;
  isDisponivel: FormControl<ITurma['isDisponivel']>;
  utilizador: FormControl<ITurma['utilizador']>;
  referencia: FormControl<ITurma['referencia']>;
  planoCurricular: FormControl<ITurma['planoCurricular']>;
  turno: FormControl<ITurma['turno']>;
};

export type TurmaFormGroup = FormGroup<TurmaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TurmaFormService {
  createTurmaFormGroup(turma: TurmaFormGroupInput = { id: null }): TurmaFormGroup {
    const turmaRawValue = {
      ...this.getFormDefaults(),
      ...turma,
    };
    return new FormGroup<TurmaFormGroupContent>({
      id: new FormControl(
        { value: turmaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chaveComposta: new FormControl(turmaRawValue.chaveComposta),
      tipoTurma: new FormControl(turmaRawValue.tipoTurma, {
        validators: [Validators.required],
      }),
      sala: new FormControl(turmaRawValue.sala, {
        validators: [Validators.required, Validators.min(1)],
      }),
      descricao: new FormControl(turmaRawValue.descricao, {
        validators: [Validators.required],
      }),
      lotacao: new FormControl(turmaRawValue.lotacao, {
        validators: [Validators.required, Validators.min(1)],
      }),
      confirmado: new FormControl(turmaRawValue.confirmado, {
        validators: [Validators.required, Validators.min(0)],
      }),
      abertura: new FormControl(turmaRawValue.abertura),
      encerramento: new FormControl(turmaRawValue.encerramento),
      criterioDescricao: new FormControl(turmaRawValue.criterioDescricao),
      criterioOrdenacaoNumero: new FormControl(turmaRawValue.criterioOrdenacaoNumero),
      fazInscricaoDepoisMatricula: new FormControl(turmaRawValue.fazInscricaoDepoisMatricula),
      isDisponivel: new FormControl(turmaRawValue.isDisponivel),
      utilizador: new FormControl(turmaRawValue.utilizador),
      referencia: new FormControl(turmaRawValue.referencia),
      planoCurricular: new FormControl(turmaRawValue.planoCurricular, {
        validators: [Validators.required],
      }),
      turno: new FormControl(turmaRawValue.turno, {
        validators: [Validators.required],
      }),
    });
  }

  getTurma(form: TurmaFormGroup): ITurma | NewTurma {
    return form.getRawValue() as ITurma | NewTurma;
  }

  resetForm(form: TurmaFormGroup, turma: TurmaFormGroupInput): void {
    const turmaRawValue = { ...this.getFormDefaults(), ...turma };
    form.reset(
      {
        ...turmaRawValue,
        id: { value: turmaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TurmaFormDefaults {
    return {
      id: null,
      fazInscricaoDepoisMatricula: false,
      isDisponivel: false,
    };
  }
}
