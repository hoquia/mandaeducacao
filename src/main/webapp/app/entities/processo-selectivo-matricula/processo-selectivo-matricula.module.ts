import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProcessoSelectivoMatriculaComponent } from './list/processo-selectivo-matricula.component';
import { ProcessoSelectivoMatriculaDetailComponent } from './detail/processo-selectivo-matricula-detail.component';
import { ProcessoSelectivoMatriculaUpdateComponent } from './update/processo-selectivo-matricula-update.component';
import { ProcessoSelectivoMatriculaDeleteDialogComponent } from './delete/processo-selectivo-matricula-delete-dialog.component';
import { ProcessoSelectivoMatriculaRoutingModule } from './route/processo-selectivo-matricula-routing.module';

@NgModule({
  imports: [SharedModule, ProcessoSelectivoMatriculaRoutingModule],
  declarations: [
    ProcessoSelectivoMatriculaComponent,
    ProcessoSelectivoMatriculaDetailComponent,
    ProcessoSelectivoMatriculaUpdateComponent,
    ProcessoSelectivoMatriculaDeleteDialogComponent,
  ],
})
export class ProcessoSelectivoMatriculaModule {}
