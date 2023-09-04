import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInstituicaoEnsino, NewInstituicaoEnsino } from '../instituicao-ensino.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInstituicaoEnsino for edit and NewInstituicaoEnsinoFormGroupInput for create.
 */
type InstituicaoEnsinoFormGroupInput = IInstituicaoEnsino | PartialWithRequiredKeyOf<NewInstituicaoEnsino>;

type InstituicaoEnsinoFormDefaults = Pick<NewInstituicaoEnsino, 'id' | 'isComparticipada'>;

type InstituicaoEnsinoFormGroupContent = {
  id: FormControl<IInstituicaoEnsino['id'] | NewInstituicaoEnsino['id']>;
  logotipo: FormControl<IInstituicaoEnsino['logotipo']>;
  logotipoContentType: FormControl<IInstituicaoEnsino['logotipoContentType']>;
  unidadeOrganica: FormControl<IInstituicaoEnsino['unidadeOrganica']>;
  nomeFiscal: FormControl<IInstituicaoEnsino['nomeFiscal']>;
  numero: FormControl<IInstituicaoEnsino['numero']>;
  nif: FormControl<IInstituicaoEnsino['nif']>;
  cae: FormControl<IInstituicaoEnsino['cae']>;
  niss: FormControl<IInstituicaoEnsino['niss']>;
  fundador: FormControl<IInstituicaoEnsino['fundador']>;
  fundacao: FormControl<IInstituicaoEnsino['fundacao']>;
  dimensao: FormControl<IInstituicaoEnsino['dimensao']>;
  slogam: FormControl<IInstituicaoEnsino['slogam']>;
  telefone: FormControl<IInstituicaoEnsino['telefone']>;
  telemovel: FormControl<IInstituicaoEnsino['telemovel']>;
  email: FormControl<IInstituicaoEnsino['email']>;
  website: FormControl<IInstituicaoEnsino['website']>;
  codigoPostal: FormControl<IInstituicaoEnsino['codigoPostal']>;
  enderecoDetalhado: FormControl<IInstituicaoEnsino['enderecoDetalhado']>;
  latitude: FormControl<IInstituicaoEnsino['latitude']>;
  longitude: FormControl<IInstituicaoEnsino['longitude']>;
  descricao: FormControl<IInstituicaoEnsino['descricao']>;
  isComparticipada: FormControl<IInstituicaoEnsino['isComparticipada']>;
  termosCompromissos: FormControl<IInstituicaoEnsino['termosCompromissos']>;
  categoriaInstituicao: FormControl<IInstituicaoEnsino['categoriaInstituicao']>;
  unidadePagadora: FormControl<IInstituicaoEnsino['unidadePagadora']>;
  tipoVinculo: FormControl<IInstituicaoEnsino['tipoVinculo']>;
  tipoInstalacao: FormControl<IInstituicaoEnsino['tipoInstalacao']>;
  sede: FormControl<IInstituicaoEnsino['sede']>;
};

export type InstituicaoEnsinoFormGroup = FormGroup<InstituicaoEnsinoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InstituicaoEnsinoFormService {
  createInstituicaoEnsinoFormGroup(instituicaoEnsino: InstituicaoEnsinoFormGroupInput = { id: null }): InstituicaoEnsinoFormGroup {
    const instituicaoEnsinoRawValue = {
      ...this.getFormDefaults(),
      ...instituicaoEnsino,
    };
    return new FormGroup<InstituicaoEnsinoFormGroupContent>({
      id: new FormControl(
        { value: instituicaoEnsinoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      logotipo: new FormControl(instituicaoEnsinoRawValue.logotipo, {
        validators: [Validators.required],
      }),
      logotipoContentType: new FormControl(instituicaoEnsinoRawValue.logotipoContentType),
      unidadeOrganica: new FormControl(instituicaoEnsinoRawValue.unidadeOrganica, {
        validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)],
      }),
      nomeFiscal: new FormControl(instituicaoEnsinoRawValue.nomeFiscal, {
        validators: [Validators.minLength(5), Validators.maxLength(100)],
      }),
      numero: new FormControl(instituicaoEnsinoRawValue.numero, {
        validators: [Validators.required],
      }),
      nif: new FormControl(instituicaoEnsinoRawValue.nif, {
        validators: [Validators.maxLength(15)],
      }),
      cae: new FormControl(instituicaoEnsinoRawValue.cae),
      niss: new FormControl(instituicaoEnsinoRawValue.niss, {
        validators: [Validators.maxLength(15)],
      }),
      fundador: new FormControl(instituicaoEnsinoRawValue.fundador),
      fundacao: new FormControl(instituicaoEnsinoRawValue.fundacao),
      dimensao: new FormControl(instituicaoEnsinoRawValue.dimensao),
      slogam: new FormControl(instituicaoEnsinoRawValue.slogam),
      telefone: new FormControl(instituicaoEnsinoRawValue.telefone, {
        validators: [Validators.required],
      }),
      telemovel: new FormControl(instituicaoEnsinoRawValue.telemovel),
      email: new FormControl(instituicaoEnsinoRawValue.email, {
        validators: [Validators.required],
      }),
      website: new FormControl(instituicaoEnsinoRawValue.website),
      codigoPostal: new FormControl(instituicaoEnsinoRawValue.codigoPostal, {
        validators: [Validators.maxLength(10)],
      }),
      enderecoDetalhado: new FormControl(instituicaoEnsinoRawValue.enderecoDetalhado, {
        validators: [Validators.required, Validators.minLength(5)],
      }),
      latitude: new FormControl(instituicaoEnsinoRawValue.latitude),
      longitude: new FormControl(instituicaoEnsinoRawValue.longitude),
      descricao: new FormControl(instituicaoEnsinoRawValue.descricao),
      isComparticipada: new FormControl(instituicaoEnsinoRawValue.isComparticipada),
      termosCompromissos: new FormControl(instituicaoEnsinoRawValue.termosCompromissos),
      categoriaInstituicao: new FormControl(instituicaoEnsinoRawValue.categoriaInstituicao),
      unidadePagadora: new FormControl(instituicaoEnsinoRawValue.unidadePagadora),
      tipoVinculo: new FormControl(instituicaoEnsinoRawValue.tipoVinculo),
      tipoInstalacao: new FormControl(instituicaoEnsinoRawValue.tipoInstalacao),
      sede: new FormControl(instituicaoEnsinoRawValue.sede),
    });
  }

  getInstituicaoEnsino(form: InstituicaoEnsinoFormGroup): IInstituicaoEnsino | NewInstituicaoEnsino {
    return form.getRawValue() as IInstituicaoEnsino | NewInstituicaoEnsino;
  }

  resetForm(form: InstituicaoEnsinoFormGroup, instituicaoEnsino: InstituicaoEnsinoFormGroupInput): void {
    const instituicaoEnsinoRawValue = { ...this.getFormDefaults(), ...instituicaoEnsino };
    form.reset(
      {
        ...instituicaoEnsinoRawValue,
        id: { value: instituicaoEnsinoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InstituicaoEnsinoFormDefaults {
    return {
      id: null,
      isComparticipada: false,
    };
  }
}
