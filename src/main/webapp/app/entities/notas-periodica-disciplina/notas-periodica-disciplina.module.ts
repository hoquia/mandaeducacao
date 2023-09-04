import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NotasPeriodicaDisciplinaComponent } from './list/notas-periodica-disciplina.component';
import { NotasPeriodicaDisciplinaDetailComponent } from './detail/notas-periodica-disciplina-detail.component';
import { NotasPeriodicaDisciplinaUpdateComponent } from './update/notas-periodica-disciplina-update.component';
import { NotasPeriodicaDisciplinaDeleteDialogComponent } from './delete/notas-periodica-disciplina-delete-dialog.component';
import { NotasPeriodicaDisciplinaRoutingModule } from './route/notas-periodica-disciplina-routing.module';

@NgModule({
  imports: [SharedModule, NotasPeriodicaDisciplinaRoutingModule],
  declarations: [
    NotasPeriodicaDisciplinaComponent,
    NotasPeriodicaDisciplinaDetailComponent,
    NotasPeriodicaDisciplinaUpdateComponent,
    NotasPeriodicaDisciplinaDeleteDialogComponent,
  ],
})
export class NotasPeriodicaDisciplinaModule {}
