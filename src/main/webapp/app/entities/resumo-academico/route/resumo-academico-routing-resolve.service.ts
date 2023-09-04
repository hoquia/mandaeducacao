import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResumoAcademico } from '../resumo-academico.model';
import { ResumoAcademicoService } from '../service/resumo-academico.service';

@Injectable({ providedIn: 'root' })
export class ResumoAcademicoRoutingResolveService implements Resolve<IResumoAcademico | null> {
  constructor(protected service: ResumoAcademicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResumoAcademico | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resumoAcademico: HttpResponse<IResumoAcademico>) => {
          if (resumoAcademico.body) {
            return of(resumoAcademico.body);
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
