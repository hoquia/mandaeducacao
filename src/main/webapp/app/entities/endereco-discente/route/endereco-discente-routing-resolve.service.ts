import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnderecoDiscente } from '../endereco-discente.model';
import { EnderecoDiscenteService } from '../service/endereco-discente.service';

@Injectable({ providedIn: 'root' })
export class EnderecoDiscenteRoutingResolveService implements Resolve<IEnderecoDiscente | null> {
  constructor(protected service: EnderecoDiscenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnderecoDiscente | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((enderecoDiscente: HttpResponse<IEnderecoDiscente>) => {
          if (enderecoDiscente.body) {
            return of(enderecoDiscente.body);
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
