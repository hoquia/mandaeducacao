import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DiscenteFormService, DiscenteFormGroup } from './discente-form.service';
import { IDiscente } from '../discente.model';
import { DiscenteService } from '../service/discente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IEncarregadoEducacao } from 'app/entities/encarregado-educacao/encarregado-educacao.model';
import { EncarregadoEducacaoService } from 'app/entities/encarregado-educacao/service/encarregado-educacao.service';
import { Sexo } from 'app/entities/enumerations/sexo.model';

@Component({
  selector: 'app-discente-update',
  templateUrl: './discente-update.component.html',
})
export class DiscenteUpdateComponent implements OnInit {
  isSaving = false;
  discente: IDiscente | null = null;
  sexoValues = Object.keys(Sexo);

  lookupItemsSharedCollection: ILookupItem[] = [];
  encarregadoEducacaosSharedCollection: IEncarregadoEducacao[] = [];

  editForm: DiscenteFormGroup = this.discenteFormService.createDiscenteFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected discenteService: DiscenteService,
    protected discenteFormService: DiscenteFormService,
    protected lookupItemService: LookupItemService,
    protected encarregadoEducacaoService: EncarregadoEducacaoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareEncarregadoEducacao = (o1: IEncarregadoEducacao | null, o2: IEncarregadoEducacao | null): boolean =>
    this.encarregadoEducacaoService.compareEncarregadoEducacao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discente }) => {
      this.discente = discente;
      if (discente) {
        this.updateForm(discente);
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
    const discente = this.discenteFormService.getDiscente(this.editForm);
    if (discente.id !== null) {
      this.subscribeToSaveResponse(this.discenteService.update(discente));
    } else {
      this.subscribeToSaveResponse(this.discenteService.create(discente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscente>>): void {
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

  protected updateForm(discente: IDiscente): void {
    this.discente = discente;
    this.discenteFormService.resetForm(this.editForm, discente);

    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      discente.nacionalidade,
      discente.naturalidade,
      discente.tipoDocumento,
      discente.profissao,
      discente.grupoSanguinio,
      discente.necessidadeEspecial
    );
    this.encarregadoEducacaosSharedCollection =
      this.encarregadoEducacaoService.addEncarregadoEducacaoToCollectionIfMissing<IEncarregadoEducacao>(
        this.encarregadoEducacaosSharedCollection,
        discente.encarregadoEducacao
      );
  }

  protected loadRelationshipsOptions(): void {
    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
            lookupItems,
            this.discente?.nacionalidade,
            this.discente?.naturalidade,
            this.discente?.tipoDocumento,
            this.discente?.profissao,
            this.discente?.grupoSanguinio,
            this.discente?.necessidadeEspecial
          )
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.encarregadoEducacaoService
      .query()
      .pipe(map((res: HttpResponse<IEncarregadoEducacao[]>) => res.body ?? []))
      .pipe(
        map((encarregadoEducacaos: IEncarregadoEducacao[]) =>
          this.encarregadoEducacaoService.addEncarregadoEducacaoToCollectionIfMissing<IEncarregadoEducacao>(
            encarregadoEducacaos,
            this.discente?.encarregadoEducacao
          )
        )
      )
      .subscribe((encarregadoEducacaos: IEncarregadoEducacao[]) => (this.encarregadoEducacaosSharedCollection = encarregadoEducacaos));
  }
}
