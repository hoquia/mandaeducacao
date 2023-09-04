import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResponsavelCurso } from '../responsavel-curso.model';
import { ResponsavelCursoService } from '../service/responsavel-curso.service';

@Injectable({ providedIn: 'root' })
export class ResponsavelCursoRoutingResolveService implements Resolve<IResponsavelCurso | null> {
  constructor(protected service: ResponsavelCursoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavelCurso | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((responsavelCurso: HttpResponse<IResponsavelCurso>) => {
          if (responsavelCurso.body) {
            return of(responsavelCurso.body);
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
