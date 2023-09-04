import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDissertacaoFinalCurso, NewDissertacaoFinalCurso } from '../dissertacao-final-curso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDissertacaoFinalCurso for edit and NewDissertacaoFinalCursoFormGroupInput for create.
 */
type DissertacaoFinalCursoFormGroupInput = IDissertacaoFinalCurso | PartialWithRequiredKeyOf<NewDissertacaoFinalCurso>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDissertacaoFinalCurso | NewDissertacaoFinalCurso> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type DissertacaoFinalCursoFormRawValue = FormValueOf<IDissertacaoFinalCurso>;

type NewDissertacaoFinalCursoFormRawValue = FormValueOf<NewDissertacaoFinalCurso>;

type DissertacaoFinalCursoFormDefaults = Pick<NewDissertacaoFinalCurso, 'id' | 'timestamp' | 'isAceiteTermosCompromisso'>;

type DissertacaoFinalCursoFormGroupContent = {
  id: FormControl<DissertacaoFinalCursoFormRawValue['id'] | NewDissertacaoFinalCurso['id']>;
  numero: FormControl<DissertacaoFinalCursoFormRawValue['numero']>;
  timestamp: FormControl<DissertacaoFinalCursoFormRawValue['timestamp']>;
  data: FormControl<DissertacaoFinalCursoFormRawValue['data']>;
  tema: FormControl<DissertacaoFinalCursoFormRawValue['tema']>;
  objectivoGeral: FormControl<DissertacaoFinalCursoFormRawValue['objectivoGeral']>;
  objectivosEspecificos: FormControl<DissertacaoFinalCursoFormRawValue['objectivosEspecificos']>;
  introducao: FormControl<DissertacaoFinalCursoFormRawValue['introducao']>;
  resumo: FormControl<DissertacaoFinalCursoFormRawValue['resumo']>;
  problema: FormControl<DissertacaoFinalCursoFormRawValue['problema']>;
  resultado: FormControl<DissertacaoFinalCursoFormRawValue['resultado']>;
  metodologia: FormControl<DissertacaoFinalCursoFormRawValue['metodologia']>;
  referenciasBibliograficas: FormControl<DissertacaoFinalCursoFormRawValue['referenciasBibliograficas']>;
  observacaoOrientador: FormControl<DissertacaoFinalCursoFormRawValue['observacaoOrientador']>;
  observacaoAreaFormacao: FormControl<DissertacaoFinalCursoFormRawValue['observacaoAreaFormacao']>;
  observacaoInstituicao: FormControl<DissertacaoFinalCursoFormRawValue['observacaoInstituicao']>;
  hash: FormControl<DissertacaoFinalCursoFormRawValue['hash']>;
  termosCompromissos: FormControl<DissertacaoFinalCursoFormRawValue['termosCompromissos']>;
  isAceiteTermosCompromisso: FormControl<DissertacaoFinalCursoFormRawValue['isAceiteTermosCompromisso']>;
  utilizador: FormControl<DissertacaoFinalCursoFormRawValue['utilizador']>;
  turma: FormControl<DissertacaoFinalCursoFormRawValue['turma']>;
  orientador: FormControl<DissertacaoFinalCursoFormRawValue['orientador']>;
  especialidade: FormControl<DissertacaoFinalCursoFormRawValue['especialidade']>;
  discente: FormControl<DissertacaoFinalCursoFormRawValue['discente']>;
  estado: FormControl<DissertacaoFinalCursoFormRawValue['estado']>;
  natureza: FormControl<DissertacaoFinalCursoFormRawValue['natureza']>;
};

