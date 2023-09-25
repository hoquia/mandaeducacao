import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReciboComponent } from './list/recibo.component';
import { ReciboDetailComponent } from './detail/recibo-detail.component';
import { ReciboUpdateComponent } from './update/recibo-update.component';
import { ReciboDeleteDialogComponent } from './delete/recibo-delete-dialog.component';
import { ReciboRoutingModule } from './route/recibo-routing.module';

@NgModule({
  imports: [SharedModule, ReciboRoutingModule],
  declarations: [ReciboComponent, ReciboDetailComponent, ReciboUpdateComponent, ReciboDeleteDialogComponent],
  exports: [ReciboComponent],
})
export class ReciboModule {}
