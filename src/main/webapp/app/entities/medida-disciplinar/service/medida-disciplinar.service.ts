import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMedidaDisciplinar, NewMedidaDisciplinar } from '../medida-disciplinar.model';

export type PartialUpdateMedidaDisciplinar = Partial<IMedidaDisciplinar> & Pick<IMedidaDisciplinar, 'id'>;

export type EntityResponseType = HttpResponse<IMedidaDisciplinar>;
export type EntityArrayResponseType = HttpResponse<IMedidaDisciplinar[]>;

@Injectable({ providedIn: 'root' })
export class MedidaDisciplinarService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/medida-disciplinars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(medidaDisciplinar: NewMedidaDisciplinar): Observable<EntityResponseType> {
    return this.http.post<IMedidaDisciplinar>(this.resourceUrl, medidaDisciplinar, { observe: 'response' });
  }

  update(medidaDisciplinar: IMedidaDisciplinar): Observable<EntityResponseType> {
    return this.http.put<IMedidaDisciplinar>(
      `${this.resourceUrl}/${this.getMedidaDisciplinarIdentifier(medidaDisciplinar)}`,
      medidaDisciplinar,
      { observe: 'response' }
    );
  }

  partialUpdate(medidaDisciplinar: PartialUpdateMedidaDisciplinar): Observable<EntityResponseType> {
    return this.http.patch<IMedidaDisciplinar>(
      `${this.resourceUrl}/${this.getMedidaDisciplinarIdentifier(medidaDisciplinar)}`,
      medidaDisciplinar,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMedidaDisciplinar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMedidaDisciplinar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMedidaDisciplinarIdentifier(medidaDisciplinar: Pick<IMedidaDisciplinar, 'id'>): number {
    return medidaDisciplinar.id;
  }

  compareMedidaDisciplinar(o1: Pick<IMedidaDisciplinar, 'id'> | null, o2: Pick<IMedidaDisciplinar, 'id'> | null): boolean {
    return o1 && o2 ? this.getMedidaDisciplinarIdentifier(o1) === this.getMedidaDisciplinarIdentifier(o2) : o1 === o2;
  }

  addMedidaDisciplinarToCollectionIfMissing<Type extends Pick<IMedidaDisciplinar, 'id'>>(
    medidaDisciplinarCollection: Type[],
    ...medidaDisciplinarsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const medidaDisciplinars: Type[] = medidaDisciplinarsToCheck.filter(isPresent);
    if (medidaDisciplinars.length > 0) {
      const medidaDisciplinarCollectionIdentifiers = medidaDisciplinarCollection.map(
        medidaDisciplinarItem => this.getMedidaDisciplinarIdentifier(medidaDisciplinarItem)!
      );
      const medidaDisciplinarsToAdd = medidaDisciplinars.filter(medidaDisciplinarItem => {
        const medidaDisciplinarIdentifier = this.getMedidaDisciplinarIdentifier(medidaDisciplinarItem);
        if (medidaDisciplinarCollectionIdentifiers.includes(medidaDisciplinarIdentifier)) {
          return false;
        }
        medidaDisciplinarCollectionIdentifiers.push(medidaDisciplinarIdentifier);
        return true;
      });
      return [...medidaDisciplinarsToAdd, ...medidaDisciplinarCollection];
    }
    return medidaDisciplinarCollection;
  }
}
