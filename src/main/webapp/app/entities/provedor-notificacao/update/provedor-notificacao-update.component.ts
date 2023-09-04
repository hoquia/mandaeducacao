import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProvedorNotificacaoFormService, ProvedorNotificacaoFormGroup } from './provedor-notificacao-form.service';
import { IProvedorNotificacao } from '../provedor-notificacao.model';
import { ProvedorNotificacaoService } from '../service/provedor-notificacao.service';
import { IInstituicaoEnsino } from 'app/entities/instituicao-ensino/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/service/instituicao-ensino.service';

@Component({
  selector: 'app-provedor-notificacao-update',
  templateUrl: './provedor-notificacao-update.component.html',
})
export class ProvedorNotificacaoUpdateComponent implements OnInit {
  isSaving = false;
  provedorNotificacao: IProvedorNotificacao | null = null;

  instituicaoEnsinosSharedCollection: IInstituicaoEnsino[] = [];

  editForm: ProvedorNotificacaoFormGroup = this.provedorNotificacaoFormService.createProvedorNotificacaoFormGroup();

  constructor(
    protected provedorNotificacaoService: ProvedorNotificacaoService,
    protected provedorNotificacaoFormService: ProvedorNotificacaoFormService,
    protected instituicaoEnsinoService: InstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareInstituicaoEnsino = (o1: IInstituicaoEnsino | null, o2: IInstituicaoEnsino | null): boolean =>
    this.instituicaoEnsinoService.compareInstituicaoEnsino(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ provedorNotificacao }) => {
      this.provedorNotificacao = provedorNotificacao;
      if (provedorNotificacao) {
        this.updateForm(provedorNotificacao);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const provedorNotificacao = this.provedorNotificacaoFormService.getProvedorNotificacao(this.editForm);
    if (provedorNotificacao.id !== null) {
      this.subscribeToSaveResponse(this.provedorNotificacaoService.update(provedorNotificacao));
    } else {
      this.subscribeToSaveResponse(this.provedorNotificacaoService.create(provedorNotificacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvedorNotificacao>>): void {
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

  protected updateForm(provedorNotificacao: IProvedorNotificacao): void {
    this.provedorNotificacao = provedorNotificacao;
    this.provedorNotificacaoFormService.resetForm(this.editForm, provedorNotificacao);

    this.instituicaoEnsinosSharedCollection = this.instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing<IInstituicaoEnsino>(
      this.instituicaoEnsinosSharedCollection,
      provedorNotificacao.instituicao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.instituicaoEnsinoService
      .query()
      .pipe(map((res: HttpResponse<IInstituicaoEnsino[]>) => res.body ?? []))
      .pipe(
        map((instituicaoEnsinos: IInstituicaoEnsino[]) =>
          this.instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing<IInstituicaoEnsino>(
            instituicaoEnsinos,
            this.provedorNotificacao?.instituicao
          )
        )
      )
      .subscribe((instituicaoEnsinos: IInstituicaoEnsino[]) => (this.instituicaoEnsinosSharedCollection = instituicaoEnsinos));
  }
}
