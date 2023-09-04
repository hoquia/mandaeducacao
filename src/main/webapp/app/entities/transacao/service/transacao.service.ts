import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransacao, NewTransacao } from '../transacao.model';

export type PartialUpdateTransacao = Partial<ITransacao> & Pick<ITransacao, 'id'>;

type RestOf<T extends ITransacao | NewTransacao> = Omit<T, 'data' | 'timestamp'> & {
  data?: string | null;
  timestamp?: string | null;
};

export type RestTransacao = RestOf<ITransacao>;

export type NewRestTransacao = RestOf<NewTransacao>;

export type PartialUpdateRestTransacao = RestOf<PartialUpdateTransacao>;

export type EntityResponseType = HttpResponse<ITransacao>;
export type EntityArrayResponseType = HttpResponse<ITransacao[]>;

@Injectable({ providedIn: 'root' })
export class TransacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transacao: NewTransacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transacao);
    return this.http
      .post<RestTransacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(transacao: ITransacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transacao);
    return this.http
      .put<RestTransacao>(`${this.resourceUrl}/${this.getTransacaoIdentifier(transacao)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(transacao: PartialUpdateTransacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transacao);
    return this.http
      .patch<RestTransacao>(`${this.resourceUrl}/${this.getTransacaoIdentifier(transacao)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTransacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTransacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTransacaoIdentifier(transacao: Pick<ITransacao, 'id'>): number {
    return transacao.id;
  }

  compareTransacao(o1: Pick<ITransacao, 'id'> | null, o2: Pick<ITransacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getTransacaoIdentifier(o1) === this.getTransacaoIdentifier(o2) : o1 === o2;
  }

  addTransacaoToCollectionIfMissing<Type extends Pick<ITransacao, 'id'>>(
    transacaoCollection: Type[],
    ...transacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const transacaos: Type[] = transacaosToCheck.filter(isPresent);
    if (transacaos.length > 0) {
      const transacaoCollectionIdentifiers = transacaoCollection.map(transacaoItem => this.getTransacaoIdentifier(transacaoItem)!);
      const transacaosToAdd = transacaos.filter(transacaoItem => {
        const transacaoIdentifier = this.getTransacaoIdentifier(transacaoItem);
        if (transacaoCollectionIdentifiers.includes(transacaoIdentifier)) {
          return false;
        }
        transacaoCollectionIdentifiers.push(transacaoIdentifier);
        return true;
      });
      return [...transacaosToAdd, ...transacaoCollection];
    }
    return transacaoCollection;
  }

  protected convertDateFromClient<T extends ITransacao | NewTransacao | PartialUpdateTransacao>(transacao: T): RestOf<T> {
    return {
      ...transacao,
      data: transacao.data?.format(DATE_FORMAT) ?? null,
      timestamp: transacao.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTransacao: RestTransacao): ITransacao {
    return {
      ...restTransacao,
      data: restTransacao.data ? dayjs(restTransacao.data) : undefined,
      timestamp: restTransacao.timestamp ? dayjs(restTransacao.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTransacao>): HttpResponse<ITransacao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTransacao[]>): HttpResponse<ITransacao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
