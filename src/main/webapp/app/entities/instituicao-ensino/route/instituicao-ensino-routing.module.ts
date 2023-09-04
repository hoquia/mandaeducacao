import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InstituicaoEnsinoComponent } from '../list/instituicao-ensino.component';
import { InstituicaoEnsinoDetailComponent } from '../detail/instituicao-ensino-detail.component';
import { InstituicaoEnsinoUpdateComponent } from '../update/instituicao-ensino-update.component';
import { InstituicaoEnsinoRoutingResolveService } from './instituicao-ensino-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const instituicaoEnsinoRoute: Routes = [
  {
    path: '',
    component: InstituicaoEnsinoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InstituicaoEnsinoDetailComponent,
    resolve: {
      instituicaoEnsino: InstituicaoEnsinoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InstituicaoEnsinoUpdateComponent,
    resolve: {
      instituicaoEnsino: InstituicaoEnsinoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InstituicaoEnsinoUpdateComponent,
    resolve: {
      instituicaoEnsino: InstituicaoEnsinoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(instituicaoEnsinoRoute)],
  exports: [RouterModule],
})
export class InstituicaoEnsinoRoutingModule {}
