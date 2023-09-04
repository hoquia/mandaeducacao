import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoDesconto } from '../plano-desconto.model';
import { PlanoDescontoService } from '../service/plano-desconto.service';

@Injectable({ providedIn: 'root' })
export class PlanoDescontoRoutingResolveService implements Resolve<IPlanoDesconto | null> {
  constructor(protected service: PlanoDescontoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoDesconto | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((planoDesconto: HttpResponse<IPlanoDesconto>) => {
          if (planoDesconto.body) {
            return of(planoDesconto.body);
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
