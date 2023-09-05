import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TransacaoFormService, TransacaoFormGroup } from './transacao-form.service';
import { ITransacao } from '../transacao.model';
import { TransacaoService } from '../service/transacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IMeioPagamento } from 'app/entities/meio-pagamento/meio-pagamento.model';
import { MeioPagamentoService } from 'app/entities/meio-pagamento/service/meio-pagamento.service';
import { IConta } from 'app/entities/conta/conta.model';
import { ContaService } from 'app/entities/conta/service/conta.service';
import { EstadoPagamento } from 'app/entities/enumerations/estado-pagamento.model';

@Component({
  selector: 'app-transacao-update',
  templateUrl: './transacao-update.component.html',
})
export class TransacaoUpdateComponent implements OnInit {
  isSaving = false;
  transacao: ITransacao | null = null;
  estadoPagamentoValues = Object.keys(EstadoPagamento);

  usersSharedCollection: IUser[] = [];
  lookupItemsSharedCollection: ILookupItem[] = [];
  matriculasSharedCollection: IMatricula[] = [];
  meioPagamentosSharedCollection: IMeioPagamento[] = [];
  contasSharedCollection: IConta[] = [];

  editForm: TransacaoFormGroup = this.transacaoFormService.createTransacaoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected transacaoService: TransacaoService,
    protected transacaoFormService: TransacaoFormService,
    protected userService: UserService,
    protected lookupItemService: LookupItemService,
    protected matriculaService: MatriculaService,
    protected meioPagamentoService: MeioPagamentoService,
    protected contaService: ContaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  compareMeioPagamento = (o1: IMeioPagamento | null, o2: IMeioPagamento | null): boolean =>
    this.meioPagamentoService.compareMeioPagamento(o1, o2);

  compareConta = (o1: IConta | null, o2: IConta | null): boolean => this.contaService.compareConta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transacao }) => {
      this.transacao = transacao;
      if (transacao) {
        this.updateForm(transacao);
      } else {
        this.editForm.patchValue({
          saldo: 0,
        });
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
    const transacao = this.transacaoFormService.getTransacao(this.editForm);
    if (transacao.id !== null) {
      this.subscribeToSaveResponse(this.transacaoService.update(transacao));
    } else {
      this.subscribeToSaveResponse(this.transacaoService.create(transacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransacao>>): void {
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

  protected updateForm(transacao: ITransacao): void {
    this.transacao = transacao;
    this.transacaoFormService.resetForm(this.editForm, transacao);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, transacao.utilizador);
    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      transacao.moeda
    );
    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      transacao.matricula
    );
    this.meioPagamentosSharedCollection = this.meioPagamentoService.addMeioPagamentoToCollectionIfMissing<IMeioPagamento>(
      this.meioPagamentosSharedCollection,
      transacao.meioPagamento
    );
    this.contasSharedCollection = this.contaService.addContaToCollectionIfMissing<IConta>(this.contasSharedCollection, transacao.conta);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.transacao?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(lookupItems, this.transacao?.moeda)
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.transacao?.matricula)
        )
      )
      .subscribe((matriculas: IMatricula[]) => (this.matriculasSharedCollection = matriculas));

    this.meioPagamentoService
      .query()
      .pipe(map((res: HttpResponse<IMeioPagamento[]>) => res.body ?? []))
      .pipe(
        map((meioPagamentos: IMeioPagamento[]) =>
          this.meioPagamentoService.addMeioPagamentoToCollectionIfMissing<IMeioPagamento>(meioPagamentos, this.transacao?.meioPagamento)
        )
      )
      .subscribe((meioPagamentos: IMeioPagamento[]) => (this.meioPagamentosSharedCollection = meioPagamentos));

    this.contaService
      .query()
      .pipe(map((res: HttpResponse<IConta[]>) => res.body ?? []))
      .pipe(map((contas: IConta[]) => this.contaService.addContaToCollectionIfMissing<IConta>(contas, this.transacao?.conta)))
      .subscribe((contas: IConta[]) => (this.contasSharedCollection = contas));
  }
}
