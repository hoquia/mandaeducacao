import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMeioPagamento, NewMeioPagamento } from '../meio-pagamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMeioPagamento for edit and NewMeioPagamentoFormGroupInput for create.
 */
type MeioPagamentoFormGroupInput = IMeioPagamento | PartialWithRequiredKeyOf<NewMeioPagamento>;

type MeioPagamentoFormDefaults = Pick<NewMeioPagamento, 'id' | 'isPagamentoInstantanio'>;

type MeioPagamentoFormGroupContent = {
  id: FormControl<IMeioPagamento['id'] | NewMeioPagamento['id']>;
  imagem: FormControl<IMeioPagamento['imagem']>;
  imagemContentType: FormControl<IMeioPagamento['imagemContentType']>;
  codigo: FormControl<IMeioPagamento['codigo']>;
  nome: FormControl<IMeioPagamento['nome']>;
  numeroDigitoReferencia: FormControl<IMeioPagamento['numeroDigitoReferencia']>;
  isPagamentoInstantanio: FormControl<IMeioPagamento['isPagamentoInstantanio']>;
  hash: FormControl<IMeioPagamento['hash']>;
  link: FormControl<IMeioPagamento['link']>;
  token: FormControl<IMeioPagamento['token']>;
  username: FormControl<IMeioPagamento['username']>;
  password: FormControl<IMeioPagamento['password']>;
  formatoReferencia: FormControl<IMeioPagamento['formatoReferencia']>;
};

export type MeioPagamentoFormGroup = FormGroup<MeioPagamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MeioPagamentoFormService {
  createMeioPagamentoFormGroup(meioPagamento: MeioPagamentoFormGroupInput = { id: null }): MeioPagamentoFormGroup {
    const meioPagamentoRawValue = {
      ...this.getFormDefaults(),
      ...meioPagamento,
    };
    return new FormGroup<MeioPagamentoFormGroupContent>({
      id: new FormControl(
        { value: meioPagamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      imagem: new FormControl(meioPagamentoRawValue.imagem),
      imagemContentType: new FormControl(meioPagamentoRawValue.imagemContentType),
      codigo: new FormControl(meioPagamentoRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(meioPagamentoRawValue.nome, {
        validators: [Validators.required],
      }),
      numeroDigitoReferencia: new FormControl(meioPagamentoRawValue.numeroDigitoReferencia, {
        validators: [Validators.min(0)],
      }),
      isPagamentoInstantanio: new FormControl(meioPagamentoRawValue.isPagamentoInstantanio),
      hash: new FormControl(meioPagamentoRawValue.hash),
      link: new FormControl(meioPagamentoRawValue.link),
      token: new FormControl(meioPagamentoRawValue.token),
      username: new FormControl(meioPagamentoRawValue.username),
      password: new FormControl(meioPagamentoRawValue.password),
      formatoReferencia: new FormControl(meioPagamentoRawValue.formatoReferencia),
    });
  }

  getMeioPagamento(form: MeioPagamentoFormGroup): IMeioPagamento | NewMeioPagamento {
    return form.getRawValue() as IMeioPagamento | NewMeioPagamento;
  }

  resetForm(form: MeioPagamentoFormGroup, meioPagamento: MeioPagamentoFormGroupInput): void {
    const meioPagamentoRawValue = { ...this.getFormDefaults(), ...meioPagamento };
    form.reset(
      {
        ...meioPagamentoRawValue,
        id: { value: meioPagamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MeioPagamentoFormDefaults {
    return {
      id: null,
      isPagamentoInstantanio: false,
    };
  }
}
