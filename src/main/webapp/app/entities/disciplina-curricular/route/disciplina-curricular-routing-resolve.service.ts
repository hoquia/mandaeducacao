import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDisciplinaCurricular } from '../disciplina-curricular.model';
import { DisciplinaCurricularService } from '../service/disciplina-curricular.service';

@Injectable({ providedIn: 'root' })
export class DisciplinaCurricularRoutingResolveService implements Resolve<IDisciplinaCurricular | null> {
  constructor(protected service: DisciplinaCurricularService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDisciplinaCurricular | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((disciplinaCurricular: HttpResponse<IDisciplinaCurricular>) => {
          if (disciplinaCurricular.body) {
            return of(disciplinaCurricular.body);
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
