import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoDesconto } from '../plano-desconto.model';

@Component({
  selector: 'app-plano-desconto-detail',
  templateUrl: './plano-desconto-detail.component.html',
})
export class PlanoDescontoDetailComponent implements OnInit {
  planoDesconto: IPlanoDesconto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoDesconto }) => {
      this.planoDesconto = planoDesconto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
