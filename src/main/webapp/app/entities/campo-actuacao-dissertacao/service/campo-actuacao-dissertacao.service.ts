import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICampoActuacaoDissertacao, NewCampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';

export type PartialUpdateCampoActuacaoDissertacao = Partial<ICampoActuacaoDissertacao> & Pick<ICampoActuacaoDissertacao, 'id'>;

export type EntityResponseType = HttpResponse<ICampoActuacaoDissertacao>;
export type EntityArrayResponseType = HttpResponse<ICampoActuacaoDissertacao[]>;

@Injectable({ providedIn: 'root' })
export class CampoActuacaoDissertacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/campo-actuacao-dissertacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(campoActuacaoDissertacao: NewCampoActuacaoDissertacao): Observable<EntityResponseType> {
    return this.http.post<ICampoActuacaoDissertacao>(this.resourceUrl, campoActuacaoDissertacao, { observe: 'response' });
  }

  update(campoActuacaoDissertacao: ICampoActuacaoDissertacao): Observable<EntityResponseType> {
    return this.http.put<ICampoActuacaoDissertacao>(
      `${this.resourceUrl}/${this.getCampoActuacaoDissertacaoIdentifier(campoActuacaoDissertacao)}`,
      campoActuacaoDissertacao,
      { observe: 'response' }
    );
  }

  partialUpdate(campoActuacaoDissertacao: PartialUpdateCampoActuacaoDissertacao): Observable<EntityResponseType> {
    return this.http.patch<ICampoActuacaoDissertacao>(
      `${this.resourceUrl}/${this.getCampoActuacaoDissertacaoIdentifier(campoActuacaoDissertacao)}`,
      campoActuacaoDissertacao,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICampoActuacaoDissertacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICampoActuacaoDissertacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCampoActuacaoDissertacaoIdentifier(campoActuacaoDissertacao: Pick<ICampoActuacaoDissertacao, 'id'>): number {
    return campoActuacaoDissertacao.id;
  }

  compareCampoActuacaoDissertacao(
    o1: Pick<ICampoActuacaoDissertacao, 'id'> | null,
    o2: Pick<ICampoActuacaoDissertacao, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getCampoActuacaoDissertacaoIdentifier(o1) === this.getCampoActuacaoDissertacaoIdentifier(o2) : o1 === o2;
  }

  addCampoActuacaoDissertacaoToCollectionIfMissing<Type extends Pick<ICampoActuacaoDissertacao, 'id'>>(
    campoActuacaoDissertacaoCollection: Type[],
    ...campoActuacaoDissertacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const campoActuacaoDissertacaos: Type[] = campoActuacaoDissertacaosToCheck.filter(isPresent);
    if (campoActuacaoDissertacaos.length > 0) {
      const campoActuacaoDissertacaoCollectionIdentifiers = campoActuacaoDissertacaoCollection.map(
        campoActuacaoDissertacaoItem => this.getCampoActuacaoDissertacaoIdentifier(campoActuacaoDissertacaoItem)!
      );
      const campoActuacaoDissertacaosToAdd = campoActuacaoDissertacaos.filter(campoActuacaoDissertacaoItem => {
        const campoActuacaoDissertacaoIdentifier = this.getCampoActuacaoDissertacaoIdentifier(campoActuacaoDissertacaoItem);
        if (campoActuacaoDissertacaoCollectionIdentifiers.includes(campoActuacaoDissertacaoIdentifier)) {
          return false;
        }
        campoActuacaoDissertacaoCollectionIdentifiers.push(campoActuacaoDissertacaoIdentifier);
        return true;
      });
      return [...campoActuacaoDissertacaosToAdd, ...campoActuacaoDissertacaoCollection];
    }
    return campoActuacaoDissertacaoCollection;
  }
}
