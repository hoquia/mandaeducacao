import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedidaDisciplinar } from '../medida-disciplinar.model';

@Component({
  selector: 'app-medida-disciplinar-detail',
  templateUrl: './medida-disciplinar-detail.component.html',
})
export class MedidaDisciplinarDetailComponent implements OnInit {
  medidaDisciplinar: IMedidaDisciplinar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medidaDisciplinar }) => {
      this.medidaDisciplinar = medidaDisciplinar;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
