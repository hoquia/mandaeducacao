import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlanoAulaComponent } from './list/plano-aula.component';
import { PlanoAulaDetailComponent } from './detail/plano-aula-detail.component';
import { PlanoAulaUpdateComponent } from './update/plano-aula-update.component';
import { PlanoAulaDeleteDialogComponent } from './delete/plano-aula-delete-dialog.component';
import { PlanoAulaRoutingModule } from './route/plano-aula-routing.module';

@NgModule({
  imports: [SharedModule, PlanoAulaRoutingModule],
  declarations: [PlanoAulaComponent, PlanoAulaDetailComponent, PlanoAulaUpdateComponent, PlanoAulaDeleteDialogComponent],
})
export class PlanoAulaModule {}
