import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOcorrencia } from '../ocorrencia.model';
import { OcorrenciaService } from '../service/ocorrencia.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './ocorrencia-delete-dialog.component.html',
})
export class OcorrenciaDeleteDialogComponent {
  ocorrencia?: IOcorrencia;

  constructor(protected ocorrenciaService: OcorrenciaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ocorrenciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
