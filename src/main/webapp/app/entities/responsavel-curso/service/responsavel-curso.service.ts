import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResponsavelCurso, NewResponsavelCurso } from '../responsavel-curso.model';

export type PartialUpdateResponsavelCurso = Partial<IResponsavelCurso> & Pick<IResponsavelCurso, 'id'>;

type RestOf<T extends IResponsavelCurso | NewResponsavelCurso> = Omit<T, 'de' | 'ate' | 'timestamp'> & {
  de?: string | null;
  ate?: string | null;
  timestamp?: string | null;
};

export type RestResponsavelCurso = RestOf<IResponsavelCurso>;

export type NewRestResponsavelCurso = RestOf<NewResponsavelCurso>;

export type PartialUpdateRestResponsavelCurso = RestOf<PartialUpdateResponsavelCurso>;

export type EntityResponseType = HttpResponse<IResponsavelCurso>;
export type EntityArrayResponseType = HttpResponse<IResponsavelCurso[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelCursoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/responsavel-cursos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(responsavelCurso: NewResponsavelCurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelCurso);
    return this.http
      .post<RestResponsavelCurso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(responsavelCurso: IResponsavelCurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelCurso);
    return this.http
      .put<RestResponsavelCurso>(`${this.resourceUrl}/${this.getResponsavelCursoIdentifier(responsavelCurso)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(responsavelCurso: PartialUpdateResponsavelCurso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelCurso);
    return this.http
      .patch<RestResponsavelCurso>(`${this.resourceUrl}/${this.getResponsavelCursoIdentifier(responsavelCurso)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResponsavelCurso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResponsavelCurso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResponsavelCursoIdentifier(responsavelCurso: Pick<IResponsavelCurso, 'id'>): number {
    return responsavelCurso.id;
  }

  compareResponsavelCurso(o1: Pick<IResponsavelCurso, 'id'> | null, o2: Pick<IResponsavelCurso, 'id'> | null): boolean {
    return o1 && o2 ? this.getResponsavelCursoIdentifier(o1) === this.getResponsavelCursoIdentifier(o2) : o1 === o2;
  }

  addResponsavelCursoToCollectionIfMissing<Type extends Pick<IResponsavelCurso, 'id'>>(
    responsavelCursoCollection: Type[],
    ...responsavelCursosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const responsavelCursos: Type[] = responsavelCursosToCheck.filter(isPresent);
    if (responsavelCursos.length > 0) {
      const responsavelCursoCollectionIdentifiers = responsavelCursoCollection.map(
        responsavelCursoItem => this.getResponsavelCursoIdentifier(responsavelCursoItem)!
      );
      const responsavelCursosToAdd = responsavelCursos.filter(responsavelCursoItem => {
        const responsavelCursoIdentifier = this.getResponsavelCursoIdentifier(responsavelCursoItem);
        if (responsavelCursoCollectionIdentifiers.includes(responsavelCursoIdentifier)) {
          return false;
        }
        responsavelCursoCollectionIdentifiers.push(responsavelCursoIdentifier);
        return true;
      });
      return [...responsavelCursosToAdd, ...responsavelCursoCollection];
    }
    return responsavelCursoCollection;
  }

  protected convertDateFromClient<T extends IResponsavelCurso | NewResponsavelCurso | PartialUpdateResponsavelCurso>(
    responsavelCurso: T
  ): RestOf<T> {
    return {
      ...responsavelCurso,
      de: responsavelCurso.de?.format(DATE_FORMAT) ?? null,
      ate: responsavelCurso.ate?.format(DATE_FORMAT) ?? null,
      timestamp: responsavelCurso.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restResponsavelCurso: RestResponsavelCurso): IResponsavelCurso {
    return {
      ...restResponsavelCurso,
      de: restResponsavelCurso.de ? dayjs(restResponsavelCurso.de) : undefined,
      ate: restResponsavelCurso.ate ? dayjs(restResponsavelCurso.ate) : undefined,
      timestamp: restResponsavelCurso.timestamp ? dayjs(restResponsavelCurso.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResponsavelCurso>): HttpResponse<IResponsavelCurso> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResponsavelCurso[]>): HttpResponse<IResponsavelCurso[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
