import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDiscente } from '../discente.model';
import { DiscenteService } from '../service/discente.service';

@Injectable({ providedIn: 'root' })
export class DiscenteRoutingResolveService implements Resolve<IDiscente | null> {
  constructor(protected service: DiscenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiscente | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((discente: HttpResponse<IDiscente>) => {
          if (discente.body) {
            return of(discente.body);
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
