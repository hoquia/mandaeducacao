import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EstadoDissertacaoFormService, EstadoDissertacaoFormGroup } from './estado-dissertacao-form.service';
import { IEstadoDissertacao } from '../estado-dissertacao.model';
import { EstadoDissertacaoService } from '../service/estado-dissertacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-estado-dissertacao-update',
  templateUrl: './estado-dissertacao-update.component.html',
})
export class EstadoDissertacaoUpdateComponent implements OnInit {
  isSaving = false;
  estadoDissertacao: IEstadoDissertacao | null = null;

  editForm: EstadoDissertacaoFormGroup = this.estadoDissertacaoFormService.createEstadoDissertacaoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected estadoDissertacaoService: EstadoDissertacaoService,
    protected estadoDissertacaoFormService: EstadoDissertacaoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoDissertacao }) => {
      this.estadoDissertacao = estadoDissertacao;
      if (estadoDissertacao) {
        this.updateForm(estadoDissertacao);
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
    const estadoDissertacao = this.estadoDissertacaoFormService.getEstadoDissertacao(this.editForm);
    if (estadoDissertacao.id !== null) {
      this.subscribeToSaveResponse(this.estadoDissertacaoService.update(estadoDissertacao));
    } else {
      this.subscribeToSaveResponse(this.estadoDissertacaoService.create(estadoDissertacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoDissertacao>>): void {
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

  protected updateForm(estadoDissertacao: IEstadoDissertacao): void {
    this.estadoDissertacao = estadoDissertacao;
    this.estadoDissertacaoFormService.resetForm(this.editForm, estadoDissertacao);
  }
}
