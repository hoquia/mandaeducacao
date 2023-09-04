import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocente, NewDocente } from '../docente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocente for edit and NewDocenteFormGroupInput for create.
 */
type DocenteFormGroupInput = IDocente | PartialWithRequiredKeyOf<NewDocente>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocente | NewDocente> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type DocenteFormRawValue = FormValueOf<IDocente>;

type NewDocenteFormRawValue = FormValueOf<NewDocente>;

type DocenteFormDefaults = Pick<NewDocente, 'id' | 'temAgregacaoPedagogica' | 'timestamp'>;

type DocenteFormGroupContent = {
  id: FormControl<DocenteFormRawValue['id'] | NewDocente['id']>;
  fotografia: FormControl<DocenteFormRawValue['fotografia']>;
  fotografiaContentType: FormControl<DocenteFormRawValue['fotografiaContentType']>;
  nome: FormControl<DocenteFormRawValue['nome']>;
  nascimento: FormControl<DocenteFormRawValue['nascimento']>;
  nif: FormControl<DocenteFormRawValue['nif']>;
  inss: FormControl<DocenteFormRawValue['inss']>;
  sexo: FormControl<DocenteFormRawValue['sexo']>;
  pai: FormControl<DocenteFormRawValue['pai']>;
  mae: FormControl<DocenteFormRawValue['mae']>;
  documentoNumero: FormControl<DocenteFormRawValue['documentoNumero']>;
  documentoEmissao: FormControl<DocenteFormRawValue['documentoEmissao']>;
  documentoValidade: FormControl<DocenteFormRawValue['documentoValidade']>;
  residencia: FormControl<DocenteFormRawValue['residencia']>;
  dataInicioFuncoes: FormControl<DocenteFormRawValue['dataInicioFuncoes']>;
  telefonePrincipal: FormControl<DocenteFormRawValue['telefonePrincipal']>;
  telefoneParente: FormControl<DocenteFormRawValue['telefoneParente']>;
  email: FormControl<DocenteFormRawValue['email']>;
  numeroAgente: FormControl<DocenteFormRawValue['numeroAgente']>;
  temAgregacaoPedagogica: FormControl<DocenteFormRawValue['temAgregacaoPedagogica']>;
  observacao: FormControl<DocenteFormRawValue['observacao']>;
  hash: FormControl<DocenteFormRawValue['hash']>;
  timestamp: FormControl<DocenteFormRawValue['timestamp']>;
  nacionalidade: FormControl<DocenteFormRawValue['nacionalidade']>;
  naturalidade: FormControl<DocenteFormRawValue['naturalidade']>;
  tipoDocumento: FormControl<DocenteFormRawValue['tipoDocumento']>;
  grauAcademico: FormControl<DocenteFormRawValue['grauAcademico']>;
  categoriaProfissional: FormControl<DocenteFormRawValue['categoriaProfissional']>;
  unidadeOrganica: FormControl<DocenteFormRawValue['unidadeOrganica']>;
  estadoCivil: FormControl<DocenteFormRawValue['estadoCivil']>;
  responsavelTurno: FormControl<DocenteFormRawValue['responsavelTurno']>;
  responsavelAreaFormacao: FormControl<DocenteFormRawValue['responsavelAreaFormacao']>;
  responsavelCurso: FormControl<DocenteFormRawValue['responsavelCurso']>;
  responsavelDisciplina: FormControl<DocenteFormRawValue['responsavelDisciplina']>;
  responsavelTurma: FormControl<DocenteFormRawValue['responsavelTurma']>;
};

