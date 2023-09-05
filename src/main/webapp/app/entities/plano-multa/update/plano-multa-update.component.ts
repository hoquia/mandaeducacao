import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlanoMultaFormService, PlanoMultaFormGroup } from './plano-multa-form.service';
import { IPlanoMulta } from '../plano-multa.model';
import { PlanoMultaService } from '../service/plano-multa.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { MetodoAplicacaoMulta } from 'app/entities/enumerations/metodo-aplicacao-multa.model';

@Component({
  selector: 'app-plano-multa-update',
  templateUrl: './plano-multa-update.component.html',
})
export class PlanoMultaUpdateComponent implements OnInit {
  isSaving = false;
  planoMulta: IPlanoMulta | null = null;
  metodoAplicacaoMultaValues = Object.keys(MetodoAplicacaoMulta);

  usersSharedCollection: IUser[] = [];

  editForm: PlanoMultaFormGroup = this.planoMultaFormService.createPlanoMultaFormGroup();

  constructor(
    protected planoMultaService: PlanoMultaService,
    protected planoMultaFormService: PlanoMultaFormService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoMulta }) => {
      this.planoMulta = planoMulta;
      if (planoMulta) {
        this.updateForm(planoMulta);
      } else {
        this.editForm.patchValue({
          isAtivo: true,
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
    const planoMulta = this.planoMultaFormService.getPlanoMulta(this.editForm);
    if (planoMulta.id !== null) {
      this.subscribeToSaveResponse(this.planoMultaService.update(planoMulta));
    } else {
      this.subscribeToSaveResponse(this.planoMultaService.create(planoMulta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoMulta>>): void {
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

  protected updateForm(planoMulta: IPlanoMulta): void {
    this.planoMulta = planoMulta;
    this.planoMultaFormService.resetForm(this.editForm, planoMulta);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, planoMulta.utilizador);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.planoMulta?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
