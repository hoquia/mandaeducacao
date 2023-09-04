import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NotasPeriodicaDisciplinaComponent } from '../list/notas-periodica-disciplina.component';
import { NotasPeriodicaDisciplinaDetailComponent } from '../detail/notas-periodica-disciplina-detail.component';
import { NotasPeriodicaDisciplinaUpdateComponent } from '../update/notas-periodica-disciplina-update.component';
import { NotasPeriodicaDisciplinaRoutingResolveService } from './notas-periodica-disciplina-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const notasPeriodicaDisciplinaRoute: Routes = [
  {
    path: '',
    component: NotasPeriodicaDisciplinaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotasPeriodicaDisciplinaDetailComponent,
    resolve: {
      notasPeriodicaDisciplina: NotasPeriodicaDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotasPeriodicaDisciplinaUpdateComponent,
    resolve: {
      notasPeriodicaDisciplina: NotasPeriodicaDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotasPeriodicaDisciplinaUpdateComponent,
    resolve: {
      notasPeriodicaDisciplina: NotasPeriodicaDisciplinaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(notasPeriodicaDisciplinaRoute)],
  exports: [RouterModule],
})
export class NotasPeriodicaDisciplinaRoutingModule {}
