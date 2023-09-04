import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LookupComponent } from './list/lookup.component';
import { LookupDetailComponent } from './detail/lookup-detail.component';
import { LookupUpdateComponent } from './update/lookup-update.component';
import { LookupDeleteDialogComponent } from './delete/lookup-delete-dialog.component';
import { LookupRoutingModule } from './route/lookup-routing.module';

@NgModule({
  imports: [SharedModule, LookupRoutingModule],
  declarations: [LookupComponent, LookupDetailComponent, LookupUpdateComponent, LookupDeleteDialogComponent],
})
export class LookupModule {}
