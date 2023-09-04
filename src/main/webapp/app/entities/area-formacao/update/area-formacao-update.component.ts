import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AreaFormacaoFormService, AreaFormacaoFormGroup } from './area-formacao-form.service';
import { IAreaFormacao } from '../area-formacao.model';
import { AreaFormacaoService } from '../service/area-formacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { INivelEnsino } from 'app/entities/nivel-ensino/nivel-ensino.model';
import { NivelEnsinoService } from 'app/entities/nivel-ensino/service/nivel-ensino.service';

@Component({
  selector: 'app-area-formacao-update',
  templateUrl: './area-formacao-update.component.html',
})
export class AreaFormacaoUpdateComponent implements OnInit {
  isSaving = false;
  areaFormacao: IAreaFormacao | null = null;

  nivelEnsinosSharedCollection: INivelEnsino[] = [];

  editForm: AreaFormacaoFormGroup = this.areaFormacaoFormService.createAreaFormacaoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected areaFormacaoService: AreaFormacaoService,
    protected areaFormacaoFormService: AreaFormacaoFormService,
    protected nivelEnsinoService: NivelEnsinoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNivelEnsino = (o1: INivelEnsino | null, o2: INivelEnsino | null): boolean => this.nivelEnsinoService.compareNivelEnsino(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaFormacao }) => {
      this.areaFormacao = areaFormacao;
      if (areaFormacao) {
        this.updateForm(areaFormacao);
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
    const areaFormacao = this.areaFormacaoFormService.getAreaFormacao(this.editForm);
    if (areaFormacao.id !== null) {
      this.subscribeToSaveResponse(this.areaFormacaoService.update(areaFormacao));
    } else {
      this.subscribeToSaveResponse(this.areaFormacaoService.create(areaFormacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaFormacao>>): void {
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

  protected updateForm(areaFormacao: IAreaFormacao): void {
    this.areaFormacao = areaFormacao;
    this.areaFormacaoFormService.resetForm(this.editForm, areaFormacao);

    this.nivelEnsinosSharedCollection = this.nivelEnsinoService.addNivelEnsinoToCollectionIfMissing<INivelEnsino>(
      this.nivelEnsinosSharedCollection,
      areaFormacao.nivelEnsino
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nivelEnsinoService
      .query()
      .pipe(map((res: HttpResponse<INivelEnsino[]>) => res.body ?? []))
      .pipe(
        map((nivelEnsinos: INivelEnsino[]) =>
          this.nivelEnsinoService.addNivelEnsinoToCollectionIfMissing<INivelEnsino>(nivelEnsinos, this.areaFormacao?.nivelEnsino)
        )
      )
      .subscribe((nivelEnsinos: INivelEnsino[]) => (this.nivelEnsinosSharedCollection = nivelEnsinos));
  }
}
