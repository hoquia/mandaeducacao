import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlanoMulta } from '../plano-multa.model';
import { PlanoMultaService } from '../service/plano-multa.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './plano-multa-delete-dialog.component.html',
})
export class PlanoMultaDeleteDialogComponent {
  planoMulta?: IPlanoMulta;

  constructor(protected planoMultaService: PlanoMultaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoMultaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
