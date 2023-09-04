import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlanoAula } from '../plano-aula.model';
import { PlanoAulaService } from '../service/plano-aula.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './plano-aula-delete-dialog.component.html',
})
export class PlanoAulaDeleteDialogComponent {
  planoAula?: IPlanoAula;

  constructor(protected planoAulaService: PlanoAulaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoAulaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
