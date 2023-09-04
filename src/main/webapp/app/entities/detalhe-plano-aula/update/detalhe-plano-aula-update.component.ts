import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DetalhePlanoAulaFormService, DetalhePlanoAulaFormGroup } from './detalhe-plano-aula-form.service';
import { IDetalhePlanoAula } from '../detalhe-plano-aula.model';
import { DetalhePlanoAulaService } from '../service/detalhe-plano-aula.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';
import { PlanoAulaService } from 'app/entities/plano-aula/service/plano-aula.service';

@Component({
  selector: 'app-detalhe-plano-aula-update',
  templateUrl: './detalhe-plano-aula-update.component.html',
})
export class DetalhePlanoAulaUpdateComponent implements OnInit {
  isSaving = false;
  detalhePlanoAula: IDetalhePlanoAula | null = null;

  planoAulasSharedCollection: IPlanoAula[] = [];

  editForm: DetalhePlanoAulaFormGroup = this.detalhePlanoAulaFormService.createDetalhePlanoAulaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected detalhePlanoAulaService: DetalhePlanoAulaService,
    protected detalhePlanoAulaFormService: DetalhePlanoAulaFormService,
    protected planoAulaService: PlanoAulaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlanoAula = (o1: IPlanoAula | null, o2: IPlanoAula | null): boolean => this.planoAulaService.comparePlanoAula(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalhePlanoAula }) => {
      this.detalhePlanoAula = detalhePlanoAula;
      if (detalhePlanoAula) {
        this.updateForm(detalhePlanoAula);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('longonkeloApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detalhePlanoAula = this.detalhePlanoAulaFormService.getDetalhePlanoAula(this.editForm);
    if (detalhePlanoAula.id !== null) {
      this.subscribeToSaveResponse(this.detalhePlanoAulaService.update(detalhePlanoAula));
    } else {
      this.subscribeToSaveResponse(this.detalhePlanoAulaService.create(detalhePlanoAula));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalhePlanoAula>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(detalhePlanoAula: IDetalhePlanoAula): void {
    this.detalhePlanoAula = detalhePlanoAula;
    this.detalhePlanoAulaFormService.resetForm(this.editForm, detalhePlanoAula);

    this.planoAulasSharedCollection = this.planoAulaService.addPlanoAulaToCollectionIfMissing<IPlanoAula>(
      this.planoAulasSharedCollection,
      detalhePlanoAula.planoAula
    );
  }

  protected loadRelationshipsOptions(): void {
    this.planoAulaService
      .query()
      .pipe(map((res: HttpResponse<IPlanoAula[]>) => res.body ?? []))
      .pipe(
        map((planoAulas: IPlanoAula[]) =>
          this.planoAulaService.addPlanoAulaToCollectionIfMissing<IPlanoAula>(planoAulas, this.detalhePlanoAula?.planoAula)
        )
      )
      .subscribe((planoAulas: IPlanoAula[]) => (this.planoAulasSharedCollection = planoAulas));
  }
}
