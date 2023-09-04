import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsavelDisciplina } from '../responsavel-disciplina.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-responsavel-disciplina-detail',
  templateUrl: './responsavel-disciplina-detail.component.html',
})
export class ResponsavelDisciplinaDetailComponent implements OnInit {
  responsavelDisciplina: IResponsavelDisciplina | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelDisciplina }) => {
      this.responsavelDisciplina = responsavelDisciplina;
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
