import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoDissertacao } from '../estado-dissertacao.model';
import { EstadoDissertacaoService } from '../service/estado-dissertacao.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './estado-dissertacao-delete-dialog.component.html',
})
export class EstadoDissertacaoDeleteDialogComponent {
  estadoDissertacao?: IEstadoDissertacao;

  constructor(protected estadoDissertacaoService: EstadoDissertacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoDissertacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
