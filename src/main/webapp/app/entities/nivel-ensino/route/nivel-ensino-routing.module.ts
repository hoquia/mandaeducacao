import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NivelEnsinoComponent } from '../list/nivel-ensino.component';
import { NivelEnsinoDetailComponent } from '../detail/nivel-ensino-detail.component';
import { NivelEnsinoUpdateComponent } from '../update/nivel-ensino-update.component';
import { NivelEnsinoRoutingResolveService } from './nivel-ensino-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const nivelEnsinoRoute: Routes = [
  {
    path: '',
    component: NivelEnsinoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NivelEnsinoDetailComponent,
    resolve: {
      nivelEnsino: NivelEnsinoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NivelEnsinoUpdateComponent,
    resolve: {
      nivelEnsino: NivelEnsinoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NivelEnsinoUpdateComponent,
    resolve: {
      nivelEnsino: NivelEnsinoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nivelEnsinoRoute)],
  exports: [RouterModule],
})
export class NivelEnsinoRoutingModule {}
