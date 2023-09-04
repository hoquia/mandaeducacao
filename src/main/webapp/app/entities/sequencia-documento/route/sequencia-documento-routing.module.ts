import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SequenciaDocumentoComponent } from '../list/sequencia-documento.component';
import { SequenciaDocumentoDetailComponent } from '../detail/sequencia-documento-detail.component';
import { SequenciaDocumentoUpdateComponent } from '../update/sequencia-documento-update.component';
import { SequenciaDocumentoRoutingResolveService } from './sequencia-documento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const sequenciaDocumentoRoute: Routes = [
  {
    path: '',
    component: SequenciaDocumentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SequenciaDocumentoDetailComponent,
    resolve: {
      sequenciaDocumento: SequenciaDocumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SequenciaDocumentoUpdateComponent,
    resolve: {
      sequenciaDocumento: SequenciaDocumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SequenciaDocumentoUpdateComponent,
    resolve: {
      sequenciaDocumento: SequenciaDocumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sequenciaDocumentoRoute)],
  exports: [RouterModule],
})
export class SequenciaDocumentoRoutingModule {}
