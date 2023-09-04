import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategoriaEmolumentoComponent } from '../list/categoria-emolumento.component';
import { CategoriaEmolumentoDetailComponent } from '../detail/categoria-emolumento-detail.component';
import { CategoriaEmolumentoUpdateComponent } from '../update/categoria-emolumento-update.component';
import { CategoriaEmolumentoRoutingResolveService } from './categoria-emolumento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categoriaEmolumentoRoute: Routes = [
  {
    path: '',
    component: CategoriaEmolumentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoriaEmolumentoDetailComponent,
    resolve: {
      categoriaEmolumento: CategoriaEmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoriaEmolumentoUpdateComponent,
    resolve: {
      categoriaEmolumento: CategoriaEmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoriaEmolumentoUpdateComponent,
    resolve: {
      categoriaEmolumento: CategoriaEmolumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categoriaEmolumentoRoute)],
  exports: [RouterModule],
})
export class CategoriaEmolumentoRoutingModule {}
