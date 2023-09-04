import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISerieDocumento } from '../serie-documento.model';
import { SerieDocumentoService } from '../service/serie-documento.service';

@Injectable({ providedIn: 'root' })
export class SerieDocumentoRoutingResolveService implements Resolve<ISerieDocumento | null> {
  constructor(protected service: SerieDocumentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISerieDocumento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((serieDocumento: HttpResponse<ISerieDocumento>) => {
          if (serieDocumento.body) {
            return of(serieDocumento.body);
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
