import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LookupComponent } from '../list/lookup.component';
import { LookupDetailComponent } from '../detail/lookup-detail.component';
import { LookupUpdateComponent } from '../update/lookup-update.component';
import { LookupRoutingResolveService } from './lookup-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const lookupRoute: Routes = [
  {
    path: '',
    component: LookupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LookupDetailComponent,
    resolve: {
      lookup: LookupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LookupUpdateComponent,
    resolve: {
      lookup: LookupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LookupUpdateComponent,
    resolve: {
      lookup: LookupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lookupRoute)],
  exports: [RouterModule],
})
export class LookupRoutingModule {}
