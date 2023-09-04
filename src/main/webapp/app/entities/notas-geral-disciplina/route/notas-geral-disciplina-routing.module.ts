import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NotasGeralDisciplinaComponent } from '../list/notas-geral-disciplina.component';
import { NotasGeralDisciplinaDetailComponent } from '../detail/notas-geral-disciplina-detail.component';
import { NotasGeralDisciplinaUpdateComponent } from '../update/notas-geral-disciplina-update.component';
import { NotasGeralDisciplinaRoutingResolveService } from './notas-geral-disciplina-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const notasGeralDisciplinaRoute: Routes = [
  {
    path: '',
    component: NotasGeralDisciplinaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotasGeralDisciplinaDetailComponent,
    resolve: {
      notasGeralDisciplina: NotasGeralDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotasGeralDisciplinaUpdateComponent,
    resolve: {
      notasGeralDisciplina: NotasGeralDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotasGeralDisciplinaUpdateComponent,
    resolve: {
      notasGeralDisciplina: NotasGeralDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(notasGeralDisciplinaRoute)],
  exports: [RouterModule],
})
export class NotasGeralDisciplinaRoutingModule {}
