import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOcorrencia } from '../ocorrencia.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-ocorrencia-detail',
  templateUrl: './ocorrencia-detail.component.html',
})
export class OcorrenciaDetailComponent implements OnInit {
  ocorrencia: IOcorrencia | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ocorrencia }) => {
      this.ocorrencia = ocorrencia;
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
