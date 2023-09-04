import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LookupItemComponent } from '../list/lookup-item.component';
import { LookupItemDetailComponent } from '../detail/lookup-item-detail.component';
import { LookupItemUpdateComponent } from '../update/lookup-item-update.component';
import { LookupItemRoutingResolveService } from './lookup-item-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const lookupItemRoute: Routes = [
  {
    path: '',
    component: LookupItemComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LookupItemDetailComponent,
    resolve: {
      lookupItem: LookupItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LookupItemUpdateComponent,
    resolve: {
      lookupItem: LookupItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LookupItemUpdateComponent,
    resolve: {
      lookupItem: LookupItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lookupItemRoute)],
  exports: [RouterModule],
})
export class LookupItemRoutingModule {}
