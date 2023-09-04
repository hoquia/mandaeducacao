import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlanoDescontoComponent } from '../list/plano-desconto.component';
import { PlanoDescontoDetailComponent } from '../detail/plano-desconto-detail.component';
import { PlanoDescontoUpdateComponent } from '../update/plano-desconto-update.component';
import { PlanoDescontoRoutingResolveService } from './plano-desconto-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const planoDescontoRoute: Routes = [
  {
    path: '',
    component: PlanoDescontoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoDescontoDetailComponent,
    resolve: {
      planoDesconto: PlanoDescontoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoDescontoUpdateComponent,
    resolve: {
      planoDesconto: PlanoDescontoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoDescontoUpdateComponent,
    resolve: {
      planoDesconto: PlanoDescontoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(planoDescontoRoute)],
  exports: [RouterModule],
})
export class PlanoDescontoRoutingModule {}
