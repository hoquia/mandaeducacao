import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransacao } from '../transacao.model';
import { TransacaoService } from '../service/transacao.service';

@Injectable({ providedIn: 'root' })
export class TransacaoRoutingResolveService implements Resolve<ITransacao | null> {
  constructor(protected service: TransacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransacao | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transacao: HttpResponse<ITransacao>) => {
          if (transacao.body) {
            return of(transacao.body);
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
