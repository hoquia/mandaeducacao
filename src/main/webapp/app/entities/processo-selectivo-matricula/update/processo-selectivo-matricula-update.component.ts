import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProcessoSelectivoMatriculaFormService, ProcessoSelectivoMatriculaFormGroup } from './processo-selectivo-matricula-form.service';
import { IProcessoSelectivoMatricula } from '../processo-selectivo-matricula.model';
import { ProcessoSelectivoMatriculaService } from '../service/processo-selectivo-matricula.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

@Component({
  selector: 'app-processo-selectivo-matricula-update',
  templateUrl: './processo-selectivo-matricula-update.component.html',
})
export class ProcessoSelectivoMatriculaUpdateComponent implements OnInit {
  isSaving = false;
  processoSelectivoMatricula: IProcessoSelectivoMatricula | null = null;

  usersSharedCollection: IUser[] = [];
  turmasSharedCollection: ITurma[] = [];
  discentesSharedCollection: IDiscente[] = [];

  editForm: ProcessoSelectivoMatriculaFormGroup = this.processoSelectivoMatriculaFormService.createProcessoSelectivoMatriculaFormGroup();

  constructor(
    protected processoSelectivoMatriculaService: ProcessoSelectivoMatriculaService,
    protected processoSelectivoMatriculaFormService: ProcessoSelectivoMatriculaFormService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected discenteService: DiscenteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processoSelectivoMatricula }) => {
      this.processoSelectivoMatricula = processoSelectivoMatricula;
      if (processoSelectivoMatricula) {
        this.updateForm(processoSelectivoMatricula);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const processoSelectivoMatricula = this.processoSelectivoMatriculaFormService.getProcessoSelectivoMatricula(this.editForm);
    if (processoSelectivoMatricula.id !== null) {
      this.subscribeToSaveResponse(this.processoSelectivoMatriculaService.update(processoSelectivoMatricula));
    } else {
      this.subscribeToSaveResponse(this.processoSelectivoMatriculaService.create(processoSelectivoMatricula));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessoSelectivoMatricula>>): void {
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

  protected updateForm(processoSelectivoMatricula: IProcessoSelectivoMatricula): void {
    this.processoSelectivoMatricula = processoSelectivoMatricula;
    this.processoSelectivoMatriculaFormService.resetForm(this.editForm, processoSelectivoMatricula);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      processoSelectivoMatricula.utilizador
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(
      this.turmasSharedCollection,
      processoSelectivoMatricula.turma
    );
    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      processoSelectivoMatricula.discente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(
        map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.processoSelectivoMatricula?.utilizador))
      )
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(
        map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.processoSelectivoMatricula?.turma))
      )
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(discentes, this.processoSelectivoMatricula?.discente)
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));
  }
}
