import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PeriodoLancamentoNotaFormService, PeriodoLancamentoNotaFormGroup } from './periodo-lancamento-nota-form.service';
import { IPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';
import { PeriodoLancamentoNotaService } from '../service/periodo-lancamento-nota.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';
import { TipoAvaliacao } from 'app/entities/enumerations/tipo-avaliacao.model';

@Component({
  selector: 'app-periodo-lancamento-nota-update',
  templateUrl: './periodo-lancamento-nota-update.component.html',
})
export class PeriodoLancamentoNotaUpdateComponent implements OnInit {
  isSaving = false;
  periodoLancamentoNota: IPeriodoLancamentoNota | null = null;
  tipoAvaliacaoValues = Object.keys(TipoAvaliacao);

  usersSharedCollection: IUser[] = [];
  classesSharedCollection: IClasse[] = [];

  editForm: PeriodoLancamentoNotaFormGroup = this.periodoLancamentoNotaFormService.createPeriodoLancamentoNotaFormGroup();

  constructor(
    protected periodoLancamentoNotaService: PeriodoLancamentoNotaService,
    protected periodoLancamentoNotaFormService: PeriodoLancamentoNotaFormService,
    protected userService: UserService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareClasse = (o1: IClasse | null, o2: IClasse | null): boolean => this.classeService.compareClasse(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodoLancamentoNota }) => {
      this.periodoLancamentoNota = periodoLancamentoNota;
      if (periodoLancamentoNota) {
        this.updateForm(periodoLancamentoNota);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const periodoLancamentoNota = this.periodoLancamentoNotaFormService.getPeriodoLancamentoNota(this.editForm);
    if (periodoLancamentoNota.id !== null) {
      this.subscribeToSaveResponse(this.periodoLancamentoNotaService.update(periodoLancamentoNota));
    } else {
      this.subscribeToSaveResponse(this.periodoLancamentoNotaService.create(periodoLancamentoNota));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodoLancamentoNota>>): void {
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

  protected updateForm(periodoLancamentoNota: IPeriodoLancamentoNota): void {
    this.periodoLancamentoNota = periodoLancamentoNota;
    this.periodoLancamentoNotaFormService.resetForm(this.editForm, periodoLancamentoNota);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      periodoLancamentoNota.utilizador
    );
    this.classesSharedCollection = this.classeService.addClasseToCollectionIfMissing<IClasse>(
      this.classesSharedCollection,
      ...(periodoLancamentoNota.classes ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.periodoLancamentoNota?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.classeService
      .query()
      .pipe(map((res: HttpResponse<IClasse[]>) => res.body ?? []))
      .pipe(
        map((classes: IClasse[]) =>
          this.classeService.addClasseToCollectionIfMissing<IClasse>(classes, ...(this.periodoLancamentoNota?.classes ?? []))
        )
      )
      .subscribe((classes: IClasse[]) => (this.classesSharedCollection = classes));
  }
}
