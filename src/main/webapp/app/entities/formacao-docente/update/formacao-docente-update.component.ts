import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FormacaoDocenteFormService, FormacaoDocenteFormGroup } from './formacao-docente-form.service';
import { IFormacaoDocente } from '../formacao-docente.model';
import { FormacaoDocenteService } from '../service/formacao-docente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';

@Component({
  selector: 'app-formacao-docente-update',
  templateUrl: './formacao-docente-update.component.html',
})
export class FormacaoDocenteUpdateComponent implements OnInit {
  isSaving = false;
  formacaoDocente: IFormacaoDocente | null = null;

  lookupItemsSharedCollection: ILookupItem[] = [];
  docentesSharedCollection: IDocente[] = [];

  editForm: FormacaoDocenteFormGroup = this.formacaoDocenteFormService.createFormacaoDocenteFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected formacaoDocenteService: FormacaoDocenteService,
    protected formacaoDocenteFormService: FormacaoDocenteFormService,
    protected lookupItemService: LookupItemService,
    protected docenteService: DocenteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ formacaoDocente }) => {
      this.formacaoDocente = formacaoDocente;
      if (formacaoDocente) {
        this.updateForm(formacaoDocente);
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
    const formacaoDocente = this.formacaoDocenteFormService.getFormacaoDocente(this.editForm);
    if (formacaoDocente.id !== null) {
      this.subscribeToSaveResponse(this.formacaoDocenteService.update(formacaoDocente));
    } else {
      this.subscribeToSaveResponse(this.formacaoDocenteService.create(formacaoDocente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormacaoDocente>>): void {
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

  protected updateForm(formacaoDocente: IFormacaoDocente): void {
    this.formacaoDocente = formacaoDocente;
    this.formacaoDocenteFormService.resetForm(this.editForm, formacaoDocente);

    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      formacaoDocente.grauAcademico
    );
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      formacaoDocente.docente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(lookupItems, this.formacaoDocente?.grauAcademico)
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(
        map((docentes: IDocente[]) =>
          this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.formacaoDocente?.docente)
        )
      )
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));
  }
}
