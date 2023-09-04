import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResponsavelTurno } from '../responsavel-turno.model';
import { ResponsavelTurnoService } from '../service/responsavel-turno.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './responsavel-turno-delete-dialog.component.html',
})
export class ResponsavelTurnoDeleteDialogComponent {
  responsavelTurno?: IResponsavelTurno;

  constructor(protected responsavelTurnoService: ResponsavelTurnoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelTurnoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
