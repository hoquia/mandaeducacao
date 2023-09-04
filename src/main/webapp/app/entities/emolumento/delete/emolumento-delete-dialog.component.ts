import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmolumento } from '../emolumento.model';
import { EmolumentoService } from '../service/emolumento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './emolumento-delete-dialog.component.html',
})
export class EmolumentoDeleteDialogComponent {
  emolumento?: IEmolumento;

  constructor(protected emolumentoService: EmolumentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emolumentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
