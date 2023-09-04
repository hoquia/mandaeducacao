import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TransferenciaTurmaComponent } from '../list/transferencia-turma.component';
import { TransferenciaTurmaDetailComponent } from '../detail/transferencia-turma-detail.component';
import { TransferenciaTurmaUpdateComponent } from '../update/transferencia-turma-update.component';
import { TransferenciaTurmaRoutingResolveService } from './transferencia-turma-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const transferenciaTurmaRoute: Routes = [
  {
    path: '',
    component: TransferenciaTurmaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransferenciaTurmaDetailComponent,
    resolve: {
      transferenciaTurma: TransferenciaTurmaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransferenciaTurmaUpdateComponent,
    resolve: {
      transferenciaTurma: TransferenciaTurmaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransferenciaTurmaUpdateComponent,
    resolve: {
      transferenciaTurma: TransferenciaTurmaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(transferenciaTurmaRoute)],
  exports: [RouterModule],
})
export class TransferenciaTurmaRoutingModule {}
