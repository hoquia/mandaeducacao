import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatricula } from '../matricula.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { IFactura } from 'app/entities/factura/factura.model';
import { FacturaService } from 'app/entities/factura/service/factura.service';
import { IRecibo } from 'app/entities/recibo/recibo.model';
import { ReciboService } from 'app/entities/recibo/service/recibo.service';

@Component({
  selector: 'app-matricula-detail',
  templateUrl: './matricula-detail.component.html',
})
export class MatriculaDetailComponent implements OnInit {
  matricula: IMatricula | null = null;
  facturas?: IFactura[];
  recibos?: IRecibo[];

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected facturaService: FacturaService,
    protected reciboService: ReciboService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matricula }) => {
      this.matricula = matricula;
      this.facturaService.query({ 'matriculaId.equals': matricula.id }).subscribe(res => {
        this.facturas = res.body ?? [];
      });

      this.reciboService.query({ 'matriculaId.equals': matricula.id }).subscribe(res => {
        this.recibos = res.body ?? [];
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
