import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDissertacaoFinalCurso } from '../dissertacao-final-curso.model';
import { DissertacaoFinalCursoService } from '../service/dissertacao-final-curso.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './dissertacao-final-curso-delete-dialog.component.html',
})
export class DissertacaoFinalCursoDeleteDialogComponent {
  dissertacaoFinalCurso?: IDissertacaoFinalCurso;

  constructor(protected dissertacaoFinalCursoService: DissertacaoFinalCursoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dissertacaoFinalCursoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
