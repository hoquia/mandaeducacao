import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TurnoComponent } from '../list/turno.component';
import { TurnoDetailComponent } from '../detail/turno-detail.component';
import { TurnoUpdateComponent } from '../update/turno-update.component';
import { TurnoRoutingResolveService } from './turno-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const turnoRoute: Routes = [
  {
    path: '',
    component: TurnoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TurnoDetailComponent,
    resolve: {
      turno: TurnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TurnoUpdateComponent,
    resolve: {
      turno: TurnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TurnoUpdateComponent,
    resolve: {
      turno: TurnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(turnoRoute)],
  exports: [RouterModule],
})
export class TurnoRoutingModule {}
