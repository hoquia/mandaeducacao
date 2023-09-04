import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisciplinaCurricular } from '../disciplina-curricular.model';

@Component({
  selector: 'app-disciplina-curricular-detail',
  templateUrl: './disciplina-curricular-detail.component.html',
})
export class DisciplinaCurricularDetailComponent implements OnInit {
  disciplinaCurricular: IDisciplinaCurricular | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplinaCurricular }) => {
      this.disciplinaCurricular = disciplinaCurricular;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
