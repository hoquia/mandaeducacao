import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INivelEnsino } from '../nivel-ensino.model';
import { NivelEnsinoService } from '../service/nivel-ensino.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nivel-ensino-delete-dialog.component.html',
})
export class NivelEnsinoDeleteDialogComponent {
  nivelEnsino?: INivelEnsino;

  constructor(protected nivelEnsinoService: NivelEnsinoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nivelEnsinoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
