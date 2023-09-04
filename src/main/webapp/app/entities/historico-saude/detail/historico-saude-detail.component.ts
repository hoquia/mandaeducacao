import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistoricoSaude } from '../historico-saude.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-historico-saude-detail',
  templateUrl: './historico-saude-detail.component.html',
})
export class HistoricoSaudeDetailComponent implements OnInit {
  historicoSaude: IHistoricoSaude | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historicoSaude }) => {
      this.historicoSaude = historicoSaude;
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
