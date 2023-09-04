import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoDesconto, NewPlanoDesconto } from '../plano-desconto.model';

export type PartialUpdatePlanoDesconto = Partial<IPlanoDesconto> & Pick<IPlanoDesconto, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoDesconto>;
export type EntityArrayResponseType = HttpResponse<IPlanoDesconto[]>;

@Injectable({ providedIn: 'root' })
export class PlanoDescontoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-descontos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(planoDesconto: NewPlanoDesconto): Observable<EntityResponseType> {
    return this.http.post<IPlanoDesconto>(this.resourceUrl, planoDesconto, { observe: 'response' });
  }

  update(planoDesconto: IPlanoDesconto): Observable<EntityResponseType> {
    return this.http.put<IPlanoDesconto>(`${this.resourceUrl}/${this.getPlanoDescontoIdentifier(planoDesconto)}`, planoDesconto, {
      observe: 'response',
    });
  }

  partialUpdate(planoDesconto: PartialUpdatePlanoDesconto): Observable<EntityResponseType> {
    return this.http.patch<IPlanoDesconto>(`${this.resourceUrl}/${this.getPlanoDescontoIdentifier(planoDesconto)}`, planoDesconto, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoDesconto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoDesconto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoDescontoIdentifier(planoDesconto: Pick<IPlanoDesconto, 'id'>): number {
    return planoDesconto.id;
  }

  comparePlanoDesconto(o1: Pick<IPlanoDesconto, 'id'> | null, o2: Pick<IPlanoDesconto, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanoDescontoIdentifier(o1) === this.getPlanoDescontoIdentifier(o2) : o1 === o2;
  }

  addPlanoDescontoToCollectionIfMissing<Type extends Pick<IPlanoDesconto, 'id'>>(
    planoDescontoCollection: Type[],
    ...planoDescontosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoDescontos: Type[] = planoDescontosToCheck.filter(isPresent);
    if (planoDescontos.length > 0) {
      const planoDescontoCollectionIdentifiers = planoDescontoCollection.map(
        planoDescontoItem => this.getPlanoDescontoIdentifier(planoDescontoItem)!
      );
      const planoDescontosToAdd = planoDescontos.filter(planoDescontoItem => {
        const planoDescontoIdentifier = this.getPlanoDescontoIdentifier(planoDescontoItem);
        if (planoDescontoCollectionIdentifiers.includes(planoDescontoIdentifier)) {
          return false;
        }
        planoDescontoCollectionIdentifiers.push(planoDescontoIdentifier);
        return true;
      });
      return [...planoDescontosToAdd, ...planoDescontoCollection];
    }
    return planoDescontoCollection;
  }
}
