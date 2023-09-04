import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResponsavelDisciplinaComponent } from './list/responsavel-disciplina.component';
import { ResponsavelDisciplinaDetailComponent } from './detail/responsavel-disciplina-detail.component';
import { ResponsavelDisciplinaUpdateComponent } from './update/responsavel-disciplina-update.component';
import { ResponsavelDisciplinaDeleteDialogComponent } from './delete/responsavel-disciplina-delete-dialog.component';
import { ResponsavelDisciplinaRoutingModule } from './route/responsavel-disciplina-routing.module';

@NgModule({
  imports: [SharedModule, ResponsavelDisciplinaRoutingModule],
  declarations: [
    ResponsavelDisciplinaComponent,
    ResponsavelDisciplinaDetailComponent,
    ResponsavelDisciplinaUpdateComponent,
    ResponsavelDisciplinaDeleteDialogComponent,
  ],
})
export class ResponsavelDisciplinaModule {}
