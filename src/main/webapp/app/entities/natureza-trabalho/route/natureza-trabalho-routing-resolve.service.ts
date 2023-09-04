import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INaturezaTrabalho } from '../natureza-trabalho.model';
import { NaturezaTrabalhoService } from '../service/natureza-trabalho.service';

@Injectable({ providedIn: 'root' })
export class NaturezaTrabalhoRoutingResolveService implements Resolve<INaturezaTrabalho | null> {
  constructor(protected service: NaturezaTrabalhoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INaturezaTrabalho | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((naturezaTrabalho: HttpResponse<INaturezaTrabalho>) => {
          if (naturezaTrabalho.body) {
            return of(naturezaTrabalho.body);
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
