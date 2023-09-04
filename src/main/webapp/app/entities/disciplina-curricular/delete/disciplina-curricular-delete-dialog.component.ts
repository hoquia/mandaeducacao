import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisciplinaCurricular } from '../disciplina-curricular.model';
import { DisciplinaCurricularService } from '../service/disciplina-curricular.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './disciplina-curricular-delete-dialog.component.html',
})
export class DisciplinaCurricularDeleteDialogComponent {
  disciplinaCurricular?: IDisciplinaCurricular;

  constructor(protected disciplinaCurricularService: DisciplinaCurricularService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.disciplinaCurricularService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
