import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CampoActuacaoDissertacaoComponent } from './list/campo-actuacao-dissertacao.component';
import { CampoActuacaoDissertacaoDetailComponent } from './detail/campo-actuacao-dissertacao-detail.component';
import { CampoActuacaoDissertacaoUpdateComponent } from './update/campo-actuacao-dissertacao-update.component';
import { CampoActuacaoDissertacaoDeleteDialogComponent } from './delete/campo-actuacao-dissertacao-delete-dialog.component';
import { CampoActuacaoDissertacaoRoutingModule } from './route/campo-actuacao-dissertacao-routing.module';

@NgModule({
  imports: [SharedModule, CampoActuacaoDissertacaoRoutingModule],
  declarations: [
    CampoActuacaoDissertacaoComponent,
    CampoActuacaoDissertacaoDetailComponent,
    CampoActuacaoDissertacaoUpdateComponent,
    CampoActuacaoDissertacaoDeleteDialogComponent,
  ],
})
export class CampoActuacaoDissertacaoModule {}
