import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SerieDocumentoComponent } from './list/serie-documento.component';
import { SerieDocumentoDetailComponent } from './detail/serie-documento-detail.component';
import { SerieDocumentoUpdateComponent } from './update/serie-documento-update.component';
import { SerieDocumentoDeleteDialogComponent } from './delete/serie-documento-delete-dialog.component';
import { SerieDocumentoRoutingModule } from './route/serie-documento-routing.module';

@NgModule({
  imports: [SharedModule, SerieDocumentoRoutingModule],
  declarations: [
    SerieDocumentoComponent,
    SerieDocumentoDetailComponent,
    SerieDocumentoUpdateComponent,
    SerieDocumentoDeleteDialogComponent,
  ],
})
export class SerieDocumentoModule {}
