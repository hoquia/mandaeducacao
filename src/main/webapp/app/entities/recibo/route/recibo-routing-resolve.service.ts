import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRecibo } from '../recibo.model';
import { ReciboService } from '../service/recibo.service';

@Injectable({ providedIn: 'root' })
export class ReciboRoutingResolveService implements Resolve<IRecibo | null> {
  constructor(protected service: ReciboService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRecibo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((recibo: HttpResponse<IRecibo>) => {
          if (recibo.body) {
            return of(recibo.body);
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
