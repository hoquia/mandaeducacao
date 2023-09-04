import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DisciplinaCurricularComponent } from '../list/disciplina-curricular.component';
import { DisciplinaCurricularDetailComponent } from '../detail/disciplina-curricular-detail.component';
import { DisciplinaCurricularUpdateComponent } from '../update/disciplina-curricular-update.component';
import { DisciplinaCurricularRoutingResolveService } from './disciplina-curricular-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const disciplinaCurricularRoute: Routes = [
  {
    path: '',
    component: DisciplinaCurricularComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DisciplinaCurricularDetailComponent,
    resolve: {
      disciplinaCurricular: DisciplinaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DisciplinaCurricularUpdateComponent,
    resolve: {
      disciplinaCurricular: DisciplinaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DisciplinaCurricularUpdateComponent,
    resolve: {
      disciplinaCurricular: DisciplinaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(disciplinaCurricularRoute)],
  exports: [RouterModule],
})
export class DisciplinaCurricularRoutingModule {}
