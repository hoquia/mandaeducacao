import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FormacaoDocenteComponent } from './list/formacao-docente.component';
import { FormacaoDocenteDetailComponent } from './detail/formacao-docente-detail.component';
import { FormacaoDocenteUpdateComponent } from './update/formacao-docente-update.component';
import { FormacaoDocenteDeleteDialogComponent } from './delete/formacao-docente-delete-dialog.component';
import { FormacaoDocenteRoutingModule } from './route/formacao-docente-routing.module';

@NgModule({
  imports: [SharedModule, FormacaoDocenteRoutingModule],
  declarations: [
    FormacaoDocenteComponent,
    FormacaoDocenteDetailComponent,
    FormacaoDocenteUpdateComponent,
    FormacaoDocenteDeleteDialogComponent,
  ],
})
export class FormacaoDocenteModule {}
