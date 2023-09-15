import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlanoAulaFormService, PlanoAulaFormGroup } from './plano-aula-form.service';
import { IPlanoAula } from '../plano-aula.model';
import { PlanoAulaService } from '../service/plano-aula.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';
import { TipoAula } from 'app/entities/enumerations/tipo-aula.model';
import { EstadoLicao } from 'app/entities/enumerations/estado-licao.model';

@Component({
  selector: 'app-plano-aula-update',
  templateUrl: './plano-aula-update.component.html',
})
export class PlanoAulaUpdateComponent implements OnInit {
  isSaving = false;
  planoAula: IPlanoAula | null = null;
  tipoAulaValues = Object.keys(TipoAula);
  estadoLicaoValues = Object.keys(EstadoLicao);

  usersSharedCollection: IUser[] = [];
  lookupItemsSharedCollection: ILookupItem[] = [];
  turmasSharedCollection: ITurma[] = [];
  docentesSharedCollection: IDocente[] = [];
  disciplinaCurricularsSharedCollection: IDisciplinaCurricular[] = [];

  editForm: PlanoAulaFormGroup = this.planoAulaFormService.createPlanoAulaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected planoAulaService: PlanoAulaService,
    protected planoAulaFormService: PlanoAulaFormService,
    protected userService: UserService,
    protected lookupItemService: LookupItemService,
    protected turmaService: TurmaService,
    protected docenteService: DocenteService,
    protected disciplinaCurricularService: DisciplinaCurricularService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareDisciplinaCurricular = (o1: IDisciplinaCurricular | null, o2: IDisciplinaCurricular | null): boolean =>
    this.disciplinaCurricularService.compareDisciplinaCurricular(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAula }) => {
      this.planoAula = planoAula;
      if (planoAula) {
        this.updateForm(planoAula);
      } else {
        this.editForm.patchValue({
          estado: EstadoLicao.PENDENTE,
          tempoTotalLicao: 0,
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
    const planoAula = this.planoAulaFormService.getPlanoAula(this.editForm);
    if (planoAula.id !== null) {
      this.subscribeToSaveResponse(this.planoAulaService.update(planoAula));
    } else {
      this.subscribeToSaveResponse(this.planoAulaService.create(planoAula));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoAula>>): void {
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

  protected updateForm(planoAula: IPlanoAula): void {
    this.planoAula = planoAula;
    this.planoAulaFormService.resetForm(this.editForm, planoAula);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, planoAula.utilizador);
    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      planoAula.unidadeTematica,
      planoAula.subUnidadeTematica
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(this.turmasSharedCollection, planoAula.turma);
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      planoAula.docente
    );
    this.disciplinaCurricularsSharedCollection =
      this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
        this.disciplinaCurricularsSharedCollection,
        planoAula.disciplinaCurricular
      );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.planoAula?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
            lookupItems,
            this.planoAula?.unidadeTematica,
            this.planoAula?.subUnidadeTematica
          )
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.turmaService
      .query({ size: 10000 })
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.planoAula?.turma)))
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.docenteService
      .query({ size: 10000 })
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(map((docentes: IDocente[]) => this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.planoAula?.docente)))
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));

    this.disciplinaCurricularService
      .query({ size: 10000 })
      .pipe(map((res: HttpResponse<IDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((disciplinaCurriculars: IDisciplinaCurricular[]) =>
          this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
            disciplinaCurriculars,
            this.planoAula?.disciplinaCurricular
          )
        )
      )
      .subscribe((disciplinaCurriculars: IDisciplinaCurricular[]) => (this.disciplinaCurricularsSharedCollection = disciplinaCurriculars));
  }
}
