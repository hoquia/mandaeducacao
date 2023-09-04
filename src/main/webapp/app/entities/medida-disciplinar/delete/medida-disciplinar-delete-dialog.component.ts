import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedidaDisciplinar } from '../medida-disciplinar.model';
import { MedidaDisciplinarService } from '../service/medida-disciplinar.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './medida-disciplinar-delete-dialog.component.html',
})
export class MedidaDisciplinarDeleteDialogComponent {
  medidaDisciplinar?: IMedidaDisciplinar;

  constructor(protected medidaDisciplinarService: MedidaDisciplinarService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medidaDisciplinarService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
