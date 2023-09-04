import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILookup } from '../lookup.model';
import { LookupService } from '../service/lookup.service';

@Injectable({ providedIn: 'root' })
export class LookupRoutingResolveService implements Resolve<ILookup | null> {
  constructor(protected service: LookupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILookup | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lookup: HttpResponse<ILookup>) => {
          if (lookup.body) {
            return of(lookup.body);
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
