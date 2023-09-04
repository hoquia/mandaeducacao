import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAnoLectivo, NewAnoLectivo } from '../ano-lectivo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnoLectivo for edit and NewAnoLectivoFormGroupInput for create.
 */
type AnoLectivoFormGroupInput = IAnoLectivo | PartialWithRequiredKeyOf<NewAnoLectivo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAnoLectivo | NewAnoLectivo> = Omit<T, 'timestam'> & {
  timestam?: string | null;
};

type AnoLectivoFormRawValue = FormValueOf<IAnoLectivo>;

type NewAnoLectivoFormRawValue = FormValueOf<NewAnoLectivo>;

type AnoLectivoFormDefaults = Pick<NewAnoLectivo, 'id' | 'timestam' | 'isActual' | 'nivesEnsinos'>;

type AnoLectivoFormGroupContent = {
  id: FormControl<AnoLectivoFormRawValue['id'] | NewAnoLectivo['id']>;
  ano: FormControl<AnoLectivoFormRawValue['ano']>;
  inicio: FormControl<AnoLectivoFormRawValue['inicio']>;
  fim: FormControl<AnoLectivoFormRawValue['fim']>;
  descricao: FormControl<AnoLectivoFormRawValue['descricao']>;
  timestam: FormControl<AnoLectivoFormRawValue['timestam']>;
  isActual: FormControl<AnoLectivoFormRawValue['isActual']>;
  directorGeral: FormControl<AnoLectivoFormRawValue['directorGeral']>;
  subDirectorPdagogico: FormControl<AnoLectivoFormRawValue['subDirectorPdagogico']>;
  subDirectorAdministrativo: FormControl<AnoLectivoFormRawValue['subDirectorAdministrativo']>;
  responsavelSecretariaGeral: FormControl<AnoLectivoFormRawValue['responsavelSecretariaGeral']>;
  responsavelSecretariaPedagogico: FormControl<AnoLectivoFormRawValue['responsavelSecretariaPedagogico']>;
  utilizador: FormControl<AnoLectivoFormRawValue['utilizador']>;
  nivesEnsinos: FormControl<AnoLectivoFormRawValue['nivesEnsinos']>;
  turma: FormControl<AnoLectivoFormRawValue['turma']>;
  horario: FormControl<AnoLectivoFormRawValue['horario']>;
  planoAula: FormControl<AnoLectivoFormRawValue['planoAula']>;
  licao: FormControl<AnoLectivoFormRawValue['licao']>;
  processoSelectivoMatricula: FormControl<AnoLectivoFormRawValue['processoSelectivoMatricula']>;
  ocorrencia: FormControl<AnoLectivoFormRawValue['ocorrencia']>;
  notasPeriodicaDisciplina: FormControl<AnoLectivoFormRawValue['notasPeriodicaDisciplina']>;
  notasGeralDisciplina: FormControl<AnoLectivoFormRawValue['notasGeralDisciplina']>;
  dissertacaoFinalCurso: FormControl<AnoLectivoFormRawValue['dissertacaoFinalCurso']>;
  factura: FormControl<AnoLectivoFormRawValue['factura']>;
  recibo: FormControl<AnoLectivoFormRawValue['recibo']>;
  responsavelTurno: FormControl<AnoLectivoFormRawValue['responsavelTurno']>;
  responsavelAreaFormacao: FormControl<AnoLectivoFormRawValue['responsavelAreaFormacao']>;
  responsavelCurso: FormControl<AnoLectivoFormRawValue['responsavelCurso']>;
  responsavelDisciplina: FormControl<AnoLectivoFormRawValue['responsavelDisciplina']>;
  responsavelTurma: FormControl<AnoLectivoFormRawValue['responsavelTurma']>;
};

