import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransferenciaSaldo } from '../transferencia-saldo.model';
import { TransferenciaSaldoService } from '../service/transferencia-saldo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './transferencia-saldo-delete-dialog.component.html',
})
export class TransferenciaSaldoDeleteDialogComponent {
  transferenciaSaldo?: ITransferenciaSaldo;

  constructor(protected transferenciaSaldoService: TransferenciaSaldoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transferenciaSaldoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
