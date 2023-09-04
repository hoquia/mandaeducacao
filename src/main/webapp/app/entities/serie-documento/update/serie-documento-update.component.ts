import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SerieDocumentoFormService, SerieDocumentoFormGroup } from './serie-documento-form.service';
import { ISerieDocumento } from '../serie-documento.model';
import { SerieDocumentoService } from '../service/serie-documento.service';
import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/service/documento-comercial.service';

@Component({
  selector: 'app-serie-documento-update',
  templateUrl: './serie-documento-update.component.html',
})
export class SerieDocumentoUpdateComponent implements OnInit {
  isSaving = false;
  serieDocumento: ISerieDocumento | null = null;

  documentoComercialsSharedCollection: IDocumentoComercial[] = [];

  editForm: SerieDocumentoFormGroup = this.serieDocumentoFormService.createSerieDocumentoFormGroup();

  constructor(
    protected serieDocumentoService: SerieDocumentoService,
    protected serieDocumentoFormService: SerieDocumentoFormService,
    protected documentoComercialService: DocumentoComercialService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDocumentoComercial = (o1: IDocumentoComercial | null, o2: IDocumentoComercial | null): boolean =>
    this.documentoComercialService.compareDocumentoComercial(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serieDocumento }) => {
      this.serieDocumento = serieDocumento;
      if (serieDocumento) {
        this.updateForm(serieDocumento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serieDocumento = this.serieDocumentoFormService.getSerieDocumento(this.editForm);
    if (serieDocumento.id !== null) {
      this.subscribeToSaveResponse(this.serieDocumentoService.update(serieDocumento));
    } else {
      this.subscribeToSaveResponse(this.serieDocumentoService.create(serieDocumento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISerieDocumento>>): void {
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

  protected updateForm(serieDocumento: ISerieDocumento): void {
    this.serieDocumento = serieDocumento;
    this.serieDocumentoFormService.resetForm(this.editForm, serieDocumento);

    this.documentoComercialsSharedCollection =
      this.documentoComercialService.addDocumentoComercialToCollectionIfMissing<IDocumentoComercial>(
        this.documentoComercialsSharedCollection,
        serieDocumento.tipoDocumento
      );
  }

  protected loadRelationshipsOptions(): void {
    this.documentoComercialService
      .query()
      .pipe(map((res: HttpResponse<IDocumentoComercial[]>) => res.body ?? []))
      .pipe(
        map((documentoComercials: IDocumentoComercial[]) =>
          this.documentoComercialService.addDocumentoComercialToCollectionIfMissing<IDocumentoComercial>(
            documentoComercials,
            this.serieDocumento?.tipoDocumento
          )
        )
      )
      .subscribe((documentoComercials: IDocumentoComercial[]) => (this.documentoComercialsSharedCollection = documentoComercials));
  }
}
