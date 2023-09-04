import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsavelTurma } from '../responsavel-turma.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-responsavel-turma-detail',
  templateUrl: './responsavel-turma-detail.component.html',
})
export class ResponsavelTurmaDetailComponent implements OnInit {
  responsavelTurma: IResponsavelTurma | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelTurma }) => {
      this.responsavelTurma = responsavelTurma;
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
