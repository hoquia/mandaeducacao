import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ImpostoComponent } from '../list/imposto.component';
import { ImpostoDetailComponent } from '../detail/imposto-detail.component';
import { ImpostoUpdateComponent } from '../update/imposto-update.component';
import { ImpostoRoutingResolveService } from './imposto-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const impostoRoute: Routes = [
  {
    path: '',
    component: ImpostoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ImpostoDetailComponent,
    resolve: {
      imposto: ImpostoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ImpostoUpdateComponent,
    resolve: {
      imposto: ImpostoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ImpostoUpdateComponent,
    resolve: {
      imposto: ImpostoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(impostoRoute)],
  exports: [RouterModule],
})
export class ImpostoRoutingModule {}
