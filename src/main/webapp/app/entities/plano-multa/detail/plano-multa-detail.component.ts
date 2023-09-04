import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoMulta } from '../plano-multa.model';

@Component({
  selector: 'app-plano-multa-detail',
  templateUrl: './plano-multa-detail.component.html',
})
export class PlanoMultaDetailComponent implements OnInit {
  planoMulta: IPlanoMulta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoMulta }) => {
      this.planoMulta = planoMulta;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
