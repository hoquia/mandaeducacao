import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILookupItem } from '../lookup-item.model';
import { LookupItemService } from '../service/lookup-item.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './lookup-item-delete-dialog.component.html',
})
export class LookupItemDeleteDialogComponent {
  lookupItem?: ILookupItem;

  constructor(protected lookupItemService: LookupItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lookupItemService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
