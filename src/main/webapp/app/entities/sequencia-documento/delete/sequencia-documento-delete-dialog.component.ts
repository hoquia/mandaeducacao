import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISequenciaDocumento } from '../sequencia-documento.model';
import { SequenciaDocumentoService } from '../service/sequencia-documento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './sequencia-documento-delete-dialog.component.html',
})
export class SequenciaDocumentoDeleteDialogComponent {
  sequenciaDocumento?: ISequenciaDocumento;

  constructor(protected sequenciaDocumentoService: SequenciaDocumentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sequenciaDocumentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
