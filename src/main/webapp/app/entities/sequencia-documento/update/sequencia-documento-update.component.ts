import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SequenciaDocumentoFormService, SequenciaDocumentoFormGroup } from './sequencia-documento-form.service';
import { ISequenciaDocumento } from '../sequencia-documento.model';
import { SequenciaDocumentoService } from '../service/sequencia-documento.service';
import { ISerieDocumento } from 'app/entities/serie-documento/serie-documento.model';
import { SerieDocumentoService } from 'app/entities/serie-documento/service/serie-documento.service';

@Component({
  selector: 'app-sequencia-documento-update',
  templateUrl: './sequencia-documento-update.component.html',
})
export class SequenciaDocumentoUpdateComponent implements OnInit {
  isSaving = false;
  sequenciaDocumento: ISequenciaDocumento | null = null;

  serieDocumentosSharedCollection: ISerieDocumento[] = [];

  editForm: SequenciaDocumentoFormGroup = this.sequenciaDocumentoFormService.createSequenciaDocumentoFormGroup();

  constructor(
    protected sequenciaDocumentoService: SequenciaDocumentoService,
    protected sequenciaDocumentoFormService: SequenciaDocumentoFormService,
    protected serieDocumentoService: SerieDocumentoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSerieDocumento = (o1: ISerieDocumento | null, o2: ISerieDocumento | null): boolean =>
    this.serieDocumentoService.compareSerieDocumento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenciaDocumento }) => {
      this.sequenciaDocumento = sequenciaDocumento;
      if (sequenciaDocumento) {
        this.updateForm(sequenciaDocumento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sequenciaDocumento = this.sequenciaDocumentoFormService.getSequenciaDocumento(this.editForm);
    if (sequenciaDocumento.id !== null) {
      this.subscribeToSaveResponse(this.sequenciaDocumentoService.update(sequenciaDocumento));
    } else {
      this.subscribeToSaveResponse(this.sequenciaDocumentoService.create(sequenciaDocumento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISequenciaDocumento>>): void {
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

  protected updateForm(sequenciaDocumento: ISequenciaDocumento): void {
    this.sequenciaDocumento = sequenciaDocumento;
    this.sequenciaDocumentoFormService.resetForm(this.editForm, sequenciaDocumento);

    this.serieDocumentosSharedCollection = this.serieDocumentoService.addSerieDocumentoToCollectionIfMissing<ISerieDocumento>(
      this.serieDocumentosSharedCollection,
      sequenciaDocumento.serie
    );
  }

  protected loadRelationshipsOptions(): void {
    this.serieDocumentoService
      .query()
      .pipe(map((res: HttpResponse<ISerieDocumento[]>) => res.body ?? []))
      .pipe(
        map((serieDocumentos: ISerieDocumento[]) =>
          this.serieDocumentoService.addSerieDocumentoToCollectionIfMissing<ISerieDocumento>(
            serieDocumentos,
            this.sequenciaDocumento?.serie
          )
        )
      )
      .subscribe((serieDocumentos: ISerieDocumento[]) => (this.serieDocumentosSharedCollection = serieDocumentos));
  }
}
