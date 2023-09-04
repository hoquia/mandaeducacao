import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResponsavelCursoFormService, ResponsavelCursoFormGroup } from './responsavel-curso-form.service';
import { IResponsavelCurso } from '../responsavel-curso.model';
import { ResponsavelCursoService } from '../service/responsavel-curso.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';

@Component({
  selector: 'app-responsavel-curso-update',
  templateUrl: './responsavel-curso-update.component.html',
})
export class ResponsavelCursoUpdateComponent implements OnInit {
  isSaving = false;
  responsavelCurso: IResponsavelCurso | null = null;

  usersSharedCollection: IUser[] = [];
  cursosSharedCollection: ICurso[] = [];

  editForm: ResponsavelCursoFormGroup = this.responsavelCursoFormService.createResponsavelCursoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected responsavelCursoService: ResponsavelCursoService,
    protected responsavelCursoFormService: ResponsavelCursoFormService,
    protected userService: UserService,
    protected cursoService: CursoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareCurso = (o1: ICurso | null, o2: ICurso | null): boolean => this.cursoService.compareCurso(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelCurso }) => {
      this.responsavelCurso = responsavelCurso;
      if (responsavelCurso) {
        this.updateForm(responsavelCurso);
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
    const responsavelCurso = this.responsavelCursoFormService.getResponsavelCurso(this.editForm);
    if (responsavelCurso.id !== null) {
      this.subscribeToSaveResponse(this.responsavelCursoService.update(responsavelCurso));
    } else {
      this.subscribeToSaveResponse(this.responsavelCursoService.create(responsavelCurso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavelCurso>>): void {
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

  protected updateForm(responsavelCurso: IResponsavelCurso): void {
    this.responsavelCurso = responsavelCurso;
    this.responsavelCursoFormService.resetForm(this.editForm, responsavelCurso);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      responsavelCurso.utilizador
    );
    this.cursosSharedCollection = this.cursoService.addCursoToCollectionIfMissing<ICurso>(
      this.cursosSharedCollection,
      responsavelCurso.curso
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.responsavelCurso?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.cursoService
      .query()
      .pipe(map((res: HttpResponse<ICurso[]>) => res.body ?? []))
      .pipe(map((cursos: ICurso[]) => this.cursoService.addCursoToCollectionIfMissing<ICurso>(cursos, this.responsavelCurso?.curso)))
      .subscribe((cursos: ICurso[]) => (this.cursosSharedCollection = cursos));
  }
}
