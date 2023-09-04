import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoDisciplinaCurricular } from '../estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from '../service/estado-disciplina-curricular.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './estado-disciplina-curricular-delete-dialog.component.html',
})
export class EstadoDisciplinaCurricularDeleteDialogComponent {
  estadoDisciplinaCurricular?: IEstadoDisciplinaCurricular;

  constructor(protected estadoDisciplinaCurricularService: EstadoDisciplinaCurricularService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadoDisciplinaCurricularService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
