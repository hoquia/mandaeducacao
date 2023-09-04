import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPeriodoHorario } from '../periodo-horario.model';
import { PeriodoHorarioService } from '../service/periodo-horario.service';

@Injectable({ providedIn: 'root' })
export class PeriodoHorarioRoutingResolveService implements Resolve<IPeriodoHorario | null> {
  constructor(protected service: PeriodoHorarioService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriodoHorario | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((periodoHorario: HttpResponse<IPeriodoHorario>) => {
          if (periodoHorario.body) {
            return of(periodoHorario.body);
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
