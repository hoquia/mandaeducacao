import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAplicacaoRecibo } from '../aplicacao-recibo.model';

@Component({
  selector: 'app-aplicacao-recibo-detail',
  templateUrl: './aplicacao-recibo-detail.component.html',
})
export class AplicacaoReciboDetailComponent implements OnInit {
  aplicacaoRecibo: IAplicacaoRecibo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aplicacaoRecibo }) => {
      this.aplicacaoRecibo = aplicacaoRecibo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
