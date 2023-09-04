import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaFormacao, NewAreaFormacao } from '../area-formacao.model';

export type PartialUpdateAreaFormacao = Partial<IAreaFormacao> & Pick<IAreaFormacao, 'id'>;

export type EntityResponseType = HttpResponse<IAreaFormacao>;
export type EntityArrayResponseType = HttpResponse<IAreaFormacao[]>;

@Injectable({ providedIn: 'root' })
export class AreaFormacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/area-formacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(areaFormacao: NewAreaFormacao): Observable<EntityResponseType> {
    return this.http.post<IAreaFormacao>(this.resourceUrl, areaFormacao, { observe: 'response' });
  }

  update(areaFormacao: IAreaFormacao): Observable<EntityResponseType> {
    return this.http.put<IAreaFormacao>(`${this.resourceUrl}/${this.getAreaFormacaoIdentifier(areaFormacao)}`, areaFormacao, {
      observe: 'response',
    });
  }

  partialUpdate(areaFormacao: PartialUpdateAreaFormacao): Observable<EntityResponseType> {
    return this.http.patch<IAreaFormacao>(`${this.resourceUrl}/${this.getAreaFormacaoIdentifier(areaFormacao)}`, areaFormacao, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaFormacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaFormacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAreaFormacaoIdentifier(areaFormacao: Pick<IAreaFormacao, 'id'>): number {
    return areaFormacao.id;
  }

  compareAreaFormacao(o1: Pick<IAreaFormacao, 'id'> | null, o2: Pick<IAreaFormacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getAreaFormacaoIdentifier(o1) === this.getAreaFormacaoIdentifier(o2) : o1 === o2;
  }

  addAreaFormacaoToCollectionIfMissing<Type extends Pick<IAreaFormacao, 'id'>>(
    areaFormacaoCollection: Type[],
    ...areaFormacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const areaFormacaos: Type[] = areaFormacaosToCheck.filter(isPresent);
    if (areaFormacaos.length > 0) {
      const areaFormacaoCollectionIdentifiers = areaFormacaoCollection.map(
        areaFormacaoItem => this.getAreaFormacaoIdentifier(areaFormacaoItem)!
      );
      const areaFormacaosToAdd = areaFormacaos.filter(areaFormacaoItem => {
        const areaFormacaoIdentifier = this.getAreaFormacaoIdentifier(areaFormacaoItem);
        if (areaFormacaoCollectionIdentifiers.includes(areaFormacaoIdentifier)) {
          return false;
        }
        areaFormacaoCollectionIdentifiers.push(areaFormacaoIdentifier);
        return true;
      });
      return [...areaFormacaosToAdd, ...areaFormacaoCollection];
    }
    return areaFormacaoCollection;
  }
}
