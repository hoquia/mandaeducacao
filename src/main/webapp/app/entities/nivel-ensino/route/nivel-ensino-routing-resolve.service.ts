import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INivelEnsino } from '../nivel-ensino.model';
import { NivelEnsinoService } from '../service/nivel-ensino.service';

@Injectable({ providedIn: 'root' })
export class NivelEnsinoRoutingResolveService implements Resolve<INivelEnsino | null> {
  constructor(protected service: NivelEnsinoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INivelEnsino | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nivelEnsino: HttpResponse<INivelEnsino>) => {
          if (nivelEnsino.body) {
            return of(nivelEnsino.body);
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
