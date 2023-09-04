import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmolumentoComponent } from '../list/emolumento.component';
import { EmolumentoDetailComponent } from '../detail/emolumento-detail.component';
import { EmolumentoUpdateComponent } from '../update/emolumento-update.component';
import { EmolumentoRoutingResolveService } from './emolumento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const emolumentoRoute: Routes = [
  {
    path: '',
    component: EmolumentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmolumentoDetailComponent,
    resolve: {
      emolumento: EmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmolumentoUpdateComponent,
    resolve: {
      emolumento: EmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmolumentoUpdateComponent,
    resolve: {
      emolumento: EmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(emolumentoRoute)],
  exports: [RouterModule],
})
export class EmolumentoRoutingModule {}
