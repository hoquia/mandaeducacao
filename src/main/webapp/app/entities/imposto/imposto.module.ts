import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ImpostoComponent } from './list/imposto.component';
import { ImpostoDetailComponent } from './detail/imposto-detail.component';
import { ImpostoUpdateComponent } from './update/imposto-update.component';
import { ImpostoDeleteDialogComponent } from './delete/imposto-delete-dialog.component';
import { ImpostoRoutingModule } from './route/imposto-routing.module';

@NgModule({
  imports: [SharedModule, ImpostoRoutingModule],
  declarations: [ImpostoComponent, ImpostoDetailComponent, ImpostoUpdateComponent, ImpostoDeleteDialogComponent],
})
export class ImpostoModule {}
