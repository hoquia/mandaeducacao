import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILookupItem } from '../lookup-item.model';
import { LookupItemService } from '../service/lookup-item.service';

@Injectable({ providedIn: 'root' })
export class LookupItemRoutingResolveService implements Resolve<ILookupItem | null> {
  constructor(protected service: LookupItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILookupItem | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lookupItem: HttpResponse<ILookupItem>) => {
          if (lookupItem.body) {
            return of(lookupItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
