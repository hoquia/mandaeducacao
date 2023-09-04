import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDissertacaoFinalCurso, NewDissertacaoFinalCurso } from '../dissertacao-final-curso.model';

export type PartialUpdateDissertacaoFinalCurso = Partial<IDissertacaoFinalCurso> & Pick<IDissertacaoFinalCurso, 'id'>;

type RestOf<T extends IDissertacaoFinalCurso | NewDissertacaoFinalCurso> = Omit<T, 'timestamp' | 'data'> & {
  timestamp?: string | null;
  data?: string | null;
};

export type RestDissertacaoFinalCurso = RestOf<IDissertacaoFinalCurso>;

export type NewRestDissertacaoFinalCurso = RestOf<NewDissertacaoFinalCurso>;

export type PartialUpdateRestDissertacaoFinalCurso = RestOf<PartialUpdateDissertacaoFinalCurso>;

export type EntityResponseType = HttpResponse<IDissertacaoFinalCurso>;
export type EntityArrayResponseType = HttpResponse<IDissertacaoFinalCurso[]>;

@Injectable({ providedIn: 'root' })
export class DissertacaoFinalCursoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dissertacao-final-cursos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dissertacaoFinalCurso: NewDissertacaoFinalCurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dissertacaoFinalCurso);
    return this.http
      .post<RestDissertacaoFinalCurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dissertacaoFinalCurso: IDissertacaoFinalCurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dissertacaoFinalCurso);
    return this.http
      .put<RestDissertacaoFinalCurso>(`${this.resourceUrl}/${this.getDissertacaoFinalCursoIdentifier(dissertacaoFinalCurso)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dissertacaoFinalCurso: PartialUpdateDissertacaoFinalCurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dissertacaoFinalCurso);
    return this.http
      .patch<RestDissertacaoFinalCurso>(`${this.resourceUrl}/${this.getDissertacaoFinalCursoIdentifier(dissertacaoFinalCurso)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDissertacaoFinalCurso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDissertacaoFinalCurso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDissertacaoFinalCursoIdentifier(dissertacaoFinalCurso: Pick<IDissertacaoFinalCurso, 'id'>): number {
    return dissertacaoFinalCurso.id;
  }

  compareDissertacaoFinalCurso(o1: Pick<IDissertacaoFinalCurso, 'id'> | null, o2: Pick<IDissertacaoFinalCurso, 'id'> | null): boolean {
    return o1 && o2 ? this.getDissertacaoFinalCursoIdentifier(o1) === this.getDissertacaoFinalCursoIdentifier(o2) : o1 === o2;
  }

  addDissertacaoFinalCursoToCollectionIfMissing<Type extends Pick<IDissertacaoFinalCurso, 'id'>>(
    dissertacaoFinalCursoCollection: Type[],
    ...dissertacaoFinalCursosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dissertacaoFinalCursos: Type[] = dissertacaoFinalCursosToCheck.filter(isPresent);
    if (dissertacaoFinalCursos.length > 0) {
      const dissertacaoFinalCursoCollectionIdentifiers = dissertacaoFinalCursoCollection.map(
        dissertacaoFinalCursoItem => this.getDissertacaoFinalCursoIdentifier(dissertacaoFinalCursoItem)!
      );
      const dissertacaoFinalCursosToAdd = dissertacaoFinalCursos.filter(dissertacaoFinalCursoItem => {
        const dissertacaoFinalCursoIdentifier = this.getDissertacaoFinalCursoIdentifier(dissertacaoFinalCursoItem);
        if (dissertacaoFinalCursoCollectionIdentifiers.includes(dissertacaoFinalCursoIdentifier)) {
          return false;
        }
        dissertacaoFinalCursoCollectionIdentifiers.push(dissertacaoFinalCursoIdentifier);
        return true;
      });
      return [...dissertacaoFinalCursosToAdd, ...dissertacaoFinalCursoCollection];
    }
    return dissertacaoFinalCursoCollection;
  }

  protected convertDateFromClient<T extends IDissertacaoFinalCurso | NewDissertacaoFinalCurso | PartialUpdateDissertacaoFinalCurso>(
    dissertacaoFinalCurso: T
  ): RestOf<T> {
    return {
      ...dissertacaoFinalCurso,
      timestamp: dissertacaoFinalCurso.timestamp?.toJSON() ?? null,
      data: dissertacaoFinalCurso.data?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDissertacaoFinalCurso: RestDissertacaoFinalCurso): IDissertacaoFinalCurso {
    return {
      ...restDissertacaoFinalCurso,
      timestamp: restDissertacaoFinalCurso.timestamp ? dayjs(restDissertacaoFinalCurso.timestamp) : undefined,
      data: restDissertacaoFinalCurso.data ? dayjs(restDissertacaoFinalCurso.data) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDissertacaoFinalCurso>): HttpResponse<IDissertacaoFinalCurso> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDissertacaoFinalCurso[]>): HttpResponse<IDissertacaoFinalCurso[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
