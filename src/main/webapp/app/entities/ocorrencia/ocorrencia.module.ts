import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OcorrenciaComponent } from './list/ocorrencia.component';
import { OcorrenciaDetailComponent } from './detail/ocorrencia-detail.component';
import { OcorrenciaUpdateComponent } from './update/ocorrencia-update.component';
import { OcorrenciaDeleteDialogComponent } from './delete/ocorrencia-delete-dialog.component';
import { OcorrenciaRoutingModule } from './route/ocorrencia-routing.module';

@NgModule({
  imports: [SharedModule, OcorrenciaRoutingModule],
  declarations: [OcorrenciaComponent, OcorrenciaDetailComponent, OcorrenciaUpdateComponent, OcorrenciaDeleteDialogComponent],
})
export class OcorrenciaModule {}
