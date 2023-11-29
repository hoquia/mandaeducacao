import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotasGeralDisciplina, NewNotasGeralDisciplina } from '../notas-geral-disciplina.model';

export type PartialUpdateNotasGeralDisciplina = Partial<INotasGeralDisciplina> & Pick<INotasGeralDisciplina, 'id'>;

type RestOf<T extends INotasGeralDisciplina | NewNotasGeralDisciplina> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestNotasGeralDisciplina = RestOf<INotasGeralDisciplina>;

export type NewRestNotasGeralDisciplina = RestOf<NewNotasGeralDisciplina>;

export type PartialUpdateRestNotasGeralDisciplina = RestOf<PartialUpdateNotasGeralDisciplina>;

export type EntityResponseType = HttpResponse<INotasGeralDisciplina>;
export type EntityArrayResponseType = HttpResponse<INotasGeralDisciplina[]>;

@Injectable({ providedIn: 'root' })
export class NotasGeralDisciplinaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/notas-geral-disciplinas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(notasGeralDisciplina: NewNotasGeralDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notasGeralDisciplina);
    return this.http
      .post<RestNotasGeralDisciplina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(notasGeralDisciplina: INotasGeralDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notasGeralDisciplina);
    return this.http
      .put<RestNotasGeralDisciplina>(`${this.resourceUrl}/${this.getNotasGeralDisciplinaIdentifier(notasGeralDisciplina)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(notasGeralDisciplina: PartialUpdateNotasGeralDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notasGeralDisciplina);
    return this.http
      .patch<RestNotasGeralDisciplina>(`${this.resourceUrl}/${this.getNotasGeralDisciplinaIdentifier(notasGeralDisciplina)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNotasGeralDisciplina>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNotasGeralDisciplina[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotasGeralDisciplinaIdentifier(notasGeralDisciplina: Pick<INotasGeralDisciplina, 'id'>): number {
    return notasGeralDisciplina.id;
  }

  compareNotasGeralDisciplina(o1: Pick<INotasGeralDisciplina, 'id'> | null, o2: Pick<INotasGeralDisciplina, 'id'> | null): boolean {
    return o1 && o2 ? this.getNotasGeralDisciplinaIdentifier(o1) === this.getNotasGeralDisciplinaIdentifier(o2) : o1 === o2;
  }

  addNotasGeralDisciplinaToCollectionIfMissing<Type extends Pick<INotasGeralDisciplina, 'id'>>(
    notasGeralDisciplinaCollection: Type[],
    ...notasGeralDisciplinasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notasGeralDisciplinas: Type[] = notasGeralDisciplinasToCheck.filter(isPresent);
    if (notasGeralDisciplinas.length > 0) {
      const notasGeralDisciplinaCollectionIdentifiers = notasGeralDisciplinaCollection.map(
        notasGeralDisciplinaItem => this.getNotasGeralDisciplinaIdentifier(notasGeralDisciplinaItem)!
      );
      const notasGeralDisciplinasToAdd = notasGeralDisciplinas.filter(notasGeralDisciplinaItem => {
        const notasGeralDisciplinaIdentifier = this.getNotasGeralDisciplinaIdentifier(notasGeralDisciplinaItem);
        if (notasGeralDisciplinaCollectionIdentifiers.includes(notasGeralDisciplinaIdentifier)) {
          return false;
        }
        notasGeralDisciplinaCollectionIdentifiers.push(notasGeralDisciplinaIdentifier);
        return true;
      });
      return [...notasGeralDisciplinasToAdd, ...notasGeralDisciplinaCollection];
    }
    return notasGeralDisciplinaCollection;
  }

  protected convertDateFromClient<T extends INotasGeralDisciplina | NewNotasGeralDisciplina | PartialUpdateNotasGeralDisciplina>(
    notasGeralDisciplina: T
  ): RestOf<T> {
    return {
      ...notasGeralDisciplina,
      timestamp: notasGeralDisciplina.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restNotasGeralDisciplina: RestNotasGeralDisciplina): INotasGeralDisciplina {
    return {
      ...restNotasGeralDisciplina,
      timestamp: restNotasGeralDisciplina.timestamp ? dayjs(restNotasGeralDisciplina.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNotasGeralDisciplina>): HttpResponse<INotasGeralDisciplina> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNotasGeralDisciplina[]>): HttpResponse<INotasGeralDisciplina[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  getNotasGerais(): Observable<INotasGeralDisciplina[]> {
    return this.http.get<INotasGeralDisciplina[]>(`${this.resourceUrl}`);
  }
}
