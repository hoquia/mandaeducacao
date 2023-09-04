import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDocumentoComercial, NewDocumentoComercial } from '../documento-comercial.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocumentoComercial for edit and NewDocumentoComercialFormGroupInput for create.
 */
type DocumentoComercialFormGroupInput = IDocumentoComercial | PartialWithRequiredKeyOf<NewDocumentoComercial>;

type DocumentoComercialFormDefaults = Pick<
  NewDocumentoComercial,
  | 'id'
  | 'isMovimentaEstoque'
  | 'isMovimentaCaixa'
  | 'isNotificaEntidade'
  | 'isNotificaGerente'
  | 'isEnviaSMS'
  | 'isEnviaEmail'
  | 'isEnviaPush'
  | 'validaCreditoDisponivel'
>;

type DocumentoComercialFormGroupContent = {
  id: FormControl<IDocumentoComercial['id'] | NewDocumentoComercial['id']>;
  modulo: FormControl<IDocumentoComercial['modulo']>;
  origem: FormControl<IDocumentoComercial['origem']>;
  siglaInterna: FormControl<IDocumentoComercial['siglaInterna']>;
  descricao: FormControl<IDocumentoComercial['descricao']>;
  siglaFiscal: FormControl<IDocumentoComercial['siglaFiscal']>;
  isMovimentaEstoque: FormControl<IDocumentoComercial['isMovimentaEstoque']>;
  isMovimentaCaixa: FormControl<IDocumentoComercial['isMovimentaCaixa']>;
  isNotificaEntidade: FormControl<IDocumentoComercial['isNotificaEntidade']>;
  isNotificaGerente: FormControl<IDocumentoComercial['isNotificaGerente']>;
  isEnviaSMS: FormControl<IDocumentoComercial['isEnviaSMS']>;
  isEnviaEmail: FormControl<IDocumentoComercial['isEnviaEmail']>;
  isEnviaPush: FormControl<IDocumentoComercial['isEnviaPush']>;
  validaCreditoDisponivel: FormControl<IDocumentoComercial['validaCreditoDisponivel']>;
  transformaEm: FormControl<IDocumentoComercial['transformaEm']>;
};

export type DocumentoComercialFormGroup = FormGroup<DocumentoComercialFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentoComercialFormService {
  createDocumentoComercialFormGroup(documentoComercial: DocumentoComercialFormGroupInput = { id: null }): DocumentoComercialFormGroup {
    const documentoComercialRawValue = {
      ...this.getFormDefaults(),
      ...documentoComercial,
    };
    return new FormGroup<DocumentoComercialFormGroupContent>({
      id: new FormControl(
        { value: documentoComercialRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      modulo: new FormControl(documentoComercialRawValue.modulo, {
        validators: [Validators.required],
      }),
      origem: new FormControl(documentoComercialRawValue.origem, {
        validators: [Validators.required],
      }),
      siglaInterna: new FormControl(documentoComercialRawValue.siglaInterna, {
        validators: [Validators.required, Validators.maxLength(6)],
      }),
      descricao: new FormControl(documentoComercialRawValue.descricao, {
        validators: [Validators.required],
      }),
      siglaFiscal: new FormControl(documentoComercialRawValue.siglaFiscal, {
        validators: [Validators.required],
      }),
      isMovimentaEstoque: new FormControl(documentoComercialRawValue.isMovimentaEstoque),
      isMovimentaCaixa: new FormControl(documentoComercialRawValue.isMovimentaCaixa),
      isNotificaEntidade: new FormControl(documentoComercialRawValue.isNotificaEntidade),
      isNotificaGerente: new FormControl(documentoComercialRawValue.isNotificaGerente),
      isEnviaSMS: new FormControl(documentoComercialRawValue.isEnviaSMS),
      isEnviaEmail: new FormControl(documentoComercialRawValue.isEnviaEmail),
      isEnviaPush: new FormControl(documentoComercialRawValue.isEnviaPush),
      validaCreditoDisponivel: new FormControl(documentoComercialRawValue.validaCreditoDisponivel),
      transformaEm: new FormControl(documentoComercialRawValue.transformaEm),
    });
  }

  getDocumentoComercial(form: DocumentoComercialFormGroup): IDocumentoComercial | NewDocumentoComercial {
    return form.getRawValue() as IDocumentoComercial | NewDocumentoComercial;
  }

  resetForm(form: DocumentoComercialFormGroup, documentoComercial: DocumentoComercialFormGroupInput): void {
    const documentoComercialRawValue = { ...this.getFormDefaults(), ...documentoComercial };
    form.reset(
      {
        ...documentoComercialRawValue,
        id: { value: documentoComercialRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocumentoComercialFormDefaults {
    return {
      id: null,
      isMovimentaEstoque: false,
      isMovimentaCaixa: false,
      isNotificaEntidade: false,
      isNotificaGerente: false,
      isEnviaSMS: false,
      isEnviaEmail: false,
      isEnviaPush: false,
      validaCreditoDisponivel: false,
    };
  }
}
