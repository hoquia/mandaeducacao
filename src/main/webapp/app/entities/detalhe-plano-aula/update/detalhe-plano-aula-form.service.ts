import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDetalhePlanoAula, NewDetalhePlanoAula } from '../detalhe-plano-aula.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDetalhePlanoAula for edit and NewDetalhePlanoAulaFormGroupInput for create.
 */
type DetalhePlanoAulaFormGroupInput = IDetalhePlanoAula | PartialWithRequiredKeyOf<NewDetalhePlanoAula>;

type DetalhePlanoAulaFormDefaults = Pick<NewDetalhePlanoAula, 'id'>;

type DetalhePlanoAulaFormGroupContent = {
  id: FormControl<IDetalhePlanoAula['id'] | NewDetalhePlanoAula['id']>;
  estrategiaAula: FormControl<IDetalhePlanoAula['estrategiaAula']>;
  tempoActividade: FormControl<IDetalhePlanoAula['tempoActividade']>;
  recursosEnsino: FormControl<IDetalhePlanoAula['recursosEnsino']>;
  tituloActividade: FormControl<IDetalhePlanoAula['tituloActividade']>;
  actividadesDocente: FormControl<IDetalhePlanoAula['actividadesDocente']>;
  actividadesDiscentes: FormControl<IDetalhePlanoAula['actividadesDiscentes']>;
  avaliacao: FormControl<IDetalhePlanoAula['avaliacao']>;
  bibliografia: FormControl<IDetalhePlanoAula['bibliografia']>;
  observacao: FormControl<IDetalhePlanoAula['observacao']>;
  pdf: FormControl<IDetalhePlanoAula['pdf']>;
  pdfContentType: FormControl<IDetalhePlanoAula['pdfContentType']>;
  video: FormControl<IDetalhePlanoAula['video']>;
  videoContentType: FormControl<IDetalhePlanoAula['videoContentType']>;
  audio: FormControl<IDetalhePlanoAula['audio']>;
  audioContentType: FormControl<IDetalhePlanoAula['audioContentType']>;
  planoAula: FormControl<IDetalhePlanoAula['planoAula']>;
};

export type DetalhePlanoAulaFormGroup = FormGroup<DetalhePlanoAulaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetalhePlanoAulaFormService {
  createDetalhePlanoAulaFormGroup(detalhePlanoAula: DetalhePlanoAulaFormGroupInput = { id: null }): DetalhePlanoAulaFormGroup {
    const detalhePlanoAulaRawValue = {
      ...this.getFormDefaults(),
      ...detalhePlanoAula,
    };
    return new FormGroup<DetalhePlanoAulaFormGroupContent>({
      id: new FormControl(
        { value: detalhePlanoAulaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      estrategiaAula: new FormControl(detalhePlanoAulaRawValue.estrategiaAula),
      tempoActividade: new FormControl(detalhePlanoAulaRawValue.tempoActividade, {
        validators: [Validators.required, Validators.min(0)],
      }),
      recursosEnsino: new FormControl(detalhePlanoAulaRawValue.recursosEnsino),
      tituloActividade: new FormControl(detalhePlanoAulaRawValue.tituloActividade, {
        validators: [Validators.required],
      }),
      actividadesDocente: new FormControl(detalhePlanoAulaRawValue.actividadesDocente, {
        validators: [Validators.required],
      }),
      actividadesDiscentes: new FormControl(detalhePlanoAulaRawValue.actividadesDiscentes, {
        validators: [Validators.required],
      }),
      avaliacao: new FormControl(detalhePlanoAulaRawValue.avaliacao, {
        validators: [Validators.required],
      }),
      bibliografia: new FormControl(detalhePlanoAulaRawValue.bibliografia, {
        validators: [Validators.required],
      }),
      observacao: new FormControl(detalhePlanoAulaRawValue.observacao),
      pdf: new FormControl(detalhePlanoAulaRawValue.pdf),
      pdfContentType: new FormControl(detalhePlanoAulaRawValue.pdfContentType),
      video: new FormControl(detalhePlanoAulaRawValue.video),
      videoContentType: new FormControl(detalhePlanoAulaRawValue.videoContentType),
      audio: new FormControl(detalhePlanoAulaRawValue.audio),
      audioContentType: new FormControl(detalhePlanoAulaRawValue.audioContentType),
      planoAula: new FormControl(detalhePlanoAulaRawValue.planoAula, {
        validators: [Validators.required],
      }),
    });
  }

  getDetalhePlanoAula(form: DetalhePlanoAulaFormGroup): IDetalhePlanoAula | NewDetalhePlanoAula {
    return form.getRawValue() as IDetalhePlanoAula | NewDetalhePlanoAula;
  }

  resetForm(form: DetalhePlanoAulaFormGroup, detalhePlanoAula: DetalhePlanoAulaFormGroupInput): void {
    const detalhePlanoAulaRawValue = { ...this.getFormDefaults(), ...detalhePlanoAula };
    form.reset(
      {
        ...detalhePlanoAulaRawValue,
        id: { value: detalhePlanoAulaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DetalhePlanoAulaFormDefaults {
    return {
      id: null,
    };
  }
}
