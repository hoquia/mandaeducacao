import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoDiscente } from '../anexo-discente.model';
import { AnexoDiscenteService } from '../service/anexo-discente.service';

@Injectable({ providedIn: 'root' })
export class AnexoDiscenteRoutingResolveService implements Resolve<IAnexoDiscente | null> {
  constructor(protected service: AnexoDiscenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnexoDiscente | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((anexoDiscente: HttpResponse<IAnexoDiscente>) => {
          if (anexoDiscente.body) {
            return of(anexoDiscente.body);
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
