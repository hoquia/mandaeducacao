import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResponsavelCurso } from '../responsavel-curso.model';
import { ResponsavelCursoService } from '../service/responsavel-curso.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './responsavel-curso-delete-dialog.component.html',
})
export class ResponsavelCursoDeleteDialogComponent {
  responsavelCurso?: IResponsavelCurso;

  constructor(protected responsavelCursoService: ResponsavelCursoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelCursoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
