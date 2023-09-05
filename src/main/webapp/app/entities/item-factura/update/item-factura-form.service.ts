import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IItemFactura, NewItemFactura } from '../item-factura.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IItemFactura for edit and NewItemFacturaFormGroupInput for create.
 */
type ItemFacturaFormGroupInput = IItemFactura | PartialWithRequiredKeyOf<NewItemFactura>;

type ItemFacturaFormDefaults = Pick<NewItemFactura, 'id'>;

type ItemFacturaFormGroupContent = {
  id: FormControl<IItemFactura['id'] | NewItemFactura['id']>;
  quantidade: FormControl<IItemFactura['quantidade']>;
  precoUnitario: FormControl<IItemFactura['precoUnitario']>;
  desconto: FormControl<IItemFactura['desconto']>;
  multa: FormControl<IItemFactura['multa']>;
  juro: FormControl<IItemFactura['juro']>;
  precoTotal: FormControl<IItemFactura['precoTotal']>;
  estado: FormControl<IItemFactura['estado']>;
  taxType: FormControl<IItemFactura['taxType']>;
  taxCountryRegion: FormControl<IItemFactura['taxCountryRegion']>;
  taxCode: FormControl<IItemFactura['taxCode']>;
  taxPercentage: FormControl<IItemFactura['taxPercentage']>;
  taxExemptionReason: FormControl<IItemFactura['taxExemptionReason']>;
  taxExemptionCode: FormControl<IItemFactura['taxExemptionCode']>;
  emissao: FormControl<IItemFactura['emissao']>;
  expiracao: FormControl<IItemFactura['expiracao']>;
  periodo: FormControl<IItemFactura['periodo']>;
  descricao: FormControl<IItemFactura['descricao']>;
  factura: FormControl<IItemFactura['factura']>;
  emolumento: FormControl<IItemFactura['emolumento']>;
};

export type ItemFacturaFormGroup = FormGroup<ItemFacturaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ItemFacturaFormService {
  createItemFacturaFormGroup(itemFactura: ItemFacturaFormGroupInput = { id: null }): ItemFacturaFormGroup {
    const itemFacturaRawValue = {
      ...this.getFormDefaults(),
      ...itemFactura,
    };
    return new FormGroup<ItemFacturaFormGroupContent>({
      id: new FormControl(
        { value: itemFacturaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      quantidade: new FormControl(itemFacturaRawValue.quantidade, {
        validators: [Validators.required, Validators.min(0)],
      }),
      precoUnitario: new FormControl(itemFacturaRawValue.precoUnitario, {
        validators: [Validators.required, Validators.min(0)],
      }),
      desconto: new FormControl(itemFacturaRawValue.desconto, {
        validators: [Validators.required, Validators.min(0)],
      }),
      multa: new FormControl(itemFacturaRawValue.multa, {
        validators: [Validators.required, Validators.min(0)],
      }),
      juro: new FormControl(itemFacturaRawValue.juro, {
        validators: [Validators.required, Validators.min(0)],
      }),
      precoTotal: new FormControl(itemFacturaRawValue.precoTotal, {
        validators: [Validators.required, Validators.min(0)],
      }),
      estado: new FormControl(itemFacturaRawValue.estado, {
        validators: [Validators.required],
      }),
      taxType: new FormControl(itemFacturaRawValue.taxType, {
        validators: [Validators.required, Validators.maxLength(3)],
      }),
      taxCountryRegion: new FormControl(itemFacturaRawValue.taxCountryRegion, {
        validators: [Validators.required, Validators.maxLength(6)],
      }),
      taxCode: new FormControl(itemFacturaRawValue.taxCode, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      taxPercentage: new FormControl(itemFacturaRawValue.taxPercentage, {
        validators: [Validators.min(0), Validators.max(100)],
      }),
      taxExemptionReason: new FormControl(itemFacturaRawValue.taxExemptionReason, {
        validators: [Validators.maxLength(60)],
      }),
      taxExemptionCode: new FormControl(itemFacturaRawValue.taxExemptionCode, {
        validators: [Validators.maxLength(3)],
      }),
      emissao: new FormControl(itemFacturaRawValue.emissao),
      expiracao: new FormControl(itemFacturaRawValue.expiracao),
      periodo: new FormControl(itemFacturaRawValue.periodo),
      descricao: new FormControl(itemFacturaRawValue.descricao),
      factura: new FormControl(itemFacturaRawValue.factura, {
        validators: [Validators.required],
      }),
      emolumento: new FormControl(itemFacturaRawValue.emolumento, {
        validators: [Validators.required],
      }),
    });
  }

  getItemFactura(form: ItemFacturaFormGroup): IItemFactura | NewItemFactura {
    return form.getRawValue() as IItemFactura | NewItemFactura;
  }

  resetForm(form: ItemFacturaFormGroup, itemFactura: ItemFacturaFormGroupInput): void {
    const itemFacturaRawValue = { ...this.getFormDefaults(), ...itemFactura };
    form.reset(
      {
        ...itemFacturaRawValue,
        id: { value: itemFacturaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ItemFacturaFormDefaults {
    return {
      id: null,
    };
  }
}
