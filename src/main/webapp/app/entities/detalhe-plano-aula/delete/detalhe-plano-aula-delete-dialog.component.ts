import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetalhePlanoAula } from '../detalhe-plano-aula.model';
import { DetalhePlanoAulaService } from '../service/detalhe-plano-aula.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './detalhe-plano-aula-delete-dialog.component.html',
})
export class DetalhePlanoAulaDeleteDialogComponent {
  detalhePlanoAula?: IDetalhePlanoAula;

  constructor(protected detalhePlanoAulaService: DetalhePlanoAulaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.detalhePlanoAulaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
