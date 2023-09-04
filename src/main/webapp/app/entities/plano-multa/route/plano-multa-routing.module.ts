import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlanoMultaComponent } from '../list/plano-multa.component';
import { PlanoMultaDetailComponent } from '../detail/plano-multa-detail.component';
import { PlanoMultaUpdateComponent } from '../update/plano-multa-update.component';
import { PlanoMultaRoutingResolveService } from './plano-multa-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const planoMultaRoute: Routes = [
  {
    path: '',
    component: PlanoMultaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoMultaDetailComponent,
    resolve: {
      planoMulta: PlanoMultaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoMultaUpdateComponent,
    resolve: {
      planoMulta: PlanoMultaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoMultaUpdateComponent,
    resolve: {
      planoMulta: PlanoMultaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(planoMultaRoute)],
  exports: [RouterModule],
})
export class PlanoMultaRoutingModule {}
