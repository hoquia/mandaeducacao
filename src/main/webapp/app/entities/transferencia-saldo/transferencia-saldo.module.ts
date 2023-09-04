import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransferenciaSaldoComponent } from './list/transferencia-saldo.component';
import { TransferenciaSaldoDetailComponent } from './detail/transferencia-saldo-detail.component';
import { TransferenciaSaldoUpdateComponent } from './update/transferencia-saldo-update.component';
import { TransferenciaSaldoDeleteDialogComponent } from './delete/transferencia-saldo-delete-dialog.component';
import { TransferenciaSaldoRoutingModule } from './route/transferencia-saldo-routing.module';

@NgModule({
  imports: [SharedModule, TransferenciaSaldoRoutingModule],
  declarations: [
    TransferenciaSaldoComponent,
    TransferenciaSaldoDetailComponent,
    TransferenciaSaldoUpdateComponent,
    TransferenciaSaldoDeleteDialogComponent,
  ],
})
export class TransferenciaSaldoModule {}
