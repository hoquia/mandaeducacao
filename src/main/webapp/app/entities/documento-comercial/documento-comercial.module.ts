import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DocumentoComercialComponent } from './list/documento-comercial.component';
import { DocumentoComercialDetailComponent } from './detail/documento-comercial-detail.component';
import { DocumentoComercialUpdateComponent } from './update/documento-comercial-update.component';
import { DocumentoComercialDeleteDialogComponent } from './delete/documento-comercial-delete-dialog.component';
import { DocumentoComercialRoutingModule } from './route/documento-comercial-routing.module';

@NgModule({
  imports: [SharedModule, DocumentoComercialRoutingModule],
  declarations: [
    DocumentoComercialComponent,
    DocumentoComercialDetailComponent,
    DocumentoComercialUpdateComponent,
    DocumentoComercialDeleteDialogComponent,
  ],
})
export class DocumentoComercialModule {}
