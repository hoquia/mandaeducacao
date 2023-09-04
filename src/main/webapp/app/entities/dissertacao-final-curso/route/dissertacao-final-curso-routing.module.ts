import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DissertacaoFinalCursoComponent } from '../list/dissertacao-final-curso.component';
import { DissertacaoFinalCursoDetailComponent } from '../detail/dissertacao-final-curso-detail.component';
import { DissertacaoFinalCursoUpdateComponent } from '../update/dissertacao-final-curso-update.component';
import { DissertacaoFinalCursoRoutingResolveService } from './dissertacao-final-curso-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dissertacaoFinalCursoRoute: Routes = [
  {
    path: '',
    component: DissertacaoFinalCursoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DissertacaoFinalCursoDetailComponent,
    resolve: {
      dissertacaoFinalCurso: DissertacaoFinalCursoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DissertacaoFinalCursoUpdateComponent,
    resolve: {
      dissertacaoFinalCurso: DissertacaoFinalCursoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DissertacaoFinalCursoUpdateComponent,
    resolve: {
      dissertacaoFinalCurso: DissertacaoFinalCursoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dissertacaoFinalCursoRoute)],
  exports: [RouterModule],
})
export class DissertacaoFinalCursoRoutingModule {}
