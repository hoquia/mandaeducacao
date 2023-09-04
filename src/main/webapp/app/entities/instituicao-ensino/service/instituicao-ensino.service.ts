import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInstituicaoEnsino, NewInstituicaoEnsino } from '../instituicao-ensino.model';

export type PartialUpdateInstituicaoEnsino = Partial<IInstituicaoEnsino> & Pick<IInstituicaoEnsino, 'id'>;

type RestOf<T extends IInstituicaoEnsino | NewInstituicaoEnsino> = Omit<T, 'fundacao'> & {
  fundacao?: string | null;
};

export type RestInstituicaoEnsino = RestOf<IInstituicaoEnsino>;

export type NewRestInstituicaoEnsino = RestOf<NewInstituicaoEnsino>;

export type PartialUpdateRestInstituicaoEnsino = RestOf<PartialUpdateInstituicaoEnsino>;

export type EntityResponseType = HttpResponse<IInstituicaoEnsino>;
export type EntityArrayResponseType = HttpResponse<IInstituicaoEnsino[]>;

@Injectable({ providedIn: 'root' })
export class InstituicaoEnsinoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/instituicao-ensinos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(instituicaoEnsino: NewInstituicaoEnsino): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instituicaoEnsino);
    return this.http
      .post<RestInstituicaoEnsino>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(instituicaoEnsino: IInstituicaoEnsino): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instituicaoEnsino);
    return this.http
      .put<RestInstituicaoEnsino>(`${this.resourceUrl}/${this.getInstituicaoEnsinoIdentifier(instituicaoEnsino)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(instituicaoEnsino: PartialUpdateInstituicaoEnsino): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instituicaoEnsino);
    return this.http
      .patch<RestInstituicaoEnsino>(`${this.resourceUrl}/${this.getInstituicaoEnsinoIdentifier(instituicaoEnsino)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestInstituicaoEnsino>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestInstituicaoEnsino[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInstituicaoEnsinoIdentifier(instituicaoEnsino: Pick<IInstituicaoEnsino, 'id'>): number {
    return instituicaoEnsino.id;
  }

  compareInstituicaoEnsino(o1: Pick<IInstituicaoEnsino, 'id'> | null, o2: Pick<IInstituicaoEnsino, 'id'> | null): boolean {
    return o1 && o2 ? this.getInstituicaoEnsinoIdentifier(o1) === this.getInstituicaoEnsinoIdentifier(o2) : o1 === o2;
  }

  addInstituicaoEnsinoToCollectionIfMissing<Type extends Pick<IInstituicaoEnsino, 'id'>>(
    instituicaoEnsinoCollection: Type[],
    ...instituicaoEnsinosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const instituicaoEnsinos: Type[] = instituicaoEnsinosToCheck.filter(isPresent);
    if (instituicaoEnsinos.length > 0) {
      const instituicaoEnsinoCollectionIdentifiers = instituicaoEnsinoCollection.map(
        instituicaoEnsinoItem => this.getInstituicaoEnsinoIdentifier(instituicaoEnsinoItem)!
      );
      const instituicaoEnsinosToAdd = instituicaoEnsinos.filter(instituicaoEnsinoItem => {
        const instituicaoEnsinoIdentifier = this.getInstituicaoEnsinoIdentifier(instituicaoEnsinoItem);
        if (instituicaoEnsinoCollectionIdentifiers.includes(instituicaoEnsinoIdentifier)) {
          return false;
        }
        instituicaoEnsinoCollectionIdentifiers.push(instituicaoEnsinoIdentifier);
        return true;
      });
      return [...instituicaoEnsinosToAdd, ...instituicaoEnsinoCollection];
    }
    return instituicaoEnsinoCollection;
  }

  protected convertDateFromClient<T extends IInstituicaoEnsino | NewInstituicaoEnsino | PartialUpdateInstituicaoEnsino>(
    instituicaoEnsino: T
  ): RestOf<T> {
    return {
      ...instituicaoEnsino,
      fundacao: instituicaoEnsino.fundacao?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restInstituicaoEnsino: RestInstituicaoEnsino): IInstituicaoEnsino {
    return {
      ...restInstituicaoEnsino,
      fundacao: restInstituicaoEnsino.fundacao ? dayjs(restInstituicaoEnsino.fundacao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestInstituicaoEnsino>): HttpResponse<IInstituicaoEnsino> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestInstituicaoEnsino[]>): HttpResponse<IInstituicaoEnsino[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
