import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImposto } from '../imposto.model';

@Component({
  selector: 'app-imposto-detail',
  templateUrl: './imposto-detail.component.html',
})
export class ImpostoDetailComponent implements OnInit {
  imposto: IImposto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ imposto }) => {
      this.imposto = imposto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
