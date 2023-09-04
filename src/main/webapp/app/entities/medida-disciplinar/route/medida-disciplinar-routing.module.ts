import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MedidaDisciplinarComponent } from '../list/medida-disciplinar.component';
import { MedidaDisciplinarDetailComponent } from '../detail/medida-disciplinar-detail.component';
import { MedidaDisciplinarUpdateComponent } from '../update/medida-disciplinar-update.component';
import { MedidaDisciplinarRoutingResolveService } from './medida-disciplinar-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const medidaDisciplinarRoute: Routes = [
  {
    path: '',
    component: MedidaDisciplinarComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MedidaDisciplinarDetailComponent,
    resolve: {
      medidaDisciplinar: MedidaDisciplinarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedidaDisciplinarUpdateComponent,
    resolve: {
      medidaDisciplinar: MedidaDisciplinarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedidaDisciplinarUpdateComponent,
    resolve: {
      medidaDisciplinar: MedidaDisciplinarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(medidaDisciplinarRoute)],
  exports: [RouterModule],
})
export class MedidaDisciplinarRoutingModule {}
