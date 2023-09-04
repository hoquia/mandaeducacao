import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResponsavelCursoComponent } from './list/responsavel-curso.component';
import { ResponsavelCursoDetailComponent } from './detail/responsavel-curso-detail.component';
import { ResponsavelCursoUpdateComponent } from './update/responsavel-curso-update.component';
import { ResponsavelCursoDeleteDialogComponent } from './delete/responsavel-curso-delete-dialog.component';
import { ResponsavelCursoRoutingModule } from './route/responsavel-curso-routing.module';

@NgModule({
  imports: [SharedModule, ResponsavelCursoRoutingModule],
  declarations: [
    ResponsavelCursoComponent,
    ResponsavelCursoDetailComponent,
    ResponsavelCursoUpdateComponent,
    ResponsavelCursoDeleteDialogComponent,
  ],
})
export class ResponsavelCursoModule {}
