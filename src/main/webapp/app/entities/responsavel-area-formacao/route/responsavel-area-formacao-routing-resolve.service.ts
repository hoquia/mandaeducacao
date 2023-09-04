import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResponsavelAreaFormacao } from '../responsavel-area-formacao.model';
import { ResponsavelAreaFormacaoService } from '../service/responsavel-area-formacao.service';

@Injectable({ providedIn: 'root' })
export class ResponsavelAreaFormacaoRoutingResolveService implements Resolve<IResponsavelAreaFormacao | null> {
  constructor(protected service: ResponsavelAreaFormacaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavelAreaFormacao | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((responsavelAreaFormacao: HttpResponse<IResponsavelAreaFormacao>) => {
          if (responsavelAreaFormacao.body) {
            return of(responsavelAreaFormacao.body);
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
