import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISequenciaDocumento } from '../sequencia-documento.model';
import { SequenciaDocumentoService } from '../service/sequencia-documento.service';

@Injectable({ providedIn: 'root' })
export class SequenciaDocumentoRoutingResolveService implements Resolve<ISequenciaDocumento | null> {
  constructor(protected service: SequenciaDocumentoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISequenciaDocumento | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sequenciaDocumento: HttpResponse<ISequenciaDocumento>) => {
          if (sequenciaDocumento.body) {
            return of(sequenciaDocumento.body);
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
