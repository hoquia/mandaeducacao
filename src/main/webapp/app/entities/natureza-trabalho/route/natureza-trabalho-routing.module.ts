import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NaturezaTrabalhoComponent } from '../list/natureza-trabalho.component';
import { NaturezaTrabalhoDetailComponent } from '../detail/natureza-trabalho-detail.component';
import { NaturezaTrabalhoUpdateComponent } from '../update/natureza-trabalho-update.component';
import { NaturezaTrabalhoRoutingResolveService } from './natureza-trabalho-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const naturezaTrabalhoRoute: Routes = [
  {
    path: '',
    component: NaturezaTrabalhoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NaturezaTrabalhoDetailComponent,
    resolve: {
      naturezaTrabalho: NaturezaTrabalhoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NaturezaTrabalhoUpdateComponent,
    resolve: {
      naturezaTrabalho: NaturezaTrabalhoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NaturezaTrabalhoUpdateComponent,
    resolve: {
      naturezaTrabalho: NaturezaTrabalhoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(naturezaTrabalhoRoute)],
  exports: [RouterModule],
})
export class NaturezaTrabalhoRoutingModule {}
