import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICampoActuacaoDissertacao, NewCampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICampoActuacaoDissertacao for edit and NewCampoActuacaoDissertacaoFormGroupInput for create.
 */
type CampoActuacaoDissertacaoFormGroupInput = ICampoActuacaoDissertacao | PartialWithRequiredKeyOf<NewCampoActuacaoDissertacao>;

type CampoActuacaoDissertacaoFormDefaults = Pick<NewCampoActuacaoDissertacao, 'id' | 'isActivo' | 'cursos'>;

type CampoActuacaoDissertacaoFormGroupContent = {
  id: FormControl<ICampoActuacaoDissertacao['id'] | NewCampoActuacaoDissertacao['id']>;
  nome: FormControl<ICampoActuacaoDissertacao['nome']>;
  descricao: FormControl<ICampoActuacaoDissertacao['descricao']>;
  isActivo: FormControl<ICampoActuacaoDissertacao['isActivo']>;
  cursos: FormControl<ICampoActuacaoDissertacao['cursos']>;
};

export type CampoActuacaoDissertacaoFormGroup = FormGroup<CampoActuacaoDissertacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CampoActuacaoDissertacaoFormService {
  createCampoActuacaoDissertacaoFormGroup(
    campoActuacaoDissertacao: CampoActuacaoDissertacaoFormGroupInput = { id: null }
  ): CampoActuacaoDissertacaoFormGroup {
    const campoActuacaoDissertacaoRawValue = {
      ...this.getFormDefaults(),
      ...campoActuacaoDissertacao,
    };
    return new FormGroup<CampoActuacaoDissertacaoFormGroupContent>({
      id: new FormControl(
        { value: campoActuacaoDissertacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nome: new FormControl(campoActuacaoDissertacaoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(campoActuacaoDissertacaoRawValue.descricao),
      isActivo: new FormControl(campoActuacaoDissertacaoRawValue.isActivo),
      cursos: new FormControl(campoActuacaoDissertacaoRawValue.cursos ?? []),
    });
  }

  getCampoActuacaoDissertacao(form: CampoActuacaoDissertacaoFormGroup): ICampoActuacaoDissertacao | NewCampoActuacaoDissertacao {
    return form.getRawValue() as ICampoActuacaoDissertacao | NewCampoActuacaoDissertacao;
  }

  resetForm(form: CampoActuacaoDissertacaoFormGroup, campoActuacaoDissertacao: CampoActuacaoDissertacaoFormGroupInput): void {
    const campoActuacaoDissertacaoRawValue = { ...this.getFormDefaults(), ...campoActuacaoDissertacao };
    form.reset(
      {
        ...campoActuacaoDissertacaoRawValue,
        id: { value: campoActuacaoDissertacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CampoActuacaoDissertacaoFormDefaults {
    return {
      id: null,
      isActivo: false,
      cursos: [],
    };
  }
}
