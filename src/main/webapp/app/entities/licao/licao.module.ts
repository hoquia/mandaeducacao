import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LicaoComponent } from './list/licao.component';
import { LicaoDetailComponent } from './detail/licao-detail.component';
import { LicaoUpdateComponent } from './update/licao-update.component';
import { LicaoDeleteDialogComponent } from './delete/licao-delete-dialog.component';
import { LicaoRoutingModule } from './route/licao-routing.module';

@NgModule({
  imports: [SharedModule, LicaoRoutingModule],
  declarations: [LicaoComponent, LicaoDetailComponent, LicaoUpdateComponent, LicaoDeleteDialogComponent],
})
export class LicaoModule {}
