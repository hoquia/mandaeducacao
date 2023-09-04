import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlanoMultaComponent } from './list/plano-multa.component';
import { PlanoMultaDetailComponent } from './detail/plano-multa-detail.component';
import { PlanoMultaUpdateComponent } from './update/plano-multa-update.component';
import { PlanoMultaDeleteDialogComponent } from './delete/plano-multa-delete-dialog.component';
import { PlanoMultaRoutingModule } from './route/plano-multa-routing.module';

@NgModule({
  imports: [SharedModule, PlanoMultaRoutingModule],
  declarations: [PlanoMultaComponent, PlanoMultaDetailComponent, PlanoMultaUpdateComponent, PlanoMultaDeleteDialogComponent],
})
export class PlanoMultaModule {}
