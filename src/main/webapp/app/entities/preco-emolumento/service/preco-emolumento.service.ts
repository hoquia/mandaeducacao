import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrecoEmolumento, NewPrecoEmolumento } from '../preco-emolumento.model';

export type PartialUpdatePrecoEmolumento = Partial<IPrecoEmolumento> & Pick<IPrecoEmolumento, 'id'>;

export type EntityResponseType = HttpResponse<IPrecoEmolumento>;
export type EntityArrayResponseType = HttpResponse<IPrecoEmolumento[]>;

@Injectable({ providedIn: 'root' })
export class PrecoEmolumentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/preco-emolumentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(precoEmolumento: NewPrecoEmolumento): Observable<EntityResponseType> {
    return this.http.post<IPrecoEmolumento>(this.resourceUrl, precoEmolumento, { observe: 'response' });
  }

  update(precoEmolumento: IPrecoEmolumento): Observable<EntityResponseType> {
    return this.http.put<IPrecoEmolumento>(`${this.resourceUrl}/${this.getPrecoEmolumentoIdentifier(precoEmolumento)}`, precoEmolumento, {
      observe: 'response',
    });
  }

  partialUpdate(precoEmolumento: PartialUpdatePrecoEmolumento): Observable<EntityResponseType> {
    return this.http.patch<IPrecoEmolumento>(`${this.resourceUrl}/${this.getPrecoEmolumentoIdentifier(precoEmolumento)}`, precoEmolumento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrecoEmolumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrecoEmolumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrecoEmolumentoIdentifier(precoEmolumento: Pick<IPrecoEmolumento, 'id'>): number {
    return precoEmolumento.id;
  }

  comparePrecoEmolumento(o1: Pick<IPrecoEmolumento, 'id'> | null, o2: Pick<IPrecoEmolumento, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrecoEmolumentoIdentifier(o1) === this.getPrecoEmolumentoIdentifier(o2) : o1 === o2;
  }

  addPrecoEmolumentoToCollectionIfMissing<Type extends Pick<IPrecoEmolumento, 'id'>>(
    precoEmolumentoCollection: Type[],
    ...precoEmolumentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const precoEmolumentos: Type[] = precoEmolumentosToCheck.filter(isPresent);
    if (precoEmolumentos.length > 0) {
      const precoEmolumentoCollectionIdentifiers = precoEmolumentoCollection.map(
        precoEmolumentoItem => this.getPrecoEmolumentoIdentifier(precoEmolumentoItem)!
      );
      const precoEmolumentosToAdd = precoEmolumentos.filter(precoEmolumentoItem => {
        const precoEmolumentoIdentifier = this.getPrecoEmolumentoIdentifier(precoEmolumentoItem);
        if (precoEmolumentoCollectionIdentifiers.includes(precoEmolumentoIdentifier)) {
          return false;
        }
        precoEmolumentoCollectionIdentifiers.push(precoEmolumentoIdentifier);
        return true;
      });
      return [...precoEmolumentosToAdd, ...precoEmolumentoCollection];
    }
    return precoEmolumentoCollection;
  }
}
