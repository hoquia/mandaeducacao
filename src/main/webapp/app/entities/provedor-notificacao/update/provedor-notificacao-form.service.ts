import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProvedorNotificacao, NewProvedorNotificacao } from '../provedor-notificacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProvedorNotificacao for edit and NewProvedorNotificacaoFormGroupInput for create.
 */
type ProvedorNotificacaoFormGroupInput = IProvedorNotificacao | PartialWithRequiredKeyOf<NewProvedorNotificacao>;

type ProvedorNotificacaoFormDefaults = Pick<NewProvedorNotificacao, 'id' | 'isPadrao'>;

type ProvedorNotificacaoFormGroupContent = {
  id: FormControl<IProvedorNotificacao['id'] | NewProvedorNotificacao['id']>;
  telefone: FormControl<IProvedorNotificacao['telefone']>;
  email: FormControl<IProvedorNotificacao['email']>;
  link: FormControl<IProvedorNotificacao['link']>;
  token: FormControl<IProvedorNotificacao['token']>;
  username: FormControl<IProvedorNotificacao['username']>;
  password: FormControl<IProvedorNotificacao['password']>;
  hash: FormControl<IProvedorNotificacao['hash']>;
  isPadrao: FormControl<IProvedorNotificacao['isPadrao']>;
  instituicao: FormControl<IProvedorNotificacao['instituicao']>;
};

export type ProvedorNotificacaoFormGroup = FormGroup<ProvedorNotificacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProvedorNotificacaoFormService {
  createProvedorNotificacaoFormGroup(provedorNotificacao: ProvedorNotificacaoFormGroupInput = { id: null }): ProvedorNotificacaoFormGroup {
    const provedorNotificacaoRawValue = {
      ...this.getFormDefaults(),
      ...provedorNotificacao,
    };
    return new FormGroup<ProvedorNotificacaoFormGroupContent>({
      id: new FormControl(
        { value: provedorNotificacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      telefone: new FormControl(provedorNotificacaoRawValue.telefone, {
        validators: [Validators.required],
      }),
      email: new FormControl(provedorNotificacaoRawValue.email, {
        validators: [Validators.required],
      }),
      link: new FormControl(provedorNotificacaoRawValue.link, {
        validators: [Validators.required],
      }),
      token: new FormControl(provedorNotificacaoRawValue.token, {
        validators: [Validators.required],
      }),
      username: new FormControl(provedorNotificacaoRawValue.username, {
        validators: [Validators.required],
      }),
      password: new FormControl(provedorNotificacaoRawValue.password, {
        validators: [Validators.required],
      }),
      hash: new FormControl(provedorNotificacaoRawValue.hash, {
        validators: [Validators.required],
      }),
      isPadrao: new FormControl(provedorNotificacaoRawValue.isPadrao),
      instituicao: new FormControl(provedorNotificacaoRawValue.instituicao, {
        validators: [Validators.required],
      }),
    });
  }

  getProvedorNotificacao(form: ProvedorNotificacaoFormGroup): IProvedorNotificacao | NewProvedorNotificacao {
    return form.getRawValue() as IProvedorNotificacao | NewProvedorNotificacao;
  }

  resetForm(form: ProvedorNotificacaoFormGroup, provedorNotificacao: ProvedorNotificacaoFormGroupInput): void {
    const provedorNotificacaoRawValue = { ...this.getFormDefaults(), ...provedorNotificacao };
    form.reset(
      {
        ...provedorNotificacaoRawValue,
        id: { value: provedorNotificacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProvedorNotificacaoFormDefaults {
    return {
      id: null,
      isPadrao: false,
    };
  }
}
