import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentoComercial } from '../documento-comercial.model';

@Component({
  selector: 'app-documento-comercial-detail',
  templateUrl: './documento-comercial-detail.component.html',
})
export class DocumentoComercialDetailComponent implements OnInit {
  documentoComercial: IDocumentoComercial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentoComercial }) => {
      this.documentoComercial = documentoComercial;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
