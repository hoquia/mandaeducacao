import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImposto, NewImposto } from '../imposto.model';

export type PartialUpdateImposto = Partial<IImposto> & Pick<IImposto, 'id'>;

export type EntityResponseType = HttpResponse<IImposto>;
export type EntityArrayResponseType = HttpResponse<IImposto[]>;

@Injectable({ providedIn: 'root' })
export class ImpostoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/impostos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(imposto: NewImposto): Observable<EntityResponseType> {
    return this.http.post<IImposto>(this.resourceUrl, imposto, { observe: 'response' });
  }

  update(imposto: IImposto): Observable<EntityResponseType> {
    return this.http.put<IImposto>(`${this.resourceUrl}/${this.getImpostoIdentifier(imposto)}`, imposto, { observe: 'response' });
  }

  partialUpdate(imposto: PartialUpdateImposto): Observable<EntityResponseType> {
    return this.http.patch<IImposto>(`${this.resourceUrl}/${this.getImpostoIdentifier(imposto)}`, imposto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImposto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImposto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getImpostoIdentifier(imposto: Pick<IImposto, 'id'>): number {
    return imposto.id;
  }

  compareImposto(o1: Pick<IImposto, 'id'> | null, o2: Pick<IImposto, 'id'> | null): boolean {
    return o1 && o2 ? this.getImpostoIdentifier(o1) === this.getImpostoIdentifier(o2) : o1 === o2;
  }

  addImpostoToCollectionIfMissing<Type extends Pick<IImposto, 'id'>>(
    impostoCollection: Type[],
    ...impostosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const impostos: Type[] = impostosToCheck.filter(isPresent);
    if (impostos.length > 0) {
      const impostoCollectionIdentifiers = impostoCollection.map(impostoItem => this.getImpostoIdentifier(impostoItem)!);
      const impostosToAdd = impostos.filter(impostoItem => {
        const impostoIdentifier = this.getImpostoIdentifier(impostoItem);
        if (impostoCollectionIdentifiers.includes(impostoIdentifier)) {
          return false;
        }
        impostoCollectionIdentifiers.push(impostoIdentifier);
        return true;
      });
      return [...impostosToAdd, ...impostoCollection];
    }
    return impostoCollection;
  }
}
