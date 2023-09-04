import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransacaoComponent } from './list/transacao.component';
import { TransacaoDetailComponent } from './detail/transacao-detail.component';
import { TransacaoUpdateComponent } from './update/transacao-update.component';
import { TransacaoDeleteDialogComponent } from './delete/transacao-delete-dialog.component';
import { TransacaoRoutingModule } from './route/transacao-routing.module';

@NgModule({
  imports: [SharedModule, TransacaoRoutingModule],
  declarations: [TransacaoComponent, TransacaoDetailComponent, TransacaoUpdateComponent, TransacaoDeleteDialogComponent],
})
export class TransacaoModule {}
