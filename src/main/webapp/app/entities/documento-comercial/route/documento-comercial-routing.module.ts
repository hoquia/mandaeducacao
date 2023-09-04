import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DocumentoComercialComponent } from '../list/documento-comercial.component';
import { DocumentoComercialDetailComponent } from '../detail/documento-comercial-detail.component';
import { DocumentoComercialUpdateComponent } from '../update/documento-comercial-update.component';
import { DocumentoComercialRoutingResolveService } from './documento-comercial-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const documentoComercialRoute: Routes = [
  {
    path: '',
    component: DocumentoComercialComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentoComercialDetailComponent,
    resolve: {
      documentoComercial: DocumentoComercialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentoComercialUpdateComponent,
    resolve: {
      documentoComercial: DocumentoComercialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentoComercialUpdateComponent,
    resolve: {
      documentoComercial: DocumentoComercialRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(documentoComercialRoute)],
  exports: [RouterModule],
})
export class DocumentoComercialRoutingModule {}
