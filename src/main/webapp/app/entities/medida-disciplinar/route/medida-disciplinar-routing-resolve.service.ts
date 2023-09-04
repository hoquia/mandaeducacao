import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMedidaDisciplinar } from '../medida-disciplinar.model';
import { MedidaDisciplinarService } from '../service/medida-disciplinar.service';

@Injectable({ providedIn: 'root' })
export class MedidaDisciplinarRoutingResolveService implements Resolve<IMedidaDisciplinar | null> {
  constructor(protected service: MedidaDisciplinarService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedidaDisciplinar | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((medidaDisciplinar: HttpResponse<IMedidaDisciplinar>) => {
          if (medidaDisciplinar.body) {
            return of(medidaDisciplinar.body);
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
