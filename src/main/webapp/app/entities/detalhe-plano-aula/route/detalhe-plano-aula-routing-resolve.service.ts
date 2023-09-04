import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetalhePlanoAula } from '../detalhe-plano-aula.model';
import { DetalhePlanoAulaService } from '../service/detalhe-plano-aula.service';

@Injectable({ providedIn: 'root' })
export class DetalhePlanoAulaRoutingResolveService implements Resolve<IDetalhePlanoAula | null> {
  constructor(protected service: DetalhePlanoAulaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetalhePlanoAula | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((detalhePlanoAula: HttpResponse<IDetalhePlanoAula>) => {
          if (detalhePlanoAula.body) {
            return of(detalhePlanoAula.body);
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
