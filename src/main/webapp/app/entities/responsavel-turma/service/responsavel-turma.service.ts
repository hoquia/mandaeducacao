import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResponsavelTurma, NewResponsavelTurma } from '../responsavel-turma.model';

export type PartialUpdateResponsavelTurma = Partial<IResponsavelTurma> & Pick<IResponsavelTurma, 'id'>;

type RestOf<T extends IResponsavelTurma | NewResponsavelTurma> = Omit<T, 'de' | 'ate' | 'timestamp'> & {
  de?: string | null;
  ate?: string | null;
  timestamp?: string | null;
};

export type RestResponsavelTurma = RestOf<IResponsavelTurma>;

export type NewRestResponsavelTurma = RestOf<NewResponsavelTurma>;

export type PartialUpdateRestResponsavelTurma = RestOf<PartialUpdateResponsavelTurma>;

export type EntityResponseType = HttpResponse<IResponsavelTurma>;
export type EntityArrayResponseType = HttpResponse<IResponsavelTurma[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelTurmaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/responsavel-turmas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(responsavelTurma: NewResponsavelTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelTurma);
    return this.http
      .post<RestResponsavelTurma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(responsavelTurma: IResponsavelTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelTurma);
    return this.http
      .put<RestResponsavelTurma>(`${this.resourceUrl}/${this.getResponsavelTurmaIdentifier(responsavelTurma)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(responsavelTurma: PartialUpdateResponsavelTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelTurma);
    return this.http
      .patch<RestResponsavelTurma>(`${this.resourceUrl}/${this.getResponsavelTurmaIdentifier(responsavelTurma)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResponsavelTurma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResponsavelTurma[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResponsavelTurmaIdentifier(responsavelTurma: Pick<IResponsavelTurma, 'id'>): number {
    return responsavelTurma.id;
  }

  compareResponsavelTurma(o1: Pick<IResponsavelTurma, 'id'> | null, o2: Pick<IResponsavelTurma, 'id'> | null): boolean {
    return o1 && o2 ? this.getResponsavelTurmaIdentifier(o1) === this.getResponsavelTurmaIdentifier(o2) : o1 === o2;
  }

  addResponsavelTurmaToCollectionIfMissing<Type extends Pick<IResponsavelTurma, 'id'>>(
    responsavelTurmaCollection: Type[],
    ...responsavelTurmasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const responsavelTurmas: Type[] = responsavelTurmasToCheck.filter(isPresent);
    if (responsavelTurmas.length > 0) {
      const responsavelTurmaCollectionIdentifiers = responsavelTurmaCollection.map(
        responsavelTurmaItem => this.getResponsavelTurmaIdentifier(responsavelTurmaItem)!
      );
      const responsavelTurmasToAdd = responsavelTurmas.filter(responsavelTurmaItem => {
        const responsavelTurmaIdentifier = this.getResponsavelTurmaIdentifier(responsavelTurmaItem);
        if (responsavelTurmaCollectionIdentifiers.includes(responsavelTurmaIdentifier)) {
          return false;
        }
        responsavelTurmaCollectionIdentifiers.push(responsavelTurmaIdentifier);
        return true;
      });
      return [...responsavelTurmasToAdd, ...responsavelTurmaCollection];
    }
    return responsavelTurmaCollection;
  }

  protected convertDateFromClient<T extends IResponsavelTurma | NewResponsavelTurma | PartialUpdateResponsavelTurma>(
    responsavelTurma: T
  ): RestOf<T> {
    return {
      ...responsavelTurma,
      de: responsavelTurma.de?.format(DATE_FORMAT) ?? null,
      ate: responsavelTurma.ate?.format(DATE_FORMAT) ?? null,
      timestamp: responsavelTurma.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restResponsavelTurma: RestResponsavelTurma): IResponsavelTurma {
    return {
      ...restResponsavelTurma,
      de: restResponsavelTurma.de ? dayjs(restResponsavelTurma.de) : undefined,
      ate: restResponsavelTurma.ate ? dayjs(restResponsavelTurma.ate) : undefined,
      timestamp: restResponsavelTurma.timestamp ? dayjs(restResponsavelTurma.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResponsavelTurma>): HttpResponse<IResponsavelTurma> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResponsavelTurma[]>): HttpResponse<IResponsavelTurma[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
