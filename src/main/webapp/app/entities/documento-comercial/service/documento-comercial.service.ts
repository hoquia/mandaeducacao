import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocumentoComercial, NewDocumentoComercial } from '../documento-comercial.model';

export type PartialUpdateDocumentoComercial = Partial<IDocumentoComercial> & Pick<IDocumentoComercial, 'id'>;

export type EntityResponseType = HttpResponse<IDocumentoComercial>;
export type EntityArrayResponseType = HttpResponse<IDocumentoComercial[]>;

@Injectable({ providedIn: 'root' })
export class DocumentoComercialService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/documento-comercials');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(documentoComercial: NewDocumentoComercial): Observable<EntityResponseType> {
    return this.http.post<IDocumentoComercial>(this.resourceUrl, documentoComercial, { observe: 'response' });
  }

  update(documentoComercial: IDocumentoComercial): Observable<EntityResponseType> {
    return this.http.put<IDocumentoComercial>(
      `${this.resourceUrl}/${this.getDocumentoComercialIdentifier(documentoComercial)}`,
      documentoComercial,
      { observe: 'response' }
    );
  }

  partialUpdate(documentoComercial: PartialUpdateDocumentoComercial): Observable<EntityResponseType> {
    return this.http.patch<IDocumentoComercial>(
      `${this.resourceUrl}/${this.getDocumentoComercialIdentifier(documentoComercial)}`,
      documentoComercial,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocumentoComercial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocumentoComercial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocumentoComercialIdentifier(documentoComercial: Pick<IDocumentoComercial, 'id'>): number {
    return documentoComercial.id;
  }

  compareDocumentoComercial(o1: Pick<IDocumentoComercial, 'id'> | null, o2: Pick<IDocumentoComercial, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocumentoComercialIdentifier(o1) === this.getDocumentoComercialIdentifier(o2) : o1 === o2;
  }

  addDocumentoComercialToCollectionIfMissing<Type extends Pick<IDocumentoComercial, 'id'>>(
    documentoComercialCollection: Type[],
    ...documentoComercialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const documentoComercials: Type[] = documentoComercialsToCheck.filter(isPresent);
    if (documentoComercials.length > 0) {
      const documentoComercialCollectionIdentifiers = documentoComercialCollection.map(
        documentoComercialItem => this.getDocumentoComercialIdentifier(documentoComercialItem)!
      );
      const documentoComercialsToAdd = documentoComercials.filter(documentoComercialItem => {
        const documentoComercialIdentifier = this.getDocumentoComercialIdentifier(documentoComercialItem);
        if (documentoComercialCollectionIdentifiers.includes(documentoComercialIdentifier)) {
          return false;
        }
        documentoComercialCollectionIdentifiers.push(documentoComercialIdentifier);
        return true;
      });
      return [...documentoComercialsToAdd, ...documentoComercialCollection];
    }
    return documentoComercialCollection;
  }
}
