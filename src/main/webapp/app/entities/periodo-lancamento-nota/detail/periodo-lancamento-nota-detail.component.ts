import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';

@Component({
  selector: 'app-periodo-lancamento-nota-detail',
  templateUrl: './periodo-lancamento-nota-detail.component.html',
})
export class PeriodoLancamentoNotaDetailComponent implements OnInit {
  periodoLancamentoNota: IPeriodoLancamentoNota | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodoLancamentoNota }) => {
      this.periodoLancamentoNota = periodoLancamentoNota;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
