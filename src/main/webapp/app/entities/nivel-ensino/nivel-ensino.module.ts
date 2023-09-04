import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NivelEnsinoComponent } from './list/nivel-ensino.component';
import { NivelEnsinoDetailComponent } from './detail/nivel-ensino-detail.component';
import { NivelEnsinoUpdateComponent } from './update/nivel-ensino-update.component';
import { NivelEnsinoDeleteDialogComponent } from './delete/nivel-ensino-delete-dialog.component';
import { NivelEnsinoRoutingModule } from './route/nivel-ensino-routing.module';

@NgModule({
  imports: [SharedModule, NivelEnsinoRoutingModule],
  declarations: [NivelEnsinoComponent, NivelEnsinoDetailComponent, NivelEnsinoUpdateComponent, NivelEnsinoDeleteDialogComponent],
})
export class NivelEnsinoModule {}
