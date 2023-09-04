import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LookupItemComponent } from './list/lookup-item.component';
import { LookupItemDetailComponent } from './detail/lookup-item-detail.component';
import { LookupItemUpdateComponent } from './update/lookup-item-update.component';
import { LookupItemDeleteDialogComponent } from './delete/lookup-item-delete-dialog.component';
import { LookupItemRoutingModule } from './route/lookup-item-routing.module';

@NgModule({
  imports: [SharedModule, LookupItemRoutingModule],
  declarations: [LookupItemComponent, LookupItemDetailComponent, LookupItemUpdateComponent, LookupItemDeleteDialogComponent],
})
export class LookupItemModule {}
