import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProvedorNotificacao, NewProvedorNotificacao } from '../provedor-notificacao.model';

export type PartialUpdateProvedorNotificacao = Partial<IProvedorNotificacao> & Pick<IProvedorNotificacao, 'id'>;

export type EntityResponseType = HttpResponse<IProvedorNotificacao>;
export type EntityArrayResponseType = HttpResponse<IProvedorNotificacao[]>;

@Injectable({ providedIn: 'root' })
export class ProvedorNotificacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/provedor-notificacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(provedorNotificacao: NewProvedorNotificacao): Observable<EntityResponseType> {
    return this.http.post<IProvedorNotificacao>(this.resourceUrl, provedorNotificacao, { observe: 'response' });
  }

  update(provedorNotificacao: IProvedorNotificacao): Observable<EntityResponseType> {
    return this.http.put<IProvedorNotificacao>(
      `${this.resourceUrl}/${this.getProvedorNotificacaoIdentifier(provedorNotificacao)}`,
      provedorNotificacao,
      { observe: 'response' }
    );
  }

  partialUpdate(provedorNotificacao: PartialUpdateProvedorNotificacao): Observable<EntityResponseType> {
    return this.http.patch<IProvedorNotificacao>(
      `${this.resourceUrl}/${this.getProvedorNotificacaoIdentifier(provedorNotificacao)}`,
      provedorNotificacao,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProvedorNotificacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProvedorNotificacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProvedorNotificacaoIdentifier(provedorNotificacao: Pick<IProvedorNotificacao, 'id'>): number {
    return provedorNotificacao.id;
  }

  compareProvedorNotificacao(o1: Pick<IProvedorNotificacao, 'id'> | null, o2: Pick<IProvedorNotificacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getProvedorNotificacaoIdentifier(o1) === this.getProvedorNotificacaoIdentifier(o2) : o1 === o2;
  }

  addProvedorNotificacaoToCollectionIfMissing<Type extends Pick<IProvedorNotificacao, 'id'>>(
    provedorNotificacaoCollection: Type[],
    ...provedorNotificacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const provedorNotificacaos: Type[] = provedorNotificacaosToCheck.filter(isPresent);
    if (provedorNotificacaos.length > 0) {
      const provedorNotificacaoCollectionIdentifiers = provedorNotificacaoCollection.map(
        provedorNotificacaoItem => this.getProvedorNotificacaoIdentifier(provedorNotificacaoItem)!
      );
      const provedorNotificacaosToAdd = provedorNotificacaos.filter(provedorNotificacaoItem => {
        const provedorNotificacaoIdentifier = this.getProvedorNotificacaoIdentifier(provedorNotificacaoItem);
        if (provedorNotificacaoCollectionIdentifiers.includes(provedorNotificacaoIdentifier)) {
          return false;
        }
        provedorNotificacaoCollectionIdentifiers.push(provedorNotificacaoIdentifier);
        return true;
      });
      return [...provedorNotificacaosToAdd, ...provedorNotificacaoCollection];
    }
    return provedorNotificacaoCollection;
  }
}
