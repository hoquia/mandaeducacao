import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDetalhePlanoAula, NewDetalhePlanoAula } from '../detalhe-plano-aula.model';

export type PartialUpdateDetalhePlanoAula = Partial<IDetalhePlanoAula> & Pick<IDetalhePlanoAula, 'id'>;

export type EntityResponseType = HttpResponse<IDetalhePlanoAula>;
export type EntityArrayResponseType = HttpResponse<IDetalhePlanoAula[]>;

@Injectable({ providedIn: 'root' })
export class DetalhePlanoAulaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/detalhe-plano-aulas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(detalhePlanoAula: NewDetalhePlanoAula): Observable<EntityResponseType> {
    return this.http.post<IDetalhePlanoAula>(this.resourceUrl, detalhePlanoAula, { observe: 'response' });
  }

  update(detalhePlanoAula: IDetalhePlanoAula): Observable<EntityResponseType> {
    return this.http.put<IDetalhePlanoAula>(
      `${this.resourceUrl}/${this.getDetalhePlanoAulaIdentifier(detalhePlanoAula)}`,
      detalhePlanoAula,
      { observe: 'response' }
    );
  }

  partialUpdate(detalhePlanoAula: PartialUpdateDetalhePlanoAula): Observable<EntityResponseType> {
    return this.http.patch<IDetalhePlanoAula>(
      `${this.resourceUrl}/${this.getDetalhePlanoAulaIdentifier(detalhePlanoAula)}`,
      detalhePlanoAula,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetalhePlanoAula>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetalhePlanoAula[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDetalhePlanoAulaIdentifier(detalhePlanoAula: Pick<IDetalhePlanoAula, 'id'>): number {
    return detalhePlanoAula.id;
  }

  compareDetalhePlanoAula(o1: Pick<IDetalhePlanoAula, 'id'> | null, o2: Pick<IDetalhePlanoAula, 'id'> | null): boolean {
    return o1 && o2 ? this.getDetalhePlanoAulaIdentifier(o1) === this.getDetalhePlanoAulaIdentifier(o2) : o1 === o2;
  }

  addDetalhePlanoAulaToCollectionIfMissing<Type extends Pick<IDetalhePlanoAula, 'id'>>(
    detalhePlanoAulaCollection: Type[],
    ...detalhePlanoAulasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const detalhePlanoAulas: Type[] = detalhePlanoAulasToCheck.filter(isPresent);
    if (detalhePlanoAulas.length > 0) {
      const detalhePlanoAulaCollectionIdentifiers = detalhePlanoAulaCollection.map(
        detalhePlanoAulaItem => this.getDetalhePlanoAulaIdentifier(detalhePlanoAulaItem)!
      );
      const detalhePlanoAulasToAdd = detalhePlanoAulas.filter(detalhePlanoAulaItem => {
        const detalhePlanoAulaIdentifier = this.getDetalhePlanoAulaIdentifier(detalhePlanoAulaItem);
        if (detalhePlanoAulaCollectionIdentifiers.includes(detalhePlanoAulaIdentifier)) {
          return false;
        }
        detalhePlanoAulaCollectionIdentifiers.push(detalhePlanoAulaIdentifier);
        return true;
      });
      return [...detalhePlanoAulasToAdd, ...detalhePlanoAulaCollection];
    }
    return detalhePlanoAulaCollection;
  }
}
