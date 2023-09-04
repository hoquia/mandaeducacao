import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OcorrenciaFormService, OcorrenciaFormGroup } from './ocorrencia-form.service';
import { IOcorrencia } from '../ocorrencia.model';
import { OcorrenciaService } from '../service/ocorrencia.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';
import { ICategoriaOcorrencia } from 'app/entities/categoria-ocorrencia/categoria-ocorrencia.model';
import { CategoriaOcorrenciaService } from 'app/entities/categoria-ocorrencia/service/categoria-ocorrencia.service';
import { ILicao } from 'app/entities/licao/licao.model';
import { LicaoService } from 'app/entities/licao/service/licao.service';

@Component({
  selector: 'app-ocorrencia-update',
  templateUrl: './ocorrencia-update.component.html',
})
export class OcorrenciaUpdateComponent implements OnInit {
  isSaving = false;
  ocorrencia: IOcorrencia | null = null;

  ocorrenciasSharedCollection: IOcorrencia[] = [];
  usersSharedCollection: IUser[] = [];
  docentesSharedCollection: IDocente[] = [];
  matriculasSharedCollection: IMatricula[] = [];
  categoriaOcorrenciasSharedCollection: ICategoriaOcorrencia[] = [];
  licaosSharedCollection: ILicao[] = [];

  editForm: OcorrenciaFormGroup = this.ocorrenciaFormService.createOcorrenciaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected ocorrenciaService: OcorrenciaService,
    protected ocorrenciaFormService: OcorrenciaFormService,
    protected userService: UserService,
    protected docenteService: DocenteService,
    protected matriculaService: MatriculaService,
    protected categoriaOcorrenciaService: CategoriaOcorrenciaService,
    protected licaoService: LicaoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOcorrencia = (o1: IOcorrencia | null, o2: IOcorrencia | null): boolean => this.ocorrenciaService.compareOcorrencia(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  compareCategoriaOcorrencia = (o1: ICategoriaOcorrencia | null, o2: ICategoriaOcorrencia | null): boolean =>
    this.categoriaOcorrenciaService.compareCategoriaOcorrencia(o1, o2);

  compareLicao = (o1: ILicao | null, o2: ILicao | null): boolean => this.licaoService.compareLicao(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ocorrencia }) => {
      this.ocorrencia = ocorrencia;
      if (ocorrencia) {
        this.updateForm(ocorrencia);
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
    const ocorrencia = this.ocorrenciaFormService.getOcorrencia(this.editForm);
    if (ocorrencia.id !== null) {
      this.subscribeToSaveResponse(this.ocorrenciaService.update(ocorrencia));
    } else {
      this.subscribeToSaveResponse(this.ocorrenciaService.create(ocorrencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOcorrencia>>): void {
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

  protected updateForm(ocorrencia: IOcorrencia): void {
    this.ocorrencia = ocorrencia;
    this.ocorrenciaFormService.resetForm(this.editForm, ocorrencia);

    this.ocorrenciasSharedCollection = this.ocorrenciaService.addOcorrenciaToCollectionIfMissing<IOcorrencia>(
      this.ocorrenciasSharedCollection,
      ocorrencia.referencia
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, ocorrencia.utilizador);
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      ocorrencia.docente
    );
    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      ocorrencia.matricula
    );
    this.categoriaOcorrenciasSharedCollection =
      this.categoriaOcorrenciaService.addCategoriaOcorrenciaToCollectionIfMissing<ICategoriaOcorrencia>(
        this.categoriaOcorrenciasSharedCollection,
        ocorrencia.estado
      );
    this.licaosSharedCollection = this.licaoService.addLicaoToCollectionIfMissing<ILicao>(this.licaosSharedCollection, ocorrencia.licao);
  }

  protected loadRelationshipsOptions(): void {
    this.ocorrenciaService
      .query()
      .pipe(map((res: HttpResponse<IOcorrencia[]>) => res.body ?? []))
      .pipe(
        map((ocorrencias: IOcorrencia[]) =>
          this.ocorrenciaService.addOcorrenciaToCollectionIfMissing<IOcorrencia>(ocorrencias, this.ocorrencia?.referencia)
        )
      )
      .subscribe((ocorrencias: IOcorrencia[]) => (this.ocorrenciasSharedCollection = ocorrencias));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.ocorrencia?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(
        map((docentes: IDocente[]) => this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.ocorrencia?.docente))
      )
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));

    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.ocorrencia?.matricula)
        )
      )
      .subscribe((matriculas: IMatricula[]) => (this.matriculasSharedCollection = matriculas));

    this.categoriaOcorrenciaService
      .query()
      .pipe(map((res: HttpResponse<ICategoriaOcorrencia[]>) => res.body ?? []))
      .pipe(
        map((categoriaOcorrencias: ICategoriaOcorrencia[]) =>
          this.categoriaOcorrenciaService.addCategoriaOcorrenciaToCollectionIfMissing<ICategoriaOcorrencia>(
            categoriaOcorrencias,
            this.ocorrencia?.estado
          )
        )
      )
      .subscribe((categoriaOcorrencias: ICategoriaOcorrencia[]) => (this.categoriaOcorrenciasSharedCollection = categoriaOcorrencias));

    this.licaoService
      .query()
      .pipe(map((res: HttpResponse<ILicao[]>) => res.body ?? []))
      .pipe(map((licaos: ILicao[]) => this.licaoService.addLicaoToCollectionIfMissing<ILicao>(licaos, this.ocorrencia?.licao)))
      .subscribe((licaos: ILicao[]) => (this.licaosSharedCollection = licaos));
  }
}
