import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MeioPagamentoFormService, MeioPagamentoFormGroup } from './meio-pagamento-form.service';
import { IMeioPagamento } from '../meio-pagamento.model';
import { MeioPagamentoService } from '../service/meio-pagamento.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-meio-pagamento-update',
  templateUrl: './meio-pagamento-update.component.html',
})
export class MeioPagamentoUpdateComponent implements OnInit {
  isSaving = false;
  meioPagamento: IMeioPagamento | null = null;

  editForm: MeioPagamentoFormGroup = this.meioPagamentoFormService.createMeioPagamentoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected meioPagamentoService: MeioPagamentoService,
    protected meioPagamentoFormService: MeioPagamentoFormService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meioPagamento }) => {
      this.meioPagamento = meioPagamento;
      if (meioPagamento) {
        this.updateForm(meioPagamento);
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
    const meioPagamento = this.meioPagamentoFormService.getMeioPagamento(this.editForm);
    if (meioPagamento.id !== null) {
      this.subscribeToSaveResponse(this.meioPagamentoService.update(meioPagamento));
    } else {
      this.subscribeToSaveResponse(this.meioPagamentoService.create(meioPagamento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeioPagamento>>): void {
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

  protected updateForm(meioPagamento: IMeioPagamento): void {
    this.meioPagamento = meioPagamento;
    this.meioPagamentoFormService.resetForm(this.editForm, meioPagamento);
  }
}
