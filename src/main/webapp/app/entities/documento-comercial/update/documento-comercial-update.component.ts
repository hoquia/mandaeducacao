import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DocumentoComercialFormService, DocumentoComercialFormGroup } from './documento-comercial-form.service';
import { IDocumentoComercial } from '../documento-comercial.model';
import { DocumentoComercialService } from '../service/documento-comercial.service';
import { ModuloDocumento } from 'app/entities/enumerations/modulo-documento.model';
import { OrigemDocumento } from 'app/entities/enumerations/origem-documento.model';
import { DocumentoFiscal } from 'app/entities/enumerations/documento-fiscal.model';

@Component({
  selector: 'app-documento-comercial-update',
  templateUrl: './documento-comercial-update.component.html',
})
export class DocumentoComercialUpdateComponent implements OnInit {
  isSaving = false;
  documentoComercial: IDocumentoComercial | null = null;
  moduloDocumentoValues = Object.keys(ModuloDocumento);
  origemDocumentoValues = Object.keys(OrigemDocumento);
  documentoFiscalValues = Object.keys(DocumentoFiscal);

  documentoComercialsSharedCollection: IDocumentoComercial[] = [];

  editForm: DocumentoComercialFormGroup = this.documentoComercialFormService.createDocumentoComercialFormGroup();

  constructor(
    protected documentoComercialService: DocumentoComercialService,
    protected documentoComercialFormService: DocumentoComercialFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDocumentoComercial = (o1: IDocumentoComercial | null, o2: IDocumentoComercial | null): boolean =>
    this.documentoComercialService.compareDocumentoComercial(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentoComercial }) => {
      this.documentoComercial = documentoComercial;
      if (documentoComercial) {
        this.updateForm(documentoComercial);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentoComercial = this.documentoComercialFormService.getDocumentoComercial(this.editForm);
    if (documentoComercial.id !== null) {
      this.subscribeToSaveResponse(this.documentoComercialService.update(documentoComercial));
    } else {
      this.subscribeToSaveResponse(this.documentoComercialService.create(documentoComercial));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentoComercial>>): void {
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

  protected updateForm(documentoComercial: IDocumentoComercial): void {
    this.documentoComercial = documentoComercial;
    this.documentoComercialFormService.resetForm(this.editForm, documentoComercial);

    this.documentoComercialsSharedCollection =
      this.documentoComercialService.addDocumentoComercialToCollectionIfMissing<IDocumentoComercial>(
        this.documentoComercialsSharedCollection,
        documentoComercial.transformaEm
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
            this.documentoComercial?.transformaEm
          )
        )
      )
      .subscribe((documentoComercials: IDocumentoComercial[]) => (this.documentoComercialsSharedCollection = documentoComercials));
  }
}
