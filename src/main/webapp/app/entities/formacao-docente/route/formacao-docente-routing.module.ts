import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FormacaoDocenteComponent } from '../list/formacao-docente.component';
import { FormacaoDocenteDetailComponent } from '../detail/formacao-docente-detail.component';
import { FormacaoDocenteUpdateComponent } from '../update/formacao-docente-update.component';
import { FormacaoDocenteRoutingResolveService } from './formacao-docente-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const formacaoDocenteRoute: Routes = [
  {
    path: '',
    component: FormacaoDocenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FormacaoDocenteDetailComponent,
    resolve: {
      formacaoDocente: FormacaoDocenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FormacaoDocenteUpdateComponent,
    resolve: {
      formacaoDocente: FormacaoDocenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FormacaoDocenteUpdateComponent,
    resolve: {
      formacaoDocente: FormacaoDocenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(formacaoDocenteRoute)],
  exports: [RouterModule],
})
export class FormacaoDocenteRoutingModule {}
