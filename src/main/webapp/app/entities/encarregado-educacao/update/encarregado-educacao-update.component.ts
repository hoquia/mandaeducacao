import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EncarregadoEducacaoFormService, EncarregadoEducacaoFormGroup } from './encarregado-educacao-form.service';
import { IEncarregadoEducacao } from '../encarregado-educacao.model';
import { EncarregadoEducacaoService } from '../service/encarregado-educacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { Sexo } from 'app/entities/enumerations/sexo.model';

@Component({
  selector: 'app-encarregado-educacao-update',
  templateUrl: './encarregado-educacao-update.component.html',
})
export class EncarregadoEducacaoUpdateComponent implements OnInit {
  isSaving = false;
  encarregadoEducacao: IEncarregadoEducacao | null = null;
  sexoValues = Object.keys(Sexo);

  grauParentescoCollection: ILookupItem[] = [];
  tipoDocumentoCollection: ILookupItem[] = [];
  profissaoCollection: ILookupItem[] = [];

  editForm: EncarregadoEducacaoFormGroup = this.encarregadoEducacaoFormService.createEncarregadoEducacaoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected encarregadoEducacaoService: EncarregadoEducacaoService,
    protected encarregadoEducacaoFormService: EncarregadoEducacaoFormService,
    protected lookupItemService: LookupItemService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encarregadoEducacao }) => {
      this.encarregadoEducacao = encarregadoEducacao;
      if (encarregadoEducacao) {
        this.updateForm(encarregadoEducacao);
      }
    });

    this.loadRelationshipsOptions();
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
    const encarregadoEducacao = this.encarregadoEducacaoFormService.getEncarregadoEducacao(this.editForm);
    if (encarregadoEducacao.id !== null) {
      this.subscribeToSaveResponse(this.encarregadoEducacaoService.update(encarregadoEducacao));
    } else {
      this.subscribeToSaveResponse(this.encarregadoEducacaoService.create(encarregadoEducacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncarregadoEducacao>>): void {
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

  protected updateForm(encarregadoEducacao: IEncarregadoEducacao): void {
    this.encarregadoEducacao = encarregadoEducacao;
    this.encarregadoEducacaoFormService.resetForm(this.editForm, encarregadoEducacao);

    // this.grauParentescoCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
    //   this.lookupItemsSharedCollection,
    //   encarregadoEducacao.grauParentesco,
    //   encarregadoEducacao.tipoDocumento,
    //   encarregadoEducacao.profissao
    // );
  }

  protected loadRelationshipsOptions(): void {
    // grau parentesco
    this.lookupItemService.query({ 'lookupId.equals': 979 }).subscribe(res => {
      this.grauParentescoCollection = res.body ?? [];
    });

    // tipo documento
    this.lookupItemService.query({ 'lookupId.equals': 971 }).subscribe(res => {
      this.tipoDocumentoCollection = res.body ?? [];
    });

    // profissao
    this.lookupItemService.query({ 'lookupId.equals': 980 }).subscribe(res => {
      this.profissaoCollection = res.body ?? [];
    });
  }
}
