import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEstadoDisciplinaCurricular, NewEstadoDisciplinaCurricular } from '../estado-disciplina-curricular.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEstadoDisciplinaCurricular for edit and NewEstadoDisciplinaCurricularFormGroupInput for create.
 */
type EstadoDisciplinaCurricularFormGroupInput = IEstadoDisciplinaCurricular | PartialWithRequiredKeyOf<NewEstadoDisciplinaCurricular>;

type EstadoDisciplinaCurricularFormDefaults = Pick<NewEstadoDisciplinaCurricular, 'id' | 'disciplinasCurriculars'>;

type EstadoDisciplinaCurricularFormGroupContent = {
  id: FormControl<IEstadoDisciplinaCurricular['id'] | NewEstadoDisciplinaCurricular['id']>;
  uniqueSituacaoDisciplina: FormControl<IEstadoDisciplinaCurricular['uniqueSituacaoDisciplina']>;
  classificacao: FormControl<IEstadoDisciplinaCurricular['classificacao']>;
  codigo: FormControl<IEstadoDisciplinaCurricular['codigo']>;
  descricao: FormControl<IEstadoDisciplinaCurricular['descricao']>;
  cor: FormControl<IEstadoDisciplinaCurricular['cor']>;
  valor: FormControl<IEstadoDisciplinaCurricular['valor']>;
  disciplinasCurriculars: FormControl<IEstadoDisciplinaCurricular['disciplinasCurriculars']>;
  referencia: FormControl<IEstadoDisciplinaCurricular['referencia']>;
};

export type EstadoDisciplinaCurricularFormGroup = FormGroup<EstadoDisciplinaCurricularFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EstadoDisciplinaCurricularFormService {
  createEstadoDisciplinaCurricularFormGroup(
    estadoDisciplinaCurricular: EstadoDisciplinaCurricularFormGroupInput = { id: null }
  ): EstadoDisciplinaCurricularFormGroup {
    const estadoDisciplinaCurricularRawValue = {
      ...this.getFormDefaults(),
      ...estadoDisciplinaCurricular,
    };
    return new FormGroup<EstadoDisciplinaCurricularFormGroupContent>({
      id: new FormControl(
        { value: estadoDisciplinaCurricularRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      uniqueSituacaoDisciplina: new FormControl(estadoDisciplinaCurricularRawValue.uniqueSituacaoDisciplina),
      classificacao: new FormControl(estadoDisciplinaCurricularRawValue.classificacao, {
        validators: [Validators.required],
      }),
      codigo: new FormControl(estadoDisciplinaCurricularRawValue.codigo, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(estadoDisciplinaCurricularRawValue.descricao, {
        validators: [Validators.required],
      }),
      cor: new FormControl(estadoDisciplinaCurricularRawValue.cor, {
        validators: [Validators.required],
      }),
      valor: new FormControl(estadoDisciplinaCurricularRawValue.valor, {
        validators: [Validators.required, Validators.min(0)],
      }),
      disciplinasCurriculars: new FormControl(estadoDisciplinaCurricularRawValue.disciplinasCurriculars ?? []),
      referencia: new FormControl(estadoDisciplinaCurricularRawValue.referencia),
    });
  }

  getEstadoDisciplinaCurricular(form: EstadoDisciplinaCurricularFormGroup): IEstadoDisciplinaCurricular | NewEstadoDisciplinaCurricular {
    return form.getRawValue() as IEstadoDisciplinaCurricular | NewEstadoDisciplinaCurricular;
  }

  resetForm(form: EstadoDisciplinaCurricularFormGroup, estadoDisciplinaCurricular: EstadoDisciplinaCurricularFormGroupInput): void {
    const estadoDisciplinaCurricularRawValue = { ...this.getFormDefaults(), ...estadoDisciplinaCurricular };
    form.reset(
      {
        ...estadoDisciplinaCurricularRawValue,
        id: { value: estadoDisciplinaCurricularRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EstadoDisciplinaCurricularFormDefaults {
    return {
      id: null,
      disciplinasCurriculars: [],
    };
  }
}
