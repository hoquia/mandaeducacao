import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';
import { CampoActuacaoDissertacaoService } from '../service/campo-actuacao-dissertacao.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './campo-actuacao-dissertacao-delete-dialog.component.html',
})
export class CampoActuacaoDissertacaoDeleteDialogComponent {
  campoActuacaoDissertacao?: ICampoActuacaoDissertacao;

  constructor(protected campoActuacaoDissertacaoService: CampoActuacaoDissertacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.campoActuacaoDissertacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
