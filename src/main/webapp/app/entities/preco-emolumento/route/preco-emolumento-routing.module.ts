import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrecoEmolumentoComponent } from '../list/preco-emolumento.component';
import { PrecoEmolumentoDetailComponent } from '../detail/preco-emolumento-detail.component';
import { PrecoEmolumentoUpdateComponent } from '../update/preco-emolumento-update.component';
import { PrecoEmolumentoRoutingResolveService } from './preco-emolumento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const precoEmolumentoRoute: Routes = [
  {
    path: '',
    component: PrecoEmolumentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrecoEmolumentoDetailComponent,
    resolve: {
      precoEmolumento: PrecoEmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrecoEmolumentoUpdateComponent,
    resolve: {
      precoEmolumento: PrecoEmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrecoEmolumentoUpdateComponent,
    resolve: {
      precoEmolumento: PrecoEmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(precoEmolumentoRoute)],
  exports: [RouterModule],
})
export class PrecoEmolumentoRoutingModule {}
