import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResponsavelDisciplinaComponent } from '../list/responsavel-disciplina.component';
import { ResponsavelDisciplinaDetailComponent } from '../detail/responsavel-disciplina-detail.component';
import { ResponsavelDisciplinaUpdateComponent } from '../update/responsavel-disciplina-update.component';
import { ResponsavelDisciplinaRoutingResolveService } from './responsavel-disciplina-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const responsavelDisciplinaRoute: Routes = [
  {
    path: '',
    component: ResponsavelDisciplinaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResponsavelDisciplinaDetailComponent,
    resolve: {
      responsavelDisciplina: ResponsavelDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResponsavelDisciplinaUpdateComponent,
    resolve: {
      responsavelDisciplina: ResponsavelDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResponsavelDisciplinaUpdateComponent,
    resolve: {
      responsavelDisciplina: ResponsavelDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(responsavelDisciplinaRoute)],
  exports: [RouterModule],
})
export class ResponsavelDisciplinaRoutingModule {}
