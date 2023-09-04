import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnoLectivoComponent } from '../list/ano-lectivo.component';
import { AnoLectivoDetailComponent } from '../detail/ano-lectivo-detail.component';
import { AnoLectivoUpdateComponent } from '../update/ano-lectivo-update.component';
import { AnoLectivoRoutingResolveService } from './ano-lectivo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const anoLectivoRoute: Routes = [
  {
    path: '',
    component: AnoLectivoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnoLectivoDetailComponent,
    resolve: {
      anoLectivo: AnoLectivoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnoLectivoUpdateComponent,
    resolve: {
      anoLectivo: AnoLectivoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnoLectivoUpdateComponent,
    resolve: {
      anoLectivo: AnoLectivoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(anoLectivoRoute)],
  exports: [RouterModule],
})
export class AnoLectivoRoutingModule {}
