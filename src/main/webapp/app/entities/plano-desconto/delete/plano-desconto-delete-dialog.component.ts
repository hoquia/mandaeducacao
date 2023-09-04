import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlanoDesconto } from '../plano-desconto.model';
import { PlanoDescontoService } from '../service/plano-desconto.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './plano-desconto-delete-dialog.component.html',
})
export class PlanoDescontoDeleteDialogComponent {
  planoDesconto?: IPlanoDesconto;

  constructor(protected planoDescontoService: PlanoDescontoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoDescontoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
