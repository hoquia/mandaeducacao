import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LicaoFormService, LicaoFormGroup } from './licao-form.service';
import { ILicao } from '../licao.model';
import { LicaoService } from '../service/licao.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPlanoAula } from 'app/entities/plano-aula/plano-aula.model';
import { PlanoAulaService } from 'app/entities/plano-aula/service/plano-aula.service';
import { IHorario } from 'app/entities/horario/horario.model';
import { HorarioService } from 'app/entities/horario/service/horario.service';
import { EstadoLicao } from 'app/entities/enumerations/estado-licao.model';

@Component({
  selector: 'app-licao-update',
  templateUrl: './licao-update.component.html',
})
export class LicaoUpdateComponent implements OnInit {
  isSaving = false;
  licao: ILicao | null = null;
  estadoLicaoValues = Object.keys(EstadoLicao);

  usersSharedCollection: IUser[] = [];
  planoAulasSharedCollection: IPlanoAula[] = [];
  horariosSharedCollection: IHorario[] = [];

  editForm: LicaoFormGroup = this.licaoFormService.createLicaoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected licaoService: LicaoService,
    protected licaoFormService: LicaoFormService,
    protected userService: UserService,
    protected planoAulaService: PlanoAulaService,
    protected horarioService: HorarioService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  comparePlanoAula = (o1: IPlanoAula | null, o2: IPlanoAula | null): boolean => this.planoAulaService.comparePlanoAula(o1, o2);

  compareHorario = (o1: IHorario | null, o2: IHorario | null): boolean => this.horarioService.compareHorario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licao }) => {
      this.licao = licao;
      if (licao) {
        this.updateForm(licao);
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
    const licao = this.licaoFormService.getLicao(this.editForm);
    if (licao.id !== null) {
      this.subscribeToSaveResponse(this.licaoService.update(licao));
    } else {
      this.subscribeToSaveResponse(this.licaoService.create(licao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicao>>): void {
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

  protected updateForm(licao: ILicao): void {
    this.licao = licao;
    this.licaoFormService.resetForm(this.editForm, licao);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, licao.utilizador);
    this.planoAulasSharedCollection = this.planoAulaService.addPlanoAulaToCollectionIfMissing<IPlanoAula>(
      this.planoAulasSharedCollection,
      licao.planoAula
    );
    this.horariosSharedCollection = this.horarioService.addHorarioToCollectionIfMissing<IHorario>(
      this.horariosSharedCollection,
      licao.horario
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.licao?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.planoAulaService
      .query()
      .pipe(map((res: HttpResponse<IPlanoAula[]>) => res.body ?? []))
      .pipe(
        map((planoAulas: IPlanoAula[]) =>
          this.planoAulaService.addPlanoAulaToCollectionIfMissing<IPlanoAula>(planoAulas, this.licao?.planoAula)
        )
      )
      .subscribe((planoAulas: IPlanoAula[]) => (this.planoAulasSharedCollection = planoAulas));

    this.horarioService
      .query()
      .pipe(map((res: HttpResponse<IHorario[]>) => res.body ?? []))
      .pipe(map((horarios: IHorario[]) => this.horarioService.addHorarioToCollectionIfMissing<IHorario>(horarios, this.licao?.horario)))
      .subscribe((horarios: IHorario[]) => (this.horariosSharedCollection = horarios));
  }
}
