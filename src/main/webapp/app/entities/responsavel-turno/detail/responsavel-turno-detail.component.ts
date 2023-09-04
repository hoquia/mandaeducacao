import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResponsavelTurno } from '../responsavel-turno.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'app-responsavel-turno-detail',
  templateUrl: './responsavel-turno-detail.component.html',
})
export class ResponsavelTurnoDetailComponent implements OnInit {
  responsavelTurno: IResponsavelTurno | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ responsavelTurno }) => {
      this.responsavelTurno = responsavelTurno;
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
