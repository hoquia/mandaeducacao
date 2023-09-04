import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LookupFormService, LookupFormGroup } from './lookup-form.service';
import { ILookup } from '../lookup.model';
import { LookupService } from '../service/lookup.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-lookup-update',
  templateUrl: './lookup-update.component.html',
})
export class LookupUpdateComponent implements OnInit {
  isSaving = false;
  lookup: ILookup | null = null;

  editForm: LookupFormGroup = this.lookupFormService.createLookupFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected lookupService: LookupService,
    protected lookupFormService: LookupFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lookup }) => {
      this.lookup = lookup;
      if (lookup) {
        this.updateForm(lookup);
      }
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
    const lookup = this.lookupFormService.getLookup(this.editForm);
    if (lookup.id !== null) {
      this.subscribeToSaveResponse(this.lookupService.update(lookup));
    } else {
      this.subscribeToSaveResponse(this.lookupService.create(lookup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILookup>>): void {
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

  protected updateForm(lookup: ILookup): void {
    this.lookup = lookup;
    this.lookupFormService.resetForm(this.editForm, lookup);
  }
}
