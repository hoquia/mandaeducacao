import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INaturezaTrabalho } from '../natureza-trabalho.model';
import { NaturezaTrabalhoService } from '../service/natureza-trabalho.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './natureza-trabalho-delete-dialog.component.html',
})
export class NaturezaTrabalhoDeleteDialogComponent {
  naturezaTrabalho?: INaturezaTrabalho;

  constructor(protected naturezaTrabalhoService: NaturezaTrabalhoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.naturezaTrabalhoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
