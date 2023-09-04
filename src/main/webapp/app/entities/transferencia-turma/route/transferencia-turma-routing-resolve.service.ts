import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransferenciaTurma } from '../transferencia-turma.model';
import { TransferenciaTurmaService } from '../service/transferencia-turma.service';

@Injectable({ providedIn: 'root' })
export class TransferenciaTurmaRoutingResolveService implements Resolve<ITransferenciaTurma | null> {
  constructor(protected service: TransferenciaTurmaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransferenciaTurma | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transferenciaTurma: HttpResponse<ITransferenciaTurma>) => {
          if (transferenciaTurma.body) {
            return of(transferenciaTurma.body);
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
