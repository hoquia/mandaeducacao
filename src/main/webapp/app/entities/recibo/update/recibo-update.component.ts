import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ReciboFormService, ReciboFormGroup } from './recibo-form.service';
import { IRecibo } from '../recibo.model';
import { ReciboService } from '../service/recibo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IDocumentoComercial } from 'app/entities/documento-comercial/documento-comercial.model';
import { DocumentoComercialService } from 'app/entities/documento-comercial/service/documento-comercial.service';
import { ITransacao } from 'app/entities/transacao/transacao.model';
import { TransacaoService } from 'app/entities/transacao/service/transacao.service';
import { EstadoDocumentoComercial } from 'app/entities/enumerations/estado-documento-comercial.model';

@Component({
  selector: 'app-recibo-update',
  templateUrl: './recibo-update.component.html',
})
export class ReciboUpdateComponent implements OnInit {
  isSaving = false;
  recibo: IRecibo | null = null;
  estadoDocumentoComercialValues = Object.keys(EstadoDocumentoComercial);

  usersSharedCollection: IUser[] = [];
  matriculasSharedCollection: IMatricula[] = [];
  documentoComercialsSharedCollection: IDocumentoComercial[] = [];
  transacaosSharedCollection: ITransacao[] = [];

  editForm: ReciboFormGroup = this.reciboFormService.createReciboFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected reciboService: ReciboService,
    protected reciboFormService: ReciboFormService,
    protected userService: UserService,
    protected matriculaService: MatriculaService,
    protected documentoComercialService: DocumentoComercialService,
    protected transacaoService: TransacaoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  compareDocumentoComercial = (o1: IDocumentoComercial | null, o2: IDocumentoComercial | null): boolean =>
    this.documentoComercialService.compareDocumentoComercial(o1, o2);

  compareTransacao = (o1: ITransacao | null, o2: ITransacao | null): boolean => this.transacaoService.compareTransacao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recibo }) => {
      this.recibo = recibo;
      if (recibo) {
        this.updateForm(recibo);
      } else {
        this.editForm.patchValue({
          origem: 'P',
          estado: EstadoDocumentoComercial.N,
          numero: '1010',
        });

        const matriculaID = this.activatedRoute.snapshot.queryParamMap.get('matricula_id');
        this.matriculaService.find(Number(matriculaID)).subscribe(res => {
          this.editForm.patchValue({
            matricula: res.body,
          });
        });

        this.documentoComercialService.query().subscribe(res => {
          const documento = res.body?.filter(x => x.siglaFiscal === 'RC').shift();
          this.editForm.patchValue({
            documentoComercial: documento,
          });
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
    const recibo = this.reciboFormService.getRecibo(this.editForm);
    if (recibo.id !== null) {
      this.subscribeToSaveResponse(this.reciboService.update(recibo));
    } else {
      this.subscribeToSaveResponse(this.reciboService.create(recibo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecibo>>): void {
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

  protected updateForm(recibo: IRecibo): void {
    this.recibo = recibo;
    this.reciboFormService.resetForm(this.editForm, recibo);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, recibo.utilizador);
    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      recibo.matricula
    );
    this.documentoComercialsSharedCollection =
      this.documentoComercialService.addDocumentoComercialToCollectionIfMissing<IDocumentoComercial>(
        this.documentoComercialsSharedCollection,
        recibo.documentoComercial
      );
    this.transacaosSharedCollection = this.transacaoService.addTransacaoToCollectionIfMissing<ITransacao>(
      this.transacaosSharedCollection,
      recibo.transacao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.recibo?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.recibo?.matricula)
        )
      )
      .subscribe((matriculas: IMatricula[]) => (this.matriculasSharedCollection = matriculas));

    this.documentoComercialService
      .query()
      .pipe(map((res: HttpResponse<IDocumentoComercial[]>) => res.body ?? []))
      .pipe(
        map((documentoComercials: IDocumentoComercial[]) =>
          this.documentoComercialService.addDocumentoComercialToCollectionIfMissing<IDocumentoComercial>(
            documentoComercials,
            this.recibo?.documentoComercial
          )
        )
      )
      .subscribe((documentoComercials: IDocumentoComercial[]) => (this.documentoComercialsSharedCollection = documentoComercials));

    this.transacaoService
      .query()
      .pipe(map((res: HttpResponse<ITransacao[]>) => res.body ?? []))
      .pipe(
        map((transacaos: ITransacao[]) =>
          this.transacaoService.addTransacaoToCollectionIfMissing<ITransacao>(transacaos, this.recibo?.transacao)
        )
      )
      .subscribe((transacaos: ITransacao[]) => (this.transacaosSharedCollection = transacaos));
  }
}
