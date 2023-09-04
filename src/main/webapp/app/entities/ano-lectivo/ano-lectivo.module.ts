import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnoLectivoComponent } from './list/ano-lectivo.component';
import { AnoLectivoDetailComponent } from './detail/ano-lectivo-detail.component';
import { AnoLectivoUpdateComponent } from './update/ano-lectivo-update.component';
import { AnoLectivoDeleteDialogComponent } from './delete/ano-lectivo-delete-dialog.component';
import { AnoLectivoRoutingModule } from './route/ano-lectivo-routing.module';

@NgModule({
  imports: [SharedModule, AnoLectivoRoutingModule],
  declarations: [AnoLectivoComponent, AnoLectivoDetailComponent, AnoLectivoUpdateComponent, AnoLectivoDeleteDialogComponent],
})
export class AnoLectivoModule {}
