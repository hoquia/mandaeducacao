import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFactura } from '../factura.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { IItemFactura } from 'app/entities/item-factura/item-factura.model';
import { ItemFacturaService } from 'app/entities/item-factura/service/item-factura.service';

@Component({
  selector: 'app-factura-detail',
  templateUrl: './factura-detail.component.html',
})
export class FacturaDetailComponent implements OnInit {
  factura: IFactura | null = null;
  itemFacturas?: IItemFactura[];

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute, protected itemFacturaService: ItemFacturaService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factura }) => {
      this.factura = factura;

      this.itemFacturaService.query({ 'facturaId.equals': factura.id }).subscribe(res => {
        this.itemFacturas = res.body ?? [];
      });
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
