import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrecoEmolumento } from '../preco-emolumento.model';
import { PrecoEmolumentoService } from '../service/preco-emolumento.service';

@Injectable({ providedIn: 'root' })
export class PrecoEmolumentoRoutingResolveService implements Resolve<IPrecoEmolumento | null> {
  constructor(protected service: PrecoEmolumentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrecoEmolumento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((precoEmolumento: HttpResponse<IPrecoEmolumento>) => {
          if (precoEmolumento.body) {
            return of(precoEmolumento.body);
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
