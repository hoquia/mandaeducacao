import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TransferenciaSaldoFormService, TransferenciaSaldoFormGroup } from './transferencia-saldo-form.service';
import { ITransferenciaSaldo } from '../transferencia-saldo.model';
import { TransferenciaSaldoService } from '../service/transferencia-saldo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { ITransacao } from 'app/entities/transacao/transacao.model';
import { TransacaoService } from 'app/entities/transacao/service/transacao.service';

@Component({
  selector: 'app-transferencia-saldo-update',
  templateUrl: './transferencia-saldo-update.component.html',
})
export class TransferenciaSaldoUpdateComponent implements OnInit {
  isSaving = false;
  transferenciaSaldo: ITransferenciaSaldo | null = null;

  discentesSharedCollection: IDiscente[] = [];
  usersSharedCollection: IUser[] = [];
  lookupItemsSharedCollection: ILookupItem[] = [];
  transacaosSharedCollection: ITransacao[] = [];

  editForm: TransferenciaSaldoFormGroup = this.transferenciaSaldoFormService.createTransferenciaSaldoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected transferenciaSaldoService: TransferenciaSaldoService,
    protected transferenciaSaldoFormService: TransferenciaSaldoFormService,
    protected discenteService: DiscenteService,
    protected userService: UserService,
    protected lookupItemService: LookupItemService,
    protected transacaoService: TransacaoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareTransacao = (o1: ITransacao | null, o2: ITransacao | null): boolean => this.transacaoService.compareTransacao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferenciaSaldo }) => {
      this.transferenciaSaldo = transferenciaSaldo;
      if (transferenciaSaldo) {
        this.updateForm(transferenciaSaldo);
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
    const transferenciaSaldo = this.transferenciaSaldoFormService.getTransferenciaSaldo(this.editForm);
    if (transferenciaSaldo.id !== null) {
      this.subscribeToSaveResponse(this.transferenciaSaldoService.update(transferenciaSaldo));
    } else {
      this.subscribeToSaveResponse(this.transferenciaSaldoService.create(transferenciaSaldo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransferenciaSaldo>>): void {
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

  protected updateForm(transferenciaSaldo: ITransferenciaSaldo): void {
    this.transferenciaSaldo = transferenciaSaldo;
    this.transferenciaSaldoFormService.resetForm(this.editForm, transferenciaSaldo);

    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      transferenciaSaldo.de,
      transferenciaSaldo.para
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      transferenciaSaldo.utilizador
    );
    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      transferenciaSaldo.motivoTransferencia
    );
    this.transacaosSharedCollection = this.transacaoService.addTransacaoToCollectionIfMissing<ITransacao>(
      this.transacaosSharedCollection,
      ...(transferenciaSaldo.transacoes ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
            discentes,
            this.transferenciaSaldo?.de,
            this.transferenciaSaldo?.para
          )
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.transferenciaSaldo?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(lookupItems, this.transferenciaSaldo?.motivoTransferencia)
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.transacaoService
      .query()
      .pipe(map((res: HttpResponse<ITransacao[]>) => res.body ?? []))
      .pipe(
        map((transacaos: ITransacao[]) =>
          this.transacaoService.addTransacaoToCollectionIfMissing<ITransacao>(transacaos, ...(this.transferenciaSaldo?.transacoes ?? []))
        )
      )
      .subscribe((transacaos: ITransacao[]) => (this.transacaosSharedCollection = transacaos));
  }
}
