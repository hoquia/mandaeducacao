import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOcorrencia, NewOcorrencia } from '../ocorrencia.model';

export type PartialUpdateOcorrencia = Partial<IOcorrencia> & Pick<IOcorrencia, 'id'>;

type RestOf<T extends IOcorrencia | NewOcorrencia> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestOcorrencia = RestOf<IOcorrencia>;

export type NewRestOcorrencia = RestOf<NewOcorrencia>;

export type PartialUpdateRestOcorrencia = RestOf<PartialUpdateOcorrencia>;

export type EntityResponseType = HttpResponse<IOcorrencia>;
export type EntityArrayResponseType = HttpResponse<IOcorrencia[]>;

@Injectable({ providedIn: 'root' })
export class OcorrenciaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ocorrencias');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ocorrencia: NewOcorrencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ocorrencia);
    return this.http
      .post<RestOcorrencia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(ocorrencia: IOcorrencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ocorrencia);
    return this.http
      .put<RestOcorrencia>(`${this.resourceUrl}/${this.getOcorrenciaIdentifier(ocorrencia)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(ocorrencia: PartialUpdateOcorrencia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ocorrencia);
    return this.http
      .patch<RestOcorrencia>(`${this.resourceUrl}/${this.getOcorrenciaIdentifier(ocorrencia)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOcorrencia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOcorrencia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOcorrenciaIdentifier(ocorrencia: Pick<IOcorrencia, 'id'>): number {
    return ocorrencia.id;
  }

  compareOcorrencia(o1: Pick<IOcorrencia, 'id'> | null, o2: Pick<IOcorrencia, 'id'> | null): boolean {
    return o1 && o2 ? this.getOcorrenciaIdentifier(o1) === this.getOcorrenciaIdentifier(o2) : o1 === o2;
  }

  addOcorrenciaToCollectionIfMissing<Type extends Pick<IOcorrencia, 'id'>>(
    ocorrenciaCollection: Type[],
    ...ocorrenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ocorrencias: Type[] = ocorrenciasToCheck.filter(isPresent);
    if (ocorrencias.length > 0) {
      const ocorrenciaCollectionIdentifiers = ocorrenciaCollection.map(ocorrenciaItem => this.getOcorrenciaIdentifier(ocorrenciaItem)!);
      const ocorrenciasToAdd = ocorrencias.filter(ocorrenciaItem => {
        const ocorrenciaIdentifier = this.getOcorrenciaIdentifier(ocorrenciaItem);
        if (ocorrenciaCollectionIdentifiers.includes(ocorrenciaIdentifier)) {
          return false;
        }
        ocorrenciaCollectionIdentifiers.push(ocorrenciaIdentifier);
        return true;
      });
      return [...ocorrenciasToAdd, ...ocorrenciaCollection];
    }
    return ocorrenciaCollection;
  }

  protected convertDateFromClient<T extends IOcorrencia | NewOcorrencia | PartialUpdateOcorrencia>(ocorrencia: T): RestOf<T> {
    return {
      ...ocorrencia,
      timestamp: ocorrencia.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restOcorrencia: RestOcorrencia): IOcorrencia {
    return {
      ...restOcorrencia,
      timestamp: restOcorrencia.timestamp ? dayjs(restOcorrencia.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOcorrencia>): HttpResponse<IOcorrencia> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOcorrencia[]>): HttpResponse<IOcorrencia[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
