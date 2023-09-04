import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFormacaoDocente, NewFormacaoDocente } from '../formacao-docente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFormacaoDocente for edit and NewFormacaoDocenteFormGroupInput for create.
 */
type FormacaoDocenteFormGroupInput = IFormacaoDocente | PartialWithRequiredKeyOf<NewFormacaoDocente>;

type FormacaoDocenteFormDefaults = Pick<NewFormacaoDocente, 'id'>;

type FormacaoDocenteFormGroupContent = {
  id: FormControl<IFormacaoDocente['id'] | NewFormacaoDocente['id']>;
  instituicaoEnsino: FormControl<IFormacaoDocente['instituicaoEnsino']>;
  areaFormacao: FormControl<IFormacaoDocente['areaFormacao']>;
  curso: FormControl<IFormacaoDocente['curso']>;
  especialidade: FormControl<IFormacaoDocente['especialidade']>;
  grau: FormControl<IFormacaoDocente['grau']>;
  inicio: FormControl<IFormacaoDocente['inicio']>;
  fim: FormControl<IFormacaoDocente['fim']>;
  anexo: FormControl<IFormacaoDocente['anexo']>;
  anexoContentType: FormControl<IFormacaoDocente['anexoContentType']>;
  grauAcademico: FormControl<IFormacaoDocente['grauAcademico']>;
  docente: FormControl<IFormacaoDocente['docente']>;
};

export type FormacaoDocenteFormGroup = FormGroup<FormacaoDocenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FormacaoDocenteFormService {
  createFormacaoDocenteFormGroup(formacaoDocente: FormacaoDocenteFormGroupInput = { id: null }): FormacaoDocenteFormGroup {
    const formacaoDocenteRawValue = {
      ...this.getFormDefaults(),
      ...formacaoDocente,
    };
    return new FormGroup<FormacaoDocenteFormGroupContent>({
      id: new FormControl(
        { value: formacaoDocenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      instituicaoEnsino: new FormControl(formacaoDocenteRawValue.instituicaoEnsino, {
        validators: [Validators.required],
      }),
      areaFormacao: new FormControl(formacaoDocenteRawValue.areaFormacao, {
        validators: [Validators.required],
      }),
      curso: new FormControl(formacaoDocenteRawValue.curso),
      especialidade: new FormControl(formacaoDocenteRawValue.especialidade),
      grau: new FormControl(formacaoDocenteRawValue.grau, {
        validators: [Validators.required],
      }),
      inicio: new FormControl(formacaoDocenteRawValue.inicio, {
        validators: [Validators.required],
      }),
      fim: new FormControl(formacaoDocenteRawValue.fim),
      anexo: new FormControl(formacaoDocenteRawValue.anexo),
      anexoContentType: new FormControl(formacaoDocenteRawValue.anexoContentType),
      grauAcademico: new FormControl(formacaoDocenteRawValue.grauAcademico, {
        validators: [Validators.required],
      }),
      docente: new FormControl(formacaoDocenteRawValue.docente, {
        validators: [Validators.required],
      }),
    });
  }

  getFormacaoDocente(form: FormacaoDocenteFormGroup): IFormacaoDocente | NewFormacaoDocente {
    return form.getRawValue() as IFormacaoDocente | NewFormacaoDocente;
  }

  resetForm(form: FormacaoDocenteFormGroup, formacaoDocente: FormacaoDocenteFormGroupInput): void {
    const formacaoDocenteRawValue = { ...this.getFormDefaults(), ...formacaoDocente };
    form.reset(
      {
        ...formacaoDocenteRawValue,
        id: { value: formacaoDocenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FormacaoDocenteFormDefaults {
    return {
      id: null,
    };
  }
}
