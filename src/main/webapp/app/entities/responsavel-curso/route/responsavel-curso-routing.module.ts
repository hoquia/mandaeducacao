import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResponsavelCursoComponent } from '../list/responsavel-curso.component';
import { ResponsavelCursoDetailComponent } from '../detail/responsavel-curso-detail.component';
import { ResponsavelCursoUpdateComponent } from '../update/responsavel-curso-update.component';
import { ResponsavelCursoRoutingResolveService } from './responsavel-curso-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const responsavelCursoRoute: Routes = [
  {
    path: '',
    component: ResponsavelCursoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsavelCursoDetailComponent,
    resolve: {
      responsavelCurso: ResponsavelCursoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsavelCursoUpdateComponent,
    resolve: {
      responsavelCurso: ResponsavelCursoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsavelCursoUpdateComponent,
    resolve: {
      responsavelCurso: ResponsavelCursoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(responsavelCursoRoute)],
  exports: [RouterModule],
})
export class ResponsavelCursoRoutingModule {}
