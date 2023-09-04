import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoCurricular } from '../plano-curricular.model';
import { PlanoCurricularService } from '../service/plano-curricular.service';

@Injectable({ providedIn: 'root' })
export class PlanoCurricularRoutingResolveService implements Resolve<IPlanoCurricular | null> {
  constructor(protected service: PlanoCurricularService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoCurricular | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((planoCurricular: HttpResponse<IPlanoCurricular>) => {
          if (planoCurricular.body) {
            return of(planoCurricular.body);
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
