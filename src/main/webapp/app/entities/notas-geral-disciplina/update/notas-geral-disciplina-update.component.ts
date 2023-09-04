import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NotasGeralDisciplinaFormService, NotasGeralDisciplinaFormGroup } from './notas-geral-disciplina-form.service';
import { INotasGeralDisciplina } from '../notas-geral-disciplina.model';
import { NotasGeralDisciplinaService } from '../service/notas-geral-disciplina.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from 'app/entities/estado-disciplina-curricular/service/estado-disciplina-curricular.service';

@Component({
  selector: 'app-notas-geral-disciplina-update',
  templateUrl: './notas-geral-disciplina-update.component.html',
})
export class NotasGeralDisciplinaUpdateComponent implements OnInit {
  isSaving = false;
  notasGeralDisciplina: INotasGeralDisciplina | null = null;

  usersSharedCollection: IUser[] = [];
  docentesSharedCollection: IDocente[] = [];
  disciplinaCurricularsSharedCollection: IDisciplinaCurricular[] = [];
  matriculasSharedCollection: IMatricula[] = [];
  estadoDisciplinaCurricularsSharedCollection: IEstadoDisciplinaCurricular[] = [];

  editForm: NotasGeralDisciplinaFormGroup = this.notasGeralDisciplinaFormService.createNotasGeralDisciplinaFormGroup();

  constructor(
    protected notasGeralDisciplinaService: NotasGeralDisciplinaService,
    protected notasGeralDisciplinaFormService: NotasGeralDisciplinaFormService,
    protected userService: UserService,
    protected docenteService: DocenteService,
    protected disciplinaCurricularService: DisciplinaCurricularService,
    protected matriculaService: MatriculaService,
    protected estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareDisciplinaCurricular = (o1: IDisciplinaCurricular | null, o2: IDisciplinaCurricular | null): boolean =>
    this.disciplinaCurricularService.compareDisciplinaCurricular(o1, o2);

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  compareEstadoDisciplinaCurricular = (o1: IEstadoDisciplinaCurricular | null, o2: IEstadoDisciplinaCurricular | null): boolean =>
    this.estadoDisciplinaCurricularService.compareEstadoDisciplinaCurricular(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notasGeralDisciplina }) => {
      this.notasGeralDisciplina = notasGeralDisciplina;
      if (notasGeralDisciplina) {
        this.updateForm(notasGeralDisciplina);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notasGeralDisciplina = this.notasGeralDisciplinaFormService.getNotasGeralDisciplina(this.editForm);
    if (notasGeralDisciplina.id !== null) {
      this.subscribeToSaveResponse(this.notasGeralDisciplinaService.update(notasGeralDisciplina));
    } else {
      this.subscribeToSaveResponse(this.notasGeralDisciplinaService.create(notasGeralDisciplina));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotasGeralDisciplina>>): void {
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

  protected updateForm(notasGeralDisciplina: INotasGeralDisciplina): void {
    this.notasGeralDisciplina = notasGeralDisciplina;
    this.notasGeralDisciplinaFormService.resetForm(this.editForm, notasGeralDisciplina);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      notasGeralDisciplina.utilizador
    );
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      notasGeralDisciplina.docente
    );
    this.disciplinaCurricularsSharedCollection =
      this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
        this.disciplinaCurricularsSharedCollection,
        notasGeralDisciplina.disciplinaCurricular
      );
    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      notasGeralDisciplina.matricula
    );
    this.estadoDisciplinaCurricularsSharedCollection =
      this.estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing<IEstadoDisciplinaCurricular>(
        this.estadoDisciplinaCurricularsSharedCollection,
        notasGeralDisciplina.estado
      );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.notasGeralDisciplina?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(
        map((docentes: IDocente[]) =>
          this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.notasGeralDisciplina?.docente)
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
            this.notasGeralDisciplina?.disciplinaCurricular
          )
        )
      )
      .subscribe((disciplinaCurriculars: IDisciplinaCurricular[]) => (this.disciplinaCurricularsSharedCollection = disciplinaCurriculars));

    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.notasGeralDisciplina?.matricula)
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
            this.notasGeralDisciplina?.estado
          )
        )
      )
      .subscribe(
        (estadoDisciplinaCurriculars: IEstadoDisciplinaCurricular[]) =>
          (this.estadoDisciplinaCurricularsSharedCollection = estadoDisciplinaCurriculars)
      );
  }
}
