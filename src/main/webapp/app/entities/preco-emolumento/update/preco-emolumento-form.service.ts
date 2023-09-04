import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrecoEmolumento, NewPrecoEmolumento } from '../preco-emolumento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrecoEmolumento for edit and NewPrecoEmolumentoFormGroupInput for create.
 */
type PrecoEmolumentoFormGroupInput = IPrecoEmolumento | PartialWithRequiredKeyOf<NewPrecoEmolumento>;

type PrecoEmolumentoFormDefaults = Pick<
  NewPrecoEmolumento,
  'id' | 'isEspecificoCurso' | 'isEspecificoAreaFormacao' | 'isEspecificoClasse' | 'isEspecificoTurno'
>;

type PrecoEmolumentoFormGroupContent = {
  id: FormControl<IPrecoEmolumento['id'] | NewPrecoEmolumento['id']>;
  preco: FormControl<IPrecoEmolumento['preco']>;
  isEspecificoCurso: FormControl<IPrecoEmolumento['isEspecificoCurso']>;
  isEspecificoAreaFormacao: FormControl<IPrecoEmolumento['isEspecificoAreaFormacao']>;
  isEspecificoClasse: FormControl<IPrecoEmolumento['isEspecificoClasse']>;
  isEspecificoTurno: FormControl<IPrecoEmolumento['isEspecificoTurno']>;
  utilizador: FormControl<IPrecoEmolumento['utilizador']>;
  emolumento: FormControl<IPrecoEmolumento['emolumento']>;
  areaFormacao: FormControl<IPrecoEmolumento['areaFormacao']>;
  curso: FormControl<IPrecoEmolumento['curso']>;
  classe: FormControl<IPrecoEmolumento['classe']>;
  turno: FormControl<IPrecoEmolumento['turno']>;
  planoMulta: FormControl<IPrecoEmolumento['planoMulta']>;
};

export type PrecoEmolumentoFormGroup = FormGroup<PrecoEmolumentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrecoEmolumentoFormService {
  createPrecoEmolumentoFormGroup(precoEmolumento: PrecoEmolumentoFormGroupInput = { id: null }): PrecoEmolumentoFormGroup {
    const precoEmolumentoRawValue = {
      ...this.getFormDefaults(),
      ...precoEmolumento,
    };
    return new FormGroup<PrecoEmolumentoFormGroupContent>({
      id: new FormControl(
        { value: precoEmolumentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      preco: new FormControl(precoEmolumentoRawValue.preco, {
        validators: [Validators.required, Validators.min(0)],
      }),
      isEspecificoCurso: new FormControl(precoEmolumentoRawValue.isEspecificoCurso),
      isEspecificoAreaFormacao: new FormControl(precoEmolumentoRawValue.isEspecificoAreaFormacao),
      isEspecificoClasse: new FormControl(precoEmolumentoRawValue.isEspecificoClasse),
      isEspecificoTurno: new FormControl(precoEmolumentoRawValue.isEspecificoTurno),
      utilizador: new FormControl(precoEmolumentoRawValue.utilizador),
      emolumento: new FormControl(precoEmolumentoRawValue.emolumento, {
        validators: [Validators.required],
      }),
      areaFormacao: new FormControl(precoEmolumentoRawValue.areaFormacao),
      curso: new FormControl(precoEmolumentoRawValue.curso),
      classe: new FormControl(precoEmolumentoRawValue.classe),
      turno: new FormControl(precoEmolumentoRawValue.turno),
      planoMulta: new FormControl(precoEmolumentoRawValue.planoMulta),
    });
  }

  getPrecoEmolumento(form: PrecoEmolumentoFormGroup): IPrecoEmolumento | NewPrecoEmolumento {
    return form.getRawValue() as IPrecoEmolumento | NewPrecoEmolumento;
  }

  resetForm(form: PrecoEmolumentoFormGroup, precoEmolumento: PrecoEmolumentoFormGroupInput): void {
    const precoEmolumentoRawValue = { ...this.getFormDefaults(), ...precoEmolumento };
    form.reset(
      {
        ...precoEmolumentoRawValue,
        id: { value: precoEmolumentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PrecoEmolumentoFormDefaults {
    return {
      id: null,
      isEspecificoCurso: false,
      isEspecificoAreaFormacao: false,
      isEspecificoClasse: false,
      isEspecificoTurno: false,
    };
  }
}
