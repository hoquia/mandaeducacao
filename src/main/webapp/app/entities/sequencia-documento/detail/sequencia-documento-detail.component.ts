import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISequenciaDocumento } from '../sequencia-documento.model';

@Component({
  selector: 'app-sequencia-documento-detail',
  templateUrl: './sequencia-documento-detail.component.html',
})
export class SequenciaDocumentoDetailComponent implements OnInit {
  sequenciaDocumento: ISequenciaDocumento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sequenciaDocumento }) => {
      this.sequenciaDocumento = sequenciaDocumento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
