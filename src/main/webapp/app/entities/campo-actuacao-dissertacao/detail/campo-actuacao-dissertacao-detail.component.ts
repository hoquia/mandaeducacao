import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-campo-actuacao-dissertacao-detail',
  templateUrl: './campo-actuacao-dissertacao-detail.component.html',
})
export class CampoActuacaoDissertacaoDetailComponent implements OnInit {
  campoActuacaoDissertacao: ICampoActuacaoDissertacao | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campoActuacaoDissertacao }) => {
      this.campoActuacaoDissertacao = campoActuacaoDissertacao;
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
