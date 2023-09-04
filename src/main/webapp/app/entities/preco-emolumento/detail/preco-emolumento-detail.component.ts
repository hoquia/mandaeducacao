import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrecoEmolumento } from '../preco-emolumento.model';

@Component({
  selector: 'app-preco-emolumento-detail',
  templateUrl: './preco-emolumento-detail.component.html',
})
export class PrecoEmolumentoDetailComponent implements OnInit {
  precoEmolumento: IPrecoEmolumento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ precoEmolumento }) => {
      this.precoEmolumento = precoEmolumento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
