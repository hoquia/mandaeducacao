import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { InstituicaoEnsinoFormService, InstituicaoEnsinoFormGroup } from './instituicao-ensino-form.service';
import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { InstituicaoEnsinoService } from '../service/instituicao-ensino.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';

@Component({
  selector: 'app-instituicao-ensino-update',
  templateUrl: './instituicao-ensino-update.component.html',
})
export class InstituicaoEnsinoUpdateComponent implements OnInit {
  isSaving = false;
  instituicaoEnsino: IInstituicaoEnsino | null = null;

  instituicaoEnsinosSharedCollection: IInstituicaoEnsino[] = [];
  lookupItemsSharedCollection: ILookupItem[] = [];

  editForm: InstituicaoEnsinoFormGroup = this.instituicaoEnsinoFormService.createInstituicaoEnsinoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected instituicaoEnsinoFormService: InstituicaoEnsinoFormService,
    protected lookupItemService: LookupItemService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareInstituicaoEnsino = (o1: IInstituicaoEnsino | null, o2: IInstituicaoEnsino | null): boolean =>
    this.instituicaoEnsinoService.compareInstituicaoEnsino(o1, o2);

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instituicaoEnsino }) => {
      this.instituicaoEnsino = instituicaoEnsino;
      if (instituicaoEnsino) {
        this.updateForm(instituicaoEnsino);
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const instituicaoEnsino = this.instituicaoEnsinoFormService.getInstituicaoEnsino(this.editForm);
    if (instituicaoEnsino.id !== null) {
      this.subscribeToSaveResponse(this.instituicaoEnsinoService.update(instituicaoEnsino));
    } else {
      this.subscribeToSaveResponse(this.instituicaoEnsinoService.create(instituicaoEnsino));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstituicaoEnsino>>): void {
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

  protected updateForm(instituicaoEnsino: IInstituicaoEnsino): void {
    this.instituicaoEnsino = instituicaoEnsino;
    this.instituicaoEnsinoFormService.resetForm(this.editForm, instituicaoEnsino);

    this.instituicaoEnsinosSharedCollection = this.instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing<IInstituicaoEnsino>(
      this.instituicaoEnsinosSharedCollection,
      instituicaoEnsino.sede
    );
    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      instituicaoEnsino.categoriaInstituicao,
      instituicaoEnsino.unidadePagadora,
      instituicaoEnsino.tipoVinculo,
      instituicaoEnsino.tipoInstalacao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.instituicaoEnsinoService
      .query()
      .pipe(map((res: HttpResponse<IInstituicaoEnsino[]>) => res.body ?? []))
      .pipe(
        map((instituicaoEnsinos: IInstituicaoEnsino[]) =>
          this.instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing<IInstituicaoEnsino>(
            instituicaoEnsinos,
            this.instituicaoEnsino?.sede
          )
        )
      )
      .subscribe((instituicaoEnsinos: IInstituicaoEnsino[]) => (this.instituicaoEnsinosSharedCollection = instituicaoEnsinos));

    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
            lookupItems,
            this.instituicaoEnsino?.categoriaInstituicao,
            this.instituicaoEnsino?.unidadePagadora,
            this.instituicaoEnsino?.tipoVinculo,
            this.instituicaoEnsino?.tipoInstalacao
          )
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));
  }
}
