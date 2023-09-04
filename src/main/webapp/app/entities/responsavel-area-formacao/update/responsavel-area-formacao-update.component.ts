import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResponsavelAreaFormacaoFormService, ResponsavelAreaFormacaoFormGroup } from './responsavel-area-formacao-form.service';
import { IResponsavelAreaFormacao } from '../responsavel-area-formacao.model';
import { ResponsavelAreaFormacaoService } from '../service/responsavel-area-formacao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/service/area-formacao.service';

@Component({
  selector: 'app-responsavel-area-formacao-update',
  templateUrl: './responsavel-area-formacao-update.component.html',
})
export class ResponsavelAreaFormacaoUpdateComponent implements OnInit {
  isSaving = false;
  responsavelAreaFormacao: IResponsavelAreaFormacao | null = null;

  usersSharedCollection: IUser[] = [];
  areaFormacaosSharedCollection: IAreaFormacao[] = [];

  editForm: ResponsavelAreaFormacaoFormGroup = this.responsavelAreaFormacaoFormService.createResponsavelAreaFormacaoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected responsavelAreaFormacaoService: ResponsavelAreaFormacaoService,
    protected responsavelAreaFormacaoFormService: ResponsavelAreaFormacaoFormService,
    protected userService: UserService,
    protected areaFormacaoService: AreaFormacaoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareAreaFormacao = (o1: IAreaFormacao | null, o2: IAreaFormacao | null): boolean =>
    this.areaFormacaoService.compareAreaFormacao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelAreaFormacao }) => {
      this.responsavelAreaFormacao = responsavelAreaFormacao;
      if (responsavelAreaFormacao) {
        this.updateForm(responsavelAreaFormacao);
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
    const responsavelAreaFormacao = this.responsavelAreaFormacaoFormService.getResponsavelAreaFormacao(this.editForm);
    if (responsavelAreaFormacao.id !== null) {
      this.subscribeToSaveResponse(this.responsavelAreaFormacaoService.update(responsavelAreaFormacao));
    } else {
      this.subscribeToSaveResponse(this.responsavelAreaFormacaoService.create(responsavelAreaFormacao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResponsavelAreaFormacao>>): void {
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

  protected updateForm(responsavelAreaFormacao: IResponsavelAreaFormacao): void {
    this.responsavelAreaFormacao = responsavelAreaFormacao;
    this.responsavelAreaFormacaoFormService.resetForm(this.editForm, responsavelAreaFormacao);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      responsavelAreaFormacao.utilizador
    );
    this.areaFormacaosSharedCollection = this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(
      this.areaFormacaosSharedCollection,
      responsavelAreaFormacao.areaFormacao
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.responsavelAreaFormacao?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.areaFormacaoService
      .query()
      .pipe(map((res: HttpResponse<IAreaFormacao[]>) => res.body ?? []))
      .pipe(
        map((areaFormacaos: IAreaFormacao[]) =>
          this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(
            areaFormacaos,
            this.responsavelAreaFormacao?.areaFormacao
          )
        )
      )
      .subscribe((areaFormacaos: IAreaFormacao[]) => (this.areaFormacaosSharedCollection = areaFormacaos));
  }
}
