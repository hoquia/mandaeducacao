import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlanoCurricularComponent } from './list/plano-curricular.component';
import { PlanoCurricularDetailComponent } from './detail/plano-curricular-detail.component';
import { PlanoCurricularUpdateComponent } from './update/plano-curricular-update.component';
import { PlanoCurricularDeleteDialogComponent } from './delete/plano-curricular-delete-dialog.component';
import { PlanoCurricularRoutingModule } from './route/plano-curricular-routing.module';

@NgModule({
  imports: [SharedModule, PlanoCurricularRoutingModule],
  declarations: [
    PlanoCurricularComponent,
    PlanoCurricularDetailComponent,
    PlanoCurricularUpdateComponent,
    PlanoCurricularDeleteDialogComponent,
  ],
})
export class PlanoCurricularModule {}
