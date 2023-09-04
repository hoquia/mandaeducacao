import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoriaEmolumento } from '../categoria-emolumento.model';
import { CategoriaEmolumentoService } from '../service/categoria-emolumento.service';

@Injectable({ providedIn: 'root' })
export class CategoriaEmolumentoRoutingResolveService implements Resolve<ICategoriaEmolumento | null> {
  constructor(protected service: CategoriaEmolumentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaEmolumento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categoriaEmolumento: HttpResponse<ICategoriaEmolumento>) => {
          if (categoriaEmolumento.body) {
            return of(categoriaEmolumento.body);
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
