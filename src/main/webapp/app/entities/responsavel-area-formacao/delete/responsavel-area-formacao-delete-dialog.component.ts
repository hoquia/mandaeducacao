import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResponsavelAreaFormacao } from '../responsavel-area-formacao.model';
import { ResponsavelAreaFormacaoService } from '../service/responsavel-area-formacao.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './responsavel-area-formacao-delete-dialog.component.html',
})
export class ResponsavelAreaFormacaoDeleteDialogComponent {
  responsavelAreaFormacao?: IResponsavelAreaFormacao;

  constructor(protected responsavelAreaFormacaoService: ResponsavelAreaFormacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.responsavelAreaFormacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
