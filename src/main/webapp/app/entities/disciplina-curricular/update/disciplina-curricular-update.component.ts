import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DisciplinaCurricularFormService, DisciplinaCurricularFormGroup } from './disciplina-curricular-form.service';
import { IDisciplinaCurricular } from '../disciplina-curricular.model';
import { DisciplinaCurricularService } from '../service/disciplina-curricular.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IPlanoCurricular } from 'app/entities/plano-curricular/plano-curricular.model';
import { PlanoCurricularService } from 'app/entities/plano-curricular/service/plano-curricular.service';
import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/service/disciplina.service';

@Component({
  selector: 'app-disciplina-curricular-update',
  templateUrl: './disciplina-curricular-update.component.html',
})
export class DisciplinaCurricularUpdateComponent implements OnInit {
  isSaving = false;
  disciplinaCurricular: IDisciplinaCurricular | null = null;

  disciplinaCurricularsSharedCollection: IDisciplinaCurricular[] = [];
  lookupItemsSharedCollection: ILookupItem[] = [];
  planoCurricularsSharedCollection: IPlanoCurricular[] = [];
  disciplinasSharedCollection: IDisciplina[] = [];

  editForm: DisciplinaCurricularFormGroup = this.disciplinaCurricularFormService.createDisciplinaCurricularFormGroup();

  constructor(
    protected disciplinaCurricularService: DisciplinaCurricularService,
    protected disciplinaCurricularFormService: DisciplinaCurricularFormService,
    protected lookupItemService: LookupItemService,
    protected planoCurricularService: PlanoCurricularService,
    protected disciplinaService: DisciplinaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDisciplinaCurricular = (o1: IDisciplinaCurricular | null, o2: IDisciplinaCurricular | null): boolean =>
    this.disciplinaCurricularService.compareDisciplinaCurricular(o1, o2);

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  comparePlanoCurricular = (o1: IPlanoCurricular | null, o2: IPlanoCurricular | null): boolean =>
    this.planoCurricularService.comparePlanoCurricular(o1, o2);

  compareDisciplina = (o1: IDisciplina | null, o2: IDisciplina | null): boolean => this.disciplinaService.compareDisciplina(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplinaCurricular }) => {
      this.disciplinaCurricular = disciplinaCurricular;
      if (disciplinaCurricular) {
        this.updateForm(disciplinaCurricular);
      } else {
        const planoCurricularID = Number(this.activatedRoute.snapshot.queryParamMap.get('plano_curricular_id'));

        this.planoCurricularService.find(planoCurricularID).subscribe(res => {
          this.editForm.patchValue({
            planosCurriculars: res.body?.disciplinasCurriculars,
          });
        });

        this.editForm.patchValue({
          descricao: '1010',
          mediaParaDespensar: 0,
          mediaParaExame: 0,
          mediaParaExameEspecial: 0,
          mediaParaRecurso: 0,
        });
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const disciplinaCurricular = this.disciplinaCurricularFormService.getDisciplinaCurricular(this.editForm);
    if (disciplinaCurricular.id !== null) {
      this.subscribeToSaveResponse(this.disciplinaCurricularService.update(disciplinaCurricular));
    } else {
      this.subscribeToSaveResponse(this.disciplinaCurricularService.create(disciplinaCurricular));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisciplinaCurricular>>): void {
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

  protected updateForm(disciplinaCurricular: IDisciplinaCurricular): void {
    this.disciplinaCurricular = disciplinaCurricular;
    this.disciplinaCurricularFormService.resetForm(this.editForm, disciplinaCurricular);

    this.disciplinaCurricularsSharedCollection =
      this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
        this.disciplinaCurricularsSharedCollection,
        disciplinaCurricular.referencia
      );
    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      disciplinaCurricular.componente,
      disciplinaCurricular.regime
    );
    this.planoCurricularsSharedCollection = this.planoCurricularService.addPlanoCurricularToCollectionIfMissing<IPlanoCurricular>(
      this.planoCurricularsSharedCollection,
      ...(disciplinaCurricular.planosCurriculars ?? [])
    );
    this.disciplinasSharedCollection = this.disciplinaService.addDisciplinaToCollectionIfMissing<IDisciplina>(
      this.disciplinasSharedCollection,
      disciplinaCurricular.disciplina
    );
  }

  protected loadRelationshipsOptions(): void {
    this.disciplinaCurricularService
      .query()
      .pipe(map((res: HttpResponse<IDisciplinaCurricular[]>) => res.body ?? []))
      .pipe(
        map((disciplinaCurriculars: IDisciplinaCurricular[]) =>
          this.disciplinaCurricularService.addDisciplinaCurricularToCollectionIfMissing<IDisciplinaCurricular>(
            disciplinaCurriculars,
            this.disciplinaCurricular?.referencia
          )
        )
      )
      .subscribe((disciplinaCurriculars: IDisciplinaCurricular[]) => (this.disciplinaCurricularsSharedCollection = disciplinaCurriculars));

    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
            lookupItems,
            this.disciplinaCurricular?.componente,
            this.disciplinaCurricular?.regime
          )
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.planoCurricularService
      .query()
      .pipe(map((res: HttpResponse<IPlanoCurricular[]>) => res.body ?? []))
      .pipe(
        map((planoCurriculars: IPlanoCurricular[]) =>
          this.planoCurricularService.addPlanoCurricularToCollectionIfMissing<IPlanoCurricular>(
            planoCurriculars,
            ...(this.disciplinaCurricular?.planosCurriculars ?? [])
          )
        )
      )
      .subscribe((planoCurriculars: IPlanoCurricular[]) => (this.planoCurricularsSharedCollection = planoCurriculars));

    this.disciplinaService
      .query()
      .pipe(map((res: HttpResponse<IDisciplina[]>) => res.body ?? []))
      .pipe(
        map((disciplinas: IDisciplina[]) =>
          this.disciplinaService.addDisciplinaToCollectionIfMissing<IDisciplina>(disciplinas, this.disciplinaCurricular?.disciplina)
        )
      )
      .subscribe((disciplinas: IDisciplina[]) => (this.disciplinasSharedCollection = disciplinas));
  }
}
