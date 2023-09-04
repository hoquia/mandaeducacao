import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NotasPeriodicaDisciplinaFormService, NotasPeriodicaDisciplinaFormGroup } from './notas-periodica-disciplina-form.service';
import { INotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';
import { NotasPeriodicaDisciplinaService } from '../service/notas-periodica-disciplina.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from 'app/entities/estado-disciplina-curricular/service/estado-disciplina-curricular.service';
import { Comporamento } from 'app/entities/enumerations/comporamento.model';

@Component({
  selector: 'app-notas-periodica-disciplina-update',
  templateUrl: './notas-periodica-disciplina-update.component.html',
})
export class NotasPeriodicaDisciplinaUpdateComponent implements OnInit {
  isSaving = false;
  notasPeriodicaDisciplina: INotasPeriodicaDisciplina | null = null;
  comporamentoValues = Object.keys(Comporamento);

  usersSharedCollection: IUser[] = [];
  turmasSharedCollection: ITurma[] = [];
  docentesSharedCollection: IDocente[] = [];
  disciplinaCurricularsSharedCollection: IDisciplinaCurricular[] = [];
  matriculasSharedCollection: IMatricula[] = [];
  estadoDisciplinaCurricularsSharedCollection: IEstadoDisciplinaCurricular[] = [];

  editForm: NotasPeriodicaDisciplinaFormGroup = this.notasPeriodicaDisciplinaFormService.createNotasPeriodicaDisciplinaFormGroup();

  constructor(
    protected notasPeriodicaDisciplinaService: NotasPeriodicaDisciplinaService,
    protected notasPeriodicaDisciplinaFormService: NotasPeriodicaDisciplinaFormService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected docenteService: DocenteService,
    protected disciplinaCurricularService: DisciplinaCurricularService,
    protected matriculaService: MatriculaService,
    protected estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareDisciplinaCurricular = (o1: IDisciplinaCurricular | null, o2: IDisciplinaCurricular | null): boolean =>
    this.disciplinaCurricularService.compareDisciplinaCurricular(o1, o2);

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  compareEstadoDisciplinaCurricular = (o1: IEstadoDisciplinaCurricular | null, o2: IEstadoDisciplinaCurricular | null): boolean =>
    this.estadoDisciplinaCurricularService.compareEstadoDisciplinaCurricular(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notasPeriodicaDisciplina }) => {
      this.notasPeriodicaDisciplina = notasPeriodicaDisciplina;
      if (notasPeriodicaDisciplina) {
        this.updateForm(notasPeriodicaDisciplina);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notasPeriodicaDisciplina = this.notasPeriodicaDisciplinaFormService.getNotasPeriodicaDisciplina(this.editForm);
    if (notasPeriodicaDisciplina.id !== null) {
      this.subscribeToSaveResponse(this.notasPeriodicaDisciplinaService.update(notasPeriodicaDisciplina));
    } else {
      this.subscribeToSaveResponse(this.notasPeriodicaDisciplinaService.create(notasPeriodicaDisciplina));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotasPeriodicaDisciplina>>): void {
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

  protected updateForm(notasPeriodicaDisciplina: INotasPeriodicaDisciplina): void {
    this.notasPeriodicaDisciplina = notasPeriodicaDisciplina;
    this.notasPeriodicaDisciplinaFormService.resetForm(this.editForm, notasPeriodicaDisciplina);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      notasPeriodicaDisciplina.utilizador
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(
      this.turmasSharedCollection,
      notasPeriodicaDisciplina.turma
    );
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      notasPeriodicaDisciplina.docente
    );
    this.disciplinaCurricularsSharedCollection =
      this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
        this.disciplinaCurricularsSharedCollection,
        notasPeriodicaDisciplina.disciplinaCurricular
      );
    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      notasPeriodicaDisciplina.matricula
    );
    this.estadoDisciplinaCurricularsSharedCollection =
      this.estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing<IEstadoDisciplinaCurricular>(
        this.estadoDisciplinaCurricularsSharedCollection,
        notasPeriodicaDisciplina.estado
      );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.notasPeriodicaDisciplina?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(
        map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.notasPeriodicaDisciplina?.turma))
      )
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(
        map((docentes: IDocente[]) =>
          this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.notasPeriodicaDisciplina?.docente)
        )
      )
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));

    this.disciplinaCurricularService
      .query()
      .pipe(map((res: HttpResponse<IDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((disciplinaCurriculars: IDisciplinaCurricular[]) =>
          this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
            disciplinaCurriculars,
            this.notasPeriodicaDisciplina?.disciplinaCurricular
          )
        )
      )
      .subscribe((disciplinaCurriculars: IDisciplinaCurricular[]) => (this.disciplinaCurricularsSharedCollection = disciplinaCurriculars));

    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.notasPeriodicaDisciplina?.matricula)
        )
      )
      .subscribe((matriculas: IMatricula[]) => (this.matriculasSharedCollection = matriculas));

    this.estadoDisciplinaCurricularService
      .query()
      .pipe(map((res: HttpResponse<IEstadoDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((estadoDisciplinaCurriculars: IEstadoDisciplinaCurricular[]) =>
          this.estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing<IEstadoDisciplinaCurricular>(
            estadoDisciplinaCurriculars,
            this.notasPeriodicaDisciplina?.estado
          )
        )
      )
      .subscribe(
        (estadoDisciplinaCurriculars: IEstadoDisciplinaCurricular[]) =>
          (this.estadoDisciplinaCurricularsSharedCollection = estadoDisciplinaCurriculars)
      );
  }
}
