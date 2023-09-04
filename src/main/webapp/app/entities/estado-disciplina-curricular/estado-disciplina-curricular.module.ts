import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EstadoDisciplinaCurricularComponent } from './list/estado-disciplina-curricular.component';
import { EstadoDisciplinaCurricularDetailComponent } from './detail/estado-disciplina-curricular-detail.component';
import { EstadoDisciplinaCurricularUpdateComponent } from './update/estado-disciplina-curricular-update.component';
import { EstadoDisciplinaCurricularDeleteDialogComponent } from './delete/estado-disciplina-curricular-delete-dialog.component';
import { EstadoDisciplinaCurricularRoutingModule } from './route/estado-disciplina-curricular-routing.module';

@NgModule({
  imports: [SharedModule, EstadoDisciplinaCurricularRoutingModule],
  declarations: [
    EstadoDisciplinaCurricularComponent,
    EstadoDisciplinaCurricularDetailComponent,
    EstadoDisciplinaCurricularUpdateComponent,
    EstadoDisciplinaCurricularDeleteDialogComponent,
  ],
})
export class EstadoDisciplinaCurricularModule {}
