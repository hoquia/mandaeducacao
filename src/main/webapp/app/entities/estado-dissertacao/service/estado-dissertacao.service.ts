import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstadoDissertacao, NewEstadoDissertacao } from '../estado-dissertacao.model';

export type PartialUpdateEstadoDissertacao = Partial<IEstadoDissertacao> & Pick<IEstadoDissertacao, 'id'>;

export type EntityResponseType = HttpResponse<IEstadoDissertacao>;
export type EntityArrayResponseType = HttpResponse<IEstadoDissertacao[]>;

@Injectable({ providedIn: 'root' })
export class EstadoDissertacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estado-dissertacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(estadoDissertacao: NewEstadoDissertacao): Observable<EntityResponseType> {
    return this.http.post<IEstadoDissertacao>(this.resourceUrl, estadoDissertacao, { observe: 'response' });
  }

  update(estadoDissertacao: IEstadoDissertacao): Observable<EntityResponseType> {
    return this.http.put<IEstadoDissertacao>(
      `${this.resourceUrl}/${this.getEstadoDissertacaoIdentifier(estadoDissertacao)}`,
      estadoDissertacao,
      { observe: 'response' }
    );
  }

  partialUpdate(estadoDissertacao: PartialUpdateEstadoDissertacao): Observable<EntityResponseType> {
    return this.http.patch<IEstadoDissertacao>(
      `${this.resourceUrl}/${this.getEstadoDissertacaoIdentifier(estadoDissertacao)}`,
      estadoDissertacao,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoDissertacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoDissertacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEstadoDissertacaoIdentifier(estadoDissertacao: Pick<IEstadoDissertacao, 'id'>): number {
    return estadoDissertacao.id;
  }

  compareEstadoDissertacao(o1: Pick<IEstadoDissertacao, 'id'> | null, o2: Pick<IEstadoDissertacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getEstadoDissertacaoIdentifier(o1) === this.getEstadoDissertacaoIdentifier(o2) : o1 === o2;
  }

  addEstadoDissertacaoToCollectionIfMissing<Type extends Pick<IEstadoDissertacao, 'id'>>(
    estadoDissertacaoCollection: Type[],
    ...estadoDissertacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const estadoDissertacaos: Type[] = estadoDissertacaosToCheck.filter(isPresent);
    if (estadoDissertacaos.length > 0) {
      const estadoDissertacaoCollectionIdentifiers = estadoDissertacaoCollection.map(
        estadoDissertacaoItem => this.getEstadoDissertacaoIdentifier(estadoDissertacaoItem)!
      );
      const estadoDissertacaosToAdd = estadoDissertacaos.filter(estadoDissertacaoItem => {
        const estadoDissertacaoIdentifier = this.getEstadoDissertacaoIdentifier(estadoDissertacaoItem);
        if (estadoDissertacaoCollectionIdentifiers.includes(estadoDissertacaoIdentifier)) {
          return false;
        }
        estadoDissertacaoCollectionIdentifiers.push(estadoDissertacaoIdentifier);
        return true;
      });
      return [...estadoDissertacaosToAdd, ...estadoDissertacaoCollection];
    }
    return estadoDissertacaoCollection;
  }
}
