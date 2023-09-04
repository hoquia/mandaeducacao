import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IImposto } from '../imposto.model';
import { ImpostoService } from '../service/imposto.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './imposto-delete-dialog.component.html',
})
export class ImpostoDeleteDialogComponent {
  imposto?: IImposto;

  constructor(protected impostoService: ImpostoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.impostoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
