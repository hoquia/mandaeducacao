import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaEmolumento } from '../categoria-emolumento.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { CategoriaEmolumentoService } from '../service/categoria-emolumento.service';

@Component({
  selector: 'app-categoria-emolumento-detail',
  templateUrl: './categoria-emolumento-detail.component.html',
})
export class CategoriaEmolumentoDetailComponent implements OnInit {
  categoriaEmolumento: ICategoriaEmolumento | null = null;

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected categoriaEmolumentosService: CategoriaEmolumentoService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaEmolumento }) => {
      this.categoriaEmolumento = categoriaEmolumento;
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

  protected gerarFluxoCaixa(categoriaEmolumentoID: number): void {
    this.categoriaEmolumentosService.downloadFluxoCaixa(categoriaEmolumentoID).subscribe(res => {
      const url = window.URL.createObjectURL(res);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
      a.title = `categoria-emolumentos-${categoriaEmolumentoID}`;
      a.rel = 'noopener noreferrer';
      a.click();
      window.URL.revokeObjectURL(url);
      a.remove();
    });
  }
}
