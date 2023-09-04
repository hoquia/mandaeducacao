import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnoLectivo } from '../ano-lectivo.model';

@Component({
  selector: 'app-ano-lectivo-detail',
  templateUrl: './ano-lectivo-detail.component.html',
})
export class AnoLectivoDetailComponent implements OnInit {
  anoLectivo: IAnoLectivo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anoLectivo }) => {
      this.anoLectivo = anoLectivo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
