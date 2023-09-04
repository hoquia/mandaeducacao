import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoriaOcorrencia, NewCategoriaOcorrencia } from '../categoria-ocorrencia.model';

export type PartialUpdateCategoriaOcorrencia = Partial<ICategoriaOcorrencia> & Pick<ICategoriaOcorrencia, 'id'>;

export type EntityResponseType = HttpResponse<ICategoriaOcorrencia>;
export type EntityArrayResponseType = HttpResponse<ICategoriaOcorrencia[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaOcorrenciaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categoria-ocorrencias');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categoriaOcorrencia: NewCategoriaOcorrencia): Observable<EntityResponseType> {
    return this.http.post<ICategoriaOcorrencia>(this.resourceUrl, categoriaOcorrencia, { observe: 'response' });
  }

  update(categoriaOcorrencia: ICategoriaOcorrencia): Observable<EntityResponseType> {
    return this.http.put<ICategoriaOcorrencia>(
      `${this.resourceUrl}/${this.getCategoriaOcorrenciaIdentifier(categoriaOcorrencia)}`,
      categoriaOcorrencia,
      { observe: 'response' }
    );
  }

  partialUpdate(categoriaOcorrencia: PartialUpdateCategoriaOcorrencia): Observable<EntityResponseType> {
    return this.http.patch<ICategoriaOcorrencia>(
      `${this.resourceUrl}/${this.getCategoriaOcorrenciaIdentifier(categoriaOcorrencia)}`,
      categoriaOcorrencia,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaOcorrencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaOcorrencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoriaOcorrenciaIdentifier(categoriaOcorrencia: Pick<ICategoriaOcorrencia, 'id'>): number {
    return categoriaOcorrencia.id;
  }

  compareCategoriaOcorrencia(o1: Pick<ICategoriaOcorrencia, 'id'> | null, o2: Pick<ICategoriaOcorrencia, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoriaOcorrenciaIdentifier(o1) === this.getCategoriaOcorrenciaIdentifier(o2) : o1 === o2;
  }

  addCategoriaOcorrenciaToCollectionIfMissing<Type extends Pick<ICategoriaOcorrencia, 'id'>>(
    categoriaOcorrenciaCollection: Type[],
    ...categoriaOcorrenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoriaOcorrencias: Type[] = categoriaOcorrenciasToCheck.filter(isPresent);
    if (categoriaOcorrencias.length > 0) {
      const categoriaOcorrenciaCollectionIdentifiers = categoriaOcorrenciaCollection.map(
        categoriaOcorrenciaItem => this.getCategoriaOcorrenciaIdentifier(categoriaOcorrenciaItem)!
      );
      const categoriaOcorrenciasToAdd = categoriaOcorrencias.filter(categoriaOcorrenciaItem => {
        const categoriaOcorrenciaIdentifier = this.getCategoriaOcorrenciaIdentifier(categoriaOcorrenciaItem);
        if (categoriaOcorrenciaCollectionIdentifiers.includes(categoriaOcorrenciaIdentifier)) {
          return false;
        }
        categoriaOcorrenciaCollectionIdentifiers.push(categoriaOcorrenciaIdentifier);
        return true;
      });
      return [...categoriaOcorrenciasToAdd, ...categoriaOcorrenciaCollection];
    }
    return categoriaOcorrenciaCollection;
  }
}
