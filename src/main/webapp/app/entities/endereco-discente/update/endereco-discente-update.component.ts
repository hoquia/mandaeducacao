import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EnderecoDiscenteFormService, EnderecoDiscenteFormGroup } from './endereco-discente-form.service';
import { IEnderecoDiscente } from '../endereco-discente.model';
import { EnderecoDiscenteService } from '../service/endereco-discente.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { TipoEndereco } from 'app/entities/enumerations/tipo-endereco.model';

@Component({
  selector: 'app-endereco-discente-update',
  templateUrl: './endereco-discente-update.component.html',
})
export class EnderecoDiscenteUpdateComponent implements OnInit {
  isSaving = false;
  enderecoDiscente: IEnderecoDiscente | null = null;
  tipoEnderecoValues = Object.keys(TipoEndereco);

  lookupItemsSharedCollection: ILookupItem[] = [];
  discentesSharedCollection: IDiscente[] = [];

  editForm: EnderecoDiscenteFormGroup = this.enderecoDiscenteFormService.createEnderecoDiscenteFormGroup();

  constructor(
    protected enderecoDiscenteService: EnderecoDiscenteService,
    protected enderecoDiscenteFormService: EnderecoDiscenteFormService,
    protected lookupItemService: LookupItemService,
    protected discenteService: DiscenteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enderecoDiscente }) => {
      this.enderecoDiscente = enderecoDiscente;
      if (enderecoDiscente) {
        this.updateForm(enderecoDiscente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enderecoDiscente = this.enderecoDiscenteFormService.getEnderecoDiscente(this.editForm);
    if (enderecoDiscente.id !== null) {
      this.subscribeToSaveResponse(this.enderecoDiscenteService.update(enderecoDiscente));
    } else {
      this.subscribeToSaveResponse(this.enderecoDiscenteService.create(enderecoDiscente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnderecoDiscente>>): void {
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

  protected updateForm(enderecoDiscente: IEnderecoDiscente): void {
    this.enderecoDiscente = enderecoDiscente;
    this.enderecoDiscenteFormService.resetForm(this.editForm, enderecoDiscente);

    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      enderecoDiscente.pais,
      enderecoDiscente.provincia,
      enderecoDiscente.municipio
    );
    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      enderecoDiscente.discente
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
            this.enderecoDiscente?.pais,
            this.enderecoDiscente?.provincia,
            this.enderecoDiscente?.municipio
          )
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(discentes, this.enderecoDiscente?.discente)
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));
  }
}
