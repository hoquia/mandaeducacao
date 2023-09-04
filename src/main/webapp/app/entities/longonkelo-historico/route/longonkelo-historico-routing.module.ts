import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LongonkeloHistoricoComponent } from '../list/longonkelo-historico.component';
import { LongonkeloHistoricoDetailComponent } from '../detail/longonkelo-historico-detail.component';
import { LongonkeloHistoricoUpdateComponent } from '../update/longonkelo-historico-update.component';
import { LongonkeloHistoricoRoutingResolveService } from './longonkelo-historico-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const longonkeloHistoricoRoute: Routes = [
  {
    path: '',
    component: LongonkeloHistoricoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LongonkeloHistoricoDetailComponent,
    resolve: {
      longonkeloHistorico: LongonkeloHistoricoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LongonkeloHistoricoUpdateComponent,
    resolve: {
      longonkeloHistorico: LongonkeloHistoricoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LongonkeloHistoricoUpdateComponent,
    resolve: {
      longonkeloHistorico: LongonkeloHistoricoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(longonkeloHistoricoRoute)],
  exports: [RouterModule],
})
export class LongonkeloHistoricoRoutingModule {}
