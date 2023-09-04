import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IImposto, NewImposto } from '../imposto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImposto for edit and NewImpostoFormGroupInput for create.
 */
type ImpostoFormGroupInput = IImposto | PartialWithRequiredKeyOf<NewImposto>;

type ImpostoFormDefaults = Pick<NewImposto, 'id' | 'isRetencao'>;

type ImpostoFormGroupContent = {
  id: FormControl<IImposto['id'] | NewImposto['id']>;
  descricao: FormControl<IImposto['descricao']>;
  pais: FormControl<IImposto['pais']>;
  taxa: FormControl<IImposto['taxa']>;
  isRetencao: FormControl<IImposto['isRetencao']>;
  motivoDescricao: FormControl<IImposto['motivoDescricao']>;
  motivoCodigo: FormControl<IImposto['motivoCodigo']>;
  tipoImposto: FormControl<IImposto['tipoImposto']>;
  codigoImposto: FormControl<IImposto['codigoImposto']>;
  motivoIsencaoCodigo: FormControl<IImposto['motivoIsencaoCodigo']>;
  motivoIsencaoDescricao: FormControl<IImposto['motivoIsencaoDescricao']>;
};

export type ImpostoFormGroup = FormGroup<ImpostoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImpostoFormService {
  createImpostoFormGroup(imposto: ImpostoFormGroupInput = { id: null }): ImpostoFormGroup {
    const impostoRawValue = {
      ...this.getFormDefaults(),
      ...imposto,
    };
    return new FormGroup<ImpostoFormGroupContent>({
      id: new FormControl(
        { value: impostoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descricao: new FormControl(impostoRawValue.descricao, {
        validators: [Validators.required],
      }),
      pais: new FormControl(impostoRawValue.pais),
      taxa: new FormControl(impostoRawValue.taxa, {
        validators: [Validators.required, Validators.min(0), Validators.max(100)],
      }),
      isRetencao: new FormControl(impostoRawValue.isRetencao),
      motivoDescricao: new FormControl(impostoRawValue.motivoDescricao),
      motivoCodigo: new FormControl(impostoRawValue.motivoCodigo),
      tipoImposto: new FormControl(impostoRawValue.tipoImposto, {
        validators: [Validators.required],
      }),
      codigoImposto: new FormControl(impostoRawValue.codigoImposto, {
        validators: [Validators.required],
      }),
      motivoIsencaoCodigo: new FormControl(impostoRawValue.motivoIsencaoCodigo),
      motivoIsencaoDescricao: new FormControl(impostoRawValue.motivoIsencaoDescricao),
    });
  }

  getImposto(form: ImpostoFormGroup): IImposto | NewImposto {
    return form.getRawValue() as IImposto | NewImposto;
  }

  resetForm(form: ImpostoFormGroup, imposto: ImpostoFormGroupInput): void {
    const impostoRawValue = { ...this.getFormDefaults(), ...imposto };
    form.reset(
      {
        ...impostoRawValue,
        id: { value: impostoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ImpostoFormDefaults {
    return {
      id: null,
      isRetencao: false,
    };
  }
}
