import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILongonkeloHistorico } from '../longonkelo-historico.model';
import { LongonkeloHistoricoService } from '../service/longonkelo-historico.service';

@Injectable({ providedIn: 'root' })
export class LongonkeloHistoricoRoutingResolveService implements Resolve<ILongonkeloHistorico | null> {
  constructor(protected service: LongonkeloHistoricoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILongonkeloHistorico | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((longonkeloHistorico: HttpResponse<ILongonkeloHistorico>) => {
          if (longonkeloHistorico.body) {
            return of(longonkeloHistorico.body);
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
