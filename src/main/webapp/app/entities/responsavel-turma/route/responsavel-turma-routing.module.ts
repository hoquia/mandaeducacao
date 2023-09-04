import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResponsavelTurmaComponent } from '../list/responsavel-turma.component';
import { ResponsavelTurmaDetailComponent } from '../detail/responsavel-turma-detail.component';
import { ResponsavelTurmaUpdateComponent } from '../update/responsavel-turma-update.component';
import { ResponsavelTurmaRoutingResolveService } from './responsavel-turma-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const responsavelTurmaRoute: Routes = [
  {
    path: '',
    component: ResponsavelTurmaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsavelTurmaDetailComponent,
    resolve: {
      responsavelTurma: ResponsavelTurmaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsavelTurmaUpdateComponent,
    resolve: {
      responsavelTurma: ResponsavelTurmaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsavelTurmaUpdateComponent,
    resolve: {
      responsavelTurma: ResponsavelTurmaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(responsavelTurmaRoute)],
  exports: [RouterModule],
})
export class ResponsavelTurmaRoutingModule {}
