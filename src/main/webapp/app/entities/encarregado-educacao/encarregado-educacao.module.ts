import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EncarregadoEducacaoComponent } from './list/encarregado-educacao.component';
import { EncarregadoEducacaoDetailComponent } from './detail/encarregado-educacao-detail.component';
import { EncarregadoEducacaoUpdateComponent } from './update/encarregado-educacao-update.component';
import { EncarregadoEducacaoDeleteDialogComponent } from './delete/encarregado-educacao-delete-dialog.component';
import { EncarregadoEducacaoRoutingModule } from './route/encarregado-educacao-routing.module';

@NgModule({
  imports: [SharedModule, EncarregadoEducacaoRoutingModule],
  declarations: [
    EncarregadoEducacaoComponent,
    EncarregadoEducacaoDetailComponent,
    EncarregadoEducacaoUpdateComponent,
    EncarregadoEducacaoDeleteDialogComponent,
  ],
})
export class EncarregadoEducacaoModule {}
