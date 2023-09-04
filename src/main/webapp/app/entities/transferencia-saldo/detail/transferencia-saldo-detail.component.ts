import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransferenciaSaldo } from '../transferencia-saldo.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-transferencia-saldo-detail',
  templateUrl: './transferencia-saldo-detail.component.html',
})
export class TransferenciaSaldoDetailComponent implements OnInit {
  transferenciaSaldo: ITransferenciaSaldo | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferenciaSaldo }) => {
      this.transferenciaSaldo = transferenciaSaldo;
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
