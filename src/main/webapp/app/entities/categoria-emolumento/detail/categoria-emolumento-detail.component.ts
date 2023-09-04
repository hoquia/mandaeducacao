import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaEmolumento } from '../categoria-emolumento.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-categoria-emolumento-detail',
  templateUrl: './categoria-emolumento-detail.component.html',
})
export class CategoriaEmolumentoDetailComponent implements OnInit {
  categoriaEmolumento: ICategoriaEmolumento | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

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
}
