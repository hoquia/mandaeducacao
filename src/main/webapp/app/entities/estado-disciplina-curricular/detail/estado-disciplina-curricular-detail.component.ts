import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoDisciplinaCurricular } from '../estado-disciplina-curricular.model';

@Component({
  selector: 'app-estado-disciplina-curricular-detail',
  templateUrl: './estado-disciplina-curricular-detail.component.html',
})
export class EstadoDisciplinaCurricularDetailComponent implements OnInit {
  estadoDisciplinaCurricular: IEstadoDisciplinaCurricular | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoDisciplinaCurricular }) => {
      this.estadoDisciplinaCurricular = estadoDisciplinaCurricular;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
