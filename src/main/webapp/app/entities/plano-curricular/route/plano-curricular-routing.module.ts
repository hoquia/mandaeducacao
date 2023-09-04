import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlanoCurricularComponent } from '../list/plano-curricular.component';
import { PlanoCurricularDetailComponent } from '../detail/plano-curricular-detail.component';
import { PlanoCurricularUpdateComponent } from '../update/plano-curricular-update.component';
import { PlanoCurricularRoutingResolveService } from './plano-curricular-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const planoCurricularRoute: Routes = [
  {
    path: '',
    component: PlanoCurricularComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanoCurricularDetailComponent,
    resolve: {
      planoCurricular: PlanoCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanoCurricularUpdateComponent,
    resolve: {
      planoCurricular: PlanoCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanoCurricularUpdateComponent,
    resolve: {
      planoCurricular: PlanoCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(planoCurricularRoute)],
  exports: [RouterModule],
})
export class PlanoCurricularRoutingModule {}
