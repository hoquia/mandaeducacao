import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICurso, NewCurso } from '../curso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICurso for edit and NewCursoFormGroupInput for create.
 */
type CursoFormGroupInput = ICurso | PartialWithRequiredKeyOf<NewCurso>;

type CursoFormDefaults = Pick<NewCurso, 'id' | 'camposActuacaos'>;

type CursoFormGroupContent = {
  id: FormControl<ICurso['id'] | NewCurso['id']>;
  imagem: FormControl<ICurso['imagem']>;
  imagemContentType: FormControl<ICurso['imagemContentType']>;
  codigo: FormControl<ICurso['codigo']>;
  nome: FormControl<ICurso['nome']>;
  descricao: FormControl<ICurso['descricao']>;
  areaFormacao: FormControl<ICurso['areaFormacao']>;
  camposActuacaos: FormControl<ICurso['camposActuacaos']>;
};

export type CursoFormGroup = FormGroup<CursoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CursoFormService {
  createCursoFormGroup(curso: CursoFormGroupInput = { id: null }): CursoFormGroup {
    const cursoRawValue = {
      ...this.getFormDefaults(),
      ...curso,
    };
    return new FormGroup<CursoFormGroupContent>({
      id: new FormControl(
        { value: cursoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      imagem: new FormControl(cursoRawValue.imagem),
      imagemContentType: new FormControl(cursoRawValue.imagemContentType),
      codigo: new FormControl(cursoRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(cursoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(cursoRawValue.descricao),
      areaFormacao: new FormControl(cursoRawValue.areaFormacao, {
        validators: [Validators.required],
      }),
      camposActuacaos: new FormControl(cursoRawValue.camposActuacaos ?? []),
    });
  }

  getCurso(form: CursoFormGroup): ICurso | NewCurso {
    return form.getRawValue() as ICurso | NewCurso;
  }

  resetForm(form: CursoFormGroup, curso: CursoFormGroupInput): void {
    const cursoRawValue = { ...this.getFormDefaults(), ...curso };
    form.reset(
      {
        ...cursoRawValue,
        id: { value: cursoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CursoFormDefaults {
    return {
      id: null,
      camposActuacaos: [],
    };
  }
}
