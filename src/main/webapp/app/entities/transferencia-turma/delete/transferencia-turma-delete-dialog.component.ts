import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransferenciaTurma } from '../transferencia-turma.model';
import { TransferenciaTurmaService } from '../service/transferencia-turma.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './transferencia-turma-delete-dialog.component.html',
})
export class TransferenciaTurmaDeleteDialogComponent {
  transferenciaTurma?: ITransferenciaTurma;

  constructor(protected transferenciaTurmaService: TransferenciaTurmaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transferenciaTurmaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
