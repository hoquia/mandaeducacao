import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResponsavelDisciplina } from '../responsavel-disciplina.model';
import { ResponsavelDisciplinaService } from '../service/responsavel-disciplina.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './responsavel-disciplina-delete-dialog.component.html',
})
export class ResponsavelDisciplinaDeleteDialogComponent {
  responsavelDisciplina?: IResponsavelDisciplina;

  constructor(protected responsavelDisciplinaService: ResponsavelDisciplinaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelDisciplinaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
