import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EnderecoDiscenteComponent } from './list/endereco-discente.component';
import { EnderecoDiscenteDetailComponent } from './detail/endereco-discente-detail.component';
import { EnderecoDiscenteUpdateComponent } from './update/endereco-discente-update.component';
import { EnderecoDiscenteDeleteDialogComponent } from './delete/endereco-discente-delete-dialog.component';
import { EnderecoDiscenteRoutingModule } from './route/endereco-discente-routing.module';

@NgModule({
  imports: [SharedModule, EnderecoDiscenteRoutingModule],
  declarations: [
    EnderecoDiscenteComponent,
    EnderecoDiscenteDetailComponent,
    EnderecoDiscenteUpdateComponent,
    EnderecoDiscenteDeleteDialogComponent,
  ],
})
export class EnderecoDiscenteModule {}
