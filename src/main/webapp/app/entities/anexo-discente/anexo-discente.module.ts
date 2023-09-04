import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnexoDiscenteComponent } from './list/anexo-discente.component';
import { AnexoDiscenteDetailComponent } from './detail/anexo-discente-detail.component';
import { AnexoDiscenteUpdateComponent } from './update/anexo-discente-update.component';
import { AnexoDiscenteDeleteDialogComponent } from './delete/anexo-discente-delete-dialog.component';
import { AnexoDiscenteRoutingModule } from './route/anexo-discente-routing.module';

@NgModule({
  imports: [SharedModule, AnexoDiscenteRoutingModule],
  declarations: [AnexoDiscenteComponent, AnexoDiscenteDetailComponent, AnexoDiscenteUpdateComponent, AnexoDiscenteDeleteDialogComponent],
})
export class AnexoDiscenteModule {}
