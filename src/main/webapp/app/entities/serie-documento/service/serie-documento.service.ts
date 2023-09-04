import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISerieDocumento, NewSerieDocumento } from '../serie-documento.model';

export type PartialUpdateSerieDocumento = Partial<ISerieDocumento> & Pick<ISerieDocumento, 'id'>;

export type EntityResponseType = HttpResponse<ISerieDocumento>;
export type EntityArrayResponseType = HttpResponse<ISerieDocumento[]>;

@Injectable({ providedIn: 'root' })
export class SerieDocumentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/serie-documentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(serieDocumento: NewSerieDocumento): Observable<EntityResponseType> {
    return this.http.post<ISerieDocumento>(this.resourceUrl, serieDocumento, { observe: 'response' });
  }

  update(serieDocumento: ISerieDocumento): Observable<EntityResponseType> {
    return this.http.put<ISerieDocumento>(`${this.resourceUrl}/${this.getSerieDocumentoIdentifier(serieDocumento)}`, serieDocumento, {
      observe: 'response',
    });
  }

  partialUpdate(serieDocumento: PartialUpdateSerieDocumento): Observable<EntityResponseType> {
    return this.http.patch<ISerieDocumento>(`${this.resourceUrl}/${this.getSerieDocumentoIdentifier(serieDocumento)}`, serieDocumento, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISerieDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISerieDocumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSerieDocumentoIdentifier(serieDocumento: Pick<ISerieDocumento, 'id'>): number {
    return serieDocumento.id;
  }

  compareSerieDocumento(o1: Pick<ISerieDocumento, 'id'> | null, o2: Pick<ISerieDocumento, 'id'> | null): boolean {
    return o1 && o2 ? this.getSerieDocumentoIdentifier(o1) === this.getSerieDocumentoIdentifier(o2) : o1 === o2;
  }

  addSerieDocumentoToCollectionIfMissing<Type extends Pick<ISerieDocumento, 'id'>>(
    serieDocumentoCollection: Type[],
    ...serieDocumentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const serieDocumentos: Type[] = serieDocumentosToCheck.filter(isPresent);
    if (serieDocumentos.length > 0) {
      const serieDocumentoCollectionIdentifiers = serieDocumentoCollection.map(
        serieDocumentoItem => this.getSerieDocumentoIdentifier(serieDocumentoItem)!
      );
      const serieDocumentosToAdd = serieDocumentos.filter(serieDocumentoItem => {
        const serieDocumentoIdentifier = this.getSerieDocumentoIdentifier(serieDocumentoItem);
        if (serieDocumentoCollectionIdentifiers.includes(serieDocumentoIdentifier)) {
          return false;
        }
        serieDocumentoCollectionIdentifiers.push(serieDocumentoIdentifier);
        return true;
      });
      return [...serieDocumentosToAdd, ...serieDocumentoCollection];
    }
    return serieDocumentoCollection;
  }
}
