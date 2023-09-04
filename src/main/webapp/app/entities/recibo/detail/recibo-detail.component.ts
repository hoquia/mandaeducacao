import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecibo } from '../recibo.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-recibo-detail',
  templateUrl: './recibo-detail.component.html',
})
export class ReciboDetailComponent implements OnInit {
  recibo: IRecibo | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recibo }) => {
      this.recibo = recibo;
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
