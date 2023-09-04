import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CategoriaOcorrenciaFormService, CategoriaOcorrenciaFormGroup } from './categoria-ocorrencia-form.service';
import { ICategoriaOcorrencia } from '../categoria-ocorrencia.model';
import { CategoriaOcorrenciaService } from '../service/categoria-ocorrencia.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IMedidaDisciplinar } from 'app/entities/medida-disciplinar/medida-disciplinar.model';
import { MedidaDisciplinarService } from 'app/entities/medida-disciplinar/service/medida-disciplinar.service';

@Component({
  selector: 'app-categoria-ocorrencia-update',
  templateUrl: './categoria-ocorrencia-update.component.html',
})
export class CategoriaOcorrenciaUpdateComponent implements OnInit {
  isSaving = false;
  categoriaOcorrencia: ICategoriaOcorrencia | null = null;

  categoriaOcorrenciasSharedCollection: ICategoriaOcorrencia[] = [];
  docentesSharedCollection: IDocente[] = [];
  medidaDisciplinarsSharedCollection: IMedidaDisciplinar[] = [];

  editForm: CategoriaOcorrenciaFormGroup = this.categoriaOcorrenciaFormService.createCategoriaOcorrenciaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected categoriaOcorrenciaService: CategoriaOcorrenciaService,
    protected categoriaOcorrenciaFormService: CategoriaOcorrenciaFormService,
    protected docenteService: DocenteService,
    protected medidaDisciplinarService: MedidaDisciplinarService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCategoriaOcorrencia = (o1: ICategoriaOcorrencia | null, o2: ICategoriaOcorrencia | null): boolean =>
    this.categoriaOcorrenciaService.compareCategoriaOcorrencia(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareMedidaDisciplinar = (o1: IMedidaDisciplinar | null, o2: IMedidaDisciplinar | null): boolean =>
    this.medidaDisciplinarService.compareMedidaDisciplinar(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaOcorrencia }) => {
      this.categoriaOcorrencia = categoriaOcorrencia;
      if (categoriaOcorrencia) {
        this.updateForm(categoriaOcorrencia);
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
    const categoriaOcorrencia = this.categoriaOcorrenciaFormService.getCategoriaOcorrencia(this.editForm);
    if (categoriaOcorrencia.id !== null) {
      this.subscribeToSaveResponse(this.categoriaOcorrenciaService.update(categoriaOcorrencia));
    } else {
      this.subscribeToSaveResponse(this.categoriaOcorrenciaService.create(categoriaOcorrencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaOcorrencia>>): void {
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

  protected updateForm(categoriaOcorrencia: ICategoriaOcorrencia): void {
    this.categoriaOcorrencia = categoriaOcorrencia;
    this.categoriaOcorrenciaFormService.resetForm(this.editForm, categoriaOcorrencia);

    this.categoriaOcorrenciasSharedCollection =
      this.categoriaOcorrenciaService.addCategoriaOcorrenciaToCollectionIfMissing<ICategoriaOcorrencia>(
        this.categoriaOcorrenciasSharedCollection,
        categoriaOcorrencia.referencia
      );
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      categoriaOcorrencia.encaminhar
    );
    this.medidaDisciplinarsSharedCollection = this.medidaDisciplinarService.addMedidaDisciplinarToCollectionIfMissing<IMedidaDisciplinar>(
      this.medidaDisciplinarsSharedCollection,
      categoriaOcorrencia.medidaDisciplinar
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoriaOcorrenciaService
      .query()
      .pipe(map((res: HttpResponse<ICategoriaOcorrencia[]>) => res.body ?? []))
      .pipe(
        map((categoriaOcorrencias: ICategoriaOcorrencia[]) =>
          this.categoriaOcorrenciaService.addCategoriaOcorrenciaToCollectionIfMissing<ICategoriaOcorrencia>(
            categoriaOcorrencias,
            this.categoriaOcorrencia?.referencia
          )
        )
      )
      .subscribe((categoriaOcorrencias: ICategoriaOcorrencia[]) => (this.categoriaOcorrenciasSharedCollection = categoriaOcorrencias));

    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(
        map((docentes: IDocente[]) =>
          this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.categoriaOcorrencia?.encaminhar)
        )
      )
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));

    this.medidaDisciplinarService
      .query()
      .pipe(map((res: HttpResponse<IMedidaDisciplinar[]>) => res.body ?? []))
      .pipe(
        map((medidaDisciplinars: IMedidaDisciplinar[]) =>
          this.medidaDisciplinarService.addMedidaDisciplinarToCollectionIfMissing<IMedidaDisciplinar>(
            medidaDisciplinars,
            this.categoriaOcorrencia?.medidaDisciplinar
          )
        )
      )
      .subscribe((medidaDisciplinars: IMedidaDisciplinar[]) => (this.medidaDisciplinarsSharedCollection = medidaDisciplinars));
  }
}
