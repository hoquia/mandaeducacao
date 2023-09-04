import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAreaFormacao } from '../area-formacao.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-area-formacao-detail',
  templateUrl: './area-formacao-detail.component.html',
})
export class AreaFormacaoDetailComponent implements OnInit {
  areaFormacao: IAreaFormacao | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaFormacao }) => {
      this.areaFormacao = areaFormacao;
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
