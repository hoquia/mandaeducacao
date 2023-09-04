import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INivelEnsino, NewNivelEnsino } from '../nivel-ensino.model';

export type PartialUpdateNivelEnsino = Partial<INivelEnsino> & Pick<INivelEnsino, 'id'>;

export type EntityResponseType = HttpResponse<INivelEnsino>;
export type EntityArrayResponseType = HttpResponse<INivelEnsino[]>;

@Injectable({ providedIn: 'root' })
export class NivelEnsinoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nivel-ensinos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nivelEnsino: NewNivelEnsino): Observable<EntityResponseType> {
    return this.http.post<INivelEnsino>(this.resourceUrl, nivelEnsino, { observe: 'response' });
  }

  update(nivelEnsino: INivelEnsino): Observable<EntityResponseType> {
    return this.http.put<INivelEnsino>(`${this.resourceUrl}/${this.getNivelEnsinoIdentifier(nivelEnsino)}`, nivelEnsino, {
      observe: 'response',
    });
  }

  partialUpdate(nivelEnsino: PartialUpdateNivelEnsino): Observable<EntityResponseType> {
    return this.http.patch<INivelEnsino>(`${this.resourceUrl}/${this.getNivelEnsinoIdentifier(nivelEnsino)}`, nivelEnsino, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INivelEnsino>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INivelEnsino[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNivelEnsinoIdentifier(nivelEnsino: Pick<INivelEnsino, 'id'>): number {
    return nivelEnsino.id;
  }

  compareNivelEnsino(o1: Pick<INivelEnsino, 'id'> | null, o2: Pick<INivelEnsino, 'id'> | null): boolean {
    return o1 && o2 ? this.getNivelEnsinoIdentifier(o1) === this.getNivelEnsinoIdentifier(o2) : o1 === o2;
  }

  addNivelEnsinoToCollectionIfMissing<Type extends Pick<INivelEnsino, 'id'>>(
    nivelEnsinoCollection: Type[],
    ...nivelEnsinosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nivelEnsinos: Type[] = nivelEnsinosToCheck.filter(isPresent);
    if (nivelEnsinos.length > 0) {
      const nivelEnsinoCollectionIdentifiers = nivelEnsinoCollection.map(
        nivelEnsinoItem => this.getNivelEnsinoIdentifier(nivelEnsinoItem)!
      );
      const nivelEnsinosToAdd = nivelEnsinos.filter(nivelEnsinoItem => {
        const nivelEnsinoIdentifier = this.getNivelEnsinoIdentifier(nivelEnsinoItem);
        if (nivelEnsinoCollectionIdentifiers.includes(nivelEnsinoIdentifier)) {
          return false;
        }
        nivelEnsinoCollectionIdentifiers.push(nivelEnsinoIdentifier);
        return true;
      });
      return [...nivelEnsinosToAdd, ...nivelEnsinoCollection];
    }
    return nivelEnsinoCollection;
  }
}
