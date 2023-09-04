import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResponsavelAreaFormacao, NewResponsavelAreaFormacao } from '../responsavel-area-formacao.model';

export type PartialUpdateResponsavelAreaFormacao = Partial<IResponsavelAreaFormacao> & Pick<IResponsavelAreaFormacao, 'id'>;

type RestOf<T extends IResponsavelAreaFormacao | NewResponsavelAreaFormacao> = Omit<T, 'de' | 'ate' | 'timestamp'> & {
  de?: string | null;
  ate?: string | null;
  timestamp?: string | null;
};

export type RestResponsavelAreaFormacao = RestOf<IResponsavelAreaFormacao>;

export type NewRestResponsavelAreaFormacao = RestOf<NewResponsavelAreaFormacao>;

export type PartialUpdateRestResponsavelAreaFormacao = RestOf<PartialUpdateResponsavelAreaFormacao>;

export type EntityResponseType = HttpResponse<IResponsavelAreaFormacao>;
export type EntityArrayResponseType = HttpResponse<IResponsavelAreaFormacao[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelAreaFormacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/responsavel-area-formacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(responsavelAreaFormacao: NewResponsavelAreaFormacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelAreaFormacao);
    return this.http
      .post<RestResponsavelAreaFormacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(responsavelAreaFormacao: IResponsavelAreaFormacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelAreaFormacao);
    return this.http
      .put<RestResponsavelAreaFormacao>(`${this.resourceUrl}/${this.getResponsavelAreaFormacaoIdentifier(responsavelAreaFormacao)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(responsavelAreaFormacao: PartialUpdateResponsavelAreaFormacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelAreaFormacao);
    return this.http
      .patch<RestResponsavelAreaFormacao>(
        `${this.resourceUrl}/${this.getResponsavelAreaFormacaoIdentifier(responsavelAreaFormacao)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResponsavelAreaFormacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResponsavelAreaFormacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResponsavelAreaFormacaoIdentifier(responsavelAreaFormacao: Pick<IResponsavelAreaFormacao, 'id'>): number {
    return responsavelAreaFormacao.id;
  }

  compareResponsavelAreaFormacao(
    o1: Pick<IResponsavelAreaFormacao, 'id'> | null,
    o2: Pick<IResponsavelAreaFormacao, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getResponsavelAreaFormacaoIdentifier(o1) === this.getResponsavelAreaFormacaoIdentifier(o2) : o1 === o2;
  }

  addResponsavelAreaFormacaoToCollectionIfMissing<Type extends Pick<IResponsavelAreaFormacao, 'id'>>(
    responsavelAreaFormacaoCollection: Type[],
    ...responsavelAreaFormacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const responsavelAreaFormacaos: Type[] = responsavelAreaFormacaosToCheck.filter(isPresent);
    if (responsavelAreaFormacaos.length > 0) {
      const responsavelAreaFormacaoCollectionIdentifiers = responsavelAreaFormacaoCollection.map(
        responsavelAreaFormacaoItem => this.getResponsavelAreaFormacaoIdentifier(responsavelAreaFormacaoItem)!
      );
      const responsavelAreaFormacaosToAdd = responsavelAreaFormacaos.filter(responsavelAreaFormacaoItem => {
        const responsavelAreaFormacaoIdentifier = this.getResponsavelAreaFormacaoIdentifier(responsavelAreaFormacaoItem);
        if (responsavelAreaFormacaoCollectionIdentifiers.includes(responsavelAreaFormacaoIdentifier)) {
          return false;
        }
        responsavelAreaFormacaoCollectionIdentifiers.push(responsavelAreaFormacaoIdentifier);
        return true;
      });
      return [...responsavelAreaFormacaosToAdd, ...responsavelAreaFormacaoCollection];
    }
    return responsavelAreaFormacaoCollection;
  }

  protected convertDateFromClient<T extends IResponsavelAreaFormacao | NewResponsavelAreaFormacao | PartialUpdateResponsavelAreaFormacao>(
    responsavelAreaFormacao: T
  ): RestOf<T> {
    return {
      ...responsavelAreaFormacao,
      de: responsavelAreaFormacao.de?.format(DATE_FORMAT) ?? null,
      ate: responsavelAreaFormacao.ate?.format(DATE_FORMAT) ?? null,
      timestamp: responsavelAreaFormacao.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restResponsavelAreaFormacao: RestResponsavelAreaFormacao): IResponsavelAreaFormacao {
    return {
      ...restResponsavelAreaFormacao,
      de: restResponsavelAreaFormacao.de ? dayjs(restResponsavelAreaFormacao.de) : undefined,
      ate: restResponsavelAreaFormacao.ate ? dayjs(restResponsavelAreaFormacao.ate) : undefined,
      timestamp: restResponsavelAreaFormacao.timestamp ? dayjs(restResponsavelAreaFormacao.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResponsavelAreaFormacao>): HttpResponse<IResponsavelAreaFormacao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResponsavelAreaFormacao[]>): HttpResponse<IResponsavelAreaFormacao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
