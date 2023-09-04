import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EstadoDissertacaoComponent } from '../list/estado-dissertacao.component';
import { EstadoDissertacaoDetailComponent } from '../detail/estado-dissertacao-detail.component';
import { EstadoDissertacaoUpdateComponent } from '../update/estado-dissertacao-update.component';
import { EstadoDissertacaoRoutingResolveService } from './estado-dissertacao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const estadoDissertacaoRoute: Routes = [
  {
    path: '',
    component: EstadoDissertacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstadoDissertacaoDetailComponent,
    resolve: {
      estadoDissertacao: EstadoDissertacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstadoDissertacaoUpdateComponent,
    resolve: {
      estadoDissertacao: EstadoDissertacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstadoDissertacaoUpdateComponent,
    resolve: {
      estadoDissertacao: EstadoDissertacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(estadoDissertacaoRoute)],
  exports: [RouterModule],
})
export class EstadoDissertacaoRoutingModule {}
