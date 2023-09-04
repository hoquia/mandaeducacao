import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmolumento, NewEmolumento } from '../emolumento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmolumento for edit and NewEmolumentoFormGroupInput for create.
 */
type EmolumentoFormGroupInput = IEmolumento | PartialWithRequiredKeyOf<NewEmolumento>;

type EmolumentoFormDefaults = Pick<NewEmolumento, 'id' | 'isObrigatorioMatricula' | 'isObrigatorioConfirmacao'>;

type EmolumentoFormGroupContent = {
  id: FormControl<IEmolumento['id'] | NewEmolumento['id']>;
  imagem: FormControl<IEmolumento['imagem']>;
  imagemContentType: FormControl<IEmolumento['imagemContentType']>;
  numero: FormControl<IEmolumento['numero']>;
  nome: FormControl<IEmolumento['nome']>;
  preco: FormControl<IEmolumento['preco']>;
  quantidade: FormControl<IEmolumento['quantidade']>;
  periodo: FormControl<IEmolumento['periodo']>;
  inicioPeriodo: FormControl<IEmolumento['inicioPeriodo']>;
  fimPeriodo: FormControl<IEmolumento['fimPeriodo']>;
  isObrigatorioMatricula: FormControl<IEmolumento['isObrigatorioMatricula']>;
  isObrigatorioConfirmacao: FormControl<IEmolumento['isObrigatorioConfirmacao']>;
  categoria: FormControl<IEmolumento['categoria']>;
  imposto: FormControl<IEmolumento['imposto']>;
  referencia: FormControl<IEmolumento['referencia']>;
  planoMulta: FormControl<IEmolumento['planoMulta']>;
};

export type EmolumentoFormGroup = FormGroup<EmolumentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmolumentoFormService {
  createEmolumentoFormGroup(emolumento: EmolumentoFormGroupInput = { id: null }): EmolumentoFormGroup {
    const emolumentoRawValue = {
      ...this.getFormDefaults(),
      ...emolumento,
    };
    return new FormGroup<EmolumentoFormGroupContent>({
      id: new FormControl(
        { value: emolumentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      imagem: new FormControl(emolumentoRawValue.imagem),
      imagemContentType: new FormControl(emolumentoRawValue.imagemContentType),
      numero: new FormControl(emolumentoRawValue.numero, {
        validators: [Validators.required],
      }),
      nome: new FormControl(emolumentoRawValue.nome, {
        validators: [Validators.required],
      }),
      preco: new FormControl(emolumentoRawValue.preco, {
        validators: [Validators.required, Validators.min(0)],
      }),
      quantidade: new FormControl(emolumentoRawValue.quantidade, {
        validators: [Validators.required, Validators.min(0)],
      }),
      periodo: new FormControl(emolumentoRawValue.periodo, {
        validators: [Validators.min(1), Validators.max(12)],
      }),
      inicioPeriodo: new FormControl(emolumentoRawValue.inicioPeriodo, {
        validators: [Validators.min(1), Validators.max(12)],
      }),
      fimPeriodo: new FormControl(emolumentoRawValue.fimPeriodo, {
        validators: [Validators.min(1), Validators.max(12)],
      }),
      isObrigatorioMatricula: new FormControl(emolumentoRawValue.isObrigatorioMatricula),
      isObrigatorioConfirmacao: new FormControl(emolumentoRawValue.isObrigatorioConfirmacao),
      categoria: new FormControl(emolumentoRawValue.categoria, {
        validators: [Validators.required],
      }),
      imposto: new FormControl(emolumentoRawValue.imposto, {
        validators: [Validators.required],
      }),
      referencia: new FormControl(emolumentoRawValue.referencia),
      planoMulta: new FormControl(emolumentoRawValue.planoMulta),
    });
  }

  getEmolumento(form: EmolumentoFormGroup): IEmolumento | NewEmolumento {
    return form.getRawValue() as IEmolumento | NewEmolumento;
  }

  resetForm(form: EmolumentoFormGroup, emolumento: EmolumentoFormGroupInput): void {
    const emolumentoRawValue = { ...this.getFormDefaults(), ...emolumento };
    form.reset(
      {
        ...emolumentoRawValue,
        id: { value: emolumentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmolumentoFormDefaults {
    return {
      id: null,
      isObrigatorioMatricula: false,
      isObrigatorioConfirmacao: false,
    };
  }
}
