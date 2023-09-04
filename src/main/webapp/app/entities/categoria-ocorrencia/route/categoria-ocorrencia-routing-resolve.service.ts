import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoriaOcorrencia } from '../categoria-ocorrencia.model';
import { CategoriaOcorrenciaService } from '../service/categoria-ocorrencia.service';

@Injectable({ providedIn: 'root' })
export class CategoriaOcorrenciaRoutingResolveService implements Resolve<ICategoriaOcorrencia | null> {
  constructor(protected service: CategoriaOcorrenciaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaOcorrencia | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categoriaOcorrencia: HttpResponse<ICategoriaOcorrencia>) => {
          if (categoriaOcorrencia.body) {
            return of(categoriaOcorrencia.body);
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
