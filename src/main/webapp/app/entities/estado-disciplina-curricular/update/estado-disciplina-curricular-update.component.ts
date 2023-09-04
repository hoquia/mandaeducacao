import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EstadoDisciplinaCurricularFormService, EstadoDisciplinaCurricularFormGroup } from './estado-disciplina-curricular-form.service';
import { IEstadoDisciplinaCurricular } from '../estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from '../service/estado-disciplina-curricular.service';
import { IDisciplinaCurricular } from 'app/entities/disciplina-curricular/disciplina-curricular.model';
import { DisciplinaCurricularService } from 'app/entities/disciplina-curricular/service/disciplina-curricular.service';
import { CategoriaClassificacao } from 'app/entities/enumerations/categoria-classificacao.model';

@Component({
  selector: 'app-estado-disciplina-curricular-update',
  templateUrl: './estado-disciplina-curricular-update.component.html',
})
export class EstadoDisciplinaCurricularUpdateComponent implements OnInit {
  isSaving = false;
  estadoDisciplinaCurricular: IEstadoDisciplinaCurricular | null = null;
  categoriaClassificacaoValues = Object.keys(CategoriaClassificacao);

  estadoDisciplinaCurricularsSharedCollection: IEstadoDisciplinaCurricular[] = [];
  disciplinaCurricularsSharedCollection: IDisciplinaCurricular[] = [];

  editForm: EstadoDisciplinaCurricularFormGroup = this.estadoDisciplinaCurricularFormService.createEstadoDisciplinaCurricularFormGroup();

  constructor(
    protected estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService,
    protected estadoDisciplinaCurricularFormService: EstadoDisciplinaCurricularFormService,
    protected disciplinaCurricularService: DisciplinaCurricularService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEstadoDisciplinaCurricular = (o1: IEstadoDisciplinaCurricular | null, o2: IEstadoDisciplinaCurricular | null): boolean =>
    this.estadoDisciplinaCurricularService.compareEstadoDisciplinaCurricular(o1, o2);

  compareDisciplinaCurricular = (o1: IDisciplinaCurricular | null, o2: IDisciplinaCurricular | null): boolean =>
    this.disciplinaCurricularService.compareDisciplinaCurricular(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoDisciplinaCurricular }) => {
      this.estadoDisciplinaCurricular = estadoDisciplinaCurricular;
      if (estadoDisciplinaCurricular) {
        this.updateForm(estadoDisciplinaCurricular);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadoDisciplinaCurricular = this.estadoDisciplinaCurricularFormService.getEstadoDisciplinaCurricular(this.editForm);
    if (estadoDisciplinaCurricular.id !== null) {
      this.subscribeToSaveResponse(this.estadoDisciplinaCurricularService.update(estadoDisciplinaCurricular));
    } else {
      this.subscribeToSaveResponse(this.estadoDisciplinaCurricularService.create(estadoDisciplinaCurricular));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadoDisciplinaCurricular>>): void {
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

  protected updateForm(estadoDisciplinaCurricular: IEstadoDisciplinaCurricular): void {
    this.estadoDisciplinaCurricular = estadoDisciplinaCurricular;
    this.estadoDisciplinaCurricularFormService.resetForm(this.editForm, estadoDisciplinaCurricular);

    this.estadoDisciplinaCurricularsSharedCollection =
      this.estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing<IEstadoDisciplinaCurricular>(
        this.estadoDisciplinaCurricularsSharedCollection,
        estadoDisciplinaCurricular.referencia
      );
    this.disciplinaCurricularsSharedCollection =
      this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
        this.disciplinaCurricularsSharedCollection,
        ...(estadoDisciplinaCurricular.disciplinasCurriculars ?? [])
      );
  }

  protected loadRelationshipsOptions(): void {
    this.estadoDisciplinaCurricularService
      .query()
      .pipe(map((res: HttpResponse<IEstadoDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((estadoDisciplinaCurriculars: IEstadoDisciplinaCurricular[]) =>
          this.estadoDisciplinaCurricularService.addEstadoDisciplinaCurricularToCollectionIfMissing<IEstadoDisciplinaCurricular>(
            estadoDisciplinaCurriculars,
            this.estadoDisciplinaCurricular?.referencia
          )
        )
      )
      .subscribe(
        (estadoDisciplinaCurriculars: IEstadoDisciplinaCurricular[]) =>
          (this.estadoDisciplinaCurricularsSharedCollection = estadoDisciplinaCurriculars)
      );

    this.disciplinaCurricularService
      .query()
      .pipe(map((res: HttpResponse<IDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((disciplinaCurriculars: IDisciplinaCurricular[]) =>
          this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
            disciplinaCurriculars,
            ...(this.estadoDisciplinaCurricular?.disciplinasCurriculars ?? [])
          )
        )
      )
      .subscribe((disciplinaCurriculars: IDisciplinaCurricular[]) => (this.disciplinaCurricularsSharedCollection = disciplinaCurriculars));
  }
}
