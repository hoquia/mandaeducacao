import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NotasGeralDisciplinaComponent } from './list/notas-geral-disciplina.component';
import { NotasGeralDisciplinaDetailComponent } from './detail/notas-geral-disciplina-detail.component';
import { NotasGeralDisciplinaUpdateComponent } from './update/notas-geral-disciplina-update.component';
import { NotasGeralDisciplinaDeleteDialogComponent } from './delete/notas-geral-disciplina-delete-dialog.component';
import { NotasGeralDisciplinaRoutingModule } from './route/notas-geral-disciplina-routing.module';

@NgModule({
  imports: [SharedModule, NotasGeralDisciplinaRoutingModule],
  declarations: [
    NotasGeralDisciplinaComponent,
    NotasGeralDisciplinaDetailComponent,
    NotasGeralDisciplinaUpdateComponent,
    NotasGeralDisciplinaDeleteDialogComponent,
  ],
})
export class NotasGeralDisciplinaModule {}
