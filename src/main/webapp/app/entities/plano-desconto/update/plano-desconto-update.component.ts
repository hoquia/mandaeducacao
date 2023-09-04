import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlanoDescontoFormService, PlanoDescontoFormGroup } from './plano-desconto-form.service';
import { IPlanoDesconto } from '../plano-desconto.model';
import { PlanoDescontoService } from '../service/plano-desconto.service';
import { ICategoriaEmolumento } from 'app/entities/categoria-emolumento/categoria-emolumento.model';
import { CategoriaEmolumentoService } from 'app/entities/categoria-emolumento/service/categoria-emolumento.service';

@Component({
  selector: 'app-plano-desconto-update',
  templateUrl: './plano-desconto-update.component.html',
})
export class PlanoDescontoUpdateComponent implements OnInit {
  isSaving = false;
  planoDesconto: IPlanoDesconto | null = null;

  categoriaEmolumentosSharedCollection: ICategoriaEmolumento[] = [];

  editForm: PlanoDescontoFormGroup = this.planoDescontoFormService.createPlanoDescontoFormGroup();

  constructor(
    protected planoDescontoService: PlanoDescontoService,
    protected planoDescontoFormService: PlanoDescontoFormService,
    protected categoriaEmolumentoService: CategoriaEmolumentoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCategoriaEmolumento = (o1: ICategoriaEmolumento | null, o2: ICategoriaEmolumento | null): boolean =>
    this.categoriaEmolumentoService.compareCategoriaEmolumento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoDesconto }) => {
      this.planoDesconto = planoDesconto;
      if (planoDesconto) {
        this.updateForm(planoDesconto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoDesconto = this.planoDescontoFormService.getPlanoDesconto(this.editForm);
    if (planoDesconto.id !== null) {
      this.subscribeToSaveResponse(this.planoDescontoService.update(planoDesconto));
    } else {
      this.subscribeToSaveResponse(this.planoDescontoService.create(planoDesconto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoDesconto>>): void {
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

  protected updateForm(planoDesconto: IPlanoDesconto): void {
    this.planoDesconto = planoDesconto;
    this.planoDescontoFormService.resetForm(this.editForm, planoDesconto);

    this.categoriaEmolumentosSharedCollection =
      this.categoriaEmolumentoService.addCategoriaEmolumentoToCollectionIfMissing<ICategoriaEmolumento>(
        this.categoriaEmolumentosSharedCollection,
        ...(planoDesconto.categoriasEmolumentos ?? [])
      );
  }

  protected loadRelationshipsOptions(): void {
    this.categoriaEmolumentoService
      .query()
      .pipe(map((res: HttpResponse<ICategoriaEmolumento[]>) => res.body ?? []))
      .pipe(
        map((categoriaEmolumentos: ICategoriaEmolumento[]) =>
          this.categoriaEmolumentoService.addCategoriaEmolumentoToCollectionIfMissing<ICategoriaEmolumento>(
            categoriaEmolumentos,
            ...(this.planoDesconto?.categoriasEmolumentos ?? [])
          )
        )
      )
      .subscribe((categoriaEmolumentos: ICategoriaEmolumento[]) => (this.categoriaEmolumentosSharedCollection = categoriaEmolumentos));
  }
}
