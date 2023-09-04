import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransferenciaSaldo } from '../transferencia-saldo.model';
import { TransferenciaSaldoService } from '../service/transferencia-saldo.service';

@Injectable({ providedIn: 'root' })
export class TransferenciaSaldoRoutingResolveService implements Resolve<ITransferenciaSaldo | null> {
  constructor(protected service: TransferenciaSaldoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransferenciaSaldo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transferenciaSaldo: HttpResponse<ITransferenciaSaldo>) => {
          if (transferenciaSaldo.body) {
            return of(transferenciaSaldo.body);
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
