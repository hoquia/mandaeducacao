import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DissertacaoFinalCursoFormService, DissertacaoFinalCursoFormGroup } from './dissertacao-final-curso-form.service';
import { IDissertacaoFinalCurso } from '../dissertacao-final-curso.model';
import { DissertacaoFinalCursoService } from '../service/dissertacao-final-curso.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IDocente } from 'app/entities/docente/docente.model';
import { DocenteService } from 'app/entities/docente/service/docente.service';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/service/area-formacao.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { IEstadoDissertacao } from 'app/entities/estado-dissertacao/estado-dissertacao.model';
import { EstadoDissertacaoService } from 'app/entities/estado-dissertacao/service/estado-dissertacao.service';
import { INaturezaTrabalho } from 'app/entities/natureza-trabalho/natureza-trabalho.model';
import { NaturezaTrabalhoService } from 'app/entities/natureza-trabalho/service/natureza-trabalho.service';

@Component({
  selector: 'app-dissertacao-final-curso-update',
  templateUrl: './dissertacao-final-curso-update.component.html',
})
export class DissertacaoFinalCursoUpdateComponent implements OnInit {
  isSaving = false;
  dissertacaoFinalCurso: IDissertacaoFinalCurso | null = null;

  usersSharedCollection: IUser[] = [];
  turmasSharedCollection: ITurma[] = [];
  docentesSharedCollection: IDocente[] = [];
  areaFormacaosSharedCollection: IAreaFormacao[] = [];
  discentesSharedCollection: IDiscente[] = [];
  estadoDissertacaosSharedCollection: IEstadoDissertacao[] = [];
  naturezaTrabalhosSharedCollection: INaturezaTrabalho[] = [];

  editForm: DissertacaoFinalCursoFormGroup = this.dissertacaoFinalCursoFormService.createDissertacaoFinalCursoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected dissertacaoFinalCursoService: DissertacaoFinalCursoService,
    protected dissertacaoFinalCursoFormService: DissertacaoFinalCursoFormService,
    protected userService: UserService,
    protected turmaService: TurmaService,
    protected docenteService: DocenteService,
    protected areaFormacaoService: AreaFormacaoService,
    protected discenteService: DiscenteService,
    protected estadoDissertacaoService: EstadoDissertacaoService,
    protected naturezaTrabalhoService: NaturezaTrabalhoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareDocente = (o1: IDocente | null, o2: IDocente | null): boolean => this.docenteService.compareDocente(o1, o2);

  compareAreaFormacao = (o1: IAreaFormacao | null, o2: IAreaFormacao | null): boolean =>
    this.areaFormacaoService.compareAreaFormacao(o1, o2);

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  compareEstadoDissertacao = (o1: IEstadoDissertacao | null, o2: IEstadoDissertacao | null): boolean =>
    this.estadoDissertacaoService.compareEstadoDissertacao(o1, o2);

  compareNaturezaTrabalho = (o1: INaturezaTrabalho | null, o2: INaturezaTrabalho | null): boolean =>
    this.naturezaTrabalhoService.compareNaturezaTrabalho(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dissertacaoFinalCurso }) => {
      this.dissertacaoFinalCurso = dissertacaoFinalCurso;
      if (dissertacaoFinalCurso) {
        this.updateForm(dissertacaoFinalCurso);
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
    const dissertacaoFinalCurso = this.dissertacaoFinalCursoFormService.getDissertacaoFinalCurso(this.editForm);
    if (dissertacaoFinalCurso.id !== null) {
      this.subscribeToSaveResponse(this.dissertacaoFinalCursoService.update(dissertacaoFinalCurso));
    } else {
      this.subscribeToSaveResponse(this.dissertacaoFinalCursoService.create(dissertacaoFinalCurso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDissertacaoFinalCurso>>): void {
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

  protected updateForm(dissertacaoFinalCurso: IDissertacaoFinalCurso): void {
    this.dissertacaoFinalCurso = dissertacaoFinalCurso;
    this.dissertacaoFinalCursoFormService.resetForm(this.editForm, dissertacaoFinalCurso);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      dissertacaoFinalCurso.utilizador
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(
      this.turmasSharedCollection,
      dissertacaoFinalCurso.turma
    );
    this.docentesSharedCollection = this.docenteService.addDocenteToCollectionIfMissing<IDocente>(
      this.docentesSharedCollection,
      dissertacaoFinalCurso.orientador
    );
    this.areaFormacaosSharedCollection = this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(
      this.areaFormacaosSharedCollection,
      dissertacaoFinalCurso.especialidade
    );
    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      dissertacaoFinalCurso.discente
    );
    this.estadoDissertacaosSharedCollection = this.estadoDissertacaoService.addEstadoDissertacaoToCollectionIfMissing<IEstadoDissertacao>(
      this.estadoDissertacaosSharedCollection,
      dissertacaoFinalCurso.estado
    );
    this.naturezaTrabalhosSharedCollection = this.naturezaTrabalhoService.addNaturezaTrabalhoToCollectionIfMissing<INaturezaTrabalho>(
      this.naturezaTrabalhosSharedCollection,
      dissertacaoFinalCurso.natureza
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.dissertacaoFinalCurso?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.dissertacaoFinalCurso?.turma)))
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.docenteService
      .query()
      .pipe(map((res: HttpResponse<IDocente[]>) => res.body ?? []))
      .pipe(
        map((docentes: IDocente[]) =>
          this.docenteService.addDocenteToCollectionIfMissing<IDocente>(docentes, this.dissertacaoFinalCurso?.orientador)
        )
      )
      .subscribe((docentes: IDocente[]) => (this.docentesSharedCollection = docentes));

    this.areaFormacaoService
      .query()
      .pipe(map((res: HttpResponse<IAreaFormacao[]>) => res.body ?? []))
      .pipe(
        map((areaFormacaos: IAreaFormacao[]) =>
          this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(
            areaFormacaos,
            this.dissertacaoFinalCurso?.especialidade
          )
        )
      )
      .subscribe((areaFormacaos: IAreaFormacao[]) => (this.areaFormacaosSharedCollection = areaFormacaos));

    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(discentes, this.dissertacaoFinalCurso?.discente)
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));

    this.estadoDissertacaoService
      .query()
      .pipe(map((res: HttpResponse<IEstadoDissertacao[]>) => res.body ?? []))
      .pipe(
        map((estadoDissertacaos: IEstadoDissertacao[]) =>
          this.estadoDissertacaoService.addEstadoDissertacaoToCollectionIfMissing<IEstadoDissertacao>(
            estadoDissertacaos,
            this.dissertacaoFinalCurso?.estado
          )
        )
      )
      .subscribe((estadoDissertacaos: IEstadoDissertacao[]) => (this.estadoDissertacaosSharedCollection = estadoDissertacaos));

    this.naturezaTrabalhoService
      .query()
      .pipe(map((res: HttpResponse<INaturezaTrabalho[]>) => res.body ?? []))
      .pipe(
        map((naturezaTrabalhos: INaturezaTrabalho[]) =>
          this.naturezaTrabalhoService.addNaturezaTrabalhoToCollectionIfMissing<INaturezaTrabalho>(
            naturezaTrabalhos,
            this.dissertacaoFinalCurso?.natureza
          )
        )
      )
      .subscribe((naturezaTrabalhos: INaturezaTrabalho[]) => (this.naturezaTrabalhosSharedCollection = naturezaTrabalhos));
  }
}
