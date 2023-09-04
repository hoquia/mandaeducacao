import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CursoFormService, CursoFormGroup } from './curso-form.service';
import { ICurso } from '../curso.model';
import { CursoService } from '../service/curso.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/service/area-formacao.service';

@Component({
  selector: 'app-curso-update',
  templateUrl: './curso-update.component.html',
})
export class CursoUpdateComponent implements OnInit {
  isSaving = false;
  curso: ICurso | null = null;

  areaFormacaosSharedCollection: IAreaFormacao[] = [];

  editForm: CursoFormGroup = this.cursoFormService.createCursoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected cursoService: CursoService,
    protected cursoFormService: CursoFormService,
    protected areaFormacaoService: AreaFormacaoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAreaFormacao = (o1: IAreaFormacao | null, o2: IAreaFormacao | null): boolean =>
    this.areaFormacaoService.compareAreaFormacao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ curso }) => {
      this.curso = curso;
      if (curso) {
        this.updateForm(curso);
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
    const curso = this.cursoFormService.getCurso(this.editForm);
    if (curso.id !== null) {
      this.subscribeToSaveResponse(this.cursoService.update(curso));
    } else {
      this.subscribeToSaveResponse(this.cursoService.create(curso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurso>>): void {
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

  protected updateForm(curso: ICurso): void {
    this.curso = curso;
    this.cursoFormService.resetForm(this.editForm, curso);

    this.areaFormacaosSharedCollection = this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(
      this.areaFormacaosSharedCollection,
      curso.areaFormacao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.areaFormacaoService
      .query()
      .pipe(map((res: HttpResponse<IAreaFormacao[]>) => res.body ?? []))
      .pipe(
        map((areaFormacaos: IAreaFormacao[]) =>
          this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(areaFormacaos, this.curso?.areaFormacao)
        )
      )
      .subscribe((areaFormacaos: IAreaFormacao[]) => (this.areaFormacaosSharedCollection = areaFormacaos));
  }
}
