import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResumoImpostoFactura } from '../resumo-imposto-factura.model';
import { ResumoImpostoFacturaService } from '../service/resumo-imposto-factura.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './resumo-imposto-factura-delete-dialog.component.html',
})
export class ResumoImpostoFacturaDeleteDialogComponent {
  resumoImpostoFactura?: IResumoImpostoFactura;

  constructor(protected resumoImpostoFacturaService: ResumoImpostoFacturaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resumoImpostoFacturaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
