import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoDissertacao } from '../estado-dissertacao.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-estado-dissertacao-detail',
  templateUrl: './estado-dissertacao-detail.component.html',
})
export class EstadoDissertacaoDetailComponent implements OnInit {
  estadoDissertacao: IEstadoDissertacao | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadoDissertacao }) => {
      this.estadoDissertacao = estadoDissertacao;
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
