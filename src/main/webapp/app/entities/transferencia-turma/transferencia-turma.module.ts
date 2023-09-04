import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TransferenciaTurmaComponent } from './list/transferencia-turma.component';
import { TransferenciaTurmaDetailComponent } from './detail/transferencia-turma-detail.component';
import { TransferenciaTurmaUpdateComponent } from './update/transferencia-turma-update.component';
import { TransferenciaTurmaDeleteDialogComponent } from './delete/transferencia-turma-delete-dialog.component';
import { TransferenciaTurmaRoutingModule } from './route/transferencia-turma-routing.module';

@NgModule({
  imports: [SharedModule, TransferenciaTurmaRoutingModule],
  declarations: [
    TransferenciaTurmaComponent,
    TransferenciaTurmaDetailComponent,
    TransferenciaTurmaUpdateComponent,
    TransferenciaTurmaDeleteDialogComponent,
  ],
})
export class TransferenciaTurmaModule {}
