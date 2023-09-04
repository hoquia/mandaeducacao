import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoriaEmolumento } from '../categoria-emolumento.model';
import { CategoriaEmolumentoService } from '../service/categoria-emolumento.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './categoria-emolumento-delete-dialog.component.html',
})
export class CategoriaEmolumentoDeleteDialogComponent {
  categoriaEmolumento?: ICategoriaEmolumento;

  constructor(protected categoriaEmolumentoService: CategoriaEmolumentoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaEmolumentoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
