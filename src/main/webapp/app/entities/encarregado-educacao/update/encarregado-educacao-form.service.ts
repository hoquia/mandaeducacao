import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEncarregadoEducacao, NewEncarregadoEducacao } from '../encarregado-educacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEncarregadoEducacao for edit and NewEncarregadoEducacaoFormGroupInput for create.
 */
type EncarregadoEducacaoFormGroupInput = IEncarregadoEducacao | PartialWithRequiredKeyOf<NewEncarregadoEducacao>;

type EncarregadoEducacaoFormDefaults = Pick<NewEncarregadoEducacao, 'id'>;

type EncarregadoEducacaoFormGroupContent = {
  id: FormControl<IEncarregadoEducacao['id'] | NewEncarregadoEducacao['id']>;
  fotografia: FormControl<IEncarregadoEducacao['fotografia']>;
  fotografiaContentType: FormControl<IEncarregadoEducacao['fotografiaContentType']>;
  nome: FormControl<IEncarregadoEducacao['nome']>;
  nascimento: FormControl<IEncarregadoEducacao['nascimento']>;
  nif: FormControl<IEncarregadoEducacao['nif']>;
  sexo: FormControl<IEncarregadoEducacao['sexo']>;
  documentoNumero: FormControl<IEncarregadoEducacao['documentoNumero']>;
  telefonePrincipal: FormControl<IEncarregadoEducacao['telefonePrincipal']>;
  telefoneAlternativo: FormControl<IEncarregadoEducacao['telefoneAlternativo']>;
  email: FormControl<IEncarregadoEducacao['email']>;
  residencia: FormControl<IEncarregadoEducacao['residencia']>;
  enderecoTrabalho: FormControl<IEncarregadoEducacao['enderecoTrabalho']>;
  rendaMensal: FormControl<IEncarregadoEducacao['rendaMensal']>;
  empresaTrabalho: FormControl<IEncarregadoEducacao['empresaTrabalho']>;
  hash: FormControl<IEncarregadoEducacao['hash']>;
  grauParentesco: FormControl<IEncarregadoEducacao['grauParentesco']>;
  tipoDocumento: FormControl<IEncarregadoEducacao['tipoDocumento']>;
  profissao: FormControl<IEncarregadoEducacao['profissao']>;
};

export type EncarregadoEducacaoFormGroup = FormGroup<EncarregadoEducacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EncarregadoEducacaoFormService {
  createEncarregadoEducacaoFormGroup(encarregadoEducacao: EncarregadoEducacaoFormGroupInput = { id: null }): EncarregadoEducacaoFormGroup {
    const encarregadoEducacaoRawValue = {
      ...this.getFormDefaults(),
      ...encarregadoEducacao,
    };
    return new FormGroup<EncarregadoEducacaoFormGroupContent>({
      id: new FormControl(
        { value: encarregadoEducacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fotografia: new FormControl(encarregadoEducacaoRawValue.fotografia),
      fotografiaContentType: new FormControl(encarregadoEducacaoRawValue.fotografiaContentType),
      nome: new FormControl(encarregadoEducacaoRawValue.nome, {
        validators: [Validators.required],
      }),
      nascimento: new FormControl(encarregadoEducacaoRawValue.nascimento, {
        validators: [Validators.required],
      }),
      nif: new FormControl(encarregadoEducacaoRawValue.nif),
      sexo: new FormControl(encarregadoEducacaoRawValue.sexo, {
        validators: [Validators.required],
      }),
      documentoNumero: new FormControl(encarregadoEducacaoRawValue.documentoNumero, {
        validators: [Validators.required],
      }),
      telefonePrincipal: new FormControl(encarregadoEducacaoRawValue.telefonePrincipal, {
        validators: [Validators.required],
      }),
      telefoneAlternativo: new FormControl(encarregadoEducacaoRawValue.telefoneAlternativo),
      email: new FormControl(encarregadoEducacaoRawValue.email),
      residencia: new FormControl(encarregadoEducacaoRawValue.residencia),
      enderecoTrabalho: new FormControl(encarregadoEducacaoRawValue.enderecoTrabalho),
      rendaMensal: new FormControl(encarregadoEducacaoRawValue.rendaMensal, {
        validators: [Validators.min(0)],
      }),
      empresaTrabalho: new FormControl(encarregadoEducacaoRawValue.empresaTrabalho),
      hash: new FormControl(encarregadoEducacaoRawValue.hash),
      grauParentesco: new FormControl(encarregadoEducacaoRawValue.grauParentesco, {
        validators: [Validators.required],
      }),
      tipoDocumento: new FormControl(encarregadoEducacaoRawValue.tipoDocumento, {
        validators: [Validators.required],
      }),
      profissao: new FormControl(encarregadoEducacaoRawValue.profissao),
    });
  }

  getEncarregadoEducacao(form: EncarregadoEducacaoFormGroup): IEncarregadoEducacao | NewEncarregadoEducacao {
    return form.getRawValue() as IEncarregadoEducacao | NewEncarregadoEducacao;
  }

  resetForm(form: EncarregadoEducacaoFormGroup, encarregadoEducacao: EncarregadoEducacaoFormGroupInput): void {
    const encarregadoEducacaoRawValue = { ...this.getFormDefaults(), ...encarregadoEducacao };
    form.reset(
      {
        ...encarregadoEducacaoRawValue,
        id: { value: encarregadoEducacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EncarregadoEducacaoFormDefaults {
    return {
      id: null,
    };
  }
}
