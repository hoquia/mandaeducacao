import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';
import { PeriodoLancamentoNotaService } from '../service/periodo-lancamento-nota.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './periodo-lancamento-nota-delete-dialog.component.html',
})
export class PeriodoLancamentoNotaDeleteDialogComponent {
  periodoLancamentoNota?: IPeriodoLancamentoNota;

  constructor(protected periodoLancamentoNotaService: PeriodoLancamentoNotaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodoLancamentoNotaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
