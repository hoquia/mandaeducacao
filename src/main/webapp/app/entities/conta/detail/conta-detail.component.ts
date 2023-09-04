import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConta } from '../conta.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-conta-detail',
  templateUrl: './conta-detail.component.html',
})
export class ContaDetailComponent implements OnInit {
  conta: IConta | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conta }) => {
      this.conta = conta;
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
