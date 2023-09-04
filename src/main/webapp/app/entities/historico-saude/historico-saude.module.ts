import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HistoricoSaudeComponent } from './list/historico-saude.component';
import { HistoricoSaudeDetailComponent } from './detail/historico-saude-detail.component';
import { HistoricoSaudeUpdateComponent } from './update/historico-saude-update.component';
import { HistoricoSaudeDeleteDialogComponent } from './delete/historico-saude-delete-dialog.component';
import { HistoricoSaudeRoutingModule } from './route/historico-saude-routing.module';

@NgModule({
  imports: [SharedModule, HistoricoSaudeRoutingModule],
  declarations: [
    HistoricoSaudeComponent,
    HistoricoSaudeDetailComponent,
    HistoricoSaudeUpdateComponent,
    HistoricoSaudeDeleteDialogComponent,
  ],
})
export class HistoricoSaudeModule {}
