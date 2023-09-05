import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmolumento } from '../emolumento.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { IPrecoEmolumento } from 'app/entities/preco-emolumento/preco-emolumento.model';
import { PrecoEmolumentoService } from 'app/entities/preco-emolumento/service/preco-emolumento.service';

@Component({
  selector: 'app-emolumento-detail',
  templateUrl: './emolumento-detail.component.html',
})
export class EmolumentoDetailComponent implements OnInit {
  emolumento: IEmolumento | null = null;
  precoEmolumentos?: IPrecoEmolumento[];

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected precoEmolumentoService: PrecoEmolumentoService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emolumento }) => {
      this.emolumento = emolumento;

      this.precoEmolumentoService.query({ 'emolumentoId.equals': emolumento.id }).subscribe(res => {
        this.precoEmolumentos = res.body ?? [];
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
}
