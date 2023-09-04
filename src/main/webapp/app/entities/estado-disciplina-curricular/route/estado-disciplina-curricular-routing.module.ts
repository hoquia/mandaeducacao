import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EstadoDisciplinaCurricularComponent } from '../list/estado-disciplina-curricular.component';
import { EstadoDisciplinaCurricularDetailComponent } from '../detail/estado-disciplina-curricular-detail.component';
import { EstadoDisciplinaCurricularUpdateComponent } from '../update/estado-disciplina-curricular-update.component';
import { EstadoDisciplinaCurricularRoutingResolveService } from './estado-disciplina-curricular-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const estadoDisciplinaCurricularRoute: Routes = [
  {
    path: '',
    component: EstadoDisciplinaCurricularComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstadoDisciplinaCurricularDetailComponent,
    resolve: {
      estadoDisciplinaCurricular: EstadoDisciplinaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstadoDisciplinaCurricularUpdateComponent,
    resolve: {
      estadoDisciplinaCurricular: EstadoDisciplinaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstadoDisciplinaCurricularUpdateComponent,
    resolve: {
      estadoDisciplinaCurricular: EstadoDisciplinaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(estadoDisciplinaCurricularRoute)],
  exports: [RouterModule],
})
export class EstadoDisciplinaCurricularRoutingModule {}
