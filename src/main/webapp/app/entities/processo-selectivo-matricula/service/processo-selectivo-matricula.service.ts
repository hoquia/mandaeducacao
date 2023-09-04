import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProcessoSelectivoMatricula, NewProcessoSelectivoMatricula } from '../processo-selectivo-matricula.model';

export type PartialUpdateProcessoSelectivoMatricula = Partial<IProcessoSelectivoMatricula> & Pick<IProcessoSelectivoMatricula, 'id'>;

type RestOf<T extends IProcessoSelectivoMatricula | NewProcessoSelectivoMatricula> = Omit<T, 'dataTeste'> & {
  dataTeste?: string | null;
};

export type RestProcessoSelectivoMatricula = RestOf<IProcessoSelectivoMatricula>;

export type NewRestProcessoSelectivoMatricula = RestOf<NewProcessoSelectivoMatricula>;

export type PartialUpdateRestProcessoSelectivoMatricula = RestOf<PartialUpdateProcessoSelectivoMatricula>;

export type EntityResponseType = HttpResponse<IProcessoSelectivoMatricula>;
export type EntityArrayResponseType = HttpResponse<IProcessoSelectivoMatricula[]>;

@Injectable({ providedIn: 'root' })
export class ProcessoSelectivoMatriculaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/processo-selectivo-matriculas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(processoSelectivoMatricula: NewProcessoSelectivoMatricula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processoSelectivoMatricula);
    return this.http
      .post<RestProcessoSelectivoMatricula>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(processoSelectivoMatricula: IProcessoSelectivoMatricula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processoSelectivoMatricula);
    return this.http
      .put<RestProcessoSelectivoMatricula>(
        `${this.resourceUrl}/${this.getProcessoSelectivoMatriculaIdentifier(processoSelectivoMatricula)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(processoSelectivoMatricula: PartialUpdateProcessoSelectivoMatricula): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processoSelectivoMatricula);
    return this.http
      .patch<RestProcessoSelectivoMatricula>(
        `${this.resourceUrl}/${this.getProcessoSelectivoMatriculaIdentifier(processoSelectivoMatricula)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProcessoSelectivoMatricula>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProcessoSelectivoMatricula[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProcessoSelectivoMatriculaIdentifier(processoSelectivoMatricula: Pick<IProcessoSelectivoMatricula, 'id'>): number {
    return processoSelectivoMatricula.id;
  }

  compareProcessoSelectivoMatricula(
    o1: Pick<IProcessoSelectivoMatricula, 'id'> | null,
    o2: Pick<IProcessoSelectivoMatricula, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getProcessoSelectivoMatriculaIdentifier(o1) === this.getProcessoSelectivoMatriculaIdentifier(o2) : o1 === o2;
  }

  addProcessoSelectivoMatriculaToCollectionIfMissing<Type extends Pick<IProcessoSelectivoMatricula, 'id'>>(
    processoSelectivoMatriculaCollection: Type[],
    ...processoSelectivoMatriculasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const processoSelectivoMatriculas: Type[] = processoSelectivoMatriculasToCheck.filter(isPresent);
    if (processoSelectivoMatriculas.length > 0) {
      const processoSelectivoMatriculaCollectionIdentifiers = processoSelectivoMatriculaCollection.map(
        processoSelectivoMatriculaItem => this.getProcessoSelectivoMatriculaIdentifier(processoSelectivoMatriculaItem)!
      );
      const processoSelectivoMatriculasToAdd = processoSelectivoMatriculas.filter(processoSelectivoMatriculaItem => {
        const processoSelectivoMatriculaIdentifier = this.getProcessoSelectivoMatriculaIdentifier(processoSelectivoMatriculaItem);
        if (processoSelectivoMatriculaCollectionIdentifiers.includes(processoSelectivoMatriculaIdentifier)) {
          return false;
        }
        processoSelectivoMatriculaCollectionIdentifiers.push(processoSelectivoMatriculaIdentifier);
        return true;
      });
      return [...processoSelectivoMatriculasToAdd, ...processoSelectivoMatriculaCollection];
    }
    return processoSelectivoMatriculaCollection;
  }

  protected convertDateFromClient<
    T extends IProcessoSelectivoMatricula | NewProcessoSelectivoMatricula | PartialUpdateProcessoSelectivoMatricula
  >(processoSelectivoMatricula: T): RestOf<T> {
    return {
      ...processoSelectivoMatricula,
      dataTeste: processoSelectivoMatricula.dataTeste?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProcessoSelectivoMatricula: RestProcessoSelectivoMatricula): IProcessoSelectivoMatricula {
    return {
      ...restProcessoSelectivoMatricula,
      dataTeste: restProcessoSelectivoMatricula.dataTeste ? dayjs(restProcessoSelectivoMatricula.dataTeste) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProcessoSelectivoMatricula>): HttpResponse<IProcessoSelectivoMatricula> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestProcessoSelectivoMatricula[]>
  ): HttpResponse<IProcessoSelectivoMatricula[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
