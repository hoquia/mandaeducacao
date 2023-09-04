import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResponsavelAreaFormacaoComponent } from '../list/responsavel-area-formacao.component';
import { ResponsavelAreaFormacaoDetailComponent } from '../detail/responsavel-area-formacao-detail.component';
import { ResponsavelAreaFormacaoUpdateComponent } from '../update/responsavel-area-formacao-update.component';
import { ResponsavelAreaFormacaoRoutingResolveService } from './responsavel-area-formacao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const responsavelAreaFormacaoRoute: Routes = [
  {
    path: '',
    component: ResponsavelAreaFormacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsavelAreaFormacaoDetailComponent,
    resolve: {
      responsavelAreaFormacao: ResponsavelAreaFormacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsavelAreaFormacaoUpdateComponent,
    resolve: {
      responsavelAreaFormacao: ResponsavelAreaFormacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsavelAreaFormacaoUpdateComponent,
    resolve: {
      responsavelAreaFormacao: ResponsavelAreaFormacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(responsavelAreaFormacaoRoute)],
  exports: [RouterModule],
})
export class ResponsavelAreaFormacaoRoutingModule {}
