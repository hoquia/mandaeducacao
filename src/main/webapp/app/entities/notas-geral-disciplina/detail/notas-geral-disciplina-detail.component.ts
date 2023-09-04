import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotasGeralDisciplina } from '../notas-geral-disciplina.model';

@Component({
  selector: 'app-notas-geral-disciplina-detail',
  templateUrl: './notas-geral-disciplina-detail.component.html',
})
export class NotasGeralDisciplinaDetailComponent implements OnInit {
  notasGeralDisciplina: INotasGeralDisciplina | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notasGeralDisciplina }) => {
      this.notasGeralDisciplina = notasGeralDisciplina;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
