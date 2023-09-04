import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmolumento, NewEmolumento } from '../emolumento.model';

export type PartialUpdateEmolumento = Partial<IEmolumento> & Pick<IEmolumento, 'id'>;

export type EntityResponseType = HttpResponse<IEmolumento>;
export type EntityArrayResponseType = HttpResponse<IEmolumento[]>;

@Injectable({ providedIn: 'root' })
export class EmolumentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emolumentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(emolumento: NewEmolumento): Observable<EntityResponseType> {
    return this.http.post<IEmolumento>(this.resourceUrl, emolumento, { observe: 'response' });
  }

  update(emolumento: IEmolumento): Observable<EntityResponseType> {
    return this.http.put<IEmolumento>(`${this.resourceUrl}/${this.getEmolumentoIdentifier(emolumento)}`, emolumento, {
      observe: 'response',
    });
  }

  partialUpdate(emolumento: PartialUpdateEmolumento): Observable<EntityResponseType> {
    return this.http.patch<IEmolumento>(`${this.resourceUrl}/${this.getEmolumentoIdentifier(emolumento)}`, emolumento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmolumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmolumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmolumentoIdentifier(emolumento: Pick<IEmolumento, 'id'>): number {
    return emolumento.id;
  }

  compareEmolumento(o1: Pick<IEmolumento, 'id'> | null, o2: Pick<IEmolumento, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmolumentoIdentifier(o1) === this.getEmolumentoIdentifier(o2) : o1 === o2;
  }

  addEmolumentoToCollectionIfMissing<Type extends Pick<IEmolumento, 'id'>>(
    emolumentoCollection: Type[],
    ...emolumentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const emolumentos: Type[] = emolumentosToCheck.filter(isPresent);
    if (emolumentos.length > 0) {
      const emolumentoCollectionIdentifiers = emolumentoCollection.map(emolumentoItem => this.getEmolumentoIdentifier(emolumentoItem)!);
      const emolumentosToAdd = emolumentos.filter(emolumentoItem => {
        const emolumentoIdentifier = this.getEmolumentoIdentifier(emolumentoItem);
        if (emolumentoCollectionIdentifiers.includes(emolumentoIdentifier)) {
          return false;
        }
        emolumentoCollectionIdentifiers.push(emolumentoIdentifier);
        return true;
      });
      return [...emolumentosToAdd, ...emolumentoCollection];
    }
    return emolumentoCollection;
  }
}
