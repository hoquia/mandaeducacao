import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MeioPagamentoComponent } from './list/meio-pagamento.component';
import { MeioPagamentoDetailComponent } from './detail/meio-pagamento-detail.component';
import { MeioPagamentoUpdateComponent } from './update/meio-pagamento-update.component';
import { MeioPagamentoDeleteDialogComponent } from './delete/meio-pagamento-delete-dialog.component';
import { MeioPagamentoRoutingModule } from './route/meio-pagamento-routing.module';

@NgModule({
  imports: [SharedModule, MeioPagamentoRoutingModule],
  declarations: [MeioPagamentoComponent, MeioPagamentoDetailComponent, MeioPagamentoUpdateComponent, MeioPagamentoDeleteDialogComponent],
})
export class MeioPagamentoModule {}
