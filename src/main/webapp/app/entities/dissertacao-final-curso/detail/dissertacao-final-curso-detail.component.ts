import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDissertacaoFinalCurso } from '../dissertacao-final-curso.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-dissertacao-final-curso-detail',
  templateUrl: './dissertacao-final-curso-detail.component.html',
})
export class DissertacaoFinalCursoDetailComponent implements OnInit {
  dissertacaoFinalCurso: IDissertacaoFinalCurso | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dissertacaoFinalCurso }) => {
      this.dissertacaoFinalCurso = dissertacaoFinalCurso;
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
