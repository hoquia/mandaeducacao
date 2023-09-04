import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEncarregadoEducacao } from '../encarregado-educacao.model';
import { EncarregadoEducacaoService } from '../service/encarregado-educacao.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './encarregado-educacao-delete-dialog.component.html',
})
export class EncarregadoEducacaoDeleteDialogComponent {
  encarregadoEducacao?: IEncarregadoEducacao;

  constructor(protected encarregadoEducacaoService: EncarregadoEducacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.encarregadoEducacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
