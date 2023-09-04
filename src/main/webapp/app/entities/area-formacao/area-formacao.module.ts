import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AreaFormacaoComponent } from './list/area-formacao.component';
import { AreaFormacaoDetailComponent } from './detail/area-formacao-detail.component';
import { AreaFormacaoUpdateComponent } from './update/area-formacao-update.component';
import { AreaFormacaoDeleteDialogComponent } from './delete/area-formacao-delete-dialog.component';
import { AreaFormacaoRoutingModule } from './route/area-formacao-routing.module';

@NgModule({
  imports: [SharedModule, AreaFormacaoRoutingModule],
  declarations: [AreaFormacaoComponent, AreaFormacaoDetailComponent, AreaFormacaoUpdateComponent, AreaFormacaoDeleteDialogComponent],
})
export class AreaFormacaoModule {}
