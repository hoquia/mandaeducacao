import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumoAcademico } from '../resumo-academico.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-resumo-academico-detail',
  templateUrl: './resumo-academico-detail.component.html',
})
export class ResumoAcademicoDetailComponent implements OnInit {
  resumoAcademico: IResumoAcademico | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resumoAcademico }) => {
      this.resumoAcademico = resumoAcademico;
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
