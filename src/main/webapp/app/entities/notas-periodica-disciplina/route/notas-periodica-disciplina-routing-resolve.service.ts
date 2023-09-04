import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';
import { NotasPeriodicaDisciplinaService } from '../service/notas-periodica-disciplina.service';

@Injectable({ providedIn: 'root' })
export class NotasPeriodicaDisciplinaRoutingResolveService implements Resolve<INotasPeriodicaDisciplina | null> {
  constructor(protected service: NotasPeriodicaDisciplinaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotasPeriodicaDisciplina | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((notasPeriodicaDisciplina: HttpResponse<INotasPeriodicaDisciplina>) => {
          if (notasPeriodicaDisciplina.body) {
            return of(notasPeriodicaDisciplina.body);
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
