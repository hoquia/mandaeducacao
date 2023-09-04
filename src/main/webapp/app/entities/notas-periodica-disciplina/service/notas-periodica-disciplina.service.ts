import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotasPeriodicaDisciplina, NewNotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';

export type PartialUpdateNotasPeriodicaDisciplina = Partial<INotasPeriodicaDisciplina> & Pick<INotasPeriodicaDisciplina, 'id'>;

type RestOf<T extends INotasPeriodicaDisciplina | NewNotasPeriodicaDisciplina> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestNotasPeriodicaDisciplina = RestOf<INotasPeriodicaDisciplina>;

export type NewRestNotasPeriodicaDisciplina = RestOf<NewNotasPeriodicaDisciplina>;

export type PartialUpdateRestNotasPeriodicaDisciplina = RestOf<PartialUpdateNotasPeriodicaDisciplina>;

export type EntityResponseType = HttpResponse<INotasPeriodicaDisciplina>;
export type EntityArrayResponseType = HttpResponse<INotasPeriodicaDisciplina[]>;

@Injectable({ providedIn: 'root' })
export class NotasPeriodicaDisciplinaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/notas-periodica-disciplinas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(notasPeriodicaDisciplina: NewNotasPeriodicaDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notasPeriodicaDisciplina);
    return this.http
      .post<RestNotasPeriodicaDisciplina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(notasPeriodicaDisciplina: INotasPeriodicaDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notasPeriodicaDisciplina);
    return this.http
      .put<RestNotasPeriodicaDisciplina>(
        `${this.resourceUrl}/${this.getNotasPeriodicaDisciplinaIdentifier(notasPeriodicaDisciplina)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(notasPeriodicaDisciplina: PartialUpdateNotasPeriodicaDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notasPeriodicaDisciplina);
    return this.http
      .patch<RestNotasPeriodicaDisciplina>(
        `${this.resourceUrl}/${this.getNotasPeriodicaDisciplinaIdentifier(notasPeriodicaDisciplina)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNotasPeriodicaDisciplina>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNotasPeriodicaDisciplina[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotasPeriodicaDisciplinaIdentifier(notasPeriodicaDisciplina: Pick<INotasPeriodicaDisciplina, 'id'>): number {
    return notasPeriodicaDisciplina.id;
  }

  compareNotasPeriodicaDisciplina(
    o1: Pick<INotasPeriodicaDisciplina, 'id'> | null,
    o2: Pick<INotasPeriodicaDisciplina, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getNotasPeriodicaDisciplinaIdentifier(o1) === this.getNotasPeriodicaDisciplinaIdentifier(o2) : o1 === o2;
  }

  addNotasPeriodicaDisciplinaToCollectionIfMissing<Type extends Pick<INotasPeriodicaDisciplina, 'id'>>(
    notasPeriodicaDisciplinaCollection: Type[],
    ...notasPeriodicaDisciplinasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notasPeriodicaDisciplinas: Type[] = notasPeriodicaDisciplinasToCheck.filter(isPresent);
    if (notasPeriodicaDisciplinas.length > 0) {
      const notasPeriodicaDisciplinaCollectionIdentifiers = notasPeriodicaDisciplinaCollection.map(
        notasPeriodicaDisciplinaItem => this.getNotasPeriodicaDisciplinaIdentifier(notasPeriodicaDisciplinaItem)!
      );
      const notasPeriodicaDisciplinasToAdd = notasPeriodicaDisciplinas.filter(notasPeriodicaDisciplinaItem => {
        const notasPeriodicaDisciplinaIdentifier = this.getNotasPeriodicaDisciplinaIdentifier(notasPeriodicaDisciplinaItem);
        if (notasPeriodicaDisciplinaCollectionIdentifiers.includes(notasPeriodicaDisciplinaIdentifier)) {
          return false;
        }
        notasPeriodicaDisciplinaCollectionIdentifiers.push(notasPeriodicaDisciplinaIdentifier);
        return true;
      });
      return [...notasPeriodicaDisciplinasToAdd, ...notasPeriodicaDisciplinaCollection];
    }
    return notasPeriodicaDisciplinaCollection;
  }

  protected convertDateFromClient<
    T extends INotasPeriodicaDisciplina | NewNotasPeriodicaDisciplina | PartialUpdateNotasPeriodicaDisciplina
  >(notasPeriodicaDisciplina: T): RestOf<T> {
    return {
      ...notasPeriodicaDisciplina,
      timestamp: notasPeriodicaDisciplina.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restNotasPeriodicaDisciplina: RestNotasPeriodicaDisciplina): INotasPeriodicaDisciplina {
    return {
      ...restNotasPeriodicaDisciplina,
      timestamp: restNotasPeriodicaDisciplina.timestamp ? dayjs(restNotasPeriodicaDisciplina.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNotasPeriodicaDisciplina>): HttpResponse<INotasPeriodicaDisciplina> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNotasPeriodicaDisciplina[]>): HttpResponse<INotasPeriodicaDisciplina[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
