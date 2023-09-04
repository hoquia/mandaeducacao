import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResponsavelTurnoComponent } from './list/responsavel-turno.component';
import { ResponsavelTurnoDetailComponent } from './detail/responsavel-turno-detail.component';
import { ResponsavelTurnoUpdateComponent } from './update/responsavel-turno-update.component';
import { ResponsavelTurnoDeleteDialogComponent } from './delete/responsavel-turno-delete-dialog.component';
import { ResponsavelTurnoRoutingModule } from './route/responsavel-turno-routing.module';

@NgModule({
  imports: [SharedModule, ResponsavelTurnoRoutingModule],
  declarations: [
    ResponsavelTurnoComponent,
    ResponsavelTurnoDetailComponent,
    ResponsavelTurnoUpdateComponent,
    ResponsavelTurnoDeleteDialogComponent,
  ],
})
export class ResponsavelTurnoModule {}
