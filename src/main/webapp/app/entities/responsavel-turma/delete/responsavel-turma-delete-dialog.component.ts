import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResponsavelTurma } from '../responsavel-turma.model';
import { ResponsavelTurmaService } from '../service/responsavel-turma.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './responsavel-turma-delete-dialog.component.html',
})
export class ResponsavelTurmaDeleteDialogComponent {
  responsavelTurma?: IResponsavelTurma;

  constructor(protected responsavelTurmaService: ResponsavelTurmaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelTurmaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
