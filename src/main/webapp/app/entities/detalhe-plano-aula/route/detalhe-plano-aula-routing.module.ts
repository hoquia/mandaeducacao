import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetalhePlanoAulaComponent } from '../list/detalhe-plano-aula.component';
import { DetalhePlanoAulaDetailComponent } from '../detail/detalhe-plano-aula-detail.component';
import { DetalhePlanoAulaUpdateComponent } from '../update/detalhe-plano-aula-update.component';
import { DetalhePlanoAulaRoutingResolveService } from './detalhe-plano-aula-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const detalhePlanoAulaRoute: Routes = [
  {
    path: '',
    component: DetalhePlanoAulaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetalhePlanoAulaDetailComponent,
    resolve: {
      detalhePlanoAula: DetalhePlanoAulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetalhePlanoAulaUpdateComponent,
    resolve: {
      detalhePlanoAula: DetalhePlanoAulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetalhePlanoAulaUpdateComponent,
    resolve: {
      detalhePlanoAula: DetalhePlanoAulaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detalhePlanoAulaRoute)],
  exports: [RouterModule],
})
export class DetalhePlanoAulaRoutingModule {}
