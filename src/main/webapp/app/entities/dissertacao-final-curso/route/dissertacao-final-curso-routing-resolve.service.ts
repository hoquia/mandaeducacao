import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDissertacaoFinalCurso } from '../dissertacao-final-curso.model';
import { DissertacaoFinalCursoService } from '../service/dissertacao-final-curso.service';

@Injectable({ providedIn: 'root' })
export class DissertacaoFinalCursoRoutingResolveService implements Resolve<IDissertacaoFinalCurso | null> {
  constructor(protected service: DissertacaoFinalCursoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDissertacaoFinalCurso | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dissertacaoFinalCurso: HttpResponse<IDissertacaoFinalCurso>) => {
          if (dissertacaoFinalCurso.body) {
            return of(dissertacaoFinalCurso.body);
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
