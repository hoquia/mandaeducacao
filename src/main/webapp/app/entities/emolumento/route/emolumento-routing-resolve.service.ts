import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmolumento } from '../emolumento.model';
import { EmolumentoService } from '../service/emolumento.service';

@Injectable({ providedIn: 'root' })
export class EmolumentoRoutingResolveService implements Resolve<IEmolumento | null> {
  constructor(protected service: EmolumentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmolumento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((emolumento: HttpResponse<IEmolumento>) => {
          if (emolumento.body) {
            return of(emolumento.body);
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
