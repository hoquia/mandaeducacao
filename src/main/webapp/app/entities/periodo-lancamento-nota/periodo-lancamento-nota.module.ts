import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PeriodoLancamentoNotaComponent } from './list/periodo-lancamento-nota.component';
import { PeriodoLancamentoNotaDetailComponent } from './detail/periodo-lancamento-nota-detail.component';
import { PeriodoLancamentoNotaUpdateComponent } from './update/periodo-lancamento-nota-update.component';
import { PeriodoLancamentoNotaDeleteDialogComponent } from './delete/periodo-lancamento-nota-delete-dialog.component';
import { PeriodoLancamentoNotaRoutingModule } from './route/periodo-lancamento-nota-routing.module';

@NgModule({
  imports: [SharedModule, PeriodoLancamentoNotaRoutingModule],
  declarations: [
    PeriodoLancamentoNotaComponent,
    PeriodoLancamentoNotaDetailComponent,
    PeriodoLancamentoNotaUpdateComponent,
    PeriodoLancamentoNotaDeleteDialogComponent,
  ],
})
export class PeriodoLancamentoNotaModule {}
