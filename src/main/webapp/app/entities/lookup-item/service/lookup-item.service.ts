import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILookupItem, NewLookupItem } from '../lookup-item.model';

export type PartialUpdateLookupItem = Partial<ILookupItem> & Pick<ILookupItem, 'id'>;

export type EntityResponseType = HttpResponse<ILookupItem>;
export type EntityArrayResponseType = HttpResponse<ILookupItem[]>;

@Injectable({ providedIn: 'root' })
export class LookupItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/lookup-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(lookupItem: NewLookupItem): Observable<EntityResponseType> {
    return this.http.post<ILookupItem>(this.resourceUrl, lookupItem, { observe: 'response' });
  }

  update(lookupItem: ILookupItem): Observable<EntityResponseType> {
    return this.http.put<ILookupItem>(`${this.resourceUrl}/${this.getLookupItemIdentifier(lookupItem)}`, lookupItem, {
      observe: 'response',
    });
  }

  partialUpdate(lookupItem: PartialUpdateLookupItem): Observable<EntityResponseType> {
    return this.http.patch<ILookupItem>(`${this.resourceUrl}/${this.getLookupItemIdentifier(lookupItem)}`, lookupItem, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILookupItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILookupItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLookupItemIdentifier(lookupItem: Pick<ILookupItem, 'id'>): number {
    return lookupItem.id;
  }

  compareLookupItem(o1: Pick<ILookupItem, 'id'> | null, o2: Pick<ILookupItem, 'id'> | null): boolean {
    return o1 && o2 ? this.getLookupItemIdentifier(o1) === this.getLookupItemIdentifier(o2) : o1 === o2;
  }

  addLookupItemToCollectionIfMissing<Type extends Pick<ILookupItem, 'id'>>(
    lookupItemCollection: Type[],
    ...lookupItemsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const lookupItems: Type[] = lookupItemsToCheck.filter(isPresent);
    if (lookupItems.length > 0) {
      const lookupItemCollectionIdentifiers = lookupItemCollection.map(lookupItemItem => this.getLookupItemIdentifier(lookupItemItem)!);
      const lookupItemsToAdd = lookupItems.filter(lookupItemItem => {
        const lookupItemIdentifier = this.getLookupItemIdentifier(lookupItemItem);
        if (lookupItemCollectionIdentifiers.includes(lookupItemIdentifier)) {
          return false;
        }
        lookupItemCollectionIdentifiers.push(lookupItemIdentifier);
        return true;
      });
      return [...lookupItemsToAdd, ...lookupItemCollection];
    }
    return lookupItemCollection;
  }
}
