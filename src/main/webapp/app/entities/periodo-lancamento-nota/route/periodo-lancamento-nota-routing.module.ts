import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PeriodoLancamentoNotaComponent } from '../list/periodo-lancamento-nota.component';
import { PeriodoLancamentoNotaDetailComponent } from '../detail/periodo-lancamento-nota-detail.component';
import { PeriodoLancamentoNotaUpdateComponent } from '../update/periodo-lancamento-nota-update.component';
import { PeriodoLancamentoNotaRoutingResolveService } from './periodo-lancamento-nota-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const periodoLancamentoNotaRoute: Routes = [
  {
    path: '',
    component: PeriodoLancamentoNotaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeriodoLancamentoNotaDetailComponent,
    resolve: {
      periodoLancamentoNota: PeriodoLancamentoNotaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeriodoLancamentoNotaUpdateComponent,
    resolve: {
      periodoLancamentoNota: PeriodoLancamentoNotaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeriodoLancamentoNotaUpdateComponent,
    resolve: {
      periodoLancamentoNota: PeriodoLancamentoNotaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(periodoLancamentoNotaRoute)],
  exports: [RouterModule],
})
export class PeriodoLancamentoNotaRoutingModule {}
