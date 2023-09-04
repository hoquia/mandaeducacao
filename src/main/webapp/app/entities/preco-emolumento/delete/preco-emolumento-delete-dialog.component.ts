import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrecoEmolumento } from '../preco-emolumento.model';
import { PrecoEmolumentoService } from '../service/preco-emolumento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './preco-emolumento-delete-dialog.component.html',
})
export class PrecoEmolumentoDeleteDialogComponent {
  precoEmolumento?: IPrecoEmolumento;

  constructor(protected precoEmolumentoService: PrecoEmolumentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.precoEmolumentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
