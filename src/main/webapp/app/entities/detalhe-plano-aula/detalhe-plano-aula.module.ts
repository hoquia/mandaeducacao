import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DetalhePlanoAulaComponent } from './list/detalhe-plano-aula.component';
import { DetalhePlanoAulaDetailComponent } from './detail/detalhe-plano-aula-detail.component';
import { DetalhePlanoAulaUpdateComponent } from './update/detalhe-plano-aula-update.component';
import { DetalhePlanoAulaDeleteDialogComponent } from './delete/detalhe-plano-aula-delete-dialog.component';
import { DetalhePlanoAulaRoutingModule } from './route/detalhe-plano-aula-routing.module';

@NgModule({
  imports: [SharedModule, DetalhePlanoAulaRoutingModule],
  declarations: [
    DetalhePlanoAulaComponent,
    DetalhePlanoAulaDetailComponent,
    DetalhePlanoAulaUpdateComponent,
    DetalhePlanoAulaDeleteDialogComponent,
  ],
})
export class DetalhePlanoAulaModule {}
