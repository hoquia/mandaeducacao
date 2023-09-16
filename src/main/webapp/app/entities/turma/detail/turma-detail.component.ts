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

  constructor(protected activatedRoute: ActivatedRoute, protected turmaService: TurmaService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ turma }) => {
      this.turma = turma;
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
}
