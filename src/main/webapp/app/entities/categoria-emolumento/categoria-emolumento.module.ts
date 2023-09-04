import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategoriaEmolumentoComponent } from './list/categoria-emolumento.component';
import { CategoriaEmolumentoDetailComponent } from './detail/categoria-emolumento-detail.component';
import { CategoriaEmolumentoUpdateComponent } from './update/categoria-emolumento-update.component';
import { CategoriaEmolumentoDeleteDialogComponent } from './delete/categoria-emolumento-delete-dialog.component';
import { CategoriaEmolumentoRoutingModule } from './route/categoria-emolumento-routing.module';

@NgModule({
  imports: [SharedModule, CategoriaEmolumentoRoutingModule],
  declarations: [
    CategoriaEmolumentoComponent,
    CategoriaEmolumentoDetailComponent,
    CategoriaEmolumentoUpdateComponent,
    CategoriaEmolumentoDeleteDialogComponent,
  ],
})
export class CategoriaEmolumentoModule {}
