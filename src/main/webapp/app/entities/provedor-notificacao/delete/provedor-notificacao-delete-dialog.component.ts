import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProvedorNotificacao } from '../provedor-notificacao.model';
import { ProvedorNotificacaoService } from '../service/provedor-notificacao.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './provedor-notificacao-delete-dialog.component.html',
})
export class ProvedorNotificacaoDeleteDialogComponent {
  provedorNotificacao?: IProvedorNotificacao;

  constructor(protected provedorNotificacaoService: ProvedorNotificacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.provedorNotificacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
