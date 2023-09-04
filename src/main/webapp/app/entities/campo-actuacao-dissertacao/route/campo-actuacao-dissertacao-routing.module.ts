import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CampoActuacaoDissertacaoComponent } from '../list/campo-actuacao-dissertacao.component';
import { CampoActuacaoDissertacaoDetailComponent } from '../detail/campo-actuacao-dissertacao-detail.component';
import { CampoActuacaoDissertacaoUpdateComponent } from '../update/campo-actuacao-dissertacao-update.component';
import { CampoActuacaoDissertacaoRoutingResolveService } from './campo-actuacao-dissertacao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const campoActuacaoDissertacaoRoute: Routes = [
  {
    path: '',
    component: CampoActuacaoDissertacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CampoActuacaoDissertacaoDetailComponent,
    resolve: {
      campoActuacaoDissertacao: CampoActuacaoDissertacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CampoActuacaoDissertacaoUpdateComponent,
    resolve: {
      campoActuacaoDissertacao: CampoActuacaoDissertacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CampoActuacaoDissertacaoUpdateComponent,
    resolve: {
      campoActuacaoDissertacao: CampoActuacaoDissertacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(campoActuacaoDissertacaoRoute)],
  exports: [RouterModule],
})
export class CampoActuacaoDissertacaoRoutingModule {}
