import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlanoDescontoComponent } from './list/plano-desconto.component';
import { PlanoDescontoDetailComponent } from './detail/plano-desconto-detail.component';
import { PlanoDescontoUpdateComponent } from './update/plano-desconto-update.component';
import { PlanoDescontoDeleteDialogComponent } from './delete/plano-desconto-delete-dialog.component';
import { PlanoDescontoRoutingModule } from './route/plano-desconto-routing.module';

@NgModule({
  imports: [SharedModule, PlanoDescontoRoutingModule],
  declarations: [PlanoDescontoComponent, PlanoDescontoDetailComponent, PlanoDescontoUpdateComponent, PlanoDescontoDeleteDialogComponent],
})
export class PlanoDescontoModule {}
