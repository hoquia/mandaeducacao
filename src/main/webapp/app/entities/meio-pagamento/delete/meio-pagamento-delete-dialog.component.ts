import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMeioPagamento } from '../meio-pagamento.model';
import { MeioPagamentoService } from '../service/meio-pagamento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './meio-pagamento-delete-dialog.component.html',
})
export class MeioPagamentoDeleteDialogComponent {
  meioPagamento?: IMeioPagamento;

  constructor(protected meioPagamentoService: MeioPagamentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.meioPagamentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
