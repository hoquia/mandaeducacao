import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { InstituicaoEnsinoService } from '../service/instituicao-ensino.service';

@Injectable({ providedIn: 'root' })
export class InstituicaoEnsinoRoutingResolveService implements Resolve<IInstituicaoEnsino | null> {
  constructor(protected service: InstituicaoEnsinoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInstituicaoEnsino | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((instituicaoEnsino: HttpResponse<IInstituicaoEnsino>) => {
          if (instituicaoEnsino.body) {
            return of(instituicaoEnsino.body);
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
