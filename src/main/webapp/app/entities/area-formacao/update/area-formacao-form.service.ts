import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAreaFormacao, NewAreaFormacao } from '../area-formacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAreaFormacao for edit and NewAreaFormacaoFormGroupInput for create.
 */
type AreaFormacaoFormGroupInput = IAreaFormacao | PartialWithRequiredKeyOf<NewAreaFormacao>;

type AreaFormacaoFormDefaults = Pick<NewAreaFormacao, 'id'>;

type AreaFormacaoFormGroupContent = {
  id: FormControl<IAreaFormacao['id'] | NewAreaFormacao['id']>;
  imagem: FormControl<IAreaFormacao['imagem']>;
  imagemContentType: FormControl<IAreaFormacao['imagemContentType']>;
  codigo: FormControl<IAreaFormacao['codigo']>;
  nome: FormControl<IAreaFormacao['nome']>;
  descricao: FormControl<IAreaFormacao['descricao']>;
  nivelEnsino: FormControl<IAreaFormacao['nivelEnsino']>;
};

export type AreaFormacaoFormGroup = FormGroup<AreaFormacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AreaFormacaoFormService {
  createAreaFormacaoFormGroup(areaFormacao: AreaFormacaoFormGroupInput = { id: null }): AreaFormacaoFormGroup {
    const areaFormacaoRawValue = {
      ...this.getFormDefaults(),
      ...areaFormacao,
    };
    return new FormGroup<AreaFormacaoFormGroupContent>({
      id: new FormControl(
        { value: areaFormacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      imagem: new FormControl(areaFormacaoRawValue.imagem),
      imagemContentType: new FormControl(areaFormacaoRawValue.imagemContentType),
      codigo: new FormControl(areaFormacaoRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(areaFormacaoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(areaFormacaoRawValue.descricao),
      nivelEnsino: new FormControl(areaFormacaoRawValue.nivelEnsino, {
        validators: [Validators.required],
      }),
    });
  }

  getAreaFormacao(form: AreaFormacaoFormGroup): IAreaFormacao | NewAreaFormacao {
    return form.getRawValue() as IAreaFormacao | NewAreaFormacao;
  }

  resetForm(form: AreaFormacaoFormGroup, areaFormacao: AreaFormacaoFormGroupInput): void {
    const areaFormacaoRawValue = { ...this.getFormDefaults(), ...areaFormacao };
    form.reset(
      {
        ...areaFormacaoRawValue,
        id: { value: areaFormacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AreaFormacaoFormDefaults {
    return {
      id: null,
    };
  }
}
