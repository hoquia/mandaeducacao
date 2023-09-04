import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CategoriaEmolumentoFormService, CategoriaEmolumentoFormGroup } from './categoria-emolumento-form.service';
import { ICategoriaEmolumento } from '../categoria-emolumento.model';
import { CategoriaEmolumentoService } from '../service/categoria-emolumento.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';
import { PlanoMultaService } from 'app/entities/plano-multa/service/plano-multa.service';

@Component({
  selector: 'app-categoria-emolumento-update',
  templateUrl: './categoria-emolumento-update.component.html',
})
export class CategoriaEmolumentoUpdateComponent implements OnInit {
  isSaving = false;
  categoriaEmolumento: ICategoriaEmolumento | null = null;

  planoMultasSharedCollection: IPlanoMulta[] = [];

  editForm: CategoriaEmolumentoFormGroup = this.categoriaEmolumentoFormService.createCategoriaEmolumentoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected categoriaEmolumentoService: CategoriaEmolumentoService,
    protected categoriaEmolumentoFormService: CategoriaEmolumentoFormService,
    protected planoMultaService: PlanoMultaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlanoMulta = (o1: IPlanoMulta | null, o2: IPlanoMulta | null): boolean => this.planoMultaService.comparePlanoMulta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaEmolumento }) => {
      this.categoriaEmolumento = categoriaEmolumento;
      if (categoriaEmolumento) {
        this.updateForm(categoriaEmolumento);
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
    const categoriaEmolumento = this.categoriaEmolumentoFormService.getCategoriaEmolumento(this.editForm);
    if (categoriaEmolumento.id !== null) {
      this.subscribeToSaveResponse(this.categoriaEmolumentoService.update(categoriaEmolumento));
    } else {
      this.subscribeToSaveResponse(this.categoriaEmolumentoService.create(categoriaEmolumento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaEmolumento>>): void {
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

  protected updateForm(categoriaEmolumento: ICategoriaEmolumento): void {
    this.categoriaEmolumento = categoriaEmolumento;
    this.categoriaEmolumentoFormService.resetForm(this.editForm, categoriaEmolumento);

    this.planoMultasSharedCollection = this.planoMultaService.addPlanoMultaToCollectionIfMissing<IPlanoMulta>(
      this.planoMultasSharedCollection,
      categoriaEmolumento.planoMulta
    );
  }

  protected loadRelationshipsOptions(): void {
    this.planoMultaService
      .query()
      .pipe(map((res: HttpResponse<IPlanoMulta[]>) => res.body ?? []))
      .pipe(
        map((planoMultas: IPlanoMulta[]) =>
          this.planoMultaService.addPlanoMultaToCollectionIfMissing<IPlanoMulta>(planoMultas, this.categoriaEmolumento?.planoMulta)
        )
      )
      .subscribe((planoMultas: IPlanoMulta[]) => (this.planoMultasSharedCollection = planoMultas));
  }
}
