import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITransferenciaTurma, NewTransferenciaTurma } from '../transferencia-turma.model';

export type PartialUpdateTransferenciaTurma = Partial<ITransferenciaTurma> & Pick<ITransferenciaTurma, 'id'>;

type RestOf<T extends ITransferenciaTurma | NewTransferenciaTurma> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestTransferenciaTurma = RestOf<ITransferenciaTurma>;

export type NewRestTransferenciaTurma = RestOf<NewTransferenciaTurma>;

export type PartialUpdateRestTransferenciaTurma = RestOf<PartialUpdateTransferenciaTurma>;

export type EntityResponseType = HttpResponse<ITransferenciaTurma>;
export type EntityArrayResponseType = HttpResponse<ITransferenciaTurma[]>;

@Injectable({ providedIn: 'root' })
export class TransferenciaTurmaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/transferencia-turmas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(transferenciaTurma: NewTransferenciaTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferenciaTurma);
    return this.http
      .post<RestTransferenciaTurma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(transferenciaTurma: ITransferenciaTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferenciaTurma);
    return this.http
      .put<RestTransferenciaTurma>(`${this.resourceUrl}/${this.getTransferenciaTurmaIdentifier(transferenciaTurma)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(transferenciaTurma: PartialUpdateTransferenciaTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transferenciaTurma);
    return this.http
      .patch<RestTransferenciaTurma>(`${this.resourceUrl}/${this.getTransferenciaTurmaIdentifier(transferenciaTurma)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTransferenciaTurma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTransferenciaTurma[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTransferenciaTurmaIdentifier(transferenciaTurma: Pick<ITransferenciaTurma, 'id'>): number {
    return transferenciaTurma.id;
  }

  compareTransferenciaTurma(o1: Pick<ITransferenciaTurma, 'id'> | null, o2: Pick<ITransferenciaTurma, 'id'> | null): boolean {
    return o1 && o2 ? this.getTransferenciaTurmaIdentifier(o1) === this.getTransferenciaTurmaIdentifier(o2) : o1 === o2;
  }

  addTransferenciaTurmaToCollectionIfMissing<Type extends Pick<ITransferenciaTurma, 'id'>>(
    transferenciaTurmaCollection: Type[],
    ...transferenciaTurmasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const transferenciaTurmas: Type[] = transferenciaTurmasToCheck.filter(isPresent);
    if (transferenciaTurmas.length > 0) {
      const transferenciaTurmaCollectionIdentifiers = transferenciaTurmaCollection.map(
        transferenciaTurmaItem => this.getTransferenciaTurmaIdentifier(transferenciaTurmaItem)!
      );
      const transferenciaTurmasToAdd = transferenciaTurmas.filter(transferenciaTurmaItem => {
        const transferenciaTurmaIdentifier = this.getTransferenciaTurmaIdentifier(transferenciaTurmaItem);
        if (transferenciaTurmaCollectionIdentifiers.includes(transferenciaTurmaIdentifier)) {
          return false;
        }
        transferenciaTurmaCollectionIdentifiers.push(transferenciaTurmaIdentifier);
        return true;
      });
      return [...transferenciaTurmasToAdd, ...transferenciaTurmaCollection];
    }
    return transferenciaTurmaCollection;
  }

  protected convertDateFromClient<T extends ITransferenciaTurma | NewTransferenciaTurma | PartialUpdateTransferenciaTurma>(
    transferenciaTurma: T
  ): RestOf<T> {
    return {
      ...transferenciaTurma,
      timestamp: transferenciaTurma.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTransferenciaTurma: RestTransferenciaTurma): ITransferenciaTurma {
    return {
      ...restTransferenciaTurma,
      timestamp: restTransferenciaTurma.timestamp ? dayjs(restTransferenciaTurma.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTransferenciaTurma>): HttpResponse<ITransferenciaTurma> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTransferenciaTurma[]>): HttpResponse<ITransferenciaTurma[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
