import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAplicacaoRecibo, NewAplicacaoRecibo } from '../aplicacao-recibo.model';

export type PartialUpdateAplicacaoRecibo = Partial<IAplicacaoRecibo> & Pick<IAplicacaoRecibo, 'id'>;

type RestOf<T extends IAplicacaoRecibo | NewAplicacaoRecibo> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestAplicacaoRecibo = RestOf<IAplicacaoRecibo>;

export type NewRestAplicacaoRecibo = RestOf<NewAplicacaoRecibo>;

export type PartialUpdateRestAplicacaoRecibo = RestOf<PartialUpdateAplicacaoRecibo>;

export type EntityResponseType = HttpResponse<IAplicacaoRecibo>;
export type EntityArrayResponseType = HttpResponse<IAplicacaoRecibo[]>;

@Injectable({ providedIn: 'root' })
export class AplicacaoReciboService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/aplicacao-recibos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(aplicacaoRecibo: NewAplicacaoRecibo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aplicacaoRecibo);
    return this.http
      .post<RestAplicacaoRecibo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(aplicacaoRecibo: IAplicacaoRecibo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aplicacaoRecibo);
    return this.http
      .put<RestAplicacaoRecibo>(`${this.resourceUrl}/${this.getAplicacaoReciboIdentifier(aplicacaoRecibo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(aplicacaoRecibo: PartialUpdateAplicacaoRecibo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aplicacaoRecibo);
    return this.http
      .patch<RestAplicacaoRecibo>(`${this.resourceUrl}/${this.getAplicacaoReciboIdentifier(aplicacaoRecibo)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAplicacaoRecibo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAplicacaoRecibo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAplicacaoReciboIdentifier(aplicacaoRecibo: Pick<IAplicacaoRecibo, 'id'>): number {
    return aplicacaoRecibo.id;
  }

  compareAplicacaoRecibo(o1: Pick<IAplicacaoRecibo, 'id'> | null, o2: Pick<IAplicacaoRecibo, 'id'> | null): boolean {
    return o1 && o2 ? this.getAplicacaoReciboIdentifier(o1) === this.getAplicacaoReciboIdentifier(o2) : o1 === o2;
  }

  addAplicacaoReciboToCollectionIfMissing<Type extends Pick<IAplicacaoRecibo, 'id'>>(
    aplicacaoReciboCollection: Type[],
    ...aplicacaoRecibosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const aplicacaoRecibos: Type[] = aplicacaoRecibosToCheck.filter(isPresent);
    if (aplicacaoRecibos.length > 0) {
      const aplicacaoReciboCollectionIdentifiers = aplicacaoReciboCollection.map(
        aplicacaoReciboItem => this.getAplicacaoReciboIdentifier(aplicacaoReciboItem)!
      );
      const aplicacaoRecibosToAdd = aplicacaoRecibos.filter(aplicacaoReciboItem => {
        const aplicacaoReciboIdentifier = this.getAplicacaoReciboIdentifier(aplicacaoReciboItem);
        if (aplicacaoReciboCollectionIdentifiers.includes(aplicacaoReciboIdentifier)) {
          return false;
        }
        aplicacaoReciboCollectionIdentifiers.push(aplicacaoReciboIdentifier);
        return true;
      });
      return [...aplicacaoRecibosToAdd, ...aplicacaoReciboCollection];
    }
    return aplicacaoReciboCollection;
  }

  protected convertDateFromClient<T extends IAplicacaoRecibo | NewAplicacaoRecibo | PartialUpdateAplicacaoRecibo>(
    aplicacaoRecibo: T
  ): RestOf<T> {
    return {
      ...aplicacaoRecibo,
      timestamp: aplicacaoRecibo.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAplicacaoRecibo: RestAplicacaoRecibo): IAplicacaoRecibo {
    return {
      ...restAplicacaoRecibo,
      timestamp: restAplicacaoRecibo.timestamp ? dayjs(restAplicacaoRecibo.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAplicacaoRecibo>): HttpResponse<IAplicacaoRecibo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAplicacaoRecibo[]>): HttpResponse<IAplicacaoRecibo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
