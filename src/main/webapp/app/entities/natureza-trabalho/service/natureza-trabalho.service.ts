import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INaturezaTrabalho, NewNaturezaTrabalho } from '../natureza-trabalho.model';

export type PartialUpdateNaturezaTrabalho = Partial<INaturezaTrabalho> & Pick<INaturezaTrabalho, 'id'>;

export type EntityResponseType = HttpResponse<INaturezaTrabalho>;
export type EntityArrayResponseType = HttpResponse<INaturezaTrabalho[]>;

@Injectable({ providedIn: 'root' })
export class NaturezaTrabalhoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/natureza-trabalhos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(naturezaTrabalho: NewNaturezaTrabalho): Observable<EntityResponseType> {
    return this.http.post<INaturezaTrabalho>(this.resourceUrl, naturezaTrabalho, { observe: 'response' });
  }

  update(naturezaTrabalho: INaturezaTrabalho): Observable<EntityResponseType> {
    return this.http.put<INaturezaTrabalho>(
      `${this.resourceUrl}/${this.getNaturezaTrabalhoIdentifier(naturezaTrabalho)}`,
      naturezaTrabalho,
      { observe: 'response' }
    );
  }

  partialUpdate(naturezaTrabalho: PartialUpdateNaturezaTrabalho): Observable<EntityResponseType> {
    return this.http.patch<INaturezaTrabalho>(
      `${this.resourceUrl}/${this.getNaturezaTrabalhoIdentifier(naturezaTrabalho)}`,
      naturezaTrabalho,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INaturezaTrabalho>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INaturezaTrabalho[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNaturezaTrabalhoIdentifier(naturezaTrabalho: Pick<INaturezaTrabalho, 'id'>): number {
    return naturezaTrabalho.id;
  }

  compareNaturezaTrabalho(o1: Pick<INaturezaTrabalho, 'id'> | null, o2: Pick<INaturezaTrabalho, 'id'> | null): boolean {
    return o1 && o2 ? this.getNaturezaTrabalhoIdentifier(o1) === this.getNaturezaTrabalhoIdentifier(o2) : o1 === o2;
  }

  addNaturezaTrabalhoToCollectionIfMissing<Type extends Pick<INaturezaTrabalho, 'id'>>(
    naturezaTrabalhoCollection: Type[],
    ...naturezaTrabalhosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const naturezaTrabalhos: Type[] = naturezaTrabalhosToCheck.filter(isPresent);
    if (naturezaTrabalhos.length > 0) {
      const naturezaTrabalhoCollectionIdentifiers = naturezaTrabalhoCollection.map(
        naturezaTrabalhoItem => this.getNaturezaTrabalhoIdentifier(naturezaTrabalhoItem)!
      );
      const naturezaTrabalhosToAdd = naturezaTrabalhos.filter(naturezaTrabalhoItem => {
        const naturezaTrabalhoIdentifier = this.getNaturezaTrabalhoIdentifier(naturezaTrabalhoItem);
        if (naturezaTrabalhoCollectionIdentifiers.includes(naturezaTrabalhoIdentifier)) {
          return false;
        }
        naturezaTrabalhoCollectionIdentifiers.push(naturezaTrabalhoIdentifier);
        return true;
      });
      return [...naturezaTrabalhosToAdd, ...naturezaTrabalhoCollection];
    }
    return naturezaTrabalhoCollection;
  }
}
