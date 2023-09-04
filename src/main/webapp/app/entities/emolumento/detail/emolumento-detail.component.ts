import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmolumento } from '../emolumento.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-emolumento-detail',
  templateUrl: './emolumento-detail.component.html',
})
export class EmolumentoDetailComponent implements OnInit {
  emolumento: IEmolumento | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emolumento }) => {
      this.emolumento = emolumento;
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
