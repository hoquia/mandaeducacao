import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransferenciaTurma } from '../transferencia-turma.model';

@Component({
  selector: 'app-transferencia-turma-detail',
  templateUrl: './transferencia-turma-detail.component.html',
})
export class TransferenciaTurmaDetailComponent implements OnInit {
  transferenciaTurma: ITransferenciaTurma | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferenciaTurma }) => {
      this.transferenciaTurma = transferenciaTurma;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
