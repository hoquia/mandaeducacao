import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAreaFormacao } from '../area-formacao.model';
import { AreaFormacaoService } from '../service/area-formacao.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './area-formacao-delete-dialog.component.html',
})
export class AreaFormacaoDeleteDialogComponent {
  areaFormacao?: IAreaFormacao;

  constructor(protected areaFormacaoService: AreaFormacaoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.areaFormacaoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
