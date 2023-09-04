import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AplicacaoReciboComponent } from '../list/aplicacao-recibo.component';
import { AplicacaoReciboDetailComponent } from '../detail/aplicacao-recibo-detail.component';
import { AplicacaoReciboUpdateComponent } from '../update/aplicacao-recibo-update.component';
import { AplicacaoReciboRoutingResolveService } from './aplicacao-recibo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const aplicacaoReciboRoute: Routes = [
  {
    path: '',
    component: AplicacaoReciboComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AplicacaoReciboDetailComponent,
    resolve: {
      aplicacaoRecibo: AplicacaoReciboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AplicacaoReciboUpdateComponent,
    resolve: {
      aplicacaoRecibo: AplicacaoReciboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AplicacaoReciboUpdateComponent,
    resolve: {
      aplicacaoRecibo: AplicacaoReciboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(aplicacaoReciboRoute)],
  exports: [RouterModule],
})
export class AplicacaoReciboRoutingModule {}
