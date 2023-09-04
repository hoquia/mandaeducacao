import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';

@Component({
  selector: 'app-notas-periodica-disciplina-detail',
  templateUrl: './notas-periodica-disciplina-detail.component.html',
})
export class NotasPeriodicaDisciplinaDetailComponent implements OnInit {
  notasPeriodicaDisciplina: INotasPeriodicaDisciplina | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notasPeriodicaDisciplina }) => {
      this.notasPeriodicaDisciplina = notasPeriodicaDisciplina;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
