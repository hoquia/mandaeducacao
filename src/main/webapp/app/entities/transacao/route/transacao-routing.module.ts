import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransacaoComponent } from '../list/transacao.component';
import { TransacaoDetailComponent } from '../detail/transacao-detail.component';
import { TransacaoUpdateComponent } from '../update/transacao-update.component';
import { TransacaoRoutingResolveService } from './transacao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const transacaoRoute: Routes = [
  {
    path: '',
    component: TransacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransacaoDetailComponent,
    resolve: {
      transacao: TransacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransacaoUpdateComponent,
    resolve: {
      transacao: TransacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransacaoUpdateComponent,
    resolve: {
      transacao: TransacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transacaoRoute)],
  exports: [RouterModule],
})
export class TransacaoRoutingModule {}
