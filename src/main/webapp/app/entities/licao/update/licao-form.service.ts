import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILicao, NewLicao } from '../licao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILicao for edit and NewLicaoFormGroupInput for create.
 */
type LicaoFormGroupInput = ILicao | PartialWithRequiredKeyOf<NewLicao>;

type LicaoFormDefaults = Pick<NewLicao, 'id'>;

type LicaoFormGroupContent = {
  id: FormControl<ILicao['id'] | NewLicao['id']>;
  chaveComposta: FormControl<ILicao['chaveComposta']>;
  numero: FormControl<ILicao['numero']>;
  estado: FormControl<ILicao['estado']>;
  descricao: FormControl<ILicao['descricao']>;
  utilizador: FormControl<ILicao['utilizador']>;
  planoAula: FormControl<ILicao['planoAula']>;
  horario: FormControl<ILicao['horario']>;
};

export type LicaoFormGroup = FormGroup<LicaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LicaoFormService {
  createLicaoFormGroup(licao: LicaoFormGroupInput = { id: null }): LicaoFormGroup {
    const licaoRawValue = {
      ...this.getFormDefaults(),
      ...licao,
    };
    return new FormGroup<LicaoFormGroupContent>({
      id: new FormControl(
        { value: licaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      chaveComposta: new FormControl(licaoRawValue.chaveComposta),
      numero: new FormControl(licaoRawValue.numero, {
        validators: [Validators.required, Validators.min(1)],
      }),
      estado: new FormControl(licaoRawValue.estado, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(licaoRawValue.descricao),
      utilizador: new FormControl(licaoRawValue.utilizador),
      planoAula: new FormControl(licaoRawValue.planoAula, {
        validators: [Validators.required],
      }),
      horario: new FormControl(licaoRawValue.horario, {
        validators: [Validators.required],
      }),
    });
  }

  getLicao(form: LicaoFormGroup): ILicao | NewLicao {
    return form.getRawValue() as ILicao | NewLicao;
  }

  resetForm(form: LicaoFormGroup, licao: LicaoFormGroupInput): void {
    const licaoRawValue = { ...this.getFormDefaults(), ...licao };
    form.reset(
      {
        ...licaoRawValue,
        id: { value: licaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LicaoFormDefaults {
    return {
      id: null,
    };
  }
}
