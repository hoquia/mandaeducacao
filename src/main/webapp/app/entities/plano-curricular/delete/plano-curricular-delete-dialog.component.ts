import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlanoCurricular } from '../plano-curricular.model';
import { PlanoCurricularService } from '../service/plano-curricular.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './plano-curricular-delete-dialog.component.html',
})
export class PlanoCurricularDeleteDialogComponent {
  planoCurricular?: IPlanoCurricular;

  constructor(protected planoCurricularService: PlanoCurricularService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoCurricularService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
