import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PeriodoHorarioComponent } from '../list/periodo-horario.component';
import { PeriodoHorarioDetailComponent } from '../detail/periodo-horario-detail.component';
import { PeriodoHorarioUpdateComponent } from '../update/periodo-horario-update.component';
import { PeriodoHorarioRoutingResolveService } from './periodo-horario-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const periodoHorarioRoute: Routes = [
  {
    path: '',
    component: PeriodoHorarioComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeriodoHorarioDetailComponent,
    resolve: {
      periodoHorario: PeriodoHorarioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeriodoHorarioUpdateComponent,
    resolve: {
      periodoHorario: PeriodoHorarioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeriodoHorarioUpdateComponent,
    resolve: {
      periodoHorario: PeriodoHorarioRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(periodoHorarioRoute)],
  exports: [RouterModule],
})
export class PeriodoHorarioRoutingModule {}
