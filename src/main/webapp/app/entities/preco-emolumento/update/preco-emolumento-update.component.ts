import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PrecoEmolumentoFormService, PrecoEmolumentoFormGroup } from './preco-emolumento-form.service';
import { IPrecoEmolumento } from '../preco-emolumento.model';
import { PrecoEmolumentoService } from '../service/preco-emolumento.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { EmolumentoService } from 'app/entities/emolumento/service/emolumento.service';
import { IAreaFormacao } from 'app/entities/area-formacao/area-formacao.model';
import { AreaFormacaoService } from 'app/entities/area-formacao/service/area-formacao.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';
import { ITurno } from 'app/entities/turno/turno.model';
import { TurnoService } from 'app/entities/turno/service/turno.service';
import { IPlanoMulta } from 'app/entities/plano-multa/plano-multa.model';
import { PlanoMultaService } from 'app/entities/plano-multa/service/plano-multa.service';
import { IEmolumento } from './../../emolumento/emolumento.model';

@Component({
  selector: 'app-preco-emolumento-update',
  templateUrl: './preco-emolumento-update.component.html',
})
export class PrecoEmolumentoUpdateComponent implements OnInit {
  isSaving = false;
  precoEmolumento: IPrecoEmolumento | null = null;

  usersSharedCollection: IUser[] = [];
  emolumentosSharedCollection: IEmolumento[] = [];
  areaFormacaosSharedCollection: IAreaFormacao[] = [];
  cursosSharedCollection: ICurso[] = [];
  classesSharedCollection: IClasse[] = [];
  turnosSharedCollection: ITurno[] = [];
  planoMultasSharedCollection: IPlanoMulta[] = [];

  editForm: PrecoEmolumentoFormGroup = this.precoEmolumentoFormService.createPrecoEmolumentoFormGroup();

