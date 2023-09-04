import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReciboComponent } from '../list/recibo.component';
import { ReciboDetailComponent } from '../detail/recibo-detail.component';
import { ReciboUpdateComponent } from '../update/recibo-update.component';
import { ReciboRoutingResolveService } from './recibo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const reciboRoute: Routes = [
  {
    path: '',
    component: ReciboComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReciboDetailComponent,
    resolve: {
      recibo: ReciboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReciboUpdateComponent,
    resolve: {
      recibo: ReciboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReciboUpdateComponent,
    resolve: {
      recibo: ReciboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reciboRoute)],
  exports: [RouterModule],
})
export class ReciboRoutingModule {}
