import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MedidaDisciplinarComponent } from './list/medida-disciplinar.component';
import { MedidaDisciplinarDetailComponent } from './detail/medida-disciplinar-detail.component';
import { MedidaDisciplinarUpdateComponent } from './update/medida-disciplinar-update.component';
import { MedidaDisciplinarDeleteDialogComponent } from './delete/medida-disciplinar-delete-dialog.component';
import { MedidaDisciplinarRoutingModule } from './route/medida-disciplinar-routing.module';

@NgModule({
  imports: [SharedModule, MedidaDisciplinarRoutingModule],
  declarations: [
    MedidaDisciplinarComponent,
    MedidaDisciplinarDetailComponent,
    MedidaDisciplinarUpdateComponent,
    MedidaDisciplinarDeleteDialogComponent,
  ],
})
export class MedidaDisciplinarModule {}
