import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlanoCurricularFormService, PlanoCurricularFormGroup } from './plano-curricular-form.service';
import { IPlanoCurricular } from '../plano-curricular.model';
import { PlanoCurricularService } from '../service/plano-curricular.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';

@Component({
  selector: 'app-plano-curricular-update',
  templateUrl: './plano-curricular-update.component.html',
})
export class PlanoCurricularUpdateComponent implements OnInit {
  isSaving = false;
  planoCurricular: IPlanoCurricular | null = null;

  usersSharedCollection: IUser[] = [];
  classesSharedCollection: IClasse[] = [];
  cursosSharedCollection: ICurso[] = [];

  editForm: PlanoCurricularFormGroup = this.planoCurricularFormService.createPlanoCurricularFormGroup();

  constructor(
    protected planoCurricularService: PlanoCurricularService,
    protected planoCurricularFormService: PlanoCurricularFormService,
    protected userService: UserService,
    protected classeService: ClasseService,
    protected cursoService: CursoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareClasse = (o1: IClasse | null, o2: IClasse | null): boolean => this.classeService.compareClasse(o1, o2);

  compareCurso = (o1: ICurso | null, o2: ICurso | null): boolean => this.cursoService.compareCurso(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoCurricular }) => {
      this.planoCurricular = planoCurricular;
      if (planoCurricular) {
        this.updateForm(planoCurricular);
      } else {
        this.editForm.patchValue({
          descricao: 'NA',
          formulaClassificacaoFinal: 'NA',
          numeroDisciplinaAprova: 0,
          numeroDisciplinaReprova: 0,
          numeroDisciplinaRecurso: 0,
          numeroDisciplinaExame: 0,
          numeroDisciplinaExameEspecial: 0,
          numeroFaltaReprova: 0,
          pesoMedia1: 0,
          pesoMedia2: 0,
          pesoMedia3: 0,
          pesoExame: 0,
          pesoExameEspecial: 0,
          pesoNotaCoselho: 0,
          pesoRecurso: 0,
          siglaProva1: 'P1',
          siglaProva2: 'P2',
          siglaProva3: 'P3',
          siglaMedia1: 'M1',
          siglaMedia2: 'M2',
          siglaMedia3: 'M3',
          formulaMedia: 'NA',
          formulaDispensa: 'NA',
          formulaExame: 'NA',
          formulaExameEspecial: 'NA',
          formulaRecurso: 'NA',
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
    const planoCurricular = this.planoCurricularFormService.getPlanoCurricular(this.editForm);
    if (planoCurricular.id !== null) {
      this.subscribeToSaveResponse(this.planoCurricularService.update(planoCurricular));
    } else {
      this.subscribeToSaveResponse(this.planoCurricularService.create(planoCurricular));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoCurricular>>): void {
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

  protected updateForm(planoCurricular: IPlanoCurricular): void {
    this.planoCurricular = planoCurricular;
    this.planoCurricularFormService.resetForm(this.editForm, planoCurricular);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      planoCurricular.utilizador
    );
    this.classesSharedCollection = this.classeService.addClasseToCollectionIfMissing<IClasse>(
      this.classesSharedCollection,
      planoCurricular.classe
    );
    this.cursosSharedCollection = this.cursoService.addCursoToCollectionIfMissing<ICurso>(
      this.cursosSharedCollection,
      planoCurricular.curso
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.planoCurricular?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.classeService
      .query()
      .pipe(map((res: HttpResponse<IClasse[]>) => res.body ?? []))
      .pipe(map((classes: IClasse[]) => this.classeService.addClasseToCollectionIfMissing<IClasse>(classes, this.planoCurricular?.classe)))
      .subscribe((classes: IClasse[]) => (this.classesSharedCollection = classes));

    this.cursoService
      .query()
      .pipe(map((res: HttpResponse<ICurso[]>) => res.body ?? []))
      .pipe(map((cursos: ICurso[]) => this.cursoService.addCursoToCollectionIfMissing<ICurso>(cursos, this.planoCurricular?.curso)))
      .subscribe((cursos: ICurso[]) => (this.cursosSharedCollection = cursos));
  }
}
