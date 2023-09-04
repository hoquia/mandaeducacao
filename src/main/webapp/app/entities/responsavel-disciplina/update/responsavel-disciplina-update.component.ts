import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResponsavelDisciplinaFormService, ResponsavelDisciplinaFormGroup } from './responsavel-disciplina-form.service';
import { IResponsavelDisciplina } from '../responsavel-disciplina.model';
import { ResponsavelDisciplinaService } from '../service/responsavel-disciplina.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/service/disciplina.service';

@Component({
  selector: 'app-responsavel-disciplina-update',
  templateUrl: './responsavel-disciplina-update.component.html',
})
export class ResponsavelDisciplinaUpdateComponent implements OnInit {
  isSaving = false;
  responsavelDisciplina: IResponsavelDisciplina | null = null;

  usersSharedCollection: IUser[] = [];
  disciplinasSharedCollection: IDisciplina[] = [];

  editForm: ResponsavelDisciplinaFormGroup = this.responsavelDisciplinaFormService.createResponsavelDisciplinaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected responsavelDisciplinaService: ResponsavelDisciplinaService,
    protected responsavelDisciplinaFormService: ResponsavelDisciplinaFormService,
    protected userService: UserService,
    protected disciplinaService: DisciplinaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareDisciplina = (o1: IDisciplina | null, o2: IDisciplina | null): boolean => this.disciplinaService.compareDisciplina(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelDisciplina }) => {
      this.responsavelDisciplina = responsavelDisciplina;
      if (responsavelDisciplina) {
        this.updateForm(responsavelDisciplina);
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
    const responsavelDisciplina = this.responsavelDisciplinaFormService.getResponsavelDisciplina(this.editForm);
    if (responsavelDisciplina.id !== null) {
      this.subscribeToSaveResponse(this.responsavelDisciplinaService.update(responsavelDisciplina));
    } else {
      this.subscribeToSaveResponse(this.responsavelDisciplinaService.create(responsavelDisciplina));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavelDisciplina>>): void {
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

  protected updateForm(responsavelDisciplina: IResponsavelDisciplina): void {
    this.responsavelDisciplina = responsavelDisciplina;
    this.responsavelDisciplinaFormService.resetForm(this.editForm, responsavelDisciplina);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      responsavelDisciplina.utilizador
    );
    this.disciplinasSharedCollection = this.disciplinaService.addDisciplinaToCollectionIfMissing<IDisciplina>(
      this.disciplinasSharedCollection,
      responsavelDisciplina.disciplina
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.responsavelDisciplina?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.disciplinaService
      .query()
      .pipe(map((res: HttpResponse<IDisciplina[]>) => res.body ?? []))
      .pipe(
        map((disciplinas: IDisciplina[]) =>
          this.disciplinaService.addDisciplinaToCollectionIfMissing<IDisciplina>(disciplinas, this.responsavelDisciplina?.disciplina)
        )
      )
      .subscribe((disciplinas: IDisciplina[]) => (this.disciplinasSharedCollection = disciplinas));
  }
}
