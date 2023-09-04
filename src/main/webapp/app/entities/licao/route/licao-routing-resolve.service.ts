import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILicao } from '../licao.model';
import { LicaoService } from '../service/licao.service';

@Injectable({ providedIn: 'root' })
export class LicaoRoutingResolveService implements Resolve<ILicao | null> {
  constructor(protected service: LicaoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILicao | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((licao: HttpResponse<ILicao>) => {
          if (licao.body) {
            return of(licao.body);
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
