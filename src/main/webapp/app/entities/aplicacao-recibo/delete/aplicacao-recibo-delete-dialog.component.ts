import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAplicacaoRecibo } from '../aplicacao-recibo.model';
import { AplicacaoReciboService } from '../service/aplicacao-recibo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './aplicacao-recibo-delete-dialog.component.html',
})
export class AplicacaoReciboDeleteDialogComponent {
  aplicacaoRecibo?: IAplicacaoRecibo;

  constructor(protected aplicacaoReciboService: AplicacaoReciboService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aplicacaoReciboService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
