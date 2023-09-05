import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EmolumentoFormService, EmolumentoFormGroup } from './emolumento-form.service';
import { IEmolumento } from '../emolumento.model';
import { EmolumentoService } from '../service/emolumento.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICategoriaEmolumento } from 'app/entities/categoria-emolumento/categoria-emolumento.model';
import { CategoriaEmolumentoService } from 'app/entities/categoria-emolumento/service/categoria-emolumento.service';
import { IImposto } from 'app/entities/imposto/imposto.model';
import { ImpostoService } from 'app/entities/imposto/service/imposto.service';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';
import { PlanoMultaService } from 'app/entities/plano-multa/service/plano-multa.service';

@Component({
  selector: 'app-emolumento-update',
  templateUrl: './emolumento-update.component.html',
})
export class EmolumentoUpdateComponent implements OnInit {
  isSaving = false;
  emolumento: IEmolumento | null = null;

  emolumentosSharedCollection: IEmolumento[] = [];
  categoriaEmolumentosSharedCollection: ICategoriaEmolumento[] = [];
  impostosSharedCollection: IImposto[] = [];
  planoMultasSharedCollection: IPlanoMulta[] = [];

  editForm: EmolumentoFormGroup = this.emolumentoFormService.createEmolumentoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected emolumentoService: EmolumentoService,
    protected emolumentoFormService: EmolumentoFormService,
    protected categoriaEmolumentoService: CategoriaEmolumentoService,
    protected impostoService: ImpostoService,
    protected planoMultaService: PlanoMultaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected router: Router
  ) {}

  compareEmolumento = (o1: IEmolumento | null, o2: IEmolumento | null): boolean => this.emolumentoService.compareEmolumento(o1, o2);

  compareCategoriaEmolumento = (o1: ICategoriaEmolumento | null, o2: ICategoriaEmolumento | null): boolean =>
    this.categoriaEmolumentoService.compareCategoriaEmolumento(o1, o2);

  compareImposto = (o1: IImposto | null, o2: IImposto | null): boolean => this.impostoService.compareImposto(o1, o2);

  comparePlanoMulta = (o1: IPlanoMulta | null, o2: IPlanoMulta | null): boolean => this.planoMultaService.comparePlanoMulta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emolumento }) => {
      this.emolumento = emolumento;
      if (emolumento) {
        this.updateForm(emolumento);
      } else {
        this.editForm.patchValue({
          quantidade: 1,
        });
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
    const emolumento = this.emolumentoFormService.getEmolumento(this.editForm);
    if (emolumento.id !== null) {
      this.subscribeToSaveResponse(this.emolumentoService.update(emolumento));
    } else {
      this.subscribeToSaveResponse(this.emolumentoService.create(emolumento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmolumento>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: e => this.onSaveSuccess(e.body!.id),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(id: any): void {
    this.router.navigate(['/emolumento', id, 'view']);
    // this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(emolumento: IEmolumento): void {
    this.emolumento = emolumento;
    this.emolumentoFormService.resetForm(this.editForm, emolumento);

    this.emolumentosSharedCollection = this.emolumentoService.addEmolumentoToCollectionIfMissing<IEmolumento>(
      this.emolumentosSharedCollection,
      emolumento.referencia
    );
    this.categoriaEmolumentosSharedCollection =
      this.categoriaEmolumentoService.addCategoriaEmolumentoToCollectionIfMissing<ICategoriaEmolumento>(
        this.categoriaEmolumentosSharedCollection,
        emolumento.categoria
      );
    this.impostosSharedCollection = this.impostoService.addImpostoToCollectionIfMissing<IImposto>(
      this.impostosSharedCollection,
      emolumento.imposto
    );
    this.planoMultasSharedCollection = this.planoMultaService.addPlanoMultaToCollectionIfMissing<IPlanoMulta>(
      this.planoMultasSharedCollection,
      emolumento.planoMulta
    );
  }

  protected loadRelationshipsOptions(): void {
    this.emolumentoService
      .query()
      .pipe(map((res: HttpResponse<IEmolumento[]>) => res.body ?? []))
      .pipe(
        map((emolumentos: IEmolumento[]) =>
          this.emolumentoService.addEmolumentoToCollectionIfMissing<IEmolumento>(emolumentos, this.emolumento?.referencia)
        )
      )
      .subscribe((emolumentos: IEmolumento[]) => (this.emolumentosSharedCollection = emolumentos));

    this.categoriaEmolumentoService
      .query()
      .pipe(map((res: HttpResponse<ICategoriaEmolumento[]>) => res.body ?? []))
      .pipe(
        map((categoriaEmolumentos: ICategoriaEmolumento[]) =>
          this.categoriaEmolumentoService.addCategoriaEmolumentoToCollectionIfMissing<ICategoriaEmolumento>(
            categoriaEmolumentos,
            this.emolumento?.categoria
          )
        )
      )
      .subscribe((categoriaEmolumentos: ICategoriaEmolumento[]) => (this.categoriaEmolumentosSharedCollection = categoriaEmolumentos));

    this.impostoService
      .query()
      .pipe(map((res: HttpResponse<IImposto[]>) => res.body ?? []))
      .pipe(
        map((impostos: IImposto[]) => this.impostoService.addImpostoToCollectionIfMissing<IImposto>(impostos, this.emolumento?.imposto))
      )
      .subscribe((impostos: IImposto[]) => (this.impostosSharedCollection = impostos));

    this.planoMultaService
      .query()
      .pipe(map((res: HttpResponse<IPlanoMulta[]>) => res.body ?? []))
      .pipe(
        map((planoMultas: IPlanoMulta[]) =>
          this.planoMultaService.addPlanoMultaToCollectionIfMissing<IPlanoMulta>(planoMultas, this.emolumento?.planoMulta)
        )
      )
      .subscribe((planoMultas: IPlanoMulta[]) => (this.planoMultasSharedCollection = planoMultas));
  }
}
