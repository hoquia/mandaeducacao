import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransferenciaSaldoComponent } from '../list/transferencia-saldo.component';
import { TransferenciaSaldoDetailComponent } from '../detail/transferencia-saldo-detail.component';
import { TransferenciaSaldoUpdateComponent } from '../update/transferencia-saldo-update.component';
import { TransferenciaSaldoRoutingResolveService } from './transferencia-saldo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const transferenciaSaldoRoute: Routes = [
  {
    path: '',
    component: TransferenciaSaldoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransferenciaSaldoDetailComponent,
    resolve: {
      transferenciaSaldo: TransferenciaSaldoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransferenciaSaldoUpdateComponent,
    resolve: {
      transferenciaSaldo: TransferenciaSaldoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransferenciaSaldoUpdateComponent,
    resolve: {
      transferenciaSaldo: TransferenciaSaldoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transferenciaSaldoRoute)],
  exports: [RouterModule],
})
export class TransferenciaSaldoRoutingModule {}
