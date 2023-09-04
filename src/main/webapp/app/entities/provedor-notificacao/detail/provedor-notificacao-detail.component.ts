import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvedorNotificacao } from '../provedor-notificacao.model';

@Component({
  selector: 'app-provedor-notificacao-detail',
  templateUrl: './provedor-notificacao-detail.component.html',
})
export class ProvedorNotificacaoDetailComponent implements OnInit {
  provedorNotificacao: IProvedorNotificacao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ provedorNotificacao }) => {
      this.provedorNotificacao = provedorNotificacao;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
