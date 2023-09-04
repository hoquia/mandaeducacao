import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OcorrenciaComponent } from '../list/ocorrencia.component';
import { OcorrenciaDetailComponent } from '../detail/ocorrencia-detail.component';
import { OcorrenciaUpdateComponent } from '../update/ocorrencia-update.component';
import { OcorrenciaRoutingResolveService } from './ocorrencia-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ocorrenciaRoute: Routes = [
  {
    path: '',
    component: OcorrenciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OcorrenciaDetailComponent,
    resolve: {
      ocorrencia: OcorrenciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OcorrenciaUpdateComponent,
    resolve: {
      ocorrencia: OcorrenciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OcorrenciaUpdateComponent,
    resolve: {
      ocorrencia: OcorrenciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ocorrenciaRoute)],
  exports: [RouterModule],
})
export class OcorrenciaRoutingModule {}
