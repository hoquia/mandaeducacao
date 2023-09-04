import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcessoSelectivoMatricula } from '../processo-selectivo-matricula.model';

@Component({
  selector: 'app-processo-selectivo-matricula-detail',
  templateUrl: './processo-selectivo-matricula-detail.component.html',
})
export class ProcessoSelectivoMatriculaDetailComponent implements OnInit {
  processoSelectivoMatricula: IProcessoSelectivoMatricula | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processoSelectivoMatricula }) => {
      this.processoSelectivoMatricula = processoSelectivoMatricula;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
