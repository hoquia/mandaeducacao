import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoAula, NewPlanoAula } from '../plano-aula.model';

export type PartialUpdatePlanoAula = Partial<IPlanoAula> & Pick<IPlanoAula, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoAula>;
export type EntityArrayResponseType = HttpResponse<IPlanoAula[]>;

@Injectable({ providedIn: 'root' })
export class PlanoAulaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-aulas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(planoAula: NewPlanoAula): Observable<EntityResponseType> {
    return this.http.post<IPlanoAula>(this.resourceUrl, planoAula, { observe: 'response' });
  }

  update(planoAula: IPlanoAula): Observable<EntityResponseType> {
    return this.http.put<IPlanoAula>(`${this.resourceUrl}/${this.getPlanoAulaIdentifier(planoAula)}`, planoAula, { observe: 'response' });
  }

  partialUpdate(planoAula: PartialUpdatePlanoAula): Observable<EntityResponseType> {
    return this.http.patch<IPlanoAula>(`${this.resourceUrl}/${this.getPlanoAulaIdentifier(planoAula)}`, planoAula, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoAula>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoAula[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoAulaIdentifier(planoAula: Pick<IPlanoAula, 'id'>): number {
    return planoAula.id;
  }

  comparePlanoAula(o1: Pick<IPlanoAula, 'id'> | null, o2: Pick<IPlanoAula, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanoAulaIdentifier(o1) === this.getPlanoAulaIdentifier(o2) : o1 === o2;
  }

  addPlanoAulaToCollectionIfMissing<Type extends Pick<IPlanoAula, 'id'>>(
    planoAulaCollection: Type[],
    ...planoAulasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoAulas: Type[] = planoAulasToCheck.filter(isPresent);
    if (planoAulas.length > 0) {
      const planoAulaCollectionIdentifiers = planoAulaCollection.map(planoAulaItem => this.getPlanoAulaIdentifier(planoAulaItem)!);
      const planoAulasToAdd = planoAulas.filter(planoAulaItem => {
        const planoAulaIdentifier = this.getPlanoAulaIdentifier(planoAulaItem);
        if (planoAulaCollectionIdentifiers.includes(planoAulaIdentifier)) {
          return false;
        }
        planoAulaCollectionIdentifiers.push(planoAulaIdentifier);
        return true;
      });
      return [...planoAulasToAdd, ...planoAulaCollection];
    }
    return planoAulaCollection;
  }
}
