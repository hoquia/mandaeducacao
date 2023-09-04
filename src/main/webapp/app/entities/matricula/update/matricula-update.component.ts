import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MatriculaFormService, MatriculaFormGroup } from './matricula-form.service';
import { IMatricula } from '../matricula.model';
import { MatriculaService } from '../service/matricula.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IPlanoDesconto } from 'app/entities/plano-desconto/plano-desconto.model';
import { PlanoDescontoService } from 'app/entities/plano-desconto/service/plano-desconto.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IEncarregadoEducacao } from 'app/entities/encarregado-educacao/encarregado-educacao.model';
import { EncarregadoEducacaoService } from 'app/entities/encarregado-educacao/service/encarregado-educacao.service';
import { IDiscente } from 'app/entities/discente/discente.model';
import { DiscenteService } from 'app/entities/discente/service/discente.service';
import { EstadoAcademico } from 'app/entities/enumerations/estado-academico.model';

@Component({
  selector: 'app-matricula-update',
  templateUrl: './matricula-update.component.html',
})
export class MatriculaUpdateComponent implements OnInit {
  isSaving = false;
  matricula: IMatricula | null = null;
  estadoAcademicoValues = Object.keys(EstadoAcademico);

  matriculasSharedCollection: IMatricula[] = [];
  usersSharedCollection: IUser[] = [];
  planoDescontosSharedCollection: IPlanoDesconto[] = [];
  turmasSharedCollection: ITurma[] = [];
  encarregadoEducacaosSharedCollection: IEncarregadoEducacao[] = [];
  discentesSharedCollection: IDiscente[] = [];

  editForm: MatriculaFormGroup = this.matriculaFormService.createMatriculaFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected matriculaService: MatriculaService,
    protected matriculaFormService: MatriculaFormService,
    protected userService: UserService,
    protected planoDescontoService: PlanoDescontoService,
    protected turmaService: TurmaService,
    protected encarregadoEducacaoService: EncarregadoEducacaoService,
    protected discenteService: DiscenteService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  comparePlanoDesconto = (o1: IPlanoDesconto | null, o2: IPlanoDesconto | null): boolean =>
    this.planoDescontoService.comparePlanoDesconto(o1, o2);

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareEncarregadoEducacao = (o1: IEncarregadoEducacao | null, o2: IEncarregadoEducacao | null): boolean =>
    this.encarregadoEducacaoService.compareEncarregadoEducacao(o1, o2);

  compareDiscente = (o1: IDiscente | null, o2: IDiscente | null): boolean => this.discenteService.compareDiscente(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricula }) => {
      this.matricula = matricula;
      if (matricula) {
        this.updateForm(matricula);
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
    const matricula = this.matriculaFormService.getMatricula(this.editForm);
    if (matricula.id !== null) {
      this.subscribeToSaveResponse(this.matriculaService.update(matricula));
    } else {
      this.subscribeToSaveResponse(this.matriculaService.create(matricula));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatricula>>): void {
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

  protected updateForm(matricula: IMatricula): void {
    this.matricula = matricula;
    this.matriculaFormService.resetForm(this.editForm, matricula);

    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      matricula.referencia
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, matricula.utilizador);
    this.planoDescontosSharedCollection = this.planoDescontoService.addPlanoDescontoToCollectionIfMissing<IPlanoDesconto>(
      this.planoDescontosSharedCollection,
      ...(matricula.categoriasMatriculas ?? [])
    );
    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(this.turmasSharedCollection, matricula.turma);
    this.encarregadoEducacaosSharedCollection =
      this.encarregadoEducacaoService.addEncarregadoEducacaoToCollectionIfMissing<IEncarregadoEducacao>(
        this.encarregadoEducacaosSharedCollection,
        matricula.responsavelFinanceiro
      );
    this.discentesSharedCollection = this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(
      this.discentesSharedCollection,
      matricula.discente
    );
  }

  protected loadRelationshipsOptions(): void {
    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.matricula?.referencia)
        )
      )
      .subscribe((matriculas: IMatricula[]) => (this.matriculasSharedCollection = matriculas));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.matricula?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.planoDescontoService
      .query()
      .pipe(map((res: HttpResponse<IPlanoDesconto[]>) => res.body ?? []))
      .pipe(
        map((planoDescontos: IPlanoDesconto[]) =>
          this.planoDescontoService.addPlanoDescontoToCollectionIfMissing<IPlanoDesconto>(
            planoDescontos,
            ...(this.matricula?.categoriasMatriculas ?? [])
          )
        )
      )
      .subscribe((planoDescontos: IPlanoDesconto[]) => (this.planoDescontosSharedCollection = planoDescontos));

    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(map((turmas: ITurma[]) => this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.matricula?.turma)))
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.encarregadoEducacaoService
      .query()
      .pipe(map((res: HttpResponse<IEncarregadoEducacao[]>) => res.body ?? []))
      .pipe(
        map((encarregadoEducacaos: IEncarregadoEducacao[]) =>
          this.encarregadoEducacaoService.addEncarregadoEducacaoToCollectionIfMissing<IEncarregadoEducacao>(
            encarregadoEducacaos,
            this.matricula?.responsavelFinanceiro
          )
        )
      )
      .subscribe((encarregadoEducacaos: IEncarregadoEducacao[]) => (this.encarregadoEducacaosSharedCollection = encarregadoEducacaos));

    this.discenteService
      .query()
      .pipe(map((res: HttpResponse<IDiscente[]>) => res.body ?? []))
      .pipe(
        map((discentes: IDiscente[]) =>
          this.discenteService.addDiscenteToCollectionIfMissing<IDiscente>(discentes, this.matricula?.discente)
        )
      )
      .subscribe((discentes: IDiscente[]) => (this.discentesSharedCollection = discentes));
  }
}
