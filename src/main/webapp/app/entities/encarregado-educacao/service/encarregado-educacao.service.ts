import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEncarregadoEducacao, NewEncarregadoEducacao } from '../encarregado-educacao.model';

export type PartialUpdateEncarregadoEducacao = Partial<IEncarregadoEducacao> & Pick<IEncarregadoEducacao, 'id'>;

type RestOf<T extends IEncarregadoEducacao | NewEncarregadoEducacao> = Omit<T, 'nascimento'> & {
  nascimento?: string | null;
};

export type RestEncarregadoEducacao = RestOf<IEncarregadoEducacao>;

export type NewRestEncarregadoEducacao = RestOf<NewEncarregadoEducacao>;

export type PartialUpdateRestEncarregadoEducacao = RestOf<PartialUpdateEncarregadoEducacao>;

export type EntityResponseType = HttpResponse<IEncarregadoEducacao>;
export type EntityArrayResponseType = HttpResponse<IEncarregadoEducacao[]>;

@Injectable({ providedIn: 'root' })
export class EncarregadoEducacaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/encarregado-educacaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(encarregadoEducacao: NewEncarregadoEducacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encarregadoEducacao);
    return this.http
      .post<RestEncarregadoEducacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(encarregadoEducacao: IEncarregadoEducacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encarregadoEducacao);
    return this.http
      .put<RestEncarregadoEducacao>(`${this.resourceUrl}/${this.getEncarregadoEducacaoIdentifier(encarregadoEducacao)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(encarregadoEducacao: PartialUpdateEncarregadoEducacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encarregadoEducacao);
    return this.http
      .patch<RestEncarregadoEducacao>(`${this.resourceUrl}/${this.getEncarregadoEducacaoIdentifier(encarregadoEducacao)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEncarregadoEducacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEncarregadoEducacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEncarregadoEducacaoIdentifier(encarregadoEducacao: Pick<IEncarregadoEducacao, 'id'>): number {
    return encarregadoEducacao.id;
  }

  compareEncarregadoEducacao(o1: Pick<IEncarregadoEducacao, 'id'> | null, o2: Pick<IEncarregadoEducacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getEncarregadoEducacaoIdentifier(o1) === this.getEncarregadoEducacaoIdentifier(o2) : o1 === o2;
  }

  addEncarregadoEducacaoToCollectionIfMissing<Type extends Pick<IEncarregadoEducacao, 'id'>>(
    encarregadoEducacaoCollection: Type[],
    ...encarregadoEducacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const encarregadoEducacaos: Type[] = encarregadoEducacaosToCheck.filter(isPresent);
    if (encarregadoEducacaos.length > 0) {
      const encarregadoEducacaoCollectionIdentifiers = encarregadoEducacaoCollection.map(
        encarregadoEducacaoItem => this.getEncarregadoEducacaoIdentifier(encarregadoEducacaoItem)!
      );
      const encarregadoEducacaosToAdd = encarregadoEducacaos.filter(encarregadoEducacaoItem => {
        const encarregadoEducacaoIdentifier = this.getEncarregadoEducacaoIdentifier(encarregadoEducacaoItem);
        if (encarregadoEducacaoCollectionIdentifiers.includes(encarregadoEducacaoIdentifier)) {
          return false;
        }
        encarregadoEducacaoCollectionIdentifiers.push(encarregadoEducacaoIdentifier);
        return true;
      });
      return [...encarregadoEducacaosToAdd, ...encarregadoEducacaoCollection];
    }
    return encarregadoEducacaoCollection;
  }

  protected convertDateFromClient<T extends IEncarregadoEducacao | NewEncarregadoEducacao | PartialUpdateEncarregadoEducacao>(
    encarregadoEducacao: T
  ): RestOf<T> {
    return {
      ...encarregadoEducacao,
      nascimento: encarregadoEducacao.nascimento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restEncarregadoEducacao: RestEncarregadoEducacao): IEncarregadoEducacao {
    return {
      ...restEncarregadoEducacao,
      nascimento: restEncarregadoEducacao.nascimento ? dayjs(restEncarregadoEducacao.nascimento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEncarregadoEducacao>): HttpResponse<IEncarregadoEducacao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEncarregadoEducacao[]>): HttpResponse<IEncarregadoEducacao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
