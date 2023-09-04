import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IResumoImpostoFactura, NewResumoImpostoFactura } from '../resumo-imposto-factura.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResumoImpostoFactura for edit and NewResumoImpostoFacturaFormGroupInput for create.
 */
type ResumoImpostoFacturaFormGroupInput = IResumoImpostoFactura | PartialWithRequiredKeyOf<NewResumoImpostoFactura>;

type ResumoImpostoFacturaFormDefaults = Pick<NewResumoImpostoFactura, 'id' | 'isRetencao'>;

type ResumoImpostoFacturaFormGroupContent = {
  id: FormControl<IResumoImpostoFactura['id'] | NewResumoImpostoFactura['id']>;
  isRetencao: FormControl<IResumoImpostoFactura['isRetencao']>;
  descricao: FormControl<IResumoImpostoFactura['descricao']>;
  tipo: FormControl<IResumoImpostoFactura['tipo']>;
  taxa: FormControl<IResumoImpostoFactura['taxa']>;
  incidencia: FormControl<IResumoImpostoFactura['incidencia']>;
  montante: FormControl<IResumoImpostoFactura['montante']>;
  factura: FormControl<IResumoImpostoFactura['factura']>;
};

export type ResumoImpostoFacturaFormGroup = FormGroup<ResumoImpostoFacturaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResumoImpostoFacturaFormService {
  createResumoImpostoFacturaFormGroup(
    resumoImpostoFactura: ResumoImpostoFacturaFormGroupInput = { id: null }
  ): ResumoImpostoFacturaFormGroup {
    const resumoImpostoFacturaRawValue = {
      ...this.getFormDefaults(),
      ...resumoImpostoFactura,
    };
    return new FormGroup<ResumoImpostoFacturaFormGroupContent>({
      id: new FormControl(
        { value: resumoImpostoFacturaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      isRetencao: new FormControl(resumoImpostoFacturaRawValue.isRetencao),
      descricao: new FormControl(resumoImpostoFacturaRawValue.descricao, {
        validators: [Validators.required],
      }),
      tipo: new FormControl(resumoImpostoFacturaRawValue.tipo, {
        validators: [Validators.required],
      }),
      taxa: new FormControl(resumoImpostoFacturaRawValue.taxa, {
        validators: [Validators.required, Validators.min(0), Validators.max(100)],
      }),
      incidencia: new FormControl(resumoImpostoFacturaRawValue.incidencia, {
        validators: [Validators.required, Validators.min(0)],
      }),
      montante: new FormControl(resumoImpostoFacturaRawValue.montante, {
        validators: [Validators.required, Validators.min(0)],
      }),
      factura: new FormControl(resumoImpostoFacturaRawValue.factura, {
        validators: [Validators.required],
      }),
    });
  }

  getResumoImpostoFactura(form: ResumoImpostoFacturaFormGroup): IResumoImpostoFactura | NewResumoImpostoFactura {
    return form.getRawValue() as IResumoImpostoFactura | NewResumoImpostoFactura;
  }

  resetForm(form: ResumoImpostoFacturaFormGroup, resumoImpostoFactura: ResumoImpostoFacturaFormGroupInput): void {
    const resumoImpostoFacturaRawValue = { ...this.getFormDefaults(), ...resumoImpostoFactura };
    form.reset(
      {
        ...resumoImpostoFacturaRawValue,
        id: { value: resumoImpostoFacturaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResumoImpostoFacturaFormDefaults {
    return {
      id: null,
      isRetencao: false,
    };
  }
}
