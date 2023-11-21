import { EmolumentoService } from 'app/entities/emolumento/service/emolumento.service';
import { IEmolumento } from './../../emolumento/emolumento.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITurma } from '../turma.model';
import { TurmaService } from '../service/turma.service';

@Component({
  selector: 'app-turma-detail',
  templateUrl: './turma-detail.component.html',
})
export class TurmaDetailComponent implements OnInit {
  turma: ITurma | null = null;
  emolumentosSharedCollection: IEmolumento[] = [];
  emolumentoSelecionadoID = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected turmaService: TurmaService,
    protected emolumentoService: EmolumentoService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ turma }) => {
      this.turma = turma;

      this.emolumentoService.query().subscribe(res => {
        this.emolumentosSharedCollection = res.body ?? [];
      });
    });
  }

  previousState(): void {
    window.history.back();
  }

  protected gerarListaPresenca(turmaID: number): void {
    this.turmaService.downloadListaPresencaPdf(turmaID).subscribe(res => {
      const url = window.URL.createObjectURL(res);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      a.title = `lista-presenca-turma-${turmaID}`;
      a.rel = 'noopener noreferrer';
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });
  }

  protected gerarHorarioDiscente(turmaID: number): void {
    this.turmaService.downloadHorarioDiscentePdf(turmaID).subscribe(res => {
      const url = window.URL.createObjectURL(res);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      a.title = `horario-discente-${turmaID}`;
      a.rel = 'noopener noreferrer';
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });
  }

  protected gerarListaPagoNaoPago(turmaID: number): void {
    this.turmaService.downloadListaPagoNaoPagoPdf(turmaID, this.emolumentoSelecionadoID).subscribe(res => {
      const url = window.URL.createObjectURL(res);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      a.title = `lista-pago-nao-pago-turma-${turmaID}`;
      a.rel = 'noopener noreferrer';
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });
  }

  protected gerarEstratoFinanceiro(turmaID: number): void {
    this.turmaService.downloadEstratoFinanceiro(turmaID, this.emolumentoSelecionadoID).subscribe(res => {
      const url = window.URL.createObjectURL(res);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      a.title = `estrato-financeiro-turma-${turmaID}`;
      a.rel = 'noopener noreferrer';
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });
  }
}
