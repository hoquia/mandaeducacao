import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResumoAcademicoComponent } from './list/resumo-academico.component';
import { ResumoAcademicoDetailComponent } from './detail/resumo-academico-detail.component';
import { ResumoAcademicoUpdateComponent } from './update/resumo-academico-update.component';
import { ResumoAcademicoDeleteDialogComponent } from './delete/resumo-academico-delete-dialog.component';
import { ResumoAcademicoRoutingModule } from './route/resumo-academico-routing.module';

@NgModule({
  imports: [SharedModule, ResumoAcademicoRoutingModule],
  declarations: [
    ResumoAcademicoComponent,
    ResumoAcademicoDetailComponent,
    ResumoAcademicoUpdateComponent,
    ResumoAcademicoDeleteDialogComponent,
  ],
})
export class ResumoAcademicoModule {}
