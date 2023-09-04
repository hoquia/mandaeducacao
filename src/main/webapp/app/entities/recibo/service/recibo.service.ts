import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRecibo, NewRecibo } from '../recibo.model';

export type PartialUpdateRecibo = Partial<IRecibo> & Pick<IRecibo, 'id'>;

type RestOf<T extends IRecibo | NewRecibo> = Omit<T, 'data' | 'vencimento' | 'timestamp'> & {
  data?: string | null;
  vencimento?: string | null;
  timestamp?: string | null;
};

export type RestRecibo = RestOf<IRecibo>;

export type NewRestRecibo = RestOf<NewRecibo>;

export type PartialUpdateRestRecibo = RestOf<PartialUpdateRecibo>;

export type EntityResponseType = HttpResponse<IRecibo>;
export type EntityArrayResponseType = HttpResponse<IRecibo[]>;

@Injectable({ providedIn: 'root' })
export class ReciboService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/recibos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(recibo: NewRecibo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recibo);
    return this.http
      .post<RestRecibo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(recibo: IRecibo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recibo);
    return this.http
      .put<RestRecibo>(`${this.resourceUrl}/${this.getReciboIdentifier(recibo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(recibo: PartialUpdateRecibo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(recibo);
    return this.http
      .patch<RestRecibo>(`${this.resourceUrl}/${this.getReciboIdentifier(recibo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRecibo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRecibo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReciboIdentifier(recibo: Pick<IRecibo, 'id'>): number {
    return recibo.id;
  }

  compareRecibo(o1: Pick<IRecibo, 'id'> | null, o2: Pick<IRecibo, 'id'> | null): boolean {
    return o1 && o2 ? this.getReciboIdentifier(o1) === this.getReciboIdentifier(o2) : o1 === o2;
  }

  addReciboToCollectionIfMissing<Type extends Pick<IRecibo, 'id'>>(
    reciboCollection: Type[],
    ...recibosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const recibos: Type[] = recibosToCheck.filter(isPresent);
    if (recibos.length > 0) {
      const reciboCollectionIdentifiers = reciboCollection.map(reciboItem => this.getReciboIdentifier(reciboItem)!);
      const recibosToAdd = recibos.filter(reciboItem => {
        const reciboIdentifier = this.getReciboIdentifier(reciboItem);
        if (reciboCollectionIdentifiers.includes(reciboIdentifier)) {
          return false;
        }
        reciboCollectionIdentifiers.push(reciboIdentifier);
        return true;
      });
      return [...recibosToAdd, ...reciboCollection];
    }
    return reciboCollection;
  }

  protected convertDateFromClient<T extends IRecibo | NewRecibo | PartialUpdateRecibo>(recibo: T): RestOf<T> {
    return {
      ...recibo,
      data: recibo.data?.format(DATE_FORMAT) ?? null,
      vencimento: recibo.vencimento?.format(DATE_FORMAT) ?? null,
      timestamp: recibo.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRecibo: RestRecibo): IRecibo {
    return {
      ...restRecibo,
      data: restRecibo.data ? dayjs(restRecibo.data) : undefined,
      vencimento: restRecibo.vencimento ? dayjs(restRecibo.vencimento) : undefined,
      timestamp: restRecibo.timestamp ? dayjs(restRecibo.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRecibo>): HttpResponse<IRecibo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRecibo[]>): HttpResponse<IRecibo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
