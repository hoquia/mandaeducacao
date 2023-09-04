import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResumoImpostoFacturaComponent } from '../list/resumo-imposto-factura.component';
import { ResumoImpostoFacturaDetailComponent } from '../detail/resumo-imposto-factura-detail.component';
import { ResumoImpostoFacturaUpdateComponent } from '../update/resumo-imposto-factura-update.component';
import { ResumoImpostoFacturaRoutingResolveService } from './resumo-imposto-factura-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const resumoImpostoFacturaRoute: Routes = [
  {
    path: '',
    component: ResumoImpostoFacturaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResumoImpostoFacturaDetailComponent,
    resolve: {
      resumoImpostoFactura: ResumoImpostoFacturaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResumoImpostoFacturaUpdateComponent,
    resolve: {
      resumoImpostoFactura: ResumoImpostoFacturaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResumoImpostoFacturaUpdateComponent,
    resolve: {
      resumoImpostoFactura: ResumoImpostoFacturaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resumoImpostoFacturaRoute)],
  exports: [RouterModule],
})
export class ResumoImpostoFacturaRoutingModule {}
