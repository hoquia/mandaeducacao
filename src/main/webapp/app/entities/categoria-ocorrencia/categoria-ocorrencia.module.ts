import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategoriaOcorrenciaComponent } from './list/categoria-ocorrencia.component';
import { CategoriaOcorrenciaDetailComponent } from './detail/categoria-ocorrencia-detail.component';
import { CategoriaOcorrenciaUpdateComponent } from './update/categoria-ocorrencia-update.component';
import { CategoriaOcorrenciaDeleteDialogComponent } from './delete/categoria-ocorrencia-delete-dialog.component';
import { CategoriaOcorrenciaRoutingModule } from './route/categoria-ocorrencia-routing.module';

@NgModule({
  imports: [SharedModule, CategoriaOcorrenciaRoutingModule],
  declarations: [
    CategoriaOcorrenciaComponent,
    CategoriaOcorrenciaDetailComponent,
    CategoriaOcorrenciaUpdateComponent,
    CategoriaOcorrenciaDeleteDialogComponent,
  ],
})
export class CategoriaOcorrenciaModule {}
