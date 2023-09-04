import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResponsavelAreaFormacaoComponent } from './list/responsavel-area-formacao.component';
import { ResponsavelAreaFormacaoDetailComponent } from './detail/responsavel-area-formacao-detail.component';
import { ResponsavelAreaFormacaoUpdateComponent } from './update/responsavel-area-formacao-update.component';
import { ResponsavelAreaFormacaoDeleteDialogComponent } from './delete/responsavel-area-formacao-delete-dialog.component';
import { ResponsavelAreaFormacaoRoutingModule } from './route/responsavel-area-formacao-routing.module';

@NgModule({
  imports: [SharedModule, ResponsavelAreaFormacaoRoutingModule],
  declarations: [
    ResponsavelAreaFormacaoComponent,
    ResponsavelAreaFormacaoDetailComponent,
    ResponsavelAreaFormacaoUpdateComponent,
    ResponsavelAreaFormacaoDeleteDialogComponent,
  ],
})
export class ResponsavelAreaFormacaoModule {}
