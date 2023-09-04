import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IItemFactura } from '../item-factura.model';
import { ItemFacturaService } from '../service/item-factura.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './item-factura-delete-dialog.component.html',
})
export class ItemFacturaDeleteDialogComponent {
  itemFactura?: IItemFactura;

  constructor(protected itemFacturaService: ItemFacturaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemFacturaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
