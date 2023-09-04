import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDiscente, NewDiscente } from '../discente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDiscente for edit and NewDiscenteFormGroupInput for create.
 */
type DiscenteFormGroupInput = IDiscente | PartialWithRequiredKeyOf<NewDiscente>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDiscente | NewDiscente> = Omit<T, 'dataIngresso'> & {
  dataIngresso?: string | null;
};

type DiscenteFormRawValue = FormValueOf<IDiscente>;

type NewDiscenteFormRawValue = FormValueOf<NewDiscente>;

type DiscenteFormDefaults = Pick<
  NewDiscente,
  | 'id'
  | 'isEncarregadoEducacao'
  | 'isTrabalhador'
  | 'isFilhoAntigoConbatente'
  | 'isAtestadoPobreza'
  | 'isAsmatico'
  | 'isAlergico'
  | 'isPraticaEducacaoFisica'
  | 'isAutorizadoMedicacao'
  | 'dataIngresso'
>;

type DiscenteFormGroupContent = {
  id: FormControl<DiscenteFormRawValue['id'] | NewDiscente['id']>;
  fotografia: FormControl<DiscenteFormRawValue['fotografia']>;
  fotografiaContentType: FormControl<DiscenteFormRawValue['fotografiaContentType']>;
  nome: FormControl<DiscenteFormRawValue['nome']>;
  nascimento: FormControl<DiscenteFormRawValue['nascimento']>;
  documentoNumero: FormControl<DiscenteFormRawValue['documentoNumero']>;
  documentoEmissao: FormControl<DiscenteFormRawValue['documentoEmissao']>;
  documentoValidade: FormControl<DiscenteFormRawValue['documentoValidade']>;
  nif: FormControl<DiscenteFormRawValue['nif']>;
  sexo: FormControl<DiscenteFormRawValue['sexo']>;
  pai: FormControl<DiscenteFormRawValue['pai']>;
  mae: FormControl<DiscenteFormRawValue['mae']>;
  telefonePrincipal: FormControl<DiscenteFormRawValue['telefonePrincipal']>;
  telefoneParente: FormControl<DiscenteFormRawValue['telefoneParente']>;
  email: FormControl<DiscenteFormRawValue['email']>;
  isEncarregadoEducacao: FormControl<DiscenteFormRawValue['isEncarregadoEducacao']>;
  isTrabalhador: FormControl<DiscenteFormRawValue['isTrabalhador']>;
  isFilhoAntigoConbatente: FormControl<DiscenteFormRawValue['isFilhoAntigoConbatente']>;
  isAtestadoPobreza: FormControl<DiscenteFormRawValue['isAtestadoPobreza']>;
  nomeMedico: FormControl<DiscenteFormRawValue['nomeMedico']>;
  telefoneMedico: FormControl<DiscenteFormRawValue['telefoneMedico']>;
  instituicaoParticularSaude: FormControl<DiscenteFormRawValue['instituicaoParticularSaude']>;
  altura: FormControl<DiscenteFormRawValue['altura']>;
  peso: FormControl<DiscenteFormRawValue['peso']>;
  isAsmatico: FormControl<DiscenteFormRawValue['isAsmatico']>;
  isAlergico: FormControl<DiscenteFormRawValue['isAlergico']>;
  isPraticaEducacaoFisica: FormControl<DiscenteFormRawValue['isPraticaEducacaoFisica']>;
  isAutorizadoMedicacao: FormControl<DiscenteFormRawValue['isAutorizadoMedicacao']>;
  cuidadosEspeciaisSaude: FormControl<DiscenteFormRawValue['cuidadosEspeciaisSaude']>;
  numeroProcesso: FormControl<DiscenteFormRawValue['numeroProcesso']>;
  dataIngresso: FormControl<DiscenteFormRawValue['dataIngresso']>;
  hash: FormControl<DiscenteFormRawValue['hash']>;
  observacao: FormControl<DiscenteFormRawValue['observacao']>;
  nacionalidade: FormControl<DiscenteFormRawValue['nacionalidade']>;
  naturalidade: FormControl<DiscenteFormRawValue['naturalidade']>;
  tipoDocumento: FormControl<DiscenteFormRawValue['tipoDocumento']>;
  profissao: FormControl<DiscenteFormRawValue['profissao']>;
  grupoSanguinio: FormControl<DiscenteFormRawValue['grupoSanguinio']>;
  necessidadeEspecial: FormControl<DiscenteFormRawValue['necessidadeEspecial']>;
  encarregadoEducacao: FormControl<DiscenteFormRawValue['encarregadoEducacao']>;
};

