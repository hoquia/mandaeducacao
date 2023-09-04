import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentoComercial } from '../documento-comercial.model';
import { DocumentoComercialService } from '../service/documento-comercial.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './documento-comercial-delete-dialog.component.html',
})
export class DocumentoComercialDeleteDialogComponent {
  documentoComercial?: IDocumentoComercial;

  constructor(protected documentoComercialService: DocumentoComercialService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentoComercialService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
