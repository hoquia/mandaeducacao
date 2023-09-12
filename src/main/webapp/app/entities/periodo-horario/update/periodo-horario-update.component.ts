import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PeriodoHorarioFormService, PeriodoHorarioFormGroup } from './periodo-horario-form.service';
import { IPeriodoHorario } from '../periodo-horario.model';
import { PeriodoHorarioService } from '../service/periodo-horario.service';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';

@Component({
  selector: 'app-periodo-horario-update',
  templateUrl: './periodo-horario-update.component.html',
})
export class PeriodoHorarioUpdateComponent implements OnInit {
  isSaving = false;
  periodoHorario: IPeriodoHorario | null = null;

  turnosSharedCollection: ITurno[] = [];

  editForm: PeriodoHorarioFormGroup = this.periodoHorarioFormService.createPeriodoHorarioFormGroup();

  constructor(
    protected periodoHorarioService: PeriodoHorarioService,
    protected periodoHorarioFormService: PeriodoHorarioFormService,
    protected turnoService: TurnoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTurno = (o1: ITurno | null, o2: ITurno | null): boolean => this.turnoService.compareTurno(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodoHorario }) => {
      this.periodoHorario = periodoHorario;
      if (periodoHorario) {
        this.updateForm(periodoHorario);
      } else {
        this.editForm.patchValue({
          descricao: '1010',
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
    const periodoHorario = this.periodoHorarioFormService.getPeriodoHorario(this.editForm);
    if (periodoHorario.id !== null) {
      this.subscribeToSaveResponse(this.periodoHorarioService.update(periodoHorario));
    } else {
      this.subscribeToSaveResponse(this.periodoHorarioService.create(periodoHorario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodoHorario>>): void {
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

  protected updateForm(periodoHorario: IPeriodoHorario): void {
    this.periodoHorario = periodoHorario;
    this.periodoHorarioFormService.resetForm(this.editForm, periodoHorario);

    this.turnosSharedCollection = this.turnoService.addTurnoToCollectionIfMissing<ITurno>(
      this.turnosSharedCollection,
      periodoHorario.turno
    );
  }

  protected loadRelationshipsOptions(): void {
    this.turnoService
      .query()
      .pipe(map((res: HttpResponse<ITurno[]>) => res.body ?? []))
      .pipe(map((turnos: ITurno[]) => this.turnoService.addTurnoToCollectionIfMissing<ITurno>(turnos, this.periodoHorario?.turno)))
      .subscribe((turnos: ITurno[]) => (this.turnosSharedCollection = turnos));
  }
}
