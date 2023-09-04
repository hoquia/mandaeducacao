import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoMulta, NewPlanoMulta } from '../plano-multa.model';

export type PartialUpdatePlanoMulta = Partial<IPlanoMulta> & Pick<IPlanoMulta, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoMulta>;
export type EntityArrayResponseType = HttpResponse<IPlanoMulta[]>;

@Injectable({ providedIn: 'root' })
export class PlanoMultaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-multas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(planoMulta: NewPlanoMulta): Observable<EntityResponseType> {
    return this.http.post<IPlanoMulta>(this.resourceUrl, planoMulta, { observe: 'response' });
  }

  update(planoMulta: IPlanoMulta): Observable<EntityResponseType> {
    return this.http.put<IPlanoMulta>(`${this.resourceUrl}/${this.getPlanoMultaIdentifier(planoMulta)}`, planoMulta, {
      observe: 'response',
    });
  }

  partialUpdate(planoMulta: PartialUpdatePlanoMulta): Observable<EntityResponseType> {
    return this.http.patch<IPlanoMulta>(`${this.resourceUrl}/${this.getPlanoMultaIdentifier(planoMulta)}`, planoMulta, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoMulta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoMulta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoMultaIdentifier(planoMulta: Pick<IPlanoMulta, 'id'>): number {
    return planoMulta.id;
  }

  comparePlanoMulta(o1: Pick<IPlanoMulta, 'id'> | null, o2: Pick<IPlanoMulta, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanoMultaIdentifier(o1) === this.getPlanoMultaIdentifier(o2) : o1 === o2;
  }

  addPlanoMultaToCollectionIfMissing<Type extends Pick<IPlanoMulta, 'id'>>(
    planoMultaCollection: Type[],
    ...planoMultasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoMultas: Type[] = planoMultasToCheck.filter(isPresent);
    if (planoMultas.length > 0) {
      const planoMultaCollectionIdentifiers = planoMultaCollection.map(planoMultaItem => this.getPlanoMultaIdentifier(planoMultaItem)!);
      const planoMultasToAdd = planoMultas.filter(planoMultaItem => {
        const planoMultaIdentifier = this.getPlanoMultaIdentifier(planoMultaItem);
        if (planoMultaCollectionIdentifiers.includes(planoMultaIdentifier)) {
          return false;
        }
        planoMultaCollectionIdentifiers.push(planoMultaIdentifier);
        return true;
      });
      return [...planoMultasToAdd, ...planoMultaCollection];
    }
    return planoMultaCollection;
  }
}
