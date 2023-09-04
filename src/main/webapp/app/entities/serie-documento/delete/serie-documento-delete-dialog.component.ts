import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISerieDocumento } from '../serie-documento.model';
import { SerieDocumentoService } from '../service/serie-documento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './serie-documento-delete-dialog.component.html',
})
export class SerieDocumentoDeleteDialogComponent {
  serieDocumento?: ISerieDocumento;

  constructor(protected serieDocumentoService: SerieDocumentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serieDocumentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