export type DocenteFormGroup = FormGroup<DocenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocenteFormService {
  createDocenteFormGroup(docente: DocenteFormGroupInput = { id: null }): DocenteFormGroup {
    const docenteRawValue = this.convertDocenteToDocenteRawValue({
      ...this.getFormDefaults(),
      ...docente,
    });
    return new FormGroup<DocenteFormGroupContent>({
      id: new FormControl(
        { value: docenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fotografia: new FormControl(docenteRawValue.fotografia),
      fotografiaContentType: new FormControl(docenteRawValue.fotografiaContentType),
      nome: new FormControl(docenteRawValue.nome, {
        validators: [Validators.required],
      }),
      nascimento: new FormControl(docenteRawValue.nascimento, {
        validators: [Validators.required],
      }),
      nif: new FormControl(docenteRawValue.nif),
      inss: new FormControl(docenteRawValue.inss),
      sexo: new FormControl(docenteRawValue.sexo, {
        validators: [Validators.required],
      }),
      pai: new FormControl(docenteRawValue.pai, {
        validators: [Validators.required],
      }),
      mae: new FormControl(docenteRawValue.mae, {
        validators: [Validators.required],
      }),
      documentoNumero: new FormControl(docenteRawValue.documentoNumero, {
        validators: [Validators.required],
      }),
      documentoEmissao: new FormControl(docenteRawValue.documentoEmissao, {
        validators: [Validators.required],
      }),
      documentoValidade: new FormControl(docenteRawValue.documentoValidade, {
        validators: [Validators.required],
      }),
      residencia: new FormControl(docenteRawValue.residencia, {
        validators: [Validators.required],
      }),
      dataInicioFuncoes: new FormControl(docenteRawValue.dataInicioFuncoes, {
        validators: [Validators.required],
      }),
      telefonePrincipal: new FormControl(docenteRawValue.telefonePrincipal, {
        validators: [Validators.required],
      }),
      telefoneParente: new FormControl(docenteRawValue.telefoneParente),
      email: new FormControl(docenteRawValue.email),
      numeroAgente: new FormControl(docenteRawValue.numeroAgente),
      temAgregacaoPedagogica: new FormControl(docenteRawValue.temAgregacaoPedagogica),
      observacao: new FormControl(docenteRawValue.observacao),
      hash: new FormControl(docenteRawValue.hash),
      timestamp: new FormControl(docenteRawValue.timestamp),
      nacionalidade: new FormControl(docenteRawValue.nacionalidade, {
        validators: [Validators.required],
      }),
      naturalidade: new FormControl(docenteRawValue.naturalidade, {
        validators: [Validators.required],
      }),
      tipoDocumento: new FormControl(docenteRawValue.tipoDocumento, {
        validators: [Validators.required],
      }),
      grauAcademico: new FormControl(docenteRawValue.grauAcademico, {
        validators: [Validators.required],
      }),
      categoriaProfissional: new FormControl(docenteRawValue.categoriaProfissional),
      unidadeOrganica: new FormControl(docenteRawValue.unidadeOrganica),
      estadoCivil: new FormControl(docenteRawValue.estadoCivil, {
        validators: [Validators.required],
      }),
      responsavelTurno: new FormControl(docenteRawValue.responsavelTurno),
      responsavelAreaFormacao: new FormControl(docenteRawValue.responsavelAreaFormacao),
      responsavelCurso: new FormControl(docenteRawValue.responsavelCurso),
      responsavelDisciplina: new FormControl(docenteRawValue.responsavelDisciplina),
      responsavelTurma: new FormControl(docenteRawValue.responsavelTurma),
    });
  }

  getDocente(form: DocenteFormGroup): IDocente | NewDocente {
    return this.convertDocenteRawValueToDocente(form.getRawValue() as DocenteFormRawValue | NewDocenteFormRawValue);
  }

  resetForm(form: DocenteFormGroup, docente: DocenteFormGroupInput): void {
    const docenteRawValue = this.convertDocenteToDocenteRawValue({ ...this.getFormDefaults(), ...docente });
    form.reset(
      {
        ...docenteRawValue,
        id: { value: docenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocenteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      temAgregacaoPedagogica: false,
      timestamp: currentTime,
    };
  }

  private convertDocenteRawValueToDocente(rawDocente: DocenteFormRawValue | NewDocenteFormRawValue): IDocente | NewDocente {
    return {
      ...rawDocente,
      timestamp: dayjs(rawDocente.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertDocenteToDocenteRawValue(
    docente: IDocente | (Partial<NewDocente> & DocenteFormDefaults)
  ): DocenteFormRawValue | PartialWithRequiredKeyOf<NewDocenteFormRawValue> {
    return {
      ...docente,
      timestamp: docente.timestamp ? docente.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
