import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ResumoImpostoFacturaFormService, ResumoImpostoFacturaFormGroup } from './resumo-imposto-factura-form.service';
import { IResumoImpostoFactura } from '../resumo-imposto-factura.model';
import { ResumoImpostoFacturaService } from '../service/resumo-imposto-factura.service';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';

@Component({
  selector: 'app-resumo-imposto-factura-update',
  templateUrl: './resumo-imposto-factura-update.component.html',
})
export class ResumoImpostoFacturaUpdateComponent implements OnInit {
  isSaving = false;
  resumoImpostoFactura: IResumoImpostoFactura | null = null;

  facturasSharedCollection: IFactura[] = [];

  editForm: ResumoImpostoFacturaFormGroup = this.resumoImpostoFacturaFormService.createResumoImpostoFacturaFormGroup();

  constructor(
    protected resumoImpostoFacturaService: ResumoImpostoFacturaService,
    protected resumoImpostoFacturaFormService: ResumoImpostoFacturaFormService,
    protected facturaService: FacturaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFactura = (o1: IFactura | null, o2: IFactura | null): boolean => this.facturaService.compareFactura(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumoImpostoFactura }) => {
      this.resumoImpostoFactura = resumoImpostoFactura;
      if (resumoImpostoFactura) {
        this.updateForm(resumoImpostoFactura);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resumoImpostoFactura = this.resumoImpostoFacturaFormService.getResumoImpostoFactura(this.editForm);
    if (resumoImpostoFactura.id !== null) {
      this.subscribeToSaveResponse(this.resumoImpostoFacturaService.update(resumoImpostoFactura));
    } else {
      this.subscribeToSaveResponse(this.resumoImpostoFacturaService.create(resumoImpostoFactura));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumoImpostoFactura>>): void {
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

  protected updateForm(resumoImpostoFactura: IResumoImpostoFactura): void {
    this.resumoImpostoFactura = resumoImpostoFactura;
    this.resumoImpostoFacturaFormService.resetForm(this.editForm, resumoImpostoFactura);

    this.facturasSharedCollection = this.facturaService.addFacturaToCollectionIfMissing<IFactura>(
      this.facturasSharedCollection,
      resumoImpostoFactura.factura
    );
  }

  protected loadRelationshipsOptions(): void {
    this.facturaService
      .query()
      .pipe(map((res: HttpResponse<IFactura[]>) => res.body ?? []))
      .pipe(
        map((facturas: IFactura[]) =>
          this.facturaService.addFacturaToCollectionIfMissing<IFactura>(facturas, this.resumoImpostoFactura?.factura)
        )
      )
      .subscribe((facturas: IFactura[]) => (this.facturasSharedCollection = facturas));
  }
}
