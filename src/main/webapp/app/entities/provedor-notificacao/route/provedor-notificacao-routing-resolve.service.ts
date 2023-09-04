import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProvedorNotificacao } from '../provedor-notificacao.model';
import { ProvedorNotificacaoService } from '../service/provedor-notificacao.service';

@Injectable({ providedIn: 'root' })
export class ProvedorNotificacaoRoutingResolveService implements Resolve<IProvedorNotificacao | null> {
  constructor(protected service: ProvedorNotificacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProvedorNotificacao | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((provedorNotificacao: HttpResponse<IProvedorNotificacao>) => {
          if (provedorNotificacao.body) {
            return of(provedorNotificacao.body);
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
