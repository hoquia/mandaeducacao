import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoAula } from '../plano-aula.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { IDetalhePlanoAula } from 'app/entities/detalhe-plano-aula/detalhe-plano-aula.model';
import { DetalhePlanoAulaService } from 'app/entities/detalhe-plano-aula/service/detalhe-plano-aula.service';
import { PlanoAulaService } from '../service/plano-aula.service';

@Component({
  selector: 'app-plano-aula-detail',
  templateUrl: './plano-aula-detail.component.html',
})
export class PlanoAulaDetailComponent implements OnInit {
  planoAula: IPlanoAula | null = null;
  detalhePlanoAulas: IDetalhePlanoAula[] = [];

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected detalhePlanoAulaService: DetalhePlanoAulaService,
    protected planoAulaService: PlanoAulaService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAula }) => {
      this.planoAula = planoAula;

      this.detalhePlanoAulaService.query({ 'planoAulaId.equals': planoAula.id }).subscribe(res => {
        this.detalhePlanoAulas = res.body ?? [];
      });
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

  protected printPlanoDiarioAula(planoAulaId: number): void {
    this.planoAulaService.downloadPlanoAulaPdf(planoAulaId).subscribe(res => {
      const url = window.URL.createObjectURL(res);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      a.title = `plano-diario-aulas-${planoAulaId}`;
      a.rel = 'noopener noreferrer';
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });
  }
}
