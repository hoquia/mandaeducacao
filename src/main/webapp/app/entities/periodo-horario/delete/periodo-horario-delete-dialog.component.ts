import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriodoHorario } from '../periodo-horario.model';
import { PeriodoHorarioService } from '../service/periodo-horario.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './periodo-horario-delete-dialog.component.html',
})
export class PeriodoHorarioDeleteDialogComponent {
  periodoHorario?: IPeriodoHorario;

  constructor(protected periodoHorarioService: PeriodoHorarioService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodoHorarioService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
