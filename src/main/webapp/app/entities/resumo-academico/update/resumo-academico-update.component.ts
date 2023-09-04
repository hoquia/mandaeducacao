import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResumoAcademicoFormService, ResumoAcademicoFormGroup } from './resumo-academico-form.service';
import { IResumoAcademico } from '../resumo-academico.model';
import { ResumoAcademicoService } from '../service/resumo-academico.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { IEstadoDisciplinaCurricular } from 'app/entities/estado-disciplina-curricular/estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from 'app/entities/estado-disciplina-curricular/service/estado-disciplina-curricular.service';

@Component({
  selector: 'app-resumo-academico-update',
  templateUrl: './resumo-academico-update.component.html',
})
export class ResumoAcademicoUpdateComponent implements OnInit {
  isSaving = false;
  resumoAcademico: IResumoAcademico | null = null;

  usersSharedCollection: IUser[] = [];
  turmasSharedCollection: ITurma[] = [];
  discentesSharedCollection: IDiscente[] = [];
  estadoDisciplinaCurricularsSharedCollection: IEstadoDisciplinaCurricular[] = [];

  editForm: ResumoAcademicoFormGroup = this.resumoAcademicoFormService.createResumoAcademicoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected resumoAcademicoService: ResumoAcademicoService,
    protected resumoAcademicoFormService: ResumoAcademicoFormService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected discenteService: DiscenteService,
    protected estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  compareEstadoDisciplinaCurricular = (o1: IEstadoDisciplinaCurricular | null, o2: IEstadoDisciplinaCurricular | null): boolean =>
    this.estadoDisciplinaCurricularService.compareEstadoDisciplinaCurricular(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumoAcademico }) => {
      this.resumoAcademico = resumoAcademico;
      if (resumoAcademico) {
        this.updateForm(resumoAcademico);
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
    const resumoAcademico = this.resumoAcademicoFormService.getResumoAcademico(this.editForm);
    if (resumoAcademico.id !== null) {
      this.subscribeToSaveResponse(this.resumoAcademicoService.update(resumoAcademico));
    } else {
      this.subscribeToSaveResponse(this.resumoAcademicoService.create(resumoAcademico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumoAcademico>>): void {
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

  protected updateForm(resumoAcademico: IResumoAcademico): void {
    this.resumoAcademico = resumoAcademico;
    this.resumoAcademicoFormService.resetForm(this.editForm, resumoAcademico);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      resumoAcademico.utilizador
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(
      this.turmasSharedCollection,
      resumoAcademico.ultimaTurmaMatriculada
    );
    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      resumoAcademico.discente
    );
    this.estadoDisciplinaCurricularsSharedCollection =
      this.estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing<IEstadoDisciplinaCurricular>(
        this.estadoDisciplinaCurricularsSharedCollection,
        resumoAcademico.situacao
      );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.resumoAcademico?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(
        map((turmas: ITurma[]) =>
          this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.resumoAcademico?.ultimaTurmaMatriculada)
        )
      )
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(discentes, this.resumoAcademico?.discente)
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));

    this.estadoDisciplinaCurricularService
      .query()
      .pipe(map((res: HttpResponse<IEstadoDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((estadoDisciplinaCurriculars: IEstadoDisciplinaCurricular[]) =>
          this.estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing<IEstadoDisciplinaCurricular>(
            estadoDisciplinaCurriculars,
            this.resumoAcademico?.situacao
          )
        )
      )
      .subscribe(
        (estadoDisciplinaCurriculars: IEstadoDisciplinaCurricular[]) =>
          (this.estadoDisciplinaCurricularsSharedCollection = estadoDisciplinaCurriculars)
      );
  }
}
