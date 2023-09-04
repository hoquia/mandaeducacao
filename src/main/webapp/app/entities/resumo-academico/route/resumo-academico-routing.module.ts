import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumoAcademicoComponent } from '../list/resumo-academico.component';
import { ResumoAcademicoDetailComponent } from '../detail/resumo-academico-detail.component';
import { ResumoAcademicoUpdateComponent } from '../update/resumo-academico-update.component';
import { ResumoAcademicoRoutingResolveService } from './resumo-academico-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const resumoAcademicoRoute: Routes = [
  {
    path: '',
    component: ResumoAcademicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumoAcademicoDetailComponent,
    resolve: {
      resumoAcademico: ResumoAcademicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumoAcademicoUpdateComponent,
    resolve: {
      resumoAcademico: ResumoAcademicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumoAcademicoUpdateComponent,
    resolve: {
      resumoAcademico: ResumoAcademicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumoAcademicoRoute)],
  exports: [RouterModule],
})
export class ResumoAcademicoRoutingModule {}
