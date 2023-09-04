import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProvedorNotificacaoComponent } from '../list/provedor-notificacao.component';
import { ProvedorNotificacaoDetailComponent } from '../detail/provedor-notificacao-detail.component';
import { ProvedorNotificacaoUpdateComponent } from '../update/provedor-notificacao-update.component';
import { ProvedorNotificacaoRoutingResolveService } from './provedor-notificacao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const provedorNotificacaoRoute: Routes = [
  {
    path: '',
    component: ProvedorNotificacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProvedorNotificacaoDetailComponent,
    resolve: {
      provedorNotificacao: ProvedorNotificacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProvedorNotificacaoUpdateComponent,
    resolve: {
      provedorNotificacao: ProvedorNotificacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProvedorNotificacaoUpdateComponent,
    resolve: {
      provedorNotificacao: ProvedorNotificacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(provedorNotificacaoRoute)],
  exports: [RouterModule],
})
export class ProvedorNotificacaoRoutingModule {}
