import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPeriodoHorario, NewPeriodoHorario } from '../periodo-horario.model';

export type PartialUpdatePeriodoHorario = Partial<IPeriodoHorario> & Pick<IPeriodoHorario, 'id'>;

export type EntityResponseType = HttpResponse<IPeriodoHorario>;
export type EntityArrayResponseType = HttpResponse<IPeriodoHorario[]>;

@Injectable({ providedIn: 'root' })
export class PeriodoHorarioService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/periodo-horarios');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(periodoHorario: NewPeriodoHorario): Observable<EntityResponseType> {
    return this.http.post<IPeriodoHorario>(this.resourceUrl, periodoHorario, { observe: 'response' });
  }

  update(periodoHorario: IPeriodoHorario): Observable<EntityResponseType> {
    return this.http.put<IPeriodoHorario>(`${this.resourceUrl}/${this.getPeriodoHorarioIdentifier(periodoHorario)}`, periodoHorario, {
      observe: 'response',
    });
  }

  partialUpdate(periodoHorario: PartialUpdatePeriodoHorario): Observable<EntityResponseType> {
    return this.http.patch<IPeriodoHorario>(`${this.resourceUrl}/${this.getPeriodoHorarioIdentifier(periodoHorario)}`, periodoHorario, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPeriodoHorario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPeriodoHorario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPeriodoHorarioIdentifier(periodoHorario: Pick<IPeriodoHorario, 'id'>): number {
    return periodoHorario.id;
  }

  comparePeriodoHorario(o1: Pick<IPeriodoHorario, 'id'> | null, o2: Pick<IPeriodoHorario, 'id'> | null): boolean {
    return o1 && o2 ? this.getPeriodoHorarioIdentifier(o1) === this.getPeriodoHorarioIdentifier(o2) : o1 === o2;
  }

  addPeriodoHorarioToCollectionIfMissing<Type extends Pick<IPeriodoHorario, 'id'>>(
    periodoHorarioCollection: Type[],
    ...periodoHorariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const periodoHorarios: Type[] = periodoHorariosToCheck.filter(isPresent);
    if (periodoHorarios.length > 0) {
      const periodoHorarioCollectionIdentifiers = periodoHorarioCollection.map(
        periodoHorarioItem => this.getPeriodoHorarioIdentifier(periodoHorarioItem)!
      );
      const periodoHorariosToAdd = periodoHorarios.filter(periodoHorarioItem => {
        const periodoHorarioIdentifier = this.getPeriodoHorarioIdentifier(periodoHorarioItem);
        if (periodoHorarioCollectionIdentifiers.includes(periodoHorarioIdentifier)) {
          return false;
        }
        periodoHorarioCollectionIdentifiers.push(periodoHorarioIdentifier);
        return true;
      });
      return [...periodoHorariosToAdd, ...periodoHorarioCollection];
    }
    return periodoHorarioCollection;
  }
}
