import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAplicacaoRecibo } from '../aplicacao-recibo.model';
import { AplicacaoReciboService } from '../service/aplicacao-recibo.service';

@Injectable({ providedIn: 'root' })
export class AplicacaoReciboRoutingResolveService implements Resolve<IAplicacaoRecibo | null> {
  constructor(protected service: AplicacaoReciboService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAplicacaoRecibo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((aplicacaoRecibo: HttpResponse<IAplicacaoRecibo>) => {
          if (aplicacaoRecibo.body) {
            return of(aplicacaoRecibo.body);
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
