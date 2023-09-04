import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AreaFormacaoComponent } from '../list/area-formacao.component';
import { AreaFormacaoDetailComponent } from '../detail/area-formacao-detail.component';
import { AreaFormacaoUpdateComponent } from '../update/area-formacao-update.component';
import { AreaFormacaoRoutingResolveService } from './area-formacao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const areaFormacaoRoute: Routes = [
  {
    path: '',
    component: AreaFormacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AreaFormacaoDetailComponent,
    resolve: {
      areaFormacao: AreaFormacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaFormacaoUpdateComponent,
    resolve: {
      areaFormacao: AreaFormacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AreaFormacaoUpdateComponent,
    resolve: {
      areaFormacao: AreaFormacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(areaFormacaoRoute)],
  exports: [RouterModule],
})
export class AreaFormacaoRoutingModule {}
