import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InstituicaoEnsinoComponent } from './list/instituicao-ensino.component';
import { InstituicaoEnsinoDetailComponent } from './detail/instituicao-ensino-detail.component';
import { InstituicaoEnsinoUpdateComponent } from './update/instituicao-ensino-update.component';
import { InstituicaoEnsinoDeleteDialogComponent } from './delete/instituicao-ensino-delete-dialog.component';
import { InstituicaoEnsinoRoutingModule } from './route/instituicao-ensino-routing.module';

@NgModule({
  imports: [SharedModule, InstituicaoEnsinoRoutingModule],
  declarations: [
    InstituicaoEnsinoComponent,
    InstituicaoEnsinoDetailComponent,
    InstituicaoEnsinoUpdateComponent,
    InstituicaoEnsinoDeleteDialogComponent,
  ],
})
export class InstituicaoEnsinoModule {}
