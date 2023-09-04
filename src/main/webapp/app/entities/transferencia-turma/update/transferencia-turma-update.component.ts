import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TransferenciaTurmaFormService, TransferenciaTurmaFormGroup } from './transferencia-turma-form.service';
import { ITransferenciaTurma } from '../transferencia-turma.model';
import { TransferenciaTurmaService } from '../service/transferencia-turma.service';
import { ITurma } from 'app/entities/turma/turma.model';
import { TurmaService } from 'app/entities/turma/service/turma.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ILookupItem } from 'app/entities/lookup-item/lookup-item.model';
import { LookupItemService } from 'app/entities/lookup-item/service/lookup-item.service';
import { IMatricula } from 'app/entities/matricula/matricula.model';
import { MatriculaService } from 'app/entities/matricula/service/matricula.service';

@Component({
  selector: 'app-transferencia-turma-update',
  templateUrl: './transferencia-turma-update.component.html',
})
export class TransferenciaTurmaUpdateComponent implements OnInit {
  isSaving = false;
  transferenciaTurma: ITransferenciaTurma | null = null;

  turmasSharedCollection: ITurma[] = [];
  usersSharedCollection: IUser[] = [];
  lookupItemsSharedCollection: ILookupItem[] = [];
  matriculasSharedCollection: IMatricula[] = [];

  editForm: TransferenciaTurmaFormGroup = this.transferenciaTurmaFormService.createTransferenciaTurmaFormGroup();

  constructor(
    protected transferenciaTurmaService: TransferenciaTurmaService,
    protected transferenciaTurmaFormService: TransferenciaTurmaFormService,
    protected turmaService: TurmaService,
    protected userService: UserService,
    protected lookupItemService: LookupItemService,
    protected matriculaService: MatriculaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTurma = (o1: ITurma | null, o2: ITurma | null): boolean => this.turmaService.compareTurma(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareLookupItem = (o1: ILookupItem | null, o2: ILookupItem | null): boolean => this.lookupItemService.compareLookupItem(o1, o2);

  compareMatricula = (o1: IMatricula | null, o2: IMatricula | null): boolean => this.matriculaService.compareMatricula(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferenciaTurma }) => {
      this.transferenciaTurma = transferenciaTurma;
      if (transferenciaTurma) {
        this.updateForm(transferenciaTurma);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transferenciaTurma = this.transferenciaTurmaFormService.getTransferenciaTurma(this.editForm);
    if (transferenciaTurma.id !== null) {
      this.subscribeToSaveResponse(this.transferenciaTurmaService.update(transferenciaTurma));
    } else {
      this.subscribeToSaveResponse(this.transferenciaTurmaService.create(transferenciaTurma));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransferenciaTurma>>): void {
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

  protected updateForm(transferenciaTurma: ITransferenciaTurma): void {
    this.transferenciaTurma = transferenciaTurma;
    this.transferenciaTurmaFormService.resetForm(this.editForm, transferenciaTurma);

    this.turmasSharedCollection = this.turmaService.addTurmaToCollectionIfMissing<ITurma>(
      this.turmasSharedCollection,
      transferenciaTurma.de,
      transferenciaTurma.para
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      transferenciaTurma.utilizador
    );
    this.lookupItemsSharedCollection = this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(
      this.lookupItemsSharedCollection,
      transferenciaTurma.motivoTransferencia
    );
    this.matriculasSharedCollection = this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(
      this.matriculasSharedCollection,
      transferenciaTurma.matricula
    );
  }

  protected loadRelationshipsOptions(): void {
    this.turmaService
      .query()
      .pipe(map((res: HttpResponse<ITurma[]>) => res.body ?? []))
      .pipe(
        map((turmas: ITurma[]) =>
          this.turmaService.addTurmaToCollectionIfMissing<ITurma>(turmas, this.transferenciaTurma?.de, this.transferenciaTurma?.para)
        )
      )
      .subscribe((turmas: ITurma[]) => (this.turmasSharedCollection = turmas));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.transferenciaTurma?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.lookupItemService
      .query()
      .pipe(map((res: HttpResponse<ILookupItem[]>) => res.body ?? []))
      .pipe(
        map((lookupItems: ILookupItem[]) =>
          this.lookupItemService.addLookupItemToCollectionIfMissing<ILookupItem>(lookupItems, this.transferenciaTurma?.motivoTransferencia)
        )
      )
      .subscribe((lookupItems: ILookupItem[]) => (this.lookupItemsSharedCollection = lookupItems));

    this.matriculaService
      .query()
      .pipe(map((res: HttpResponse<IMatricula[]>) => res.body ?? []))
      .pipe(
        map((matriculas: IMatricula[]) =>
          this.matriculaService.addMatriculaToCollectionIfMissing<IMatricula>(matriculas, this.transferenciaTurma?.matricula)
        )
      )
      .subscribe((matriculas: IMatricula[]) => (this.matriculasSharedCollection = matriculas));
  }
}