export type AnoLectivoFormGroup = FormGroup<AnoLectivoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnoLectivoFormService {
  createAnoLectivoFormGroup(anoLectivo: AnoLectivoFormGroupInput = { id: null }): AnoLectivoFormGroup {
    const anoLectivoRawValue = this.convertAnoLectivoToAnoLectivoRawValue({
      ...this.getFormDefaults(),
      ...anoLectivo,
    });
    return new FormGroup<AnoLectivoFormGroupContent>({
      id: new FormControl(
        { value: anoLectivoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      ano: new FormControl(anoLectivoRawValue.ano, {
        validators: [Validators.required],
      }),
      inicio: new FormControl(anoLectivoRawValue.inicio, {
        validators: [Validators.required],
      }),
      fim: new FormControl(anoLectivoRawValue.fim, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(anoLectivoRawValue.descricao, {
        validators: [Validators.required],
      }),
      timestam: new FormControl(anoLectivoRawValue.timestam),
      isActual: new FormControl(anoLectivoRawValue.isActual),
      directorGeral: new FormControl(anoLectivoRawValue.directorGeral),
      subDirectorPdagogico: new FormControl(anoLectivoRawValue.subDirectorPdagogico),
      subDirectorAdministrativo: new FormControl(anoLectivoRawValue.subDirectorAdministrativo),
      responsavelSecretariaGeral: new FormControl(anoLectivoRawValue.responsavelSecretariaGeral),
      responsavelSecretariaPedagogico: new FormControl(anoLectivoRawValue.responsavelSecretariaPedagogico),
      utilizador: new FormControl(anoLectivoRawValue.utilizador),
      nivesEnsinos: new FormControl(anoLectivoRawValue.nivesEnsinos ?? []),
      turma: new FormControl(anoLectivoRawValue.turma),
      horario: new FormControl(anoLectivoRawValue.horario),
      planoAula: new FormControl(anoLectivoRawValue.planoAula),
      licao: new FormControl(anoLectivoRawValue.licao),
      processoSelectivoMatricula: new FormControl(anoLectivoRawValue.processoSelectivoMatricula),
      ocorrencia: new FormControl(anoLectivoRawValue.ocorrencia),
      notasPeriodicaDisciplina: new FormControl(anoLectivoRawValue.notasPeriodicaDisciplina),
      notasGeralDisciplina: new FormControl(anoLectivoRawValue.notasGeralDisciplina),
      dissertacaoFinalCurso: new FormControl(anoLectivoRawValue.dissertacaoFinalCurso),
      factura: new FormControl(anoLectivoRawValue.factura),
      recibo: new FormControl(anoLectivoRawValue.recibo),
      responsavelTurno: new FormControl(anoLectivoRawValue.responsavelTurno),
      responsavelAreaFormacao: new FormControl(anoLectivoRawValue.responsavelAreaFormacao),
      responsavelCurso: new FormControl(anoLectivoRawValue.responsavelCurso),
      responsavelDisciplina: new FormControl(anoLectivoRawValue.responsavelDisciplina),
      responsavelTurma: new FormControl(anoLectivoRawValue.responsavelTurma),
    });
  }

  getAnoLectivo(form: AnoLectivoFormGroup): IAnoLectivo | NewAnoLectivo {
    return this.convertAnoLectivoRawValueToAnoLectivo(form.getRawValue() as AnoLectivoFormRawValue | NewAnoLectivoFormRawValue);
  }

  resetForm(form: AnoLectivoFormGroup, anoLectivo: AnoLectivoFormGroupInput): void {
    const anoLectivoRawValue = this.convertAnoLectivoToAnoLectivoRawValue({ ...this.getFormDefaults(), ...anoLectivo });
    form.reset(
      {
        ...anoLectivoRawValue,
        id: { value: anoLectivoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnoLectivoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestam: currentTime,
      isActual: false,
      nivesEnsinos: [],
    };
  }

  private convertAnoLectivoRawValueToAnoLectivo(
    rawAnoLectivo: AnoLectivoFormRawValue | NewAnoLectivoFormRawValue
  ): IAnoLectivo | NewAnoLectivo {
    return {
      ...rawAnoLectivo,
      timestam: dayjs(rawAnoLectivo.timestam, DATE_TIME_FORMAT),
    };
  }

  private convertAnoLectivoToAnoLectivoRawValue(
    anoLectivo: IAnoLectivo | (Partial<NewAnoLectivo> & AnoLectivoFormDefaults)
  ): AnoLectivoFormRawValue | PartialWithRequiredKeyOf<NewAnoLectivoFormRawValue> {
    return {
      ...anoLectivo,
      timestam: anoLectivo.timestam ? anoLectivo.timestam.format(DATE_TIME_FORMAT) : undefined,
      nivesEnsinos: anoLectivo.nivesEnsinos ?? [],
    };
  }
}
