import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnderecoDiscente } from '../endereco-discente.model';
import { EnderecoDiscenteService } from '../service/endereco-discente.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './endereco-discente-delete-dialog.component.html',
})
export class EnderecoDiscenteDeleteDialogComponent {
  enderecoDiscente?: IEnderecoDiscente;

  constructor(protected enderecoDiscenteService: EnderecoDiscenteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enderecoDiscenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
