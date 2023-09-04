import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProvedorNotificacaoComponent } from './list/provedor-notificacao.component';
import { ProvedorNotificacaoDetailComponent } from './detail/provedor-notificacao-detail.component';
import { ProvedorNotificacaoUpdateComponent } from './update/provedor-notificacao-update.component';
import { ProvedorNotificacaoDeleteDialogComponent } from './delete/provedor-notificacao-delete-dialog.component';
import { ProvedorNotificacaoRoutingModule } from './route/provedor-notificacao-routing.module';

@NgModule({
  imports: [SharedModule, ProvedorNotificacaoRoutingModule],
  declarations: [
    ProvedorNotificacaoComponent,
    ProvedorNotificacaoDetailComponent,
    ProvedorNotificacaoUpdateComponent,
    ProvedorNotificacaoDeleteDialogComponent,
  ],
})
export class ProvedorNotificacaoModule {}
