import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnexoDiscente } from '../anexo-discente.model';
import { AnexoDiscenteService } from '../service/anexo-discente.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './anexo-discente-delete-dialog.component.html',
})
export class AnexoDiscenteDeleteDialogComponent {
  anexoDiscente?: IAnexoDiscente;

  constructor(protected anexoDiscenteService: AnexoDiscenteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anexoDiscenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
