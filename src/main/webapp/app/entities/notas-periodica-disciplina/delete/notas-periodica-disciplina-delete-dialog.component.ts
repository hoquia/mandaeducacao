import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';
import { NotasPeriodicaDisciplinaService } from '../service/notas-periodica-disciplina.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './notas-periodica-disciplina-delete-dialog.component.html',
})
export class NotasPeriodicaDisciplinaDeleteDialogComponent {
  notasPeriodicaDisciplina?: INotasPeriodicaDisciplina;

  constructor(protected notasPeriodicaDisciplinaService: NotasPeriodicaDisciplinaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notasPeriodicaDisciplinaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
