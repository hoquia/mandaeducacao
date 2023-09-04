import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategoriaOcorrencia, NewCategoriaOcorrencia } from '../categoria-ocorrencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoriaOcorrencia for edit and NewCategoriaOcorrenciaFormGroupInput for create.
 */
type CategoriaOcorrenciaFormGroupInput = ICategoriaOcorrencia | PartialWithRequiredKeyOf<NewCategoriaOcorrencia>;

type CategoriaOcorrenciaFormDefaults = Pick<
  NewCategoriaOcorrencia,
  'id' | 'isNotificaEncaregado' | 'isSendEmail' | 'isSendSms' | 'isSendPush'
>;

type CategoriaOcorrenciaFormGroupContent = {
  id: FormControl<ICategoriaOcorrencia['id'] | NewCategoriaOcorrencia['id']>;
  codigo: FormControl<ICategoriaOcorrencia['codigo']>;
  sansaoDisicplinar: FormControl<ICategoriaOcorrencia['sansaoDisicplinar']>;
  isNotificaEncaregado: FormControl<ICategoriaOcorrencia['isNotificaEncaregado']>;
  isSendEmail: FormControl<ICategoriaOcorrencia['isSendEmail']>;
  isSendSms: FormControl<ICategoriaOcorrencia['isSendSms']>;
  isSendPush: FormControl<ICategoriaOcorrencia['isSendPush']>;
  descricao: FormControl<ICategoriaOcorrencia['descricao']>;
  observacao: FormControl<ICategoriaOcorrencia['observacao']>;
  encaminhar: FormControl<ICategoriaOcorrencia['encaminhar']>;
  referencia: FormControl<ICategoriaOcorrencia['referencia']>;
  medidaDisciplinar: FormControl<ICategoriaOcorrencia['medidaDisciplinar']>;
};

export type CategoriaOcorrenciaFormGroup = FormGroup<CategoriaOcorrenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoriaOcorrenciaFormService {
  createCategoriaOcorrenciaFormGroup(categoriaOcorrencia: CategoriaOcorrenciaFormGroupInput = { id: null }): CategoriaOcorrenciaFormGroup {
    const categoriaOcorrenciaRawValue = {
      ...this.getFormDefaults(),
      ...categoriaOcorrencia,
    };
    return new FormGroup<CategoriaOcorrenciaFormGroupContent>({
      id: new FormControl(
        { value: categoriaOcorrenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codigo: new FormControl(categoriaOcorrenciaRawValue.codigo, {
        validators: [Validators.required],
      }),
      sansaoDisicplinar: new FormControl(categoriaOcorrenciaRawValue.sansaoDisicplinar),
      isNotificaEncaregado: new FormControl(categoriaOcorrenciaRawValue.isNotificaEncaregado),
      isSendEmail: new FormControl(categoriaOcorrenciaRawValue.isSendEmail),
      isSendSms: new FormControl(categoriaOcorrenciaRawValue.isSendSms),
      isSendPush: new FormControl(categoriaOcorrenciaRawValue.isSendPush),
      descricao: new FormControl(categoriaOcorrenciaRawValue.descricao, {
        validators: [Validators.required],
      }),
      observacao: new FormControl(categoriaOcorrenciaRawValue.observacao),
      encaminhar: new FormControl(categoriaOcorrenciaRawValue.encaminhar),
      referencia: new FormControl(categoriaOcorrenciaRawValue.referencia),
      medidaDisciplinar: new FormControl(categoriaOcorrenciaRawValue.medidaDisciplinar, {
        validators: [Validators.required],
      }),
    });
  }

  getCategoriaOcorrencia(form: CategoriaOcorrenciaFormGroup): ICategoriaOcorrencia | NewCategoriaOcorrencia {
    return form.getRawValue() as ICategoriaOcorrencia | NewCategoriaOcorrencia;
  }

  resetForm(form: CategoriaOcorrenciaFormGroup, categoriaOcorrencia: CategoriaOcorrenciaFormGroupInput): void {
    const categoriaOcorrenciaRawValue = { ...this.getFormDefaults(), ...categoriaOcorrencia };
    form.reset(
      {
        ...categoriaOcorrenciaRawValue,
        id: { value: categoriaOcorrenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategoriaOcorrenciaFormDefaults {
    return {
      id: null,
      isNotificaEncaregado: false,
      isSendEmail: false,
      isSendSms: false,
      isSendPush: false,
    };
  }
}
