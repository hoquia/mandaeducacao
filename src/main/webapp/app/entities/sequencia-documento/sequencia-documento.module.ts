import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SequenciaDocumentoComponent } from './list/sequencia-documento.component';
import { SequenciaDocumentoDetailComponent } from './detail/sequencia-documento-detail.component';
import { SequenciaDocumentoUpdateComponent } from './update/sequencia-documento-update.component';
import { SequenciaDocumentoDeleteDialogComponent } from './delete/sequencia-documento-delete-dialog.component';
import { SequenciaDocumentoRoutingModule } from './route/sequencia-documento-routing.module';

@NgModule({
  imports: [SharedModule, SequenciaDocumentoRoutingModule],
  declarations: [
    SequenciaDocumentoComponent,
    SequenciaDocumentoDetailComponent,
    SequenciaDocumentoUpdateComponent,
    SequenciaDocumentoDeleteDialogComponent,
  ],
})
export class SequenciaDocumentoModule {}
