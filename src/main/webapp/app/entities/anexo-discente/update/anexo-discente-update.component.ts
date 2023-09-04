import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AnexoDiscenteFormService, AnexoDiscenteFormGroup } from './anexo-discente-form.service';
import { IAnexoDiscente } from '../anexo-discente.model';
import { AnexoDiscenteService } from '../service/anexo-discente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { CategoriaAnexo } from 'app/entities/enumerations/categoria-anexo.model';

@Component({
  selector: 'app-anexo-discente-update',
  templateUrl: './anexo-discente-update.component.html',
})
export class AnexoDiscenteUpdateComponent implements OnInit {
  isSaving = false;
  anexoDiscente: IAnexoDiscente | null = null;
  categoriaAnexoValues = Object.keys(CategoriaAnexo);

  discentesSharedCollection: IDiscente[] = [];

  editForm: AnexoDiscenteFormGroup = this.anexoDiscenteFormService.createAnexoDiscenteFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected anexoDiscenteService: AnexoDiscenteService,
    protected anexoDiscenteFormService: AnexoDiscenteFormService,
    protected discenteService: DiscenteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoDiscente }) => {
      this.anexoDiscente = anexoDiscente;
      if (anexoDiscente) {
        this.updateForm(anexoDiscente);
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
    const anexoDiscente = this.anexoDiscenteFormService.getAnexoDiscente(this.editForm);
    if (anexoDiscente.id !== null) {
      this.subscribeToSaveResponse(this.anexoDiscenteService.update(anexoDiscente));
    } else {
      this.subscribeToSaveResponse(this.anexoDiscenteService.create(anexoDiscente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnexoDiscente>>): void {
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

  protected updateForm(anexoDiscente: IAnexoDiscente): void {
    this.anexoDiscente = anexoDiscente;
    this.anexoDiscenteFormService.resetForm(this.editForm, anexoDiscente);

    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      anexoDiscente.discente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(discentes, this.anexoDiscente?.discente)
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));
  }
}
