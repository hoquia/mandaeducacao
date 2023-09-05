import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IItemFactura, NewItemFactura } from '../item-factura.model';

export type PartialUpdateItemFactura = Partial<IItemFactura> & Pick<IItemFactura, 'id'>;

type RestOf<T extends IItemFactura | NewItemFactura> = Omit<T, 'emissao' | 'expiracao'> & {
  emissao?: string | null;
  expiracao?: string | null;
};

export type RestItemFactura = RestOf<IItemFactura>;

export type NewRestItemFactura = RestOf<NewItemFactura>;

export type PartialUpdateRestItemFactura = RestOf<PartialUpdateItemFactura>;

export type EntityResponseType = HttpResponse<IItemFactura>;
export type EntityArrayResponseType = HttpResponse<IItemFactura[]>;

@Injectable({ providedIn: 'root' })
export class ItemFacturaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/item-facturas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(itemFactura: NewItemFactura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemFactura);
    return this.http
      .post<RestItemFactura>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(itemFactura: IItemFactura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemFactura);
    return this.http
      .put<RestItemFactura>(`${this.resourceUrl}/${this.getItemFacturaIdentifier(itemFactura)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(itemFactura: PartialUpdateItemFactura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(itemFactura);
    return this.http
      .patch<RestItemFactura>(`${this.resourceUrl}/${this.getItemFacturaIdentifier(itemFactura)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestItemFactura>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestItemFactura[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertDateFromClient<T extends IItemFactura | NewItemFactura | PartialUpdateItemFactura>(itemFactura: T): RestOf<T> {
    return {
      ...itemFactura,
      emissao: itemFactura.emissao?.format(DATE_FORMAT) ?? null,
      expiracao: itemFactura.expiracao?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restItemFactura: RestItemFactura): IItemFactura {
    return {
      ...restItemFactura,
      emissao: restItemFactura.emissao ? dayjs(restItemFactura.emissao) : undefined,
      expiracao: restItemFactura.expiracao ? dayjs(restItemFactura.expiracao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestItemFactura>): HttpResponse<IItemFactura> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestItemFactura[]>): HttpResponse<IItemFactura[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
