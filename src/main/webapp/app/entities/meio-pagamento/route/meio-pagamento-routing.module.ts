import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MeioPagamentoComponent } from '../list/meio-pagamento.component';
import { MeioPagamentoDetailComponent } from '../detail/meio-pagamento-detail.component';
import { MeioPagamentoUpdateComponent } from '../update/meio-pagamento-update.component';
import { MeioPagamentoRoutingResolveService } from './meio-pagamento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const meioPagamentoRoute: Routes = [
  {
    path: '',
    component: MeioPagamentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MeioPagamentoDetailComponent,
    resolve: {
      meioPagamento: MeioPagamentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MeioPagamentoUpdateComponent,
    resolve: {
      meioPagamento: MeioPagamentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MeioPagamentoUpdateComponent,
    resolve: {
      meioPagamento: MeioPagamentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(meioPagamentoRoute)],
  exports: [RouterModule],
})
export class MeioPagamentoRoutingModule {}
