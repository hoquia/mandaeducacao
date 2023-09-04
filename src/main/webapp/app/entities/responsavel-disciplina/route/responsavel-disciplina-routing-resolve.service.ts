import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResponsavelDisciplina } from '../responsavel-disciplina.model';
import { ResponsavelDisciplinaService } from '../service/responsavel-disciplina.service';

@Injectable({ providedIn: 'root' })
export class ResponsavelDisciplinaRoutingResolveService implements Resolve<IResponsavelDisciplina | null> {
  constructor(protected service: ResponsavelDisciplinaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavelDisciplina | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((responsavelDisciplina: HttpResponse<IResponsavelDisciplina>) => {
          if (responsavelDisciplina.body) {
            return of(responsavelDisciplina.body);
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
