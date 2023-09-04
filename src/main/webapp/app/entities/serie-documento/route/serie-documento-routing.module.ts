import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SerieDocumentoComponent } from '../list/serie-documento.component';
import { SerieDocumentoDetailComponent } from '../detail/serie-documento-detail.component';
import { SerieDocumentoUpdateComponent } from '../update/serie-documento-update.component';
import { SerieDocumentoRoutingResolveService } from './serie-documento-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const serieDocumentoRoute: Routes = [
  {
    path: '',
    component: SerieDocumentoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SerieDocumentoDetailComponent,
    resolve: {
      serieDocumento: SerieDocumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SerieDocumentoUpdateComponent,
    resolve: {
      serieDocumento: SerieDocumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SerieDocumentoUpdateComponent,
    resolve: {
      serieDocumento: SerieDocumentoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serieDocumentoRoute)],
  exports: [RouterModule],
})
export class SerieDocumentoRoutingModule {}
