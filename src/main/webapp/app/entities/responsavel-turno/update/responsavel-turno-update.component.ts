import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResponsavelTurnoFormService, ResponsavelTurnoFormGroup } from './responsavel-turno-form.service';
import { IResponsavelTurno } from '../responsavel-turno.model';
import { ResponsavelTurnoService } from '../service/responsavel-turno.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';

@Component({
  selector: 'app-responsavel-turno-update',
  templateUrl: './responsavel-turno-update.component.html',
})
export class ResponsavelTurnoUpdateComponent implements OnInit {
  isSaving = false;
  responsavelTurno: IResponsavelTurno | null = null;

  usersSharedCollection: IUser[] = [];
  turnosSharedCollection: ITurno[] = [];

  editForm: ResponsavelTurnoFormGroup = this.responsavelTurnoFormService.createResponsavelTurnoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected responsavelTurnoService: ResponsavelTurnoService,
    protected responsavelTurnoFormService: ResponsavelTurnoFormService,
    protected userService: UserService,
    protected turnoService: TurnoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareTurno = (o1: ITurno | null, o2: ITurno | null): boolean => this.turnoService.compareTurno(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelTurno }) => {
      this.responsavelTurno = responsavelTurno;
      if (responsavelTurno) {
        this.updateForm(responsavelTurno);
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
    const responsavelTurno = this.responsavelTurnoFormService.getResponsavelTurno(this.editForm);
    if (responsavelTurno.id !== null) {
      this.subscribeToSaveResponse(this.responsavelTurnoService.update(responsavelTurno));
    } else {
      this.subscribeToSaveResponse(this.responsavelTurnoService.create(responsavelTurno));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavelTurno>>): void {
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

  protected updateForm(responsavelTurno: IResponsavelTurno): void {
    this.responsavelTurno = responsavelTurno;
    this.responsavelTurnoFormService.resetForm(this.editForm, responsavelTurno);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      responsavelTurno.utilizador
    );
    this.turnosSharedCollection = this.turnoService.addTurnoToCollectionIfMissing<ITurno>(
      this.turnosSharedCollection,
      responsavelTurno.turno
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.responsavelTurno?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.turnoService
      .query()
      .pipe(map((res: HttpResponse<ITurno[]>) => res.body ?? []))
      .pipe(map((turnos: ITurno[]) => this.turnoService.addTurnoToCollectionIfMissing<ITurno>(turnos, this.responsavelTurno?.turno)))
      .subscribe((turnos: ITurno[]) => (this.turnosSharedCollection = turnos));
  }
}
