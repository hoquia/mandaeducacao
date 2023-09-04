import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResponsavelTurno } from '../responsavel-turno.model';
import { ResponsavelTurnoService } from '../service/responsavel-turno.service';

@Injectable({ providedIn: 'root' })
export class ResponsavelTurnoRoutingResolveService implements Resolve<IResponsavelTurno | null> {
  constructor(protected service: ResponsavelTurnoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavelTurno | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((responsavelTurno: HttpResponse<IResponsavelTurno>) => {
          if (responsavelTurno.body) {
            return of(responsavelTurno.body);
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
