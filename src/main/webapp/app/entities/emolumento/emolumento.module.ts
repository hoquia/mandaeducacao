import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmolumentoComponent } from './list/emolumento.component';
import { EmolumentoDetailComponent } from './detail/emolumento-detail.component';
import { EmolumentoUpdateComponent } from './update/emolumento-update.component';
import { EmolumentoDeleteDialogComponent } from './delete/emolumento-delete-dialog.component';
import { EmolumentoRoutingModule } from './route/emolumento-routing.module';

@NgModule({
  imports: [SharedModule, EmolumentoRoutingModule],
  declarations: [EmolumentoComponent, EmolumentoDetailComponent, EmolumentoUpdateComponent, EmolumentoDeleteDialogComponent],
})
export class EmolumentoModule {}
