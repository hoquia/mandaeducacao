import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INotasGeralDisciplina } from '../notas-geral-disciplina.model';
import { NotasGeralDisciplinaService } from '../service/notas-geral-disciplina.service';

@Injectable({ providedIn: 'root' })
export class NotasGeralDisciplinaRoutingResolveService implements Resolve<INotasGeralDisciplina | null> {
  constructor(protected service: NotasGeralDisciplinaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotasGeralDisciplina | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((notasGeralDisciplina: HttpResponse<INotasGeralDisciplina>) => {
          if (notasGeralDisciplina.body) {
            return of(notasGeralDisciplina.body);
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
