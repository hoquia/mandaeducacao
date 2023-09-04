import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProcessoSelectivoMatricula } from '../processo-selectivo-matricula.model';
import { ProcessoSelectivoMatriculaService } from '../service/processo-selectivo-matricula.service';

@Injectable({ providedIn: 'root' })
export class ProcessoSelectivoMatriculaRoutingResolveService implements Resolve<IProcessoSelectivoMatricula | null> {
  constructor(protected service: ProcessoSelectivoMatriculaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcessoSelectivoMatricula | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((processoSelectivoMatricula: HttpResponse<IProcessoSelectivoMatricula>) => {
          if (processoSelectivoMatricula.body) {
            return of(processoSelectivoMatricula.body);
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
