import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormacaoDocente } from '../formacao-docente.model';
import { FormacaoDocenteService } from '../service/formacao-docente.service';

@Injectable({ providedIn: 'root' })
export class FormacaoDocenteRoutingResolveService implements Resolve<IFormacaoDocente | null> {
  constructor(protected service: FormacaoDocenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormacaoDocente | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((formacaoDocente: HttpResponse<IFormacaoDocente>) => {
          if (formacaoDocente.body) {
            return of(formacaoDocente.body);
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
