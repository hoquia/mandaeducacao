import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CampoActuacaoDissertacaoFormService, CampoActuacaoDissertacaoFormGroup } from './campo-actuacao-dissertacao-form.service';
import { ICampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';
import { CampoActuacaoDissertacaoService } from '../service/campo-actuacao-dissertacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';

@Component({
  selector: 'app-campo-actuacao-dissertacao-update',
  templateUrl: './campo-actuacao-dissertacao-update.component.html',
})
export class CampoActuacaoDissertacaoUpdateComponent implements OnInit {
  isSaving = false;
  campoActuacaoDissertacao: ICampoActuacaoDissertacao | null = null;

  cursosSharedCollection: ICurso[] = [];

  editForm: CampoActuacaoDissertacaoFormGroup = this.campoActuacaoDissertacaoFormService.createCampoActuacaoDissertacaoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected campoActuacaoDissertacaoService: CampoActuacaoDissertacaoService,
    protected campoActuacaoDissertacaoFormService: CampoActuacaoDissertacaoFormService,
    protected cursoService: CursoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCurso = (o1: ICurso | null, o2: ICurso | null): boolean => this.cursoService.compareCurso(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campoActuacaoDissertacao }) => {
      this.campoActuacaoDissertacao = campoActuacaoDissertacao;
      if (campoActuacaoDissertacao) {
        this.updateForm(campoActuacaoDissertacao);
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
    const campoActuacaoDissertacao = this.campoActuacaoDissertacaoFormService.getCampoActuacaoDissertacao(this.editForm);
    if (campoActuacaoDissertacao.id !== null) {
      this.subscribeToSaveResponse(this.campoActuacaoDissertacaoService.update(campoActuacaoDissertacao));
    } else {
      this.subscribeToSaveResponse(this.campoActuacaoDissertacaoService.create(campoActuacaoDissertacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampoActuacaoDissertacao>>): void {
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

  protected updateForm(campoActuacaoDissertacao: ICampoActuacaoDissertacao): void {
    this.campoActuacaoDissertacao = campoActuacaoDissertacao;
    this.campoActuacaoDissertacaoFormService.resetForm(this.editForm, campoActuacaoDissertacao);

    this.cursosSharedCollection = this.cursoService.addCursoToCollectionIfMissing<ICurso>(
      this.cursosSharedCollection,
      ...(campoActuacaoDissertacao.cursos ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cursoService
      .query()
      .pipe(map((res: HttpResponse<ICurso[]>) => res.body ?? []))
      .pipe(
        map((cursos: ICurso[]) =>
          this.cursoService.addCursoToCollectionIfMissing<ICurso>(cursos, ...(this.campoActuacaoDissertacao?.cursos ?? []))
        )
      )
      .subscribe((cursos: ICurso[]) => (this.cursosSharedCollection = cursos));
  }
}
