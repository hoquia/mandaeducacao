import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnoLectivo } from '../ano-lectivo.model';
import { AnoLectivoService } from '../service/ano-lectivo.service';

@Injectable({ providedIn: 'root' })
export class AnoLectivoRoutingResolveService implements Resolve<IAnoLectivo | null> {
  constructor(protected service: AnoLectivoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnoLectivo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((anoLectivo: HttpResponse<IAnoLectivo>) => {
          if (anoLectivo.body) {
            return of(anoLectivo.body);
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
