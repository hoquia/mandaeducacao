import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoriaEmolumento, NewCategoriaEmolumento } from '../categoria-emolumento.model';

export type PartialUpdateCategoriaEmolumento = Partial<ICategoriaEmolumento> & Pick<ICategoriaEmolumento, 'id'>;

export type EntityResponseType = HttpResponse<ICategoriaEmolumento>;
export type EntityArrayResponseType = HttpResponse<ICategoriaEmolumento[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaEmolumentoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categoria-emolumentos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categoriaEmolumento: NewCategoriaEmolumento): Observable<EntityResponseType> {
    return this.http.post<ICategoriaEmolumento>(this.resourceUrl, categoriaEmolumento, { observe: 'response' });
  }

  update(categoriaEmolumento: ICategoriaEmolumento): Observable<EntityResponseType> {
    return this.http.put<ICategoriaEmolumento>(
      `${this.resourceUrl}/${this.getCategoriaEmolumentoIdentifier(categoriaEmolumento)}`,
      categoriaEmolumento,
      { observe: 'response' }
    );
  }

  partialUpdate(categoriaEmolumento: PartialUpdateCategoriaEmolumento): Observable<EntityResponseType> {
    return this.http.patch<ICategoriaEmolumento>(
      `${this.resourceUrl}/${this.getCategoriaEmolumentoIdentifier(categoriaEmolumento)}`,
      categoriaEmolumento,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaEmolumento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaEmolumento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoriaEmolumentoIdentifier(categoriaEmolumento: Pick<ICategoriaEmolumento, 'id'>): number {
    return categoriaEmolumento.id;
  }

  compareCategoriaEmolumento(o1: Pick<ICategoriaEmolumento, 'id'> | null, o2: Pick<ICategoriaEmolumento, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoriaEmolumentoIdentifier(o1) === this.getCategoriaEmolumentoIdentifier(o2) : o1 === o2;
  }

  addCategoriaEmolumentoToCollectionIfMissing<Type extends Pick<ICategoriaEmolumento, 'id'>>(
    categoriaEmolumentoCollection: Type[],
    ...categoriaEmolumentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoriaEmolumentos: Type[] = categoriaEmolumentosToCheck.filter(isPresent);
    if (categoriaEmolumentos.length > 0) {
      const categoriaEmolumentoCollectionIdentifiers = categoriaEmolumentoCollection.map(
        categoriaEmolumentoItem => this.getCategoriaEmolumentoIdentifier(categoriaEmolumentoItem)!
      );
      const categoriaEmolumentosToAdd = categoriaEmolumentos.filter(categoriaEmolumentoItem => {
        const categoriaEmolumentoIdentifier = this.getCategoriaEmolumentoIdentifier(categoriaEmolumentoItem);
        if (categoriaEmolumentoCollectionIdentifiers.includes(categoriaEmolumentoIdentifier)) {
          return false;
        }
        categoriaEmolumentoCollectionIdentifiers.push(categoriaEmolumentoIdentifier);
        return true;
      });
      return [...categoriaEmolumentosToAdd, ...categoriaEmolumentoCollection];
    }
    return categoriaEmolumentoCollection;
  }
}
