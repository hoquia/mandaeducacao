import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NaturezaTrabalhoFormService, NaturezaTrabalhoFormGroup } from './natureza-trabalho-form.service';
import { INaturezaTrabalho } from '../natureza-trabalho.model';
import { NaturezaTrabalhoService } from '../service/natureza-trabalho.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-natureza-trabalho-update',
  templateUrl: './natureza-trabalho-update.component.html',
})
export class NaturezaTrabalhoUpdateComponent implements OnInit {
  isSaving = false;
  naturezaTrabalho: INaturezaTrabalho | null = null;

  editForm: NaturezaTrabalhoFormGroup = this.naturezaTrabalhoFormService.createNaturezaTrabalhoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected naturezaTrabalhoService: NaturezaTrabalhoService,
    protected naturezaTrabalhoFormService: NaturezaTrabalhoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ naturezaTrabalho }) => {
      this.naturezaTrabalho = naturezaTrabalho;
      if (naturezaTrabalho) {
        this.updateForm(naturezaTrabalho);
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
    const naturezaTrabalho = this.naturezaTrabalhoFormService.getNaturezaTrabalho(this.editForm);
    if (naturezaTrabalho.id !== null) {
      this.subscribeToSaveResponse(this.naturezaTrabalhoService.update(naturezaTrabalho));
    } else {
      this.subscribeToSaveResponse(this.naturezaTrabalhoService.create(naturezaTrabalho));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INaturezaTrabalho>>): void {
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

  protected updateForm(naturezaTrabalho: INaturezaTrabalho): void {
    this.naturezaTrabalho = naturezaTrabalho;
    this.naturezaTrabalhoFormService.resetForm(this.editForm, naturezaTrabalho);
  }
}
