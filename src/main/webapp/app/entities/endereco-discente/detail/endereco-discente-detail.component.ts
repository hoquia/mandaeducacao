import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnderecoDiscente } from '../endereco-discente.model';

@Component({
  selector: 'app-endereco-discente-detail',
  templateUrl: './endereco-discente-detail.component.html',
})
export class EnderecoDiscenteDetailComponent implements OnInit {
  enderecoDiscente: IEnderecoDiscente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enderecoDiscente }) => {
      this.enderecoDiscente = enderecoDiscente;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
