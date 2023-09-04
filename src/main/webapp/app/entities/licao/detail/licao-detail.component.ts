import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicao } from '../licao.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-licao-detail',
  templateUrl: './licao-detail.component.html',
})
export class LicaoDetailComponent implements OnInit {
  licao: ILicao | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licao }) => {
      this.licao = licao;
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
