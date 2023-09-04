import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PeriodoHorarioComponent } from './list/periodo-horario.component';
import { PeriodoHorarioDetailComponent } from './detail/periodo-horario-detail.component';
import { PeriodoHorarioUpdateComponent } from './update/periodo-horario-update.component';
import { PeriodoHorarioDeleteDialogComponent } from './delete/periodo-horario-delete-dialog.component';
import { PeriodoHorarioRoutingModule } from './route/periodo-horario-routing.module';

@NgModule({
  imports: [SharedModule, PeriodoHorarioRoutingModule],
  declarations: [
    PeriodoHorarioComponent,
    PeriodoHorarioDetailComponent,
    PeriodoHorarioUpdateComponent,
    PeriodoHorarioDeleteDialogComponent,
  ],
})
export class PeriodoHorarioModule {}
