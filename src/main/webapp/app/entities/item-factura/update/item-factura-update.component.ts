import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ItemFacturaFormService, ItemFacturaFormGroup } from './item-factura-form.service';
import { IItemFactura } from '../item-factura.model';
import { ItemFacturaService } from '../service/item-factura.service';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IEmolumento } from 'app/entities/emolumento/emolumento.model';
import { EmolumentoService } from 'app/entities/emolumento/service/emolumento.service';
import { EstadoItemFactura } from 'app/entities/enumerations/estado-item-factura.model';

@Component({
  selector: 'app-item-factura-update',
  templateUrl: './item-factura-update.component.html',
})
export class ItemFacturaUpdateComponent implements OnInit {
  isSaving = false;
  itemFactura: IItemFactura | null = null;
  estadoItemFacturaValues = Object.keys(EstadoItemFactura);

  facturasSharedCollection: IFactura[] = [];
  emolumentosSharedCollection: IEmolumento[] = [];

  editForm: ItemFacturaFormGroup = this.itemFacturaFormService.createItemFacturaFormGroup();

  constructor(
    protected itemFacturaService: ItemFacturaService,
    protected itemFacturaFormService: ItemFacturaFormService,
    protected facturaService: FacturaService,
    protected emolumentoService: EmolumentoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFactura = (o1: IFactura | null, o2: IFactura | null): boolean => this.facturaService.compareFactura(o1, o2);

  compareEmolumento = (o1: IEmolumento | null, o2: IEmolumento | null): boolean => this.emolumentoService.compareEmolumento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemFactura }) => {
      this.itemFactura = itemFactura;
      if (itemFactura) {
        this.updateForm(itemFactura);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemFactura = this.itemFacturaFormService.getItemFactura(this.editForm);
    if (itemFactura.id !== null) {
      this.subscribeToSaveResponse(this.itemFacturaService.update(itemFactura));
    } else {
      this.subscribeToSaveResponse(this.itemFacturaService.create(itemFactura));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemFactura>>): void {
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

  protected updateForm(itemFactura: IItemFactura): void {
    this.itemFactura = itemFactura;
    this.itemFacturaFormService.resetForm(this.editForm, itemFactura);

    this.facturasSharedCollection = this.facturaService.addFacturaToCollectionIfMissing<IFactura>(
      this.facturasSharedCollection,
      itemFactura.factura
    );
    this.emolumentosSharedCollection = this.emolumentoService.addEmolumentoToCollectionIfMissing<IEmolumento>(
      this.emolumentosSharedCollection,
      itemFactura.emolumento
    );
  }

  protected loadRelationshipsOptions(): void {
    this.facturaService
      .query()
      .pipe(map((res: HttpResponse<IFactura[]>) => res.body ?? []))
      .pipe(
        map((facturas: IFactura[]) => this.facturaService.addFacturaToCollectionIfMissing<IFactura>(facturas, this.itemFactura?.factura))
      )
      .subscribe((facturas: IFactura[]) => (this.facturasSharedCollection = facturas));

    this.emolumentoService
      .query()
      .pipe(map((res: HttpResponse<IEmolumento[]>) => res.body ?? []))
      .pipe(
        map((emolumentos: IEmolumento[]) =>
          this.emolumentoService.addEmolumentoToCollectionIfMissing<IEmolumento>(emolumentos, this.itemFactura?.emolumento)
        )
      )
      .subscribe((emolumentos: IEmolumento[]) => (this.emolumentosSharedCollection = emolumentos));
  }
}
