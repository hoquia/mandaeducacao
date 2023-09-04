import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategoriaOcorrenciaComponent } from '../list/categoria-ocorrencia.component';
import { CategoriaOcorrenciaDetailComponent } from '../detail/categoria-ocorrencia-detail.component';
import { CategoriaOcorrenciaUpdateComponent } from '../update/categoria-ocorrencia-update.component';
import { CategoriaOcorrenciaRoutingResolveService } from './categoria-ocorrencia-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categoriaOcorrenciaRoute: Routes = [
  {
    path: '',
    component: CategoriaOcorrenciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoriaOcorrenciaDetailComponent,
    resolve: {
      categoriaOcorrencia: CategoriaOcorrenciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoriaOcorrenciaUpdateComponent,
    resolve: {
      categoriaOcorrencia: CategoriaOcorrenciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoriaOcorrenciaUpdateComponent,
    resolve: {
      categoriaOcorrencia: CategoriaOcorrenciaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categoriaOcorrenciaRoute)],
  exports: [RouterModule],
})
export class CategoriaOcorrenciaRoutingModule {}
