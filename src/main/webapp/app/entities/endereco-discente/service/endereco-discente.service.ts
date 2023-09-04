import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnderecoDiscente, NewEnderecoDiscente } from '../endereco-discente.model';

export type PartialUpdateEnderecoDiscente = Partial<IEnderecoDiscente> & Pick<IEnderecoDiscente, 'id'>;

export type EntityResponseType = HttpResponse<IEnderecoDiscente>;
export type EntityArrayResponseType = HttpResponse<IEnderecoDiscente[]>;

@Injectable({ providedIn: 'root' })
export class EnderecoDiscenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/endereco-discentes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(enderecoDiscente: NewEnderecoDiscente): Observable<EntityResponseType> {
    return this.http.post<IEnderecoDiscente>(this.resourceUrl, enderecoDiscente, { observe: 'response' });
  }

  update(enderecoDiscente: IEnderecoDiscente): Observable<EntityResponseType> {
    return this.http.put<IEnderecoDiscente>(
      `${this.resourceUrl}/${this.getEnderecoDiscenteIdentifier(enderecoDiscente)}`,
      enderecoDiscente,
      { observe: 'response' }
    );
  }

  partialUpdate(enderecoDiscente: PartialUpdateEnderecoDiscente): Observable<EntityResponseType> {
    return this.http.patch<IEnderecoDiscente>(
      `${this.resourceUrl}/${this.getEnderecoDiscenteIdentifier(enderecoDiscente)}`,
      enderecoDiscente,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnderecoDiscente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnderecoDiscente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEnderecoDiscenteIdentifier(enderecoDiscente: Pick<IEnderecoDiscente, 'id'>): number {
    return enderecoDiscente.id;
  }

  compareEnderecoDiscente(o1: Pick<IEnderecoDiscente, 'id'> | null, o2: Pick<IEnderecoDiscente, 'id'> | null): boolean {
    return o1 && o2 ? this.getEnderecoDiscenteIdentifier(o1) === this.getEnderecoDiscenteIdentifier(o2) : o1 === o2;
  }

  addEnderecoDiscenteToCollectionIfMissing<Type extends Pick<IEnderecoDiscente, 'id'>>(
    enderecoDiscenteCollection: Type[],
    ...enderecoDiscentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const enderecoDiscentes: Type[] = enderecoDiscentesToCheck.filter(isPresent);
    if (enderecoDiscentes.length > 0) {
      const enderecoDiscenteCollectionIdentifiers = enderecoDiscenteCollection.map(
        enderecoDiscenteItem => this.getEnderecoDiscenteIdentifier(enderecoDiscenteItem)!
      );
      const enderecoDiscentesToAdd = enderecoDiscentes.filter(enderecoDiscenteItem => {
        const enderecoDiscenteIdentifier = this.getEnderecoDiscenteIdentifier(enderecoDiscenteItem);
        if (enderecoDiscenteCollectionIdentifiers.includes(enderecoDiscenteIdentifier)) {
          return false;
        }
        enderecoDiscenteCollectionIdentifiers.push(enderecoDiscenteIdentifier);
        return true;
      });
      return [...enderecoDiscentesToAdd, ...enderecoDiscenteCollection];
    }
    return enderecoDiscenteCollection;
  }
}