export type DissertacaoFinalCursoFormGroup = FormGroup<DissertacaoFinalCursoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DissertacaoFinalCursoFormService {
  createDissertacaoFinalCursoFormGroup(
    dissertacaoFinalCurso: DissertacaoFinalCursoFormGroupInput = { id: null }
  ): DissertacaoFinalCursoFormGroup {
    const dissertacaoFinalCursoRawValue = this.convertDissertacaoFinalCursoToDissertacaoFinalCursoRawValue({
      ...this.getFormDefaults(),
      ...dissertacaoFinalCurso,
    });
    return new FormGroup<DissertacaoFinalCursoFormGroupContent>({
      id: new FormControl(
        { value: dissertacaoFinalCursoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numero: new FormControl(dissertacaoFinalCursoRawValue.numero, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(dissertacaoFinalCursoRawValue.timestamp),
      data: new FormControl(dissertacaoFinalCursoRawValue.data, {
        validators: [Validators.required],
      }),
      tema: new FormControl(dissertacaoFinalCursoRawValue.tema, {
        validators: [Validators.required],
      }),
      objectivoGeral: new FormControl(dissertacaoFinalCursoRawValue.objectivoGeral, {
        validators: [Validators.required],
      }),
      objectivosEspecificos: new FormControl(dissertacaoFinalCursoRawValue.objectivosEspecificos, {
        validators: [Validators.required],
      }),
      introducao: new FormControl(dissertacaoFinalCursoRawValue.introducao, {
        validators: [Validators.required],
      }),
      resumo: new FormControl(dissertacaoFinalCursoRawValue.resumo, {
        validators: [Validators.required],
      }),
      problema: new FormControl(dissertacaoFinalCursoRawValue.problema, {
        validators: [Validators.required],
      }),
      resultado: new FormControl(dissertacaoFinalCursoRawValue.resultado, {
        validators: [Validators.required],
      }),
      metodologia: new FormControl(dissertacaoFinalCursoRawValue.metodologia, {
        validators: [Validators.required],
      }),
      referenciasBibliograficas: new FormControl(dissertacaoFinalCursoRawValue.referenciasBibliograficas, {
        validators: [Validators.required],
      }),
      observacaoOrientador: new FormControl(dissertacaoFinalCursoRawValue.observacaoOrientador),
      observacaoAreaFormacao: new FormControl(dissertacaoFinalCursoRawValue.observacaoAreaFormacao),
      observacaoInstituicao: new FormControl(dissertacaoFinalCursoRawValue.observacaoInstituicao),
      hash: new FormControl(dissertacaoFinalCursoRawValue.hash),
      termosCompromissos: new FormControl(dissertacaoFinalCursoRawValue.termosCompromissos),
      isAceiteTermosCompromisso: new FormControl(dissertacaoFinalCursoRawValue.isAceiteTermosCompromisso),
      utilizador: new FormControl(dissertacaoFinalCursoRawValue.utilizador),
      turma: new FormControl(dissertacaoFinalCursoRawValue.turma, {
        validators: [Validators.required],
      }),
      orientador: new FormControl(dissertacaoFinalCursoRawValue.orientador, {
        validators: [Validators.required],
      }),
      especialidade: new FormControl(dissertacaoFinalCursoRawValue.especialidade, {
        validators: [Validators.required],
      }),
      discente: new FormControl(dissertacaoFinalCursoRawValue.discente, {
        validators: [Validators.required],
      }),
      estado: new FormControl(dissertacaoFinalCursoRawValue.estado, {
        validators: [Validators.required],
      }),
      natureza: new FormControl(dissertacaoFinalCursoRawValue.natureza, {
        validators: [Validators.required],
      }),
    });
  }

  getDissertacaoFinalCurso(form: DissertacaoFinalCursoFormGroup): IDissertacaoFinalCurso | NewDissertacaoFinalCurso {
    return this.convertDissertacaoFinalCursoRawValueToDissertacaoFinalCurso(
      form.getRawValue() as DissertacaoFinalCursoFormRawValue | NewDissertacaoFinalCursoFormRawValue
    );
  }

  resetForm(form: DissertacaoFinalCursoFormGroup, dissertacaoFinalCurso: DissertacaoFinalCursoFormGroupInput): void {
    const dissertacaoFinalCursoRawValue = this.convertDissertacaoFinalCursoToDissertacaoFinalCursoRawValue({
      ...this.getFormDefaults(),
      ...dissertacaoFinalCurso,
    });
    form.reset(
      {
        ...dissertacaoFinalCursoRawValue,
        id: { value: dissertacaoFinalCursoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DissertacaoFinalCursoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
      isAceiteTermosCompromisso: false,
    };
  }

  private convertDissertacaoFinalCursoRawValueToDissertacaoFinalCurso(
    rawDissertacaoFinalCurso: DissertacaoFinalCursoFormRawValue | NewDissertacaoFinalCursoFormRawValue
  ): IDissertacaoFinalCurso | NewDissertacaoFinalCurso {
    return {
      ...rawDissertacaoFinalCurso,
      timestamp: dayjs(rawDissertacaoFinalCurso.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertDissertacaoFinalCursoToDissertacaoFinalCursoRawValue(
    dissertacaoFinalCurso: IDissertacaoFinalCurso | (Partial<NewDissertacaoFinalCurso> & DissertacaoFinalCursoFormDefaults)
  ): DissertacaoFinalCursoFormRawValue | PartialWithRequiredKeyOf<NewDissertacaoFinalCursoFormRawValue> {
    return {
      ...dissertacaoFinalCurso,
      timestamp: dissertacaoFinalCurso.timestamp ? dissertacaoFinalCurso.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
