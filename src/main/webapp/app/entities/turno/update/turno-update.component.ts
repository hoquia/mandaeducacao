import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TurnoFormService, TurnoFormGroup } from './turno-form.service';
import { ITurno } from '../turno.model';
import { TurnoService } from '../service/turno.service';

@Component({
  selector: 'app-turno-update',
  templateUrl: './turno-update.component.html',
})
export class TurnoUpdateComponent implements OnInit {
  isSaving = false;
  turno: ITurno | null = null;

  editForm: TurnoFormGroup = this.turnoFormService.createTurnoFormGroup();

  constructor(
    protected turnoService: TurnoService,
    protected turnoFormService: TurnoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ turno }) => {
      this.turno = turno;
      if (turno) {
        this.updateForm(turno);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const turno = this.turnoFormService.getTurno(this.editForm);
    if (turno.id !== null) {
      this.subscribeToSaveResponse(this.turnoService.update(turno));
    } else {
      this.subscribeToSaveResponse(this.turnoService.create(turno));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITurno>>): void {
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

  protected updateForm(turno: ITurno): void {
    this.turno = turno;
    this.turnoFormService.resetForm(this.editForm, turno);
  }
}
