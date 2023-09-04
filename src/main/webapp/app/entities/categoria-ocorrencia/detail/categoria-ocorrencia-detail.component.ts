import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaOcorrencia } from '../categoria-ocorrencia.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-categoria-ocorrencia-detail',
  templateUrl: './categoria-ocorrencia-detail.component.html',
})
export class CategoriaOcorrenciaDetailComponent implements OnInit {
  categoriaOcorrencia: ICategoriaOcorrencia | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaOcorrencia }) => {
      this.categoriaOcorrencia = categoriaOcorrencia;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
