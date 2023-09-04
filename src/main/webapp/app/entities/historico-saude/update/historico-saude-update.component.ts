import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HistoricoSaudeFormService, HistoricoSaudeFormGroup } from './historico-saude-form.service';
import { IHistoricoSaude } from '../historico-saude.model';
import { HistoricoSaudeService } from '../service/historico-saude.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';

@Component({
  selector: 'app-historico-saude-update',
  templateUrl: './historico-saude-update.component.html',
})
export class HistoricoSaudeUpdateComponent implements OnInit {
  isSaving = false;
  historicoSaude: IHistoricoSaude | null = null;

  usersSharedCollection: IUser[] = [];
  discentesSharedCollection: IDiscente[] = [];

  editForm: HistoricoSaudeFormGroup = this.historicoSaudeFormService.createHistoricoSaudeFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected historicoSaudeService: HistoricoSaudeService,
    protected historicoSaudeFormService: HistoricoSaudeFormService,
    protected userService: UserService,
    protected discenteService: DiscenteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historicoSaude }) => {
      this.historicoSaude = historicoSaude;
      if (historicoSaude) {
        this.updateForm(historicoSaude);
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
    const historicoSaude = this.historicoSaudeFormService.getHistoricoSaude(this.editForm);
    if (historicoSaude.id !== null) {
      this.subscribeToSaveResponse(this.historicoSaudeService.update(historicoSaude));
    } else {
      this.subscribeToSaveResponse(this.historicoSaudeService.create(historicoSaude));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoricoSaude>>): void {
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

  protected updateForm(historicoSaude: IHistoricoSaude): void {
    this.historicoSaude = historicoSaude;
    this.historicoSaudeFormService.resetForm(this.editForm, historicoSaude);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      historicoSaude.utilizador
    );
    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      historicoSaude.discente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.historicoSaude?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(discentes, this.historicoSaude?.discente)
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));
  }
}
