import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoMulta } from '../plano-multa.model';
import { PlanoMultaService } from '../service/plano-multa.service';

@Injectable({ providedIn: 'root' })
export class PlanoMultaRoutingResolveService implements Resolve<IPlanoMulta | null> {
  constructor(protected service: PlanoMultaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoMulta | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((planoMulta: HttpResponse<IPlanoMulta>) => {
          if (planoMulta.body) {
            return of(planoMulta.body);
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
