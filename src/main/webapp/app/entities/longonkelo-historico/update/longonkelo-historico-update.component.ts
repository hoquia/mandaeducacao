import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LongonkeloHistoricoFormService, LongonkeloHistoricoFormGroup } from './longonkelo-historico-form.service';
import { ILongonkeloHistorico } from '../longonkelo-historico.model';
import { LongonkeloHistoricoService } from '../service/longonkelo-historico.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'app-longonkelo-historico-update',
  templateUrl: './longonkelo-historico-update.component.html',
})
export class LongonkeloHistoricoUpdateComponent implements OnInit {
  isSaving = false;
  longonkeloHistorico: ILongonkeloHistorico | null = null;

  usersSharedCollection: IUser[] = [];

  editForm: LongonkeloHistoricoFormGroup = this.longonkeloHistoricoFormService.createLongonkeloHistoricoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected longonkeloHistoricoService: LongonkeloHistoricoService,
    protected longonkeloHistoricoFormService: LongonkeloHistoricoFormService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ longonkeloHistorico }) => {
      this.longonkeloHistorico = longonkeloHistorico;
      if (longonkeloHistorico) {
        this.updateForm(longonkeloHistorico);
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
    const longonkeloHistorico = this.longonkeloHistoricoFormService.getLongonkeloHistorico(this.editForm);
    if (longonkeloHistorico.id !== null) {
      this.subscribeToSaveResponse(this.longonkeloHistoricoService.update(longonkeloHistorico));
    } else {
      this.subscribeToSaveResponse(this.longonkeloHistoricoService.create(longonkeloHistorico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILongonkeloHistorico>>): void {
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

  protected updateForm(longonkeloHistorico: ILongonkeloHistorico): void {
    this.longonkeloHistorico = longonkeloHistorico;
    this.longonkeloHistoricoFormService.resetForm(this.editForm, longonkeloHistorico);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      longonkeloHistorico.utilizador
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.longonkeloHistorico?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
