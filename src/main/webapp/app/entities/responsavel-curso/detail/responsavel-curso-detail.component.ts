import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsavelCurso } from '../responsavel-curso.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-responsavel-curso-detail',
  templateUrl: './responsavel-curso-detail.component.html',
})
export class ResponsavelCursoDetailComponent implements OnInit {
  responsavelCurso: IResponsavelCurso | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelCurso }) => {
      this.responsavelCurso = responsavelCurso;
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
