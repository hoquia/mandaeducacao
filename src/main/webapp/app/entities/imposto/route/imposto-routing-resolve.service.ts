import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImposto } from '../imposto.model';
import { ImpostoService } from '../service/imposto.service';

@Injectable({ providedIn: 'root' })
export class ImpostoRoutingResolveService implements Resolve<IImposto | null> {
  constructor(protected service: ImpostoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImposto | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((imposto: HttpResponse<IImposto>) => {
          if (imposto.body) {
            return of(imposto.body);
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
