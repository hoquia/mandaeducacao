import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnexoDiscente } from '../anexo-discente.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-anexo-discente-detail',
  templateUrl: './anexo-discente-detail.component.html',
})
export class AnexoDiscenteDetailComponent implements OnInit {
  anexoDiscente: IAnexoDiscente | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anexoDiscente }) => {
      this.anexoDiscente = anexoDiscente;
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
