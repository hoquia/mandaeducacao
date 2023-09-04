import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ClasseFormService, ClasseFormGroup } from './classe-form.service';
import { IClasse } from '../classe.model';
import { ClasseService } from '../service/classe.service';
import { INivelEnsino } from 'app/entities/nivel-ensino/nivel-ensino.model';
import { NivelEnsinoService } from 'app/entities/nivel-ensino/service/nivel-ensino.service';

@Component({
  selector: 'app-classe-update',
  templateUrl: './classe-update.component.html',
})
export class ClasseUpdateComponent implements OnInit {
  isSaving = false;
  classe: IClasse | null = null;

  nivelEnsinosSharedCollection: INivelEnsino[] = [];

  editForm: ClasseFormGroup = this.classeFormService.createClasseFormGroup();

  constructor(
    protected classeService: ClasseService,
    protected classeFormService: ClasseFormService,
    protected nivelEnsinoService: NivelEnsinoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNivelEnsino = (o1: INivelEnsino | null, o2: INivelEnsino | null): boolean => this.nivelEnsinoService.compareNivelEnsino(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classe }) => {
      this.classe = classe;
      if (classe) {
        this.updateForm(classe);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classe = this.classeFormService.getClasse(this.editForm);
    if (classe.id !== null) {
      this.subscribeToSaveResponse(this.classeService.update(classe));
    } else {
      this.subscribeToSaveResponse(this.classeService.create(classe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClasse>>): void {
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

  protected updateForm(classe: IClasse): void {
    this.classe = classe;
    this.classeFormService.resetForm(this.editForm, classe);

    this.nivelEnsinosSharedCollection = this.nivelEnsinoService.addNivelEnsinoToCollectionIfMissing<INivelEnsino>(
      this.nivelEnsinosSharedCollection,
      ...(classe.nivesEnsinos ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nivelEnsinoService
      .query()
      .pipe(map((res: HttpResponse<INivelEnsino[]>) => res.body ?? []))
      .pipe(
        map((nivelEnsinos: INivelEnsino[]) =>
          this.nivelEnsinoService.addNivelEnsinoToCollectionIfMissing<INivelEnsino>(nivelEnsinos, ...(this.classe?.nivesEnsinos ?? []))
        )
      )
      .subscribe((nivelEnsinos: INivelEnsino[]) => (this.nivelEnsinosSharedCollection = nivelEnsinos));
  }
}
