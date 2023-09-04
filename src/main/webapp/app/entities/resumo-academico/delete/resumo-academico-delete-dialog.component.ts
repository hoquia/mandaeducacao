import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumoAcademico } from '../resumo-academico.model';
import { ResumoAcademicoService } from '../service/resumo-academico.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './resumo-academico-delete-dialog.component.html',
})
export class ResumoAcademicoDeleteDialogComponent {
  resumoAcademico?: IResumoAcademico;

  constructor(protected resumoAcademicoService: ResumoAcademicoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumoAcademicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
