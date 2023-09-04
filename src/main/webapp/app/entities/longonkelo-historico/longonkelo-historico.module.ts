import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LongonkeloHistoricoComponent } from './list/longonkelo-historico.component';
import { LongonkeloHistoricoDetailComponent } from './detail/longonkelo-historico-detail.component';
import { LongonkeloHistoricoUpdateComponent } from './update/longonkelo-historico-update.component';
import { LongonkeloHistoricoDeleteDialogComponent } from './delete/longonkelo-historico-delete-dialog.component';
import { LongonkeloHistoricoRoutingModule } from './route/longonkelo-historico-routing.module';

@NgModule({
  imports: [SharedModule, LongonkeloHistoricoRoutingModule],
  declarations: [
    LongonkeloHistoricoComponent,
    LongonkeloHistoricoDetailComponent,
    LongonkeloHistoricoUpdateComponent,
    LongonkeloHistoricoDeleteDialogComponent,
  ],
})
export class LongonkeloHistoricoModule {}
