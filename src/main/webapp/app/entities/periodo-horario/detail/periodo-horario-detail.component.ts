import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodoHorario } from '../periodo-horario.model';

@Component({
  selector: 'app-periodo-horario-detail',
  templateUrl: './periodo-horario-detail.component.html',
})
export class PeriodoHorarioDetailComponent implements OnInit {
  periodoHorario: IPeriodoHorario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodoHorario }) => {
      this.periodoHorario = periodoHorario;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
