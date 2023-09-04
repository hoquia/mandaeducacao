import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LookupItemFormService, LookupItemFormGroup } from './lookup-item-form.service';
import { ILookupItem } from '../lookup-item.model';
import { LookupItemService } from '../service/lookup-item.service';
import { ILookup } from 'app/entities/lookup/lookup.model';
import { LookupService } from 'app/entities/lookup/service/lookup.service';

@Component({
  selector: 'app-lookup-item-update',
  templateUrl: './lookup-item-update.component.html',
})
export class LookupItemUpdateComponent implements OnInit {
  isSaving = false;
  lookupItem: ILookupItem | null = null;

  lookupsSharedCollection: ILookup[] = [];

  editForm: LookupItemFormGroup = this.lookupItemFormService.createLookupItemFormGroup();

  constructor(
    protected lookupItemService: LookupItemService,
    protected lookupItemFormService: LookupItemFormService,
    protected lookupService: LookupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLookup = (o1: ILookup | null, o2: ILookup | null): boolean => this.lookupService.compareLookup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lookupItem }) => {
      this.lookupItem = lookupItem;
      if (lookupItem) {
        this.updateForm(lookupItem);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lookupItem = this.lookupItemFormService.getLookupItem(this.editForm);
    if (lookupItem.id !== null) {
      this.subscribeToSaveResponse(this.lookupItemService.update(lookupItem));
    } else {
      this.subscribeToSaveResponse(this.lookupItemService.create(lookupItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILookupItem>>): void {
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

  protected updateForm(lookupItem: ILookupItem): void {
    this.lookupItem = lookupItem;
    this.lookupItemFormService.resetForm(this.editForm, lookupItem);

    this.lookupsSharedCollection = this.lookupService.addLookupToCollectionIfMissing<ILookup>(
      this.lookupsSharedCollection,
      lookupItem.lookup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.lookupService
      .query()
      .pipe(map((res: HttpResponse<ILookup[]>) => res.body ?? []))
      .pipe(map((lookups: ILookup[]) => this.lookupService.addLookupToCollectionIfMissing<ILookup>(lookups, this.lookupItem?.lookup)))
      .subscribe((lookups: ILookup[]) => (this.lookupsSharedCollection = lookups));
  }
}
