import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EncarregadoEducacaoComponent } from '../list/encarregado-educacao.component';
import { EncarregadoEducacaoDetailComponent } from '../detail/encarregado-educacao-detail.component';
import { EncarregadoEducacaoUpdateComponent } from '../update/encarregado-educacao-update.component';
import { EncarregadoEducacaoRoutingResolveService } from './encarregado-educacao-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const encarregadoEducacaoRoute: Routes = [
  {
    path: '',
    component: EncarregadoEducacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EncarregadoEducacaoDetailComponent,
    resolve: {
      encarregadoEducacao: EncarregadoEducacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EncarregadoEducacaoUpdateComponent,
    resolve: {
      encarregadoEducacao: EncarregadoEducacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EncarregadoEducacaoUpdateComponent,
    resolve: {
      encarregadoEducacao: EncarregadoEducacaoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(encarregadoEducacaoRoute)],
  exports: [RouterModule],
})
export class EncarregadoEducacaoRoutingModule {}
