import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResponsavelTurnoComponent } from '../list/responsavel-turno.component';
import { ResponsavelTurnoDetailComponent } from '../detail/responsavel-turno-detail.component';
import { ResponsavelTurnoUpdateComponent } from '../update/responsavel-turno-update.component';
import { ResponsavelTurnoRoutingResolveService } from './responsavel-turno-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const responsavelTurnoRoute: Routes = [
  {
    path: '',
    component: ResponsavelTurnoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsavelTurnoDetailComponent,
    resolve: {
      responsavelTurno: ResponsavelTurnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsavelTurnoUpdateComponent,
    resolve: {
      responsavelTurno: ResponsavelTurnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsavelTurnoUpdateComponent,
    resolve: {
      responsavelTurno: ResponsavelTurnoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(responsavelTurnoRoute)],
  exports: [RouterModule],
})
export class ResponsavelTurnoRoutingModule {}
