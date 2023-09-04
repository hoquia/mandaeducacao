import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaFormacao } from '../area-formacao.model';
import { AreaFormacaoService } from '../service/area-formacao.service';

@Injectable({ providedIn: 'root' })
export class AreaFormacaoRoutingResolveService implements Resolve<IAreaFormacao | null> {
  constructor(protected service: AreaFormacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAreaFormacao | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((areaFormacao: HttpResponse<IAreaFormacao>) => {
          if (areaFormacao.body) {
            return of(areaFormacao.body);
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
