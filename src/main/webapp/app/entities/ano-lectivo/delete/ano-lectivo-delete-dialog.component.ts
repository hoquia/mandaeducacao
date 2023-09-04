import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnoLectivo } from '../ano-lectivo.model';
import { AnoLectivoService } from '../service/ano-lectivo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './ano-lectivo-delete-dialog.component.html',
})
export class AnoLectivoDeleteDialogComponent {
  anoLectivo?: IAnoLectivo;

  constructor(protected anoLectivoService: AnoLectivoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.anoLectivoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
