import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFormacaoDocente } from '../formacao-docente.model';
import { FormacaoDocenteService } from '../service/formacao-docente.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './formacao-docente-delete-dialog.component.html',
})
export class FormacaoDocenteDeleteDialogComponent {
  formacaoDocente?: IFormacaoDocente;

  constructor(protected formacaoDocenteService: FormacaoDocenteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.formacaoDocenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
