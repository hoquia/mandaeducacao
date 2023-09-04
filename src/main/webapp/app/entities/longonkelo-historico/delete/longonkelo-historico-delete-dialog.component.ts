import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILongonkeloHistorico } from '../longonkelo-historico.model';
import { LongonkeloHistoricoService } from '../service/longonkelo-historico.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './longonkelo-historico-delete-dialog.component.html',
})
export class LongonkeloHistoricoDeleteDialogComponent {
  longonkeloHistorico?: ILongonkeloHistorico;

  constructor(protected longonkeloHistoricoService: LongonkeloHistoricoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.longonkeloHistoricoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
