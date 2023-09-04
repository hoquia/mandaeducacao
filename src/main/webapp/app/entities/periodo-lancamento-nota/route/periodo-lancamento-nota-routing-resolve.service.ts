import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';
import { PeriodoLancamentoNotaService } from '../service/periodo-lancamento-nota.service';

@Injectable({ providedIn: 'root' })
export class PeriodoLancamentoNotaRoutingResolveService implements Resolve<IPeriodoLancamentoNota | null> {
  constructor(protected service: PeriodoLancamentoNotaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriodoLancamentoNota | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((periodoLancamentoNota: HttpResponse<IPeriodoLancamentoNota>) => {
          if (periodoLancamentoNota.body) {
            return of(periodoLancamentoNota.body);
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
