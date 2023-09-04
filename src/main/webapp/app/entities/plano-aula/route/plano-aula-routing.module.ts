import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlanoAulaComponent } from '../list/plano-aula.component';
import { PlanoAulaDetailComponent } from '../detail/plano-aula-detail.component';
import { PlanoAulaUpdateComponent } from '../update/plano-aula-update.component';
import { PlanoAulaRoutingResolveService } from './plano-aula-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const planoAulaRoute: Routes = [
  {
    path: '',
    component: PlanoAulaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoAulaDetailComponent,
    resolve: {
      planoAula: PlanoAulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoAulaUpdateComponent,
    resolve: {
      planoAula: PlanoAulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoAulaUpdateComponent,
    resolve: {
      planoAula: PlanoAulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(planoAulaRoute)],
  exports: [RouterModule],
})
export class PlanoAulaRoutingModule {}
