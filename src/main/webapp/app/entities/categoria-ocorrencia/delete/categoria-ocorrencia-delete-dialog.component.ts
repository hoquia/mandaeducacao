import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoriaOcorrencia } from '../categoria-ocorrencia.model';
import { CategoriaOcorrenciaService } from '../service/categoria-ocorrencia.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './categoria-ocorrencia-delete-dialog.component.html',
})
export class CategoriaOcorrenciaDeleteDialogComponent {
  categoriaOcorrencia?: ICategoriaOcorrencia;

  constructor(protected categoriaOcorrenciaService: CategoriaOcorrenciaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaOcorrenciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
