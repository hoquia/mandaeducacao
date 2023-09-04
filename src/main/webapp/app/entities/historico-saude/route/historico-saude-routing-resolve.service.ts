import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHistoricoSaude } from '../historico-saude.model';
import { HistoricoSaudeService } from '../service/historico-saude.service';

@Injectable({ providedIn: 'root' })
export class HistoricoSaudeRoutingResolveService implements Resolve<IHistoricoSaude | null> {
  constructor(protected service: HistoricoSaudeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistoricoSaude | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((historicoSaude: HttpResponse<IHistoricoSaude>) => {
          if (historicoSaude.body) {
            return of(historicoSaude.body);
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
