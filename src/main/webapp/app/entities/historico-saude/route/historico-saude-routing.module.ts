import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HistoricoSaudeComponent } from '../list/historico-saude.component';
import { HistoricoSaudeDetailComponent } from '../detail/historico-saude-detail.component';
import { HistoricoSaudeUpdateComponent } from '../update/historico-saude-update.component';
import { HistoricoSaudeRoutingResolveService } from './historico-saude-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const historicoSaudeRoute: Routes = [
  {
    path: '',
    component: HistoricoSaudeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HistoricoSaudeDetailComponent,
    resolve: {
      historicoSaude: HistoricoSaudeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HistoricoSaudeUpdateComponent,
    resolve: {
      historicoSaude: HistoricoSaudeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HistoricoSaudeUpdateComponent,
    resolve: {
      historicoSaude: HistoricoSaudeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(historicoSaudeRoute)],
  exports: [RouterModule],
})
export class HistoricoSaudeRoutingModule {}
