import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstadoDissertacao } from '../estado-dissertacao.model';
import { EstadoDissertacaoService } from '../service/estado-dissertacao.service';

@Injectable({ providedIn: 'root' })
export class EstadoDissertacaoRoutingResolveService implements Resolve<IEstadoDissertacao | null> {
  constructor(protected service: EstadoDissertacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoDissertacao | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((estadoDissertacao: HttpResponse<IEstadoDissertacao>) => {
          if (estadoDissertacao.body) {
            return of(estadoDissertacao.body);
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
