import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResponsavelTurmaFormService, ResponsavelTurmaFormGroup } from './responsavel-turma-form.service';
import { IResponsavelTurma } from '../responsavel-turma.model';
import { ResponsavelTurmaService } from '../service/responsavel-turma.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';

@Component({
  selector: 'app-responsavel-turma-update',
  templateUrl: './responsavel-turma-update.component.html',
})
export class ResponsavelTurmaUpdateComponent implements OnInit {
  isSaving = false;
  responsavelTurma: IResponsavelTurma | null = null;

  usersSharedCollection: IUser[] = [];
  turmasSharedCollection: ITurma[] = [];

  editForm: ResponsavelTurmaFormGroup = this.responsavelTurmaFormService.createResponsavelTurmaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected responsavelTurmaService: ResponsavelTurmaService,
    protected responsavelTurmaFormService: ResponsavelTurmaFormService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelTurma }) => {
      this.responsavelTurma = responsavelTurma;
      if (responsavelTurma) {
        this.updateForm(responsavelTurma);
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
    const responsavelTurma = this.responsavelTurmaFormService.getResponsavelTurma(this.editForm);
    if (responsavelTurma.id !== null) {
      this.subscribeToSaveResponse(this.responsavelTurmaService.update(responsavelTurma));
    } else {
      this.subscribeToSaveResponse(this.responsavelTurmaService.create(responsavelTurma));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavelTurma>>): void {
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

  protected updateForm(responsavelTurma: IResponsavelTurma): void {
    this.responsavelTurma = responsavelTurma;
    this.responsavelTurmaFormService.resetForm(this.editForm, responsavelTurma);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      responsavelTurma.utilizador
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(
      this.turmasSharedCollection,
      responsavelTurma.turma
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.responsavelTurma?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.responsavelTurma?.turma)))
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));
  }
}