  constructor(
    protected precoEmolumentoService: PrecoEmolumentoService,
    protected precoEmolumentoFormService: PrecoEmolumentoFormService,
    protected userService: UserService,
    protected emolumentoService: EmolumentoService,
    protected areaFormacaoService: AreaFormacaoService,
    protected cursoService: CursoService,
    protected classeService: ClasseService,
    protected turnoService: TurnoService,
    protected planoMultaService: PlanoMultaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  compareEmolumento = (o1: IEmolumento | null, o2: IEmolumento | null): boolean => this.emolumentoService.compareEmolumento(o1, o2);

  compareAreaFormacao = (o1: IAreaFormacao | null, o2: IAreaFormacao | null): boolean =>
    this.areaFormacaoService.compareAreaFormacao(o1, o2);

  compareCurso = (o1: ICurso | null, o2: ICurso | null): boolean => this.cursoService.compareCurso(o1, o2);

  compareClasse = (o1: IClasse | null, o2: IClasse | null): boolean => this.classeService.compareClasse(o1, o2);

  compareTurno = (o1: ITurno | null, o2: ITurno | null): boolean => this.turnoService.compareTurno(o1, o2);

  comparePlanoMulta = (o1: IPlanoMulta | null, o2: IPlanoMulta | null): boolean => this.planoMultaService.comparePlanoMulta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ precoEmolumento }) => {
      this.precoEmolumento = precoEmolumento;
      if (precoEmolumento) {
        this.updateForm(precoEmolumento);
      } else {
        const emolumentoID = Number(this.activatedRoute.snapshot.queryParamMap.get('emolumento_id'));

        this.emolumentoService.find(emolumentoID).subscribe(res => {
          this.editForm.patchValue({
            emolumento: res.body,
          });
        });

        this.editForm.patchValue({
          preco: 0,
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
    const precoEmolumento = this.precoEmolumentoFormService.getPrecoEmolumento(this.editForm);
    if (precoEmolumento.id !== null) {
      this.subscribeToSaveResponse(this.precoEmolumentoService.update(precoEmolumento));
    } else {
      this.subscribeToSaveResponse(this.precoEmolumentoService.create(precoEmolumento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrecoEmolumento>>): void {
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

  protected updateForm(precoEmolumento: IPrecoEmolumento): void {
    this.precoEmolumento = precoEmolumento;
    this.precoEmolumentoFormService.resetForm(this.editForm, precoEmolumento);

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(
      this.usersSharedCollection,
      precoEmolumento.utilizador
    );
    this.emolumentosSharedCollection = this.emolumentoService.addEmolumentoToCollectionIfMissing<IEmolumento>(
      this.emolumentosSharedCollection,
      precoEmolumento.emolumento
    );
    this.areaFormacaosSharedCollection = this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(
      this.areaFormacaosSharedCollection,
      precoEmolumento.areaFormacao
    );
    this.cursosSharedCollection = this.cursoService.addCursoToCollectionIfMissing<ICurso>(
      this.cursosSharedCollection,
      precoEmolumento.curso
    );
    this.classesSharedCollection = this.classeService.addClasseToCollectionIfMissing<IClasse>(
      this.classesSharedCollection,
      precoEmolumento.classe
    );
    this.turnosSharedCollection = this.turnoService.addTurnoToCollectionIfMissing<ITurno>(
      this.turnosSharedCollection,
      precoEmolumento.turno
    );
    this.planoMultasSharedCollection = this.planoMultaService.addPlanoMultaToCollectionIfMissing<IPlanoMulta>(
      this.planoMultasSharedCollection,
      precoEmolumento.planoMulta
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.precoEmolumento?.utilizador)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.emolumentoService
      .query()
      .pipe(map((res: HttpResponse<IEmolumento[]>) => res.body ?? []))
      .pipe(
        map((emolumentos: IEmolumento[]) =>
          this.emolumentoService.addEmolumentoToCollectionIfMissing<IEmolumento>(emolumentos, this.precoEmolumento?.emolumento)
        )
      )
      .subscribe((emolumentos: IEmolumento[]) => (this.emolumentosSharedCollection = emolumentos));

    this.areaFormacaoService
      .query()
      .pipe(map((res: HttpResponse<IAreaFormacao[]>) => res.body ?? []))
      .pipe(
        map((areaFormacaos: IAreaFormacao[]) =>
          this.areaFormacaoService.addAreaFormacaoToCollectionIfMissing<IAreaFormacao>(areaFormacaos, this.precoEmolumento?.areaFormacao)
        )
      )
      .subscribe((areaFormacaos: IAreaFormacao[]) => (this.areaFormacaosSharedCollection = areaFormacaos));

    this.cursoService
      .query()
      .pipe(map((res: HttpResponse<ICurso[]>) => res.body ?? []))
      .pipe(map((cursos: ICurso[]) => this.cursoService.addCursoToCollectionIfMissing<ICurso>(cursos, this.precoEmolumento?.curso)))
      .subscribe((cursos: ICurso[]) => (this.cursosSharedCollection = cursos));

    this.classeService
      .query()
      .pipe(map((res: HttpResponse<IClasse[]>) => res.body ?? []))
      .pipe(map((classes: IClasse[]) => this.classeService.addClasseToCollectionIfMissing<IClasse>(classes, this.precoEmolumento?.classe)))
      .subscribe((classes: IClasse[]) => (this.classesSharedCollection = classes));

    this.turnoService
      .query()
      .pipe(map((res: HttpResponse<ITurno[]>) => res.body ?? []))
      .pipe(map((turnos: ITurno[]) => this.turnoService.addTurnoToCollectionIfMissing<ITurno>(turnos, this.precoEmolumento?.turno)))
      .subscribe((turnos: ITurno[]) => (this.turnosSharedCollection = turnos));

    this.planoMultaService
      .query()
      .pipe(map((res: HttpResponse<IPlanoMulta[]>) => res.body ?? []))
      .pipe(
        map((planoMultas: IPlanoMulta[]) =>
          this.planoMultaService.addPlanoMultaToCollectionIfMissing<IPlanoMulta>(planoMultas, this.precoEmolumento?.planoMulta)
        )
      )
      .subscribe((planoMultas: IPlanoMulta[]) => (this.planoMultasSharedCollection = planoMultas));
  }
}
