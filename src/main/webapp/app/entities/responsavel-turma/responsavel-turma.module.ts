import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResponsavelTurmaComponent } from './list/responsavel-turma.component';
import { ResponsavelTurmaDetailComponent } from './detail/responsavel-turma-detail.component';
import { ResponsavelTurmaUpdateComponent } from './update/responsavel-turma-update.component';
import { ResponsavelTurmaDeleteDialogComponent } from './delete/responsavel-turma-delete-dialog.component';
import { ResponsavelTurmaRoutingModule } from './route/responsavel-turma-routing.module';

@NgModule({
  imports: [SharedModule, ResponsavelTurmaRoutingModule],
  declarations: [
    ResponsavelTurmaComponent,
    ResponsavelTurmaDetailComponent,
    ResponsavelTurmaUpdateComponent,
    ResponsavelTurmaDeleteDialogComponent,
  ],
})
export class ResponsavelTurmaModule {}
