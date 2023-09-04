import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransferenciaSaldo, NewTransferenciaSaldo } from '../transferencia-saldo.model';

export type PartialUpdateTransferenciaSaldo = Partial<ITransferenciaSaldo> & Pick<ITransferenciaSaldo, 'id'>;

type RestOf<T extends ITransferenciaSaldo | NewTransferenciaSaldo> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestTransferenciaSaldo = RestOf<ITransferenciaSaldo>;

export type NewRestTransferenciaSaldo = RestOf<NewTransferenciaSaldo>;

export type PartialUpdateRestTransferenciaSaldo = RestOf<PartialUpdateTransferenciaSaldo>;

export type EntityResponseType = HttpResponse<ITransferenciaSaldo>;
export type EntityArrayResponseType = HttpResponse<ITransferenciaSaldo[]>;

@Injectable({ providedIn: 'root' })
export class TransferenciaSaldoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transferencia-saldos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transferenciaSaldo: NewTransferenciaSaldo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferenciaSaldo);
    return this.http
      .post<RestTransferenciaSaldo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(transferenciaSaldo: ITransferenciaSaldo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferenciaSaldo);
    return this.http
      .put<RestTransferenciaSaldo>(`${this.resourceUrl}/${this.getTransferenciaSaldoIdentifier(transferenciaSaldo)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(transferenciaSaldo: PartialUpdateTransferenciaSaldo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferenciaSaldo);
    return this.http
      .patch<RestTransferenciaSaldo>(`${this.resourceUrl}/${this.getTransferenciaSaldoIdentifier(transferenciaSaldo)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTransferenciaSaldo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTransferenciaSaldo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTransferenciaSaldoIdentifier(transferenciaSaldo: Pick<ITransferenciaSaldo, 'id'>): number {
    return transferenciaSaldo.id;
  }

  compareTransferenciaSaldo(o1: Pick<ITransferenciaSaldo, 'id'> | null, o2: Pick<ITransferenciaSaldo, 'id'> | null): boolean {
    return o1 && o2 ? this.getTransferenciaSaldoIdentifier(o1) === this.getTransferenciaSaldoIdentifier(o2) : o1 === o2;
  }

  addTransferenciaSaldoToCollectionIfMissing<Type extends Pick<ITransferenciaSaldo, 'id'>>(
    transferenciaSaldoCollection: Type[],
    ...transferenciaSaldosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const transferenciaSaldos: Type[] = transferenciaSaldosToCheck.filter(isPresent);
    if (transferenciaSaldos.length > 0) {
      const transferenciaSaldoCollectionIdentifiers = transferenciaSaldoCollection.map(
        transferenciaSaldoItem => this.getTransferenciaSaldoIdentifier(transferenciaSaldoItem)!
      );
      const transferenciaSaldosToAdd = transferenciaSaldos.filter(transferenciaSaldoItem => {
        const transferenciaSaldoIdentifier = this.getTransferenciaSaldoIdentifier(transferenciaSaldoItem);
        if (transferenciaSaldoCollectionIdentifiers.includes(transferenciaSaldoIdentifier)) {
          return false;
        }
        transferenciaSaldoCollectionIdentifiers.push(transferenciaSaldoIdentifier);
        return true;
      });
      return [...transferenciaSaldosToAdd, ...transferenciaSaldoCollection];
    }
    return transferenciaSaldoCollection;
  }

  protected convertDateFromClient<T extends ITransferenciaSaldo | NewTransferenciaSaldo | PartialUpdateTransferenciaSaldo>(
    transferenciaSaldo: T
  ): RestOf<T> {
    return {
      ...transferenciaSaldo,
      timestamp: transferenciaSaldo.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTransferenciaSaldo: RestTransferenciaSaldo): ITransferenciaSaldo {
    return {
      ...restTransferenciaSaldo,
      timestamp: restTransferenciaSaldo.timestamp ? dayjs(restTransferenciaSaldo.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTransferenciaSaldo>): HttpResponse<ITransferenciaSaldo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTransferenciaSaldo[]>): HttpResponse<ITransferenciaSaldo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
