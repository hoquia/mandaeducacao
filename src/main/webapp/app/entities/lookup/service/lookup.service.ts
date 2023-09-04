import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILookup, NewLookup } from '../lookup.model';

export type PartialUpdateLookup = Partial<ILookup> & Pick<ILookup, 'id'>;

export type EntityResponseType = HttpResponse<ILookup>;
export type EntityArrayResponseType = HttpResponse<ILookup[]>;

@Injectable({ providedIn: 'root' })
export class LookupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lookups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lookup: NewLookup): Observable<EntityResponseType> {
    return this.http.post<ILookup>(this.resourceUrl, lookup, { observe: 'response' });
  }

  update(lookup: ILookup): Observable<EntityResponseType> {
    return this.http.put<ILookup>(`${this.resourceUrl}/${this.getLookupIdentifier(lookup)}`, lookup, { observe: 'response' });
  }

  partialUpdate(lookup: PartialUpdateLookup): Observable<EntityResponseType> {
    return this.http.patch<ILookup>(`${this.resourceUrl}/${this.getLookupIdentifier(lookup)}`, lookup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILookup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILookup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLookupIdentifier(lookup: Pick<ILookup, 'id'>): number {
    return lookup.id;
  }

  compareLookup(o1: Pick<ILookup, 'id'> | null, o2: Pick<ILookup, 'id'> | null): boolean {
    return o1 && o2 ? this.getLookupIdentifier(o1) === this.getLookupIdentifier(o2) : o1 === o2;
  }

  addLookupToCollectionIfMissing<Type extends Pick<ILookup, 'id'>>(
    lookupCollection: Type[],
    ...lookupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const lookups: Type[] = lookupsToCheck.filter(isPresent);
    if (lookups.length > 0) {
      const lookupCollectionIdentifiers = lookupCollection.map(lookupItem => this.getLookupIdentifier(lookupItem)!);
      const lookupsToAdd = lookups.filter(lookupItem => {
        const lookupIdentifier = this.getLookupIdentifier(lookupItem);
        if (lookupCollectionIdentifiers.includes(lookupIdentifier)) {
          return false;
        }
        lookupCollectionIdentifiers.push(lookupIdentifier);
        return true;
      });
      return [...lookupsToAdd, ...lookupCollection];
    }
    return lookupCollection;
  }
}
