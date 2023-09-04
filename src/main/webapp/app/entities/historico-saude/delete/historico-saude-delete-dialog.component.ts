import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistoricoSaude } from '../historico-saude.model';
import { HistoricoSaudeService } from '../service/historico-saude.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './historico-saude-delete-dialog.component.html',
})
export class HistoricoSaudeDeleteDialogComponent {
  historicoSaude?: IHistoricoSaude;

  constructor(protected historicoSaudeService: HistoricoSaudeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historicoSaudeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
