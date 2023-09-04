import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISerieDocumento, NewSerieDocumento } from '../serie-documento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISerieDocumento for edit and NewSerieDocumentoFormGroupInput for create.
 */
type SerieDocumentoFormGroupInput = ISerieDocumento | PartialWithRequiredKeyOf<NewSerieDocumento>;

type SerieDocumentoFormDefaults = Pick<NewSerieDocumento, 'id' | 'isAtivo' | 'isPadrao'>;

type SerieDocumentoFormGroupContent = {
  id: FormControl<ISerieDocumento['id'] | NewSerieDocumento['id']>;
  anoFiscal: FormControl<ISerieDocumento['anoFiscal']>;
  versao: FormControl<ISerieDocumento['versao']>;
  serie: FormControl<ISerieDocumento['serie']>;
  isAtivo: FormControl<ISerieDocumento['isAtivo']>;
  isPadrao: FormControl<ISerieDocumento['isPadrao']>;
  tipoDocumento: FormControl<ISerieDocumento['tipoDocumento']>;
};

export type SerieDocumentoFormGroup = FormGroup<SerieDocumentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SerieDocumentoFormService {
  createSerieDocumentoFormGroup(serieDocumento: SerieDocumentoFormGroupInput = { id: null }): SerieDocumentoFormGroup {
    const serieDocumentoRawValue = {
      ...this.getFormDefaults(),
      ...serieDocumento,
    };
    return new FormGroup<SerieDocumentoFormGroupContent>({
      id: new FormControl(
        { value: serieDocumentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      anoFiscal: new FormControl(serieDocumentoRawValue.anoFiscal),
      versao: new FormControl(serieDocumentoRawValue.versao, {
        validators: [Validators.required, Validators.min(0)],
      }),
      serie: new FormControl(serieDocumentoRawValue.serie, {
        validators: [Validators.required],
      }),
      isAtivo: new FormControl(serieDocumentoRawValue.isAtivo),
      isPadrao: new FormControl(serieDocumentoRawValue.isPadrao),
      tipoDocumento: new FormControl(serieDocumentoRawValue.tipoDocumento, {
        validators: [Validators.required],
      }),
    });
  }

  getSerieDocumento(form: SerieDocumentoFormGroup): ISerieDocumento | NewSerieDocumento {
    return form.getRawValue() as ISerieDocumento | NewSerieDocumento;
  }

  resetForm(form: SerieDocumentoFormGroup, serieDocumento: SerieDocumentoFormGroupInput): void {
    const serieDocumentoRawValue = { ...this.getFormDefaults(), ...serieDocumento };
    form.reset(
      {
        ...serieDocumentoRawValue,
        id: { value: serieDocumentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SerieDocumentoFormDefaults {
    return {
      id: null,
      isAtivo: false,
      isPadrao: false,
    };
  }
}
