import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DissertacaoFinalCursoComponent } from './list/dissertacao-final-curso.component';
import { DissertacaoFinalCursoDetailComponent } from './detail/dissertacao-final-curso-detail.component';
import { DissertacaoFinalCursoUpdateComponent } from './update/dissertacao-final-curso-update.component';
import { DissertacaoFinalCursoDeleteDialogComponent } from './delete/dissertacao-final-curso-delete-dialog.component';
import { DissertacaoFinalCursoRoutingModule } from './route/dissertacao-final-curso-routing.module';

@NgModule({
  imports: [SharedModule, DissertacaoFinalCursoRoutingModule],
  declarations: [
    DissertacaoFinalCursoComponent,
    DissertacaoFinalCursoDetailComponent,
    DissertacaoFinalCursoUpdateComponent,
    DissertacaoFinalCursoDeleteDialogComponent,
  ],
})
export class DissertacaoFinalCursoModule {}
