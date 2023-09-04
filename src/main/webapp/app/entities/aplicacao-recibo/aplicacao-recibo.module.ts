import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AplicacaoReciboComponent } from './list/aplicacao-recibo.component';
import { AplicacaoReciboDetailComponent } from './detail/aplicacao-recibo-detail.component';
import { AplicacaoReciboUpdateComponent } from './update/aplicacao-recibo-update.component';
import { AplicacaoReciboDeleteDialogComponent } from './delete/aplicacao-recibo-delete-dialog.component';
import { AplicacaoReciboRoutingModule } from './route/aplicacao-recibo-routing.module';

@NgModule({
  imports: [SharedModule, AplicacaoReciboRoutingModule],
  declarations: [
    AplicacaoReciboComponent,
    AplicacaoReciboDetailComponent,
    AplicacaoReciboUpdateComponent,
    AplicacaoReciboDeleteDialogComponent,
  ],
})
export class AplicacaoReciboModule {}
