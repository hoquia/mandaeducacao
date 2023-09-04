import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITurno, NewTurno } from '../turno.model';

export type PartialUpdateTurno = Partial<ITurno> & Pick<ITurno, 'id'>;

export type EntityResponseType = HttpResponse<ITurno>;
export type EntityArrayResponseType = HttpResponse<ITurno[]>;

@Injectable({ providedIn: 'root' })
export class TurnoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/turnos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(turno: NewTurno): Observable<EntityResponseType> {
    return this.http.post<ITurno>(this.resourceUrl, turno, { observe: 'response' });
  }

  update(turno: ITurno): Observable<EntityResponseType> {
    return this.http.put<ITurno>(`${this.resourceUrl}/${this.getTurnoIdentifier(turno)}`, turno, { observe: 'response' });
  }

  partialUpdate(turno: PartialUpdateTurno): Observable<EntityResponseType> {
    return this.http.patch<ITurno>(`${this.resourceUrl}/${this.getTurnoIdentifier(turno)}`, turno, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITurno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITurno[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTurnoIdentifier(turno: Pick<ITurno, 'id'>): number {
    return turno.id;
  }

  compareTurno(o1: Pick<ITurno, 'id'> | null, o2: Pick<ITurno, 'id'> | null): boolean {
    return o1 && o2 ? this.getTurnoIdentifier(o1) === this.getTurnoIdentifier(o2) : o1 === o2;
  }

  addTurnoToCollectionIfMissing<Type extends Pick<ITurno, 'id'>>(
    turnoCollection: Type[],
    ...turnosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const turnos: Type[] = turnosToCheck.filter(isPresent);
    if (turnos.length > 0) {
      const turnoCollectionIdentifiers = turnoCollection.map(turnoItem => this.getTurnoIdentifier(turnoItem)!);
      const turnosToAdd = turnos.filter(turnoItem => {
        const turnoIdentifier = this.getTurnoIdentifier(turnoItem);
        if (turnoCollectionIdentifiers.includes(turnoIdentifier)) {
          return false;
        }
        turnoCollectionIdentifiers.push(turnoIdentifier);
        return true;
      });
      return [...turnosToAdd, ...turnoCollection];
    }
    return turnoCollection;
  }
}
