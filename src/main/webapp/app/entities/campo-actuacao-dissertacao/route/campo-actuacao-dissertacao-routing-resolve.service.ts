import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';
import { CampoActuacaoDissertacaoService } from '../service/campo-actuacao-dissertacao.service';

@Injectable({ providedIn: 'root' })
export class CampoActuacaoDissertacaoRoutingResolveService implements Resolve<ICampoActuacaoDissertacao | null> {
  constructor(protected service: CampoActuacaoDissertacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICampoActuacaoDissertacao | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((campoActuacaoDissertacao: HttpResponse<ICampoActuacaoDissertacao>) => {
          if (campoActuacaoDissertacao.body) {
            return of(campoActuacaoDissertacao.body);
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
