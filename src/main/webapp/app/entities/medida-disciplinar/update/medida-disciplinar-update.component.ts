import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MedidaDisciplinarFormService, MedidaDisciplinarFormGroup } from './medida-disciplinar-form.service';
import { IMedidaDisciplinar } from '../medida-disciplinar.model';
import { MedidaDisciplinarService } from '../service/medida-disciplinar.service';
import { UnidadeDuracao } from 'app/entities/enumerations/unidade-duracao.model';
import { Suspensao } from 'app/entities/enumerations/suspensao.model';

@Component({
  selector: 'app-medida-disciplinar-update',
  templateUrl: './medida-disciplinar-update.component.html',
})
export class MedidaDisciplinarUpdateComponent implements OnInit {
  isSaving = false;
  medidaDisciplinar: IMedidaDisciplinar | null = null;
  unidadeDuracaoValues = Object.keys(UnidadeDuracao);
  suspensaoValues = Object.keys(Suspensao);

  editForm: MedidaDisciplinarFormGroup = this.medidaDisciplinarFormService.createMedidaDisciplinarFormGroup();

  constructor(
    protected medidaDisciplinarService: MedidaDisciplinarService,
    protected medidaDisciplinarFormService: MedidaDisciplinarFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medidaDisciplinar }) => {
      this.medidaDisciplinar = medidaDisciplinar;
      if (medidaDisciplinar) {
        this.updateForm(medidaDisciplinar);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medidaDisciplinar = this.medidaDisciplinarFormService.getMedidaDisciplinar(this.editForm);
    if (medidaDisciplinar.id !== null) {
      this.subscribeToSaveResponse(this.medidaDisciplinarService.update(medidaDisciplinar));
    } else {
      this.subscribeToSaveResponse(this.medidaDisciplinarService.create(medidaDisciplinar));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedidaDisciplinar>>): void {
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

  protected updateForm(medidaDisciplinar: IMedidaDisciplinar): void {
    this.medidaDisciplinar = medidaDisciplinar;
    this.medidaDisciplinarFormService.resetForm(this.editForm, medidaDisciplinar);
  }
}
