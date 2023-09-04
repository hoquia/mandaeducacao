import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResponsavelTurma } from '../responsavel-turma.model';
import { ResponsavelTurmaService } from '../service/responsavel-turma.service';

@Injectable({ providedIn: 'root' })
export class ResponsavelTurmaRoutingResolveService implements Resolve<IResponsavelTurma | null> {
  constructor(protected service: ResponsavelTurmaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavelTurma | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((responsavelTurma: HttpResponse<IResponsavelTurma>) => {
          if (responsavelTurma.body) {
            return of(responsavelTurma.body);
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
