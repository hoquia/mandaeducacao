import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItemFactura, NewItemFactura } from '../item-factura.model';

export type PartialUpdateItemFactura = Partial<IItemFactura> & Pick<IItemFactura, 'id'>;

export type EntityResponseType = HttpResponse<IItemFactura>;
export type EntityArrayResponseType = HttpResponse<IItemFactura[]>;

@Injectable({ providedIn: 'root' })
export class ItemFacturaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/item-facturas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(itemFactura: NewItemFactura): Observable<EntityResponseType> {
    return this.http.post<IItemFactura>(this.resourceUrl, itemFactura, { observe: 'response' });
  }

  update(itemFactura: IItemFactura): Observable<EntityResponseType> {
    return this.http.put<IItemFactura>(`${this.resourceUrl}/${this.getItemFacturaIdentifier(itemFactura)}`, itemFactura, {
      observe: 'response',
    });
  }

  partialUpdate(itemFactura: PartialUpdateItemFactura): Observable<EntityResponseType> {
    return this.http.patch<IItemFactura>(`${this.resourceUrl}/${this.getItemFacturaIdentifier(itemFactura)}`, itemFactura, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItemFactura>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItemFactura[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getItemFacturaIdentifier(itemFactura: Pick<IItemFactura, 'id'>): number {
    return itemFactura.id;
  }

  compareItemFactura(o1: Pick<IItemFactura, 'id'> | null, o2: Pick<IItemFactura, 'id'> | null): boolean {
    return o1 && o2 ? this.getItemFacturaIdentifier(o1) === this.getItemFacturaIdentifier(o2) : o1 === o2;
  }

  addItemFacturaToCollectionIfMissing<Type extends Pick<IItemFactura, 'id'>>(
    itemFacturaCollection: Type[],
    ...itemFacturasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const itemFacturas: Type[] = itemFacturasToCheck.filter(isPresent);
    if (itemFacturas.length > 0) {
      const itemFacturaCollectionIdentifiers = itemFacturaCollection.map(
        itemFacturaItem => this.getItemFacturaIdentifier(itemFacturaItem)!
      );
      const itemFacturasToAdd = itemFacturas.filter(itemFacturaItem => {
        const itemFacturaIdentifier = this.getItemFacturaIdentifier(itemFacturaItem);
        if (itemFacturaCollectionIdentifiers.includes(itemFacturaIdentifier)) {
          return false;
        }
        itemFacturaCollectionIdentifiers.push(itemFacturaIdentifier);
        return true;
      });
      return [...itemFacturasToAdd, ...itemFacturaCollection];
    }
    return itemFacturaCollection;
  }
}
