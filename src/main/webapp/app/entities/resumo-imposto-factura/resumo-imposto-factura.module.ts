import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumoImpostoFacturaComponent } from './list/resumo-imposto-factura.component';
import { ResumoImpostoFacturaDetailComponent } from './detail/resumo-imposto-factura-detail.component';
import { ResumoImpostoFacturaUpdateComponent } from './update/resumo-imposto-factura-update.component';
import { ResumoImpostoFacturaDeleteDialogComponent } from './delete/resumo-imposto-factura-delete-dialog.component';
import { ResumoImpostoFacturaRoutingModule } from './route/resumo-imposto-factura-routing.module';

@NgModule({
  imports: [SharedModule, ResumoImpostoFacturaRoutingModule],
  declarations: [
    ResumoImpostoFacturaComponent,
    ResumoImpostoFacturaDetailComponent,
    ResumoImpostoFacturaUpdateComponent,
    ResumoImpostoFacturaDeleteDialogComponent,
  ],
})
export class ResumoImpostoFacturaModule {}
