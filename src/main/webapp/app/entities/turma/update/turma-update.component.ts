/* eslint-disable @typescript-eslint/member-ordering */
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TurmaFormService, TurmaFormGroup } from './turma-form.service';
import { ITurma } from '../turma.model';
import { TurmaService } from '../service/turma.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPlanoCurricular } from 'app/entities/plano-curricular/plano-curricular.model';
import { PlanoCurricularService } from 'app/entities/plano-curricular/service/plano-curricular.service';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';
import { TipoTurma } from 'app/entities/enumerations/tipo-turma.model';
import { CriterioDescricaoTurma } from 'app/entities/enumerations/criterio-descricao-turma.model';
import { CriterioNumeroChamada } from 'app/entities/enumerations/criterio-numero-chamada.model';

@Component({
  selector: 'app-turma-update',
  templateUrl: './turma-update.component.html',
})
export class TurmaUpdateComponent implements OnInit {
  isSaving = false;
  turma: ITurma | null = null;
  tipoTurmaValues = Object.keys(TipoTurma);
  criterioDescricaoTurmaValues = Object.keys(CriterioDescricaoTurma);
  criterioNumeroChamadaValues = Object.keys(CriterioNumeroChamada);

  turmasSharedCollection: ITurma[] = [];
  usersSharedCollection: IUser[] = [];
  planoCurricularsSharedCollection: IPlanoCurricular[] = [];
  turnosSharedCollection: ITurno[] = [];

  editForm: TurmaFormGroup = this.turmaFormService.createTurmaFormGroup();

  constructor(
    protected turmaService: TurmaService,
    protected turmaFormService: TurmaFormService,
    protected userService: UserService,
    protected planoCurricularService: PlanoCurricularService,
    protected turnoService: TurnoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  comparePlanoCurricular = (o1: IPlanoCurricular | null, o2: IPlanoCurricular | null): boolean =>
    this.planoCurricularService.comparePlanoCurricular(o1, o2);

  compareTurno = (o1: ITurno | null, o2: ITurno | null): boolean => this.turnoService.compareTurno(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ turma }) => {
      this.turma = turma;
      if (turma) {
        this.updateForm(turma);
      } else {
        this.editForm.patchValue({
          tipoTurma: TipoTurma.AULA,
          descricao: 'NA',
          lotacao: 100,
          confirmado: 0,
          criterioDescricao: CriterioDescricaoTurma.CURSO,
          criterioOrdenacaoNumero: CriterioNumeroChamada.ALFABETICA,
          isDisponivel: true,
        });
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const turma = this.turmaFormService.getTurma(this.editForm);
    if (turma.id !== null) {
      this.subscribeToSaveResponse(this.turmaService.update(turma));
    } else {
      this.subscribeToSaveResponse(this.turmaService.create(turma));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITurma>>): void {
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

  protected updateForm(turma: ITurma): void {
    this.turma = turma;
    this.turmaFormService.resetForm(this.editForm, turma);

    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(this.turmasSharedCollection, turma.referencia);
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, turma.utilizador);
    this.planoCurricularsSharedCollection = this.planoCurricularService.addPlanoCurricularToCollectionIfMissing<IPlanoCurricular>(
      this.planoCurricularsSharedCollection,
      turma.planoCurricular
    );
    this.turnosSharedCollection = this.turnoService.addTurnoToCollectionIfMissing<ITurno>(this.turnosSharedCollection, turma.turno);
  }

  protected loadRelationshipsOptions(): void {
    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.turma?.referencia)))
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.turma?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    // TODO: Remover
    this.planoCurricularService
      .query({ size: 1000 })
      .pipe(map((res: HttpResponse<IPlanoCurricular[]>) => res.body ?? []))
      .pipe(
        map((planoCurriculars: IPlanoCurricular[]) =>
          this.planoCurricularService.addPlanoCurricularToCollectionIfMissing<IPlanoCurricular>(
            planoCurriculars,
            this.turma?.planoCurricular
          )
        )
      )
      .subscribe((planoCurriculars: IPlanoCurricular[]) => (this.planoCurricularsSharedCollection = planoCurriculars));

    this.turnoService
      .query()
      .pipe(map((res: HttpResponse<ITurno[]>) => res.body ?? []))
      .pipe(map((turnos: ITurno[]) => this.turnoService.addTurnoToCollectionIfMissing<ITurno>(turnos, this.turma?.turno)))
      .subscribe((turnos: ITurno[]) => (this.turnosSharedCollection = turnos));
  }

  onSelectReferencia(id: any): void {
    this.turmaService.find(id).subscribe(res => {
      this.turma = res.body;
      this.editForm.patchValue({
        tipoTurma: this.turma?.tipoTurma,
        descricao: 'NA',
        lotacao: this.turma?.lotacao,
        confirmado: 0,
        criterioDescricao: this.turma?.criterioDescricao,
        criterioOrdenacaoNumero: this.turma?.criterioOrdenacaoNumero,
        isDisponivel: true,
        abertura: this.turma?.abertura,
        encerramento: this.turma?.encerramento,
        planoCurricular: this.turma?.planoCurricular,
        fazInscricaoDepoisMatricula: this.turma?.fazInscricaoDepoisMatricula,
        sala: this.turma?.sala,
        turno: this.turma?.turno,
      });
    });
  }
}
