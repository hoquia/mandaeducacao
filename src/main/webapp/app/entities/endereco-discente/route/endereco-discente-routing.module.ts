import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EnderecoDiscenteComponent } from '../list/endereco-discente.component';
import { EnderecoDiscenteDetailComponent } from '../detail/endereco-discente-detail.component';
import { EnderecoDiscenteUpdateComponent } from '../update/endereco-discente-update.component';
import { EnderecoDiscenteRoutingResolveService } from './endereco-discente-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const enderecoDiscenteRoute: Routes = [
  {
    path: '',
    component: EnderecoDiscenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EnderecoDiscenteDetailComponent,
    resolve: {
      enderecoDiscente: EnderecoDiscenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EnderecoDiscenteUpdateComponent,
    resolve: {
      enderecoDiscente: EnderecoDiscenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EnderecoDiscenteUpdateComponent,
    resolve: {
      enderecoDiscente: EnderecoDiscenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(enderecoDiscenteRoute)],
  exports: [RouterModule],
})
export class EnderecoDiscenteRoutingModule {}
