import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentoComercial } from '../documento-comercial.model';
import { DocumentoComercialService } from '../service/documento-comercial.service';

@Injectable({ providedIn: 'root' })
export class DocumentoComercialRoutingResolveService implements Resolve<IDocumentoComercial | null> {
  constructor(protected service: DocumentoComercialService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentoComercial | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((documentoComercial: HttpResponse<IDocumentoComercial>) => {
          if (documentoComercial.body) {
            return of(documentoComercial.body);
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
