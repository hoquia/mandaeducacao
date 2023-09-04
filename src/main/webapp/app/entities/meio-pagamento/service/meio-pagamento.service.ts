import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMeioPagamento, NewMeioPagamento } from '../meio-pagamento.model';

export type PartialUpdateMeioPagamento = Partial<IMeioPagamento> & Pick<IMeioPagamento, 'id'>;

export type EntityResponseType = HttpResponse<IMeioPagamento>;
export type EntityArrayResponseType = HttpResponse<IMeioPagamento[]>;

@Injectable({ providedIn: 'root' })
export class MeioPagamentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/meio-pagamentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(meioPagamento: NewMeioPagamento): Observable<EntityResponseType> {
    return this.http.post<IMeioPagamento>(this.resourceUrl, meioPagamento, { observe: 'response' });
  }

  update(meioPagamento: IMeioPagamento): Observable<EntityResponseType> {
    return this.http.put<IMeioPagamento>(`${this.resourceUrl}/${this.getMeioPagamentoIdentifier(meioPagamento)}`, meioPagamento, {
      observe: 'response',
    });
  }

  partialUpdate(meioPagamento: PartialUpdateMeioPagamento): Observable<EntityResponseType> {
    return this.http.patch<IMeioPagamento>(`${this.resourceUrl}/${this.getMeioPagamentoIdentifier(meioPagamento)}`, meioPagamento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeioPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeioPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMeioPagamentoIdentifier(meioPagamento: Pick<IMeioPagamento, 'id'>): number {
    return meioPagamento.id;
  }

  compareMeioPagamento(o1: Pick<IMeioPagamento, 'id'> | null, o2: Pick<IMeioPagamento, 'id'> | null): boolean {
    return o1 && o2 ? this.getMeioPagamentoIdentifier(o1) === this.getMeioPagamentoIdentifier(o2) : o1 === o2;
  }

  addMeioPagamentoToCollectionIfMissing<Type extends Pick<IMeioPagamento, 'id'>>(
    meioPagamentoCollection: Type[],
    ...meioPagamentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const meioPagamentos: Type[] = meioPagamentosToCheck.filter(isPresent);
    if (meioPagamentos.length > 0) {
      const meioPagamentoCollectionIdentifiers = meioPagamentoCollection.map(
        meioPagamentoItem => this.getMeioPagamentoIdentifier(meioPagamentoItem)!
      );
      const meioPagamentosToAdd = meioPagamentos.filter(meioPagamentoItem => {
        const meioPagamentoIdentifier = this.getMeioPagamentoIdentifier(meioPagamentoItem);
        if (meioPagamentoCollectionIdentifiers.includes(meioPagamentoIdentifier)) {
          return false;
        }
        meioPagamentoCollectionIdentifiers.push(meioPagamentoIdentifier);
        return true;
      });
      return [...meioPagamentosToAdd, ...meioPagamentoCollection];
    }
    return meioPagamentoCollection;
  }
}
