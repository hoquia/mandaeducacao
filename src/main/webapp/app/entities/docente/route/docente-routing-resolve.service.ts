import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocente } from '../docente.model';
import { DocenteService } from '../service/docente.service';

@Injectable({ providedIn: 'root' })
export class DocenteRoutingResolveService implements Resolve<IDocente | null> {
  constructor(protected service: DocenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocente | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((docente: HttpResponse<IDocente>) => {
          if (docente.body) {
            return of(docente.body);
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
