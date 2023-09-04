import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EstadoDissertacaoComponent } from './list/estado-dissertacao.component';
import { EstadoDissertacaoDetailComponent } from './detail/estado-dissertacao-detail.component';
import { EstadoDissertacaoUpdateComponent } from './update/estado-dissertacao-update.component';
import { EstadoDissertacaoDeleteDialogComponent } from './delete/estado-dissertacao-delete-dialog.component';
import { EstadoDissertacaoRoutingModule } from './route/estado-dissertacao-routing.module';

@NgModule({
  imports: [SharedModule, EstadoDissertacaoRoutingModule],
  declarations: [
    EstadoDissertacaoComponent,
    EstadoDissertacaoDetailComponent,
    EstadoDissertacaoUpdateComponent,
    EstadoDissertacaoDeleteDialogComponent,
  ],
})
export class EstadoDissertacaoModule {}