export type DiscenteFormGroup = FormGroup<DiscenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DiscenteFormService {
  createDiscenteFormGroup(discente: DiscenteFormGroupInput = { id: null }): DiscenteFormGroup {
    const discenteRawValue = this.convertDiscenteToDiscenteRawValue({
      ...this.getFormDefaults(),
      ...discente,
    });
    return new FormGroup<DiscenteFormGroupContent>({
      id: new FormControl(
        { value: discenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fotografia: new FormControl(discenteRawValue.fotografia),
      fotografiaContentType: new FormControl(discenteRawValue.fotografiaContentType),
      nome: new FormControl(discenteRawValue.nome, {
        validators: [Validators.required],
      }),
      nascimento: new FormControl(discenteRawValue.nascimento, {
        validators: [Validators.required],
      }),
      documentoNumero: new FormControl(discenteRawValue.documentoNumero, {
        validators: [Validators.required],
      }),
      documentoEmissao: new FormControl(discenteRawValue.documentoEmissao, {
        validators: [Validators.required],
      }),
      documentoValidade: new FormControl(discenteRawValue.documentoValidade, {
        validators: [Validators.required],
      }),
      nif: new FormControl(discenteRawValue.nif),
      sexo: new FormControl(discenteRawValue.sexo, {
        validators: [Validators.required],
      }),
      pai: new FormControl(discenteRawValue.pai, {
        validators: [Validators.required],
      }),
      mae: new FormControl(discenteRawValue.mae, {
        validators: [Validators.required],
      }),
      telefonePrincipal: new FormControl(discenteRawValue.telefonePrincipal),
      telefoneParente: new FormControl(discenteRawValue.telefoneParente),
      email: new FormControl(discenteRawValue.email),
      isEncarregadoEducacao: new FormControl(discenteRawValue.isEncarregadoEducacao),
      isTrabalhador: new FormControl(discenteRawValue.isTrabalhador),
      isFilhoAntigoConbatente: new FormControl(discenteRawValue.isFilhoAntigoConbatente),
      isAtestadoPobreza: new FormControl(discenteRawValue.isAtestadoPobreza),
      nomeMedico: new FormControl(discenteRawValue.nomeMedico),
      telefoneMedico: new FormControl(discenteRawValue.telefoneMedico, {
        validators: [Validators.maxLength(9)],
      }),
      instituicaoParticularSaude: new FormControl(discenteRawValue.instituicaoParticularSaude),
      altura: new FormControl(discenteRawValue.altura, {
        validators: [Validators.min(0)],
      }),
      peso: new FormControl(discenteRawValue.peso, {
        validators: [Validators.min(0)],
      }),
      isAsmatico: new FormControl(discenteRawValue.isAsmatico),
      isAlergico: new FormControl(discenteRawValue.isAlergico),
      isPraticaEducacaoFisica: new FormControl(discenteRawValue.isPraticaEducacaoFisica),
      isAutorizadoMedicacao: new FormControl(discenteRawValue.isAutorizadoMedicacao),
      cuidadosEspeciaisSaude: new FormControl(discenteRawValue.cuidadosEspeciaisSaude),
      numeroProcesso: new FormControl(discenteRawValue.numeroProcesso, {
        validators: [Validators.required],
      }),
      dataIngresso: new FormControl(discenteRawValue.dataIngresso),
      hash: new FormControl(discenteRawValue.hash),
      observacao: new FormControl(discenteRawValue.observacao),
      nacionalidade: new FormControl(discenteRawValue.nacionalidade, {
        validators: [Validators.required],
      }),
      naturalidade: new FormControl(discenteRawValue.naturalidade, {
        validators: [Validators.required],
      }),
      tipoDocumento: new FormControl(discenteRawValue.tipoDocumento, {
        validators: [Validators.required],
      }),
      profissao: new FormControl(discenteRawValue.profissao),
      grupoSanguinio: new FormControl(discenteRawValue.grupoSanguinio),
      necessidadeEspecial: new FormControl(discenteRawValue.necessidadeEspecial),
      encarregadoEducacao: new FormControl(discenteRawValue.encarregadoEducacao),
    });
  }

  getDiscente(form: DiscenteFormGroup): IDiscente | NewDiscente {
    return this.convertDiscenteRawValueToDiscente(form.getRawValue() as DiscenteFormRawValue | NewDiscenteFormRawValue);
  }

  resetForm(form: DiscenteFormGroup, discente: DiscenteFormGroupInput): void {
    const discenteRawValue = this.convertDiscenteToDiscenteRawValue({ ...this.getFormDefaults(), ...discente });
    form.reset(
      {
        ...discenteRawValue,
        id: { value: discenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DiscenteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isEncarregadoEducacao: false,
      isTrabalhador: false,
      isFilhoAntigoConbatente: false,
      isAtestadoPobreza: false,
      isAsmatico: false,
      isAlergico: false,
      isPraticaEducacaoFisica: false,
      isAutorizadoMedicacao: false,
      dataIngresso: currentTime,
    };
  }

  private convertDiscenteRawValueToDiscente(rawDiscente: DiscenteFormRawValue | NewDiscenteFormRawValue): IDiscente | NewDiscente {
    return {
      ...rawDiscente,
      dataIngresso: dayjs(rawDiscente.dataIngresso, DATE_TIME_FORMAT),
    };
  }

  private convertDiscenteToDiscenteRawValue(
    discente: IDiscente | (Partial<NewDiscente> & DiscenteFormDefaults)
  ): DiscenteFormRawValue | PartialWithRequiredKeyOf<NewDiscenteFormRawValue> {
    return {
      ...discente,
      dataIngresso: discente.dataIngresso ? discente.dataIngresso.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
