import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AplicacaoReciboFormService, AplicacaoReciboFormGroup } from './aplicacao-recibo-form.service';
import { IAplicacaoRecibo } from '../aplicacao-recibo.model';
import { AplicacaoReciboService } from '../service/aplicacao-recibo.service';
import { IItemFactura } from 'app/entities/item-factura/item-factura.model';
import { ItemFacturaService } from 'app/entities/item-factura/service/item-factura.service';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IRecibo } from 'app/entities/recibo/recibo.model';
import { ReciboService } from 'app/entities/recibo/service/recibo.service';

@Component({
  selector: 'app-aplicacao-recibo-update',
  templateUrl: './aplicacao-recibo-update.component.html',
})
export class AplicacaoReciboUpdateComponent implements OnInit {
  isSaving = false;
  aplicacaoRecibo: IAplicacaoRecibo | null = null;

  itemFacturasSharedCollection: IItemFactura[] = [];
  facturasSharedCollection: IFactura[] = [];
  recibosSharedCollection: IRecibo[] = [];

  editForm: AplicacaoReciboFormGroup = this.aplicacaoReciboFormService.createAplicacaoReciboFormGroup();

  constructor(
    protected aplicacaoReciboService: AplicacaoReciboService,
    protected aplicacaoReciboFormService: AplicacaoReciboFormService,
    protected itemFacturaService: ItemFacturaService,
    protected facturaService: FacturaService,
    protected reciboService: ReciboService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router
  ) {}

  compareItemFactura = (o1: IItemFactura | null, o2: IItemFactura | null): boolean => this.itemFacturaService.compareItemFactura(o1, o2);

  compareFactura = (o1: IFactura | null, o2: IFactura | null): boolean => this.facturaService.compareFactura(o1, o2);

  compareRecibo = (o1: IRecibo | null, o2: IRecibo | null): boolean => this.reciboService.compareRecibo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aplicacaoRecibo }) => {
      this.aplicacaoRecibo = aplicacaoRecibo;
      if (aplicacaoRecibo) {
        this.updateForm(aplicacaoRecibo);
      } else {
        const reciboID = Number(this.activatedRoute.snapshot.queryParamMap.get('recibo_id'));

        this.reciboService.find(reciboID).subscribe(res => {
          const matriculaID = res.body?.matricula?.id;

          this.facturaService.query({ size: 10000 }).subscribe(resFac => {
            this.facturasSharedCollection = resFac.body?.filter(x => x.matricula?.id === matriculaID) ?? [];

            this.editForm.patchValue({
              recibo: res.body,
            });
          });
        });

        this.editForm.patchValue({
          totalFactura: 0,
          totalPago: 0,
          totalDiferenca: 0,
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
    const aplicacaoRecibo = this.aplicacaoReciboFormService.getAplicacaoRecibo(this.editForm);
    if (aplicacaoRecibo.id !== null) {
      this.subscribeToSaveResponse(this.aplicacaoReciboService.update(aplicacaoRecibo));
    } else {
      this.subscribeToSaveResponse(this.aplicacaoReciboService.create(aplicacaoRecibo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAplicacaoRecibo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: e => this.onSaveSuccess(e.body?.factura?.matricula?.id),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(id: any): void {
    this.router.navigate(['/matricula', id, 'view']);
    // this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(aplicacaoRecibo: IAplicacaoRecibo): void {
    this.aplicacaoRecibo = aplicacaoRecibo;
    this.aplicacaoReciboFormService.resetForm(this.editForm, aplicacaoRecibo);

    this.itemFacturasSharedCollection = this.itemFacturaService.addItemFacturaToCollectionIfMissing<IItemFactura>(
      this.itemFacturasSharedCollection,
      aplicacaoRecibo.itemFactura
    );
    this.facturasSharedCollection = this.facturaService.addFacturaToCollectionIfMissing<IFactura>(
      this.facturasSharedCollection,
      aplicacaoRecibo.factura
    );
    this.recibosSharedCollection = this.reciboService.addReciboToCollectionIfMissing<IRecibo>(
      this.recibosSharedCollection,
      aplicacaoRecibo.recibo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.facturaService
      .query()
      .pipe(map((res: HttpResponse<IFactura[]>) => res.body ?? []))
      .pipe(
        map((facturas: IFactura[]) =>
          this.facturaService.addFacturaToCollectionIfMissing<IFactura>(facturas, this.aplicacaoRecibo?.factura)
        )
      )
      .subscribe((facturas: IFactura[]) => (this.facturasSharedCollection = facturas));

    this.reciboService
      .query()
      .pipe(map((res: HttpResponse<IRecibo[]>) => res.body ?? []))
      .pipe(map((recibos: IRecibo[]) => this.reciboService.addReciboToCollectionIfMissing<IRecibo>(recibos, this.aplicacaoRecibo?.recibo)))
      .subscribe((recibos: IRecibo[]) => (this.recibosSharedCollection = recibos));
  }

  // protected searchItemFactura(): void {
  //   const id = Number(this.editForm.get('factura')?.value?.id);
  //   this.itemFacturaService.query({ 'facturaId.equals': id }).subscribe(res => {
  //     this.itemFacturasSharedCollection = res.body ?? [];
  //   });
  // }

  protected getItemFactura(): void {
    const id = Number(this.editForm.get('factura')?.value?.id);
    this.itemFacturaService.query({ size: 100000000 }).subscribe(res => {
      this.itemFacturasSharedCollection = res.body?.filter(x => x.factura?.id === id) ?? [];
    });
  }
}
