import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMeioPagamento } from '../meio-pagamento.model';
import { MeioPagamentoService } from '../service/meio-pagamento.service';

@Injectable({ providedIn: 'root' })
export class MeioPagamentoRoutingResolveService implements Resolve<IMeioPagamento | null> {
  constructor(protected service: MeioPagamentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeioPagamento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((meioPagamento: HttpResponse<IMeioPagamento>) => {
          if (meioPagamento.body) {
            return of(meioPagamento.body);
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
