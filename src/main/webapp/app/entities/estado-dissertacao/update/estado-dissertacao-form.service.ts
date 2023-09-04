import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEstadoDissertacao, NewEstadoDissertacao } from '../estado-dissertacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEstadoDissertacao for edit and NewEstadoDissertacaoFormGroupInput for create.
 */
type EstadoDissertacaoFormGroupInput = IEstadoDissertacao | PartialWithRequiredKeyOf<NewEstadoDissertacao>;

type EstadoDissertacaoFormDefaults = Pick<NewEstadoDissertacao, 'id'>;

type EstadoDissertacaoFormGroupContent = {
  id: FormControl<IEstadoDissertacao['id'] | NewEstadoDissertacao['id']>;
  codigo: FormControl<IEstadoDissertacao['codigo']>;
  nome: FormControl<IEstadoDissertacao['nome']>;
  etapa: FormControl<IEstadoDissertacao['etapa']>;
  descricao: FormControl<IEstadoDissertacao['descricao']>;
};

export type EstadoDissertacaoFormGroup = FormGroup<EstadoDissertacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EstadoDissertacaoFormService {
  createEstadoDissertacaoFormGroup(estadoDissertacao: EstadoDissertacaoFormGroupInput = { id: null }): EstadoDissertacaoFormGroup {
    const estadoDissertacaoRawValue = {
      ...this.getFormDefaults(),
      ...estadoDissertacao,
    };
    return new FormGroup<EstadoDissertacaoFormGroupContent>({
      id: new FormControl(
        { value: estadoDissertacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codigo: new FormControl(estadoDissertacaoRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(estadoDissertacaoRawValue.nome, {
        validators: [Validators.required],
      }),
      etapa: new FormControl(estadoDissertacaoRawValue.etapa, {
        validators: [Validators.min(0)],
      }),
      descricao: new FormControl(estadoDissertacaoRawValue.descricao),
    });
  }

  getEstadoDissertacao(form: EstadoDissertacaoFormGroup): IEstadoDissertacao | NewEstadoDissertacao {
    return form.getRawValue() as IEstadoDissertacao | NewEstadoDissertacao;
  }

  resetForm(form: EstadoDissertacaoFormGroup, estadoDissertacao: EstadoDissertacaoFormGroupInput): void {
    const estadoDissertacaoRawValue = { ...this.getFormDefaults(), ...estadoDissertacao };
    form.reset(
      {
        ...estadoDissertacaoRawValue,
        id: { value: estadoDissertacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EstadoDissertacaoFormDefaults {
    return {
      id: null,
    };
  }
}
