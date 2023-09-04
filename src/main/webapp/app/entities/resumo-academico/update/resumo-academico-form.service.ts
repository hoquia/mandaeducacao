import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IResumoAcademico, NewResumoAcademico } from '../resumo-academico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResumoAcademico for edit and NewResumoAcademicoFormGroupInput for create.
 */
type ResumoAcademicoFormGroupInput = IResumoAcademico | PartialWithRequiredKeyOf<NewResumoAcademico>;

type ResumoAcademicoFormDefaults = Pick<NewResumoAcademico, 'id'>;

type ResumoAcademicoFormGroupContent = {
  id: FormControl<IResumoAcademico['id'] | NewResumoAcademico['id']>;
  temaProjecto: FormControl<IResumoAcademico['temaProjecto']>;
  notaProjecto: FormControl<IResumoAcademico['notaProjecto']>;
  observacao: FormControl<IResumoAcademico['observacao']>;
  localEstagio: FormControl<IResumoAcademico['localEstagio']>;
  notaEstagio: FormControl<IResumoAcademico['notaEstagio']>;
  mediaFinalDisciplina: FormControl<IResumoAcademico['mediaFinalDisciplina']>;
  classificacaoFinal: FormControl<IResumoAcademico['classificacaoFinal']>;
  numeroGrupo: FormControl<IResumoAcademico['numeroGrupo']>;
  mesaDefesa: FormControl<IResumoAcademico['mesaDefesa']>;
  livroRegistro: FormControl<IResumoAcademico['livroRegistro']>;
  numeroFolha: FormControl<IResumoAcademico['numeroFolha']>;
  chefeSecretariaPedagogica: FormControl<IResumoAcademico['chefeSecretariaPedagogica']>;
  subDirectorPedagogico: FormControl<IResumoAcademico['subDirectorPedagogico']>;
  directorGeral: FormControl<IResumoAcademico['directorGeral']>;
  tutorProjecto: FormControl<IResumoAcademico['tutorProjecto']>;
  juriMesa: FormControl<IResumoAcademico['juriMesa']>;
  empresaEstagio: FormControl<IResumoAcademico['empresaEstagio']>;
  assinaturaDigital: FormControl<IResumoAcademico['assinaturaDigital']>;
  assinaturaDigitalContentType: FormControl<IResumoAcademico['assinaturaDigitalContentType']>;
  hash: FormControl<IResumoAcademico['hash']>;
  utilizador: FormControl<IResumoAcademico['utilizador']>;
  ultimaTurmaMatriculada: FormControl<IResumoAcademico['ultimaTurmaMatriculada']>;
  discente: FormControl<IResumoAcademico['discente']>;
  situacao: FormControl<IResumoAcademico['situacao']>;
};

export type ResumoAcademicoFormGroup = FormGroup<ResumoAcademicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResumoAcademicoFormService {
  createResumoAcademicoFormGroup(resumoAcademico: ResumoAcademicoFormGroupInput = { id: null }): ResumoAcademicoFormGroup {
    const resumoAcademicoRawValue = {
      ...this.getFormDefaults(),
      ...resumoAcademico,
    };
    return new FormGroup<ResumoAcademicoFormGroupContent>({
      id: new FormControl(
        { value: resumoAcademicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      temaProjecto: new FormControl(resumoAcademicoRawValue.temaProjecto, {
        validators: [Validators.required],
      }),
      notaProjecto: new FormControl(resumoAcademicoRawValue.notaProjecto, {
        validators: [Validators.required, Validators.min(0), Validators.max(20)],
      }),
      observacao: new FormControl(resumoAcademicoRawValue.observacao),
      localEstagio: new FormControl(resumoAcademicoRawValue.localEstagio),
      notaEstagio: new FormControl(resumoAcademicoRawValue.notaEstagio, {
        validators: [Validators.required, Validators.min(0), Validators.max(20)],
      }),
      mediaFinalDisciplina: new FormControl(resumoAcademicoRawValue.mediaFinalDisciplina, {
        validators: [Validators.required, Validators.min(0), Validators.max(20)],
      }),
      classificacaoFinal: new FormControl(resumoAcademicoRawValue.classificacaoFinal, {
        validators: [Validators.required, Validators.min(0), Validators.max(20)],
      }),
      numeroGrupo: new FormControl(resumoAcademicoRawValue.numeroGrupo, {
        validators: [Validators.required],
      }),
      mesaDefesa: new FormControl(resumoAcademicoRawValue.mesaDefesa, {
        validators: [Validators.required],
      }),
      livroRegistro: new FormControl(resumoAcademicoRawValue.livroRegistro, {
        validators: [Validators.required],
      }),
      numeroFolha: new FormControl(resumoAcademicoRawValue.numeroFolha, {
        validators: [Validators.required],
      }),
      chefeSecretariaPedagogica: new FormControl(resumoAcademicoRawValue.chefeSecretariaPedagogica, {
        validators: [Validators.required],
      }),
      subDirectorPedagogico: new FormControl(resumoAcademicoRawValue.subDirectorPedagogico, {
        validators: [Validators.required],
      }),
      directorGeral: new FormControl(resumoAcademicoRawValue.directorGeral, {
        validators: [Validators.required],
      }),
      tutorProjecto: new FormControl(resumoAcademicoRawValue.tutorProjecto, {
        validators: [Validators.required],
      }),
      juriMesa: new FormControl(resumoAcademicoRawValue.juriMesa, {
        validators: [Validators.required],
      }),
      empresaEstagio: new FormControl(resumoAcademicoRawValue.empresaEstagio, {
        validators: [Validators.required],
      }),
      assinaturaDigital: new FormControl(resumoAcademicoRawValue.assinaturaDigital, {
        validators: [Validators.required],
      }),
      assinaturaDigitalContentType: new FormControl(resumoAcademicoRawValue.assinaturaDigitalContentType),
      hash: new FormControl(resumoAcademicoRawValue.hash, {
        validators: [Validators.required],
      }),
      utilizador: new FormControl(resumoAcademicoRawValue.utilizador),
      ultimaTurmaMatriculada: new FormControl(resumoAcademicoRawValue.ultimaTurmaMatriculada, {
        validators: [Validators.required],
      }),
      discente: new FormControl(resumoAcademicoRawValue.discente, {
        validators: [Validators.required],
      }),
      situacao: new FormControl(resumoAcademicoRawValue.situacao, {
        validators: [Validators.required],
      }),
    });
  }

  getResumoAcademico(form: ResumoAcademicoFormGroup): IResumoAcademico | NewResumoAcademico {
    return form.getRawValue() as IResumoAcademico | NewResumoAcademico;
  }

  resetForm(form: ResumoAcademicoFormGroup, resumoAcademico: ResumoAcademicoFormGroupInput): void {
    const resumoAcademicoRawValue = { ...this.getFormDefaults(), ...resumoAcademico };
    form.reset(
      {
        ...resumoAcademicoRawValue,
        id: { value: resumoAcademicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResumoAcademicoFormDefaults {
    return {
      id: null,
    };
  }
}
