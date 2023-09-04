import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISequenciaDocumento, NewSequenciaDocumento } from '../sequencia-documento.model';

export type PartialUpdateSequenciaDocumento = Partial<ISequenciaDocumento> & Pick<ISequenciaDocumento, 'id'>;

type RestOf<T extends ISequenciaDocumento | NewSequenciaDocumento> = Omit<T, 'data' | 'timestamp'> & {
  data?: string | null;
  timestamp?: string | null;
};

export type RestSequenciaDocumento = RestOf<ISequenciaDocumento>;

export type NewRestSequenciaDocumento = RestOf<NewSequenciaDocumento>;

export type PartialUpdateRestSequenciaDocumento = RestOf<PartialUpdateSequenciaDocumento>;

export type EntityResponseType = HttpResponse<ISequenciaDocumento>;
export type EntityArrayResponseType = HttpResponse<ISequenciaDocumento[]>;

@Injectable({ providedIn: 'root' })
export class SequenciaDocumentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sequencia-documentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sequenciaDocumento: NewSequenciaDocumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenciaDocumento);
    return this.http
      .post<RestSequenciaDocumento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(sequenciaDocumento: ISequenciaDocumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenciaDocumento);
    return this.http
      .put<RestSequenciaDocumento>(`${this.resourceUrl}/${this.getSequenciaDocumentoIdentifier(sequenciaDocumento)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(sequenciaDocumento: PartialUpdateSequenciaDocumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sequenciaDocumento);
    return this.http
      .patch<RestSequenciaDocumento>(`${this.resourceUrl}/${this.getSequenciaDocumentoIdentifier(sequenciaDocumento)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSequenciaDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSequenciaDocumento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSequenciaDocumentoIdentifier(sequenciaDocumento: Pick<ISequenciaDocumento, 'id'>): number {
    return sequenciaDocumento.id;
  }

  compareSequenciaDocumento(o1: Pick<ISequenciaDocumento, 'id'> | null, o2: Pick<ISequenciaDocumento, 'id'> | null): boolean {
    return o1 && o2 ? this.getSequenciaDocumentoIdentifier(o1) === this.getSequenciaDocumentoIdentifier(o2) : o1 === o2;
  }

  addSequenciaDocumentoToCollectionIfMissing<Type extends Pick<ISequenciaDocumento, 'id'>>(
    sequenciaDocumentoCollection: Type[],
    ...sequenciaDocumentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sequenciaDocumentos: Type[] = sequenciaDocumentosToCheck.filter(isPresent);
    if (sequenciaDocumentos.length > 0) {
      const sequenciaDocumentoCollectionIdentifiers = sequenciaDocumentoCollection.map(
        sequenciaDocumentoItem => this.getSequenciaDocumentoIdentifier(sequenciaDocumentoItem)!
      );
      const sequenciaDocumentosToAdd = sequenciaDocumentos.filter(sequenciaDocumentoItem => {
        const sequenciaDocumentoIdentifier = this.getSequenciaDocumentoIdentifier(sequenciaDocumentoItem);
        if (sequenciaDocumentoCollectionIdentifiers.includes(sequenciaDocumentoIdentifier)) {
          return false;
        }
        sequenciaDocumentoCollectionIdentifiers.push(sequenciaDocumentoIdentifier);
        return true;
      });
      return [...sequenciaDocumentosToAdd, ...sequenciaDocumentoCollection];
    }
    return sequenciaDocumentoCollection;
  }

  protected convertDateFromClient<T extends ISequenciaDocumento | NewSequenciaDocumento | PartialUpdateSequenciaDocumento>(
    sequenciaDocumento: T
  ): RestOf<T> {
    return {
      ...sequenciaDocumento,
      data: sequenciaDocumento.data?.format(DATE_FORMAT) ?? null,
      timestamp: sequenciaDocumento.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSequenciaDocumento: RestSequenciaDocumento): ISequenciaDocumento {
    return {
      ...restSequenciaDocumento,
      data: restSequenciaDocumento.data ? dayjs(restSequenciaDocumento.data) : undefined,
      timestamp: restSequenciaDocumento.timestamp ? dayjs(restSequenciaDocumento.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSequenciaDocumento>): HttpResponse<ISequenciaDocumento> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSequenciaDocumento[]>): HttpResponse<ISequenciaDocumento[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
