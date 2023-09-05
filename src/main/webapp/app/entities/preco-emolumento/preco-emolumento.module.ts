import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrecoEmolumentoComponent } from './list/preco-emolumento.component';
import { PrecoEmolumentoDetailComponent } from './detail/preco-emolumento-detail.component';
import { PrecoEmolumentoUpdateComponent } from './update/preco-emolumento-update.component';
import { PrecoEmolumentoDeleteDialogComponent } from './delete/preco-emolumento-delete-dialog.component';
import { PrecoEmolumentoRoutingModule } from './route/preco-emolumento-routing.module';

@NgModule({
  imports: [SharedModule, PrecoEmolumentoRoutingModule],
  declarations: [
    PrecoEmolumentoComponent,
    PrecoEmolumentoDetailComponent,
    PrecoEmolumentoUpdateComponent,
    PrecoEmolumentoDeleteDialogComponent,
  ],
  exports: [PrecoEmolumentoComponent],
})
export class PrecoEmolumentoModule {}
