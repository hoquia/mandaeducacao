import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INivelEnsino, NewNivelEnsino } from '../nivel-ensino.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INivelEnsino for edit and NewNivelEnsinoFormGroupInput for create.
 */
type NivelEnsinoFormGroupInput = INivelEnsino | PartialWithRequiredKeyOf<NewNivelEnsino>;

type NivelEnsinoFormDefaults = Pick<NewNivelEnsino, 'id' | 'anoLectivos' | 'classes'>;

type NivelEnsinoFormGroupContent = {
  id: FormControl<INivelEnsino['id'] | NewNivelEnsino['id']>;
  codigo: FormControl<INivelEnsino['codigo']>;
  nome: FormControl<INivelEnsino['nome']>;
  descricao: FormControl<INivelEnsino['descricao']>;
  idadeMinima: FormControl<INivelEnsino['idadeMinima']>;
  idadeMaxima: FormControl<INivelEnsino['idadeMaxima']>;
  duracao: FormControl<INivelEnsino['duracao']>;
  unidadeDuracao: FormControl<INivelEnsino['unidadeDuracao']>;
  classeInicial: FormControl<INivelEnsino['classeInicial']>;
  classeFinal: FormControl<INivelEnsino['classeFinal']>;
  classeExame: FormControl<INivelEnsino['classeExame']>;
  totalDisciplina: FormControl<INivelEnsino['totalDisciplina']>;
  responsavelTurno: FormControl<INivelEnsino['responsavelTurno']>;
  responsavelAreaFormacao: FormControl<INivelEnsino['responsavelAreaFormacao']>;
  responsavelCurso: FormControl<INivelEnsino['responsavelCurso']>;
  responsavelDisciplina: FormControl<INivelEnsino['responsavelDisciplina']>;
  responsavelTurma: FormControl<INivelEnsino['responsavelTurma']>;
  responsavelGeral: FormControl<INivelEnsino['responsavelGeral']>;
  responsavelPedagogico: FormControl<INivelEnsino['responsavelPedagogico']>;
  responsavelAdministrativo: FormControl<INivelEnsino['responsavelAdministrativo']>;
  responsavelSecretariaGeral: FormControl<INivelEnsino['responsavelSecretariaGeral']>;
  responsavelSecretariaPedagogico: FormControl<INivelEnsino['responsavelSecretariaPedagogico']>;
  descricaoDocente: FormControl<INivelEnsino['descricaoDocente']>;
  descricaoDiscente: FormControl<INivelEnsino['descricaoDiscente']>;
  referencia: FormControl<INivelEnsino['referencia']>;
  anoLectivos: FormControl<INivelEnsino['anoLectivos']>;
  classes: FormControl<INivelEnsino['classes']>;
};

export type NivelEnsinoFormGroup = FormGroup<NivelEnsinoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NivelEnsinoFormService {
  createNivelEnsinoFormGroup(nivelEnsino: NivelEnsinoFormGroupInput = { id: null }): NivelEnsinoFormGroup {
    const nivelEnsinoRawValue = {
      ...this.getFormDefaults(),
      ...nivelEnsino,
    };
    return new FormGroup<NivelEnsinoFormGroupContent>({
      id: new FormControl(
        { value: nivelEnsinoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      codigo: new FormControl(nivelEnsinoRawValue.codigo, {
        validators: [Validators.required],
      }),
      nome: new FormControl(nivelEnsinoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(nivelEnsinoRawValue.descricao),
      idadeMinima: new FormControl(nivelEnsinoRawValue.idadeMinima, {
        validators: [Validators.min(0)],
      }),
      idadeMaxima: new FormControl(nivelEnsinoRawValue.idadeMaxima, {
        validators: [Validators.min(0)],
      }),
      duracao: new FormControl(nivelEnsinoRawValue.duracao, {
        validators: [Validators.min(0)],
      }),
      unidadeDuracao: new FormControl(nivelEnsinoRawValue.unidadeDuracao, {
        validators: [Validators.required],
      }),
      classeInicial: new FormControl(nivelEnsinoRawValue.classeInicial, {
        validators: [Validators.min(0)],
      }),
      classeFinal: new FormControl(nivelEnsinoRawValue.classeFinal, {
        validators: [Validators.min(0)],
      }),
      classeExame: new FormControl(nivelEnsinoRawValue.classeExame),
      totalDisciplina: new FormControl(nivelEnsinoRawValue.totalDisciplina),
      responsavelTurno: new FormControl(nivelEnsinoRawValue.responsavelTurno),
      responsavelAreaFormacao: new FormControl(nivelEnsinoRawValue.responsavelAreaFormacao),
      responsavelCurso: new FormControl(nivelEnsinoRawValue.responsavelCurso),
      responsavelDisciplina: new FormControl(nivelEnsinoRawValue.responsavelDisciplina),
      responsavelTurma: new FormControl(nivelEnsinoRawValue.responsavelTurma),
      responsavelGeral: new FormControl(nivelEnsinoRawValue.responsavelGeral),
      responsavelPedagogico: new FormControl(nivelEnsinoRawValue.responsavelPedagogico),
      responsavelAdministrativo: new FormControl(nivelEnsinoRawValue.responsavelAdministrativo),
      responsavelSecretariaGeral: new FormControl(nivelEnsinoRawValue.responsavelSecretariaGeral),
      responsavelSecretariaPedagogico: new FormControl(nivelEnsinoRawValue.responsavelSecretariaPedagogico),
      descricaoDocente: new FormControl(nivelEnsinoRawValue.descricaoDocente),
      descricaoDiscente: new FormControl(nivelEnsinoRawValue.descricaoDiscente),
      referencia: new FormControl(nivelEnsinoRawValue.referencia),
      anoLectivos: new FormControl(nivelEnsinoRawValue.anoLectivos ?? []),
      classes: new FormControl(nivelEnsinoRawValue.classes ?? []),
    });
  }

  getNivelEnsino(form: NivelEnsinoFormGroup): INivelEnsino | NewNivelEnsino {
    return form.getRawValue() as INivelEnsino | NewNivelEnsino;
  }

  resetForm(form: NivelEnsinoFormGroup, nivelEnsino: NivelEnsinoFormGroupInput): void {
    const nivelEnsinoRawValue = { ...this.getFormDefaults(), ...nivelEnsino };
    form.reset(
      {
        ...nivelEnsinoRawValue,
        id: { value: nivelEnsinoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NivelEnsinoFormDefaults {
    return {
      id: null,
      anoLectivos: [],
      classes: [],
    };
  }
}
