import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProcessoSelectivoMatriculaComponent } from '../list/processo-selectivo-matricula.component';
import { ProcessoSelectivoMatriculaDetailComponent } from '../detail/processo-selectivo-matricula-detail.component';
import { ProcessoSelectivoMatriculaUpdateComponent } from '../update/processo-selectivo-matricula-update.component';
import { ProcessoSelectivoMatriculaRoutingResolveService } from './processo-selectivo-matricula-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const processoSelectivoMatriculaRoute: Routes = [
  {
    path: '',
    component: ProcessoSelectivoMatriculaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcessoSelectivoMatriculaDetailComponent,
    resolve: {
      processoSelectivoMatricula: ProcessoSelectivoMatriculaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcessoSelectivoMatriculaUpdateComponent,
    resolve: {
      processoSelectivoMatricula: ProcessoSelectivoMatriculaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcessoSelectivoMatriculaUpdateComponent,
    resolve: {
      processoSelectivoMatricula: ProcessoSelectivoMatriculaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(processoSelectivoMatriculaRoute)],
  exports: [RouterModule],
})
export class ProcessoSelectivoMatriculaRoutingModule {}
