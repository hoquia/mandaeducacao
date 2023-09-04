import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnexoDiscenteComponent } from '../list/anexo-discente.component';
import { AnexoDiscenteDetailComponent } from '../detail/anexo-discente-detail.component';
import { AnexoDiscenteUpdateComponent } from '../update/anexo-discente-update.component';
import { AnexoDiscenteRoutingResolveService } from './anexo-discente-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const anexoDiscenteRoute: Routes = [
  {
    path: '',
    component: AnexoDiscenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnexoDiscenteDetailComponent,
    resolve: {
      anexoDiscente: AnexoDiscenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnexoDiscenteUpdateComponent,
    resolve: {
      anexoDiscente: AnexoDiscenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnexoDiscenteUpdateComponent,
    resolve: {
      anexoDiscente: AnexoDiscenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(anexoDiscenteRoute)],
  exports: [RouterModule],
})
export class AnexoDiscenteRoutingModule {}
