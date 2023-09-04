import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoAula } from '../plano-aula.model';
import { PlanoAulaService } from '../service/plano-aula.service';

@Injectable({ providedIn: 'root' })
export class PlanoAulaRoutingResolveService implements Resolve<IPlanoAula | null> {
  constructor(protected service: PlanoAulaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoAula | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((planoAula: HttpResponse<IPlanoAula>) => {
          if (planoAula.body) {
            return of(planoAula.body);
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
