import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DisciplinaCurricularComponent } from './list/disciplina-curricular.component';
import { DisciplinaCurricularDetailComponent } from './detail/disciplina-curricular-detail.component';
import { DisciplinaCurricularUpdateComponent } from './update/disciplina-curricular-update.component';
import { DisciplinaCurricularDeleteDialogComponent } from './delete/disciplina-curricular-delete-dialog.component';
import { DisciplinaCurricularRoutingModule } from './route/disciplina-curricular-routing.module';

@NgModule({
  imports: [SharedModule, DisciplinaCurricularRoutingModule],
  declarations: [
    DisciplinaCurricularComponent,
    DisciplinaCurricularDetailComponent,
    DisciplinaCurricularUpdateComponent,
    DisciplinaCurricularDeleteDialogComponent,
  ],
})
export class DisciplinaCurricularModule {}
