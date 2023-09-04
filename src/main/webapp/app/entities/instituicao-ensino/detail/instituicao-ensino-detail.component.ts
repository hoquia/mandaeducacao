import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-instituicao-ensino-detail',
  templateUrl: './instituicao-ensino-detail.component.html',
})
export class InstituicaoEnsinoDetailComponent implements OnInit {
  instituicaoEnsino: IInstituicaoEnsino | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instituicaoEnsino }) => {
      this.instituicaoEnsino = instituicaoEnsino;
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
