import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HorarioFormService, HorarioFormGroup } from './horario-form.service';
import { IHorario } from '../horario.model';
import { HorarioService } from '../service/horario.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IPeriodoHorario } from 'app/entities/periodo-horario/periodo-horario.model';
import { PeriodoHorarioService } from 'app/entities/periodo-horario/service/periodo-horario.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';
import { DiaSemana } from 'app/entities/enumerations/dia-semana.model';

@Component({
  selector: 'app-horario-update',
  templateUrl: './horario-update.component.html',
})
export class HorarioUpdateComponent implements OnInit {
  isSaving = false;
  horario: IHorario | null = null;
  diaSemanaValues = Object.keys(DiaSemana);

  horariosSharedCollection: IHorario[] = [];
  usersSharedCollection: IUser[] = [];
  turmasSharedCollection: ITurma[] = [];
  periodoHorariosSharedCollection: IPeriodoHorario[] = [];
  docentesSharedCollection: IDocente[] = [];
  disciplinaCurricularsSharedCollection: IDisciplinaCurricular[] = [];

  editForm: HorarioFormGroup = this.horarioFormService.createHorarioFormGroup();

  constructor(
    protected horarioService: HorarioService,
    protected horarioFormService: HorarioFormService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected periodoHorarioService: PeriodoHorarioService,
    protected docenteService: DocenteService,
    protected disciplinaCurricularService: DisciplinaCurricularService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareHorario = (o1: IHorario | null, o2: IHorario | null): boolean => this.horarioService.compareHorario(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  comparePeriodoHorario = (o1: IPeriodoHorario | null, o2: IPeriodoHorario | null): boolean =>
    this.periodoHorarioService.comparePeriodoHorario(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareDisciplinaCurricular = (o1: IDisciplinaCurricular | null, o2: IDisciplinaCurricular | null): boolean =>
    this.disciplinaCurricularService.compareDisciplinaCurricular(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ horario }) => {
      this.horario = horario;
      if (horario) {
        this.updateForm(horario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const horario = this.horarioFormService.getHorario(this.editForm);
    if (horario.id !== null) {
      this.subscribeToSaveResponse(this.horarioService.update(horario));
    } else {
      this.subscribeToSaveResponse(this.horarioService.create(horario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHorario>>): void {
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

  protected updateForm(horario: IHorario): void {
    this.horario = horario;
    this.horarioFormService.resetForm(this.editForm, horario);

    this.horariosSharedCollection = this.horarioService.addHorarioToCollectionIfMissing<IHorario>(
      this.horariosSharedCollection,
      horario.referencia
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, horario.utilizador);
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(this.turmasSharedCollection, horario.turma);
    this.periodoHorariosSharedCollection = this.periodoHorarioService.addPeriodoHorarioToCollectionIfMissing<IPeriodoHorario>(
      this.periodoHorariosSharedCollection,
      horario.periodo
    );
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      horario.docente
    );
    this.disciplinaCurricularsSharedCollection =
      this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
        this.disciplinaCurricularsSharedCollection,
        horario.disciplinaCurricular
      );
  }

  protected loadRelationshipsOptions(): void {
    this.horarioService
      .query()
      .pipe(map((res: HttpResponse<IHorario[]>) => res.body ?? []))
      .pipe(
        map((horarios: IHorario[]) => this.horarioService.addHorarioToCollectionIfMissing<IHorario>(horarios, this.horario?.referencia))
      )
      .subscribe((horarios: IHorario[]) => (this.horariosSharedCollection = horarios));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.horario?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.horario?.turma)))
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.periodoHorarioService
      .query()
      .pipe(map((res: HttpResponse<IPeriodoHorario[]>) => res.body ?? []))
      .pipe(
        map((periodoHorarios: IPeriodoHorario[]) =>
          this.periodoHorarioService.addPeriodoHorarioToCollectionIfMissing<IPeriodoHorario>(periodoHorarios, this.horario?.periodo)
        )
      )
      .subscribe((periodoHorarios: IPeriodoHorario[]) => (this.periodoHorariosSharedCollection = periodoHorarios));

    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(map((docentes: IDocente[]) => this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.horario?.docente)))
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));

    this.disciplinaCurricularService
      .query()
      .pipe(map((res: HttpResponse<IDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((disciplinaCurriculars: IDisciplinaCurricular[]) =>
          this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
            disciplinaCurriculars,
            this.horario?.disciplinaCurricular
          )
        )
      )
      .subscribe((disciplinaCurriculars: IDisciplinaCurricular[]) => (this.disciplinaCurricularsSharedCollection = disciplinaCurriculars));
  }
}
