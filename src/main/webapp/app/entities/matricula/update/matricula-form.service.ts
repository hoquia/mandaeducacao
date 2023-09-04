import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMatricula, NewMatricula } from '../matricula.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMatricula for edit and NewMatriculaFormGroupInput for create.
 */
type MatriculaFormGroupInput = IMatricula | PartialWithRequiredKeyOf<NewMatricula>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMatricula | NewMatricula> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type MatriculaFormRawValue = FormValueOf<IMatricula>;

type NewMatriculaFormRawValue = FormValueOf<NewMatricula>;

type MatriculaFormDefaults = Pick<NewMatricula, 'id' | 'timestamp' | 'isAceiteTermosCompromisso' | 'categoriasMatriculas'>;

type MatriculaFormGroupContent = {
  id: FormControl<MatriculaFormRawValue['id'] | NewMatricula['id']>;
  chaveComposta1: FormControl<MatriculaFormRawValue['chaveComposta1']>;
  chaveComposta2: FormControl<MatriculaFormRawValue['chaveComposta2']>;
  numeroMatricula: FormControl<MatriculaFormRawValue['numeroMatricula']>;
  numeroChamada: FormControl<MatriculaFormRawValue['numeroChamada']>;
  estado: FormControl<MatriculaFormRawValue['estado']>;
  timestamp: FormControl<MatriculaFormRawValue['timestamp']>;
  descricao: FormControl<MatriculaFormRawValue['descricao']>;
  termosCompromissos: FormControl<MatriculaFormRawValue['termosCompromissos']>;
  termosCompromissosContentType: FormControl<MatriculaFormRawValue['termosCompromissosContentType']>;
  isAceiteTermosCompromisso: FormControl<MatriculaFormRawValue['isAceiteTermosCompromisso']>;
  utilizador: FormControl<MatriculaFormRawValue['utilizador']>;
  categoriasMatriculas: FormControl<MatriculaFormRawValue['categoriasMatriculas']>;
  turma: FormControl<MatriculaFormRawValue['turma']>;
  responsavelFinanceiro: FormControl<MatriculaFormRawValue['responsavelFinanceiro']>;
  discente: FormControl<MatriculaFormRawValue['discente']>;
  referencia: FormControl<MatriculaFormRawValue['referencia']>;
};

export type MatriculaFormGroup = FormGroup<MatriculaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MatriculaFormService {
  createMatriculaFormGroup(matricula: MatriculaFormGroupInput = { id: null }): MatriculaFormGroup {
    const matriculaRawValue = this.convertMatriculaToMatriculaRawValue({
      ...this.getFormDefaults(),
      ...matricula,
    });
    return new FormGroup<MatriculaFormGroupContent>({
      id: new FormControl(
        { value: matriculaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chaveComposta1: new FormControl(matriculaRawValue.chaveComposta1),
      chaveComposta2: new FormControl(matriculaRawValue.chaveComposta2),
      numeroMatricula: new FormControl(matriculaRawValue.numeroMatricula, {
        validators: [Validators.required],
      }),
      numeroChamada: new FormControl(matriculaRawValue.numeroChamada, {
        validators: [Validators.min(0)],
      }),
      estado: new FormControl(matriculaRawValue.estado, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(matriculaRawValue.timestamp),
      descricao: new FormControl(matriculaRawValue.descricao),
      termosCompromissos: new FormControl(matriculaRawValue.termosCompromissos),
      termosCompromissosContentType: new FormControl(matriculaRawValue.termosCompromissosContentType),
      isAceiteTermosCompromisso: new FormControl(matriculaRawValue.isAceiteTermosCompromisso),
      utilizador: new FormControl(matriculaRawValue.utilizador),
      categoriasMatriculas: new FormControl(matriculaRawValue.categoriasMatriculas ?? []),
      turma: new FormControl(matriculaRawValue.turma, {
        validators: [Validators.required],
      }),
      responsavelFinanceiro: new FormControl(matriculaRawValue.responsavelFinanceiro),
      discente: new FormControl(matriculaRawValue.discente, {
        validators: [Validators.required],
      }),
      referencia: new FormControl(matriculaRawValue.referencia),
    });
  }

  getMatricula(form: MatriculaFormGroup): IMatricula | NewMatricula {
    return this.convertMatriculaRawValueToMatricula(form.getRawValue() as MatriculaFormRawValue | NewMatriculaFormRawValue);
  }

  resetForm(form: MatriculaFormGroup, matricula: MatriculaFormGroupInput): void {
    const matriculaRawValue = this.convertMatriculaToMatriculaRawValue({ ...this.getFormDefaults(), ...matricula });
    form.reset(
      {
        ...matriculaRawValue,
        id: { value: matriculaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MatriculaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
      isAceiteTermosCompromisso: false,
      categoriasMatriculas: [],
    };
  }

  private convertMatriculaRawValueToMatricula(rawMatricula: MatriculaFormRawValue | NewMatriculaFormRawValue): IMatricula | NewMatricula {
    return {
      ...rawMatricula,
      timestamp: dayjs(rawMatricula.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertMatriculaToMatriculaRawValue(
    matricula: IMatricula | (Partial<NewMatricula> & MatriculaFormDefaults)
  ): MatriculaFormRawValue | PartialWithRequiredKeyOf<NewMatriculaFormRawValue> {
    return {
      ...matricula,
      timestamp: matricula.timestamp ? matricula.timestamp.format(DATE_TIME_FORMAT) : undefined,
      categoriasMatriculas: matricula.categoriasMatriculas ?? [],
    };
  }
}
