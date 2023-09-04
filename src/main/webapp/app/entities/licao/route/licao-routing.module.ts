import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LicaoComponent } from '../list/licao.component';
import { LicaoDetailComponent } from '../detail/licao-detail.component';
import { LicaoUpdateComponent } from '../update/licao-update.component';
import { LicaoRoutingResolveService } from './licao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const licaoRoute: Routes = [
  {
    path: '',
    component: LicaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LicaoDetailComponent,
    resolve: {
      licao: LicaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LicaoUpdateComponent,
    resolve: {
      licao: LicaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LicaoUpdateComponent,
    resolve: {
      licao: LicaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(licaoRoute)],
  exports: [RouterModule],
})
export class LicaoRoutingModule {}
