import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumoImpostoFactura } from '../resumo-imposto-factura.model';

@Component({
  selector: 'app-resumo-imposto-factura-detail',
  templateUrl: './resumo-imposto-factura-detail.component.html',
})
export class ResumoImpostoFacturaDetailComponent implements OnInit {
  resumoImpostoFactura: IResumoImpostoFactura | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumoImpostoFactura }) => {
      this.resumoImpostoFactura = resumoImpostoFactura;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
