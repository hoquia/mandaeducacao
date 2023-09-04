import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INotasGeralDisciplina } from '../notas-geral-disciplina.model';
import { NotasGeralDisciplinaService } from '../service/notas-geral-disciplina.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './notas-geral-disciplina-delete-dialog.component.html',
})
export class NotasGeralDisciplinaDeleteDialogComponent {
  notasGeralDisciplina?: INotasGeralDisciplina;

  constructor(protected notasGeralDisciplinaService: NotasGeralDisciplinaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notasGeralDisciplinaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
