import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ImpostoFormService, ImpostoFormGroup } from './imposto-form.service';
import { IImposto } from '../imposto.model';
import { ImpostoService } from '../service/imposto.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';

@Component({
  selector: 'app-imposto-update',
  templateUrl: './imposto-update.component.html',
})
export class ImpostoUpdateComponent implements OnInit {
  isSaving = false;
  imposto: IImposto | null = null;

  lookupItemsSharedCollection: ILookupItem[] = [];

  editForm: ImpostoFormGroup = this.impostoFormService.createImpostoFormGroup();

  constructor(
    protected impostoService: ImpostoService,
    protected impostoFormService: ImpostoFormService,
    protected lookupItemService: LookupItemService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imposto }) => {
      this.imposto = imposto;
      if (imposto) {
        this.updateForm(imposto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const imposto = this.impostoFormService.getImposto(this.editForm);
    if (imposto.id !== null) {
      this.subscribeToSaveResponse(this.impostoService.update(imposto));
    } else {
      this.subscribeToSaveResponse(this.impostoService.create(imposto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImposto>>): void {
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

  protected updateForm(imposto: IImposto): void {
    this.imposto = imposto;
    this.impostoFormService.resetForm(this.editForm, imposto);

    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      imposto.tipoImposto,
      imposto.codigoImposto,
      imposto.motivoIsencaoCodigo,
      imposto.motivoIsencaoDescricao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
            lookupItems,
            this.imposto?.tipoImposto,
            this.imposto?.codigoImposto,
            this.imposto?.motivoIsencaoCodigo,
            this.imposto?.motivoIsencaoDescricao
          )
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));
  }
}
