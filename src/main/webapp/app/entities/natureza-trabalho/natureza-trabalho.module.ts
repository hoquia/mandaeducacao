import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NaturezaTrabalhoComponent } from './list/natureza-trabalho.component';
import { NaturezaTrabalhoDetailComponent } from './detail/natureza-trabalho-detail.component';
import { NaturezaTrabalhoUpdateComponent } from './update/natureza-trabalho-update.component';
import { NaturezaTrabalhoDeleteDialogComponent } from './delete/natureza-trabalho-delete-dialog.component';
import { NaturezaTrabalhoRoutingModule } from './route/natureza-trabalho-routing.module';

@NgModule({
  imports: [SharedModule, NaturezaTrabalhoRoutingModule],
  declarations: [
    NaturezaTrabalhoComponent,
    NaturezaTrabalhoDetailComponent,
    NaturezaTrabalhoUpdateComponent,
    NaturezaTrabalhoDeleteDialogComponent,
  ],
})
export class NaturezaTrabalhoModule {}
