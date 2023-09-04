import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResponsavelTurno, NewResponsavelTurno } from '../responsavel-turno.model';

export type PartialUpdateResponsavelTurno = Partial<IResponsavelTurno> & Pick<IResponsavelTurno, 'id'>;

type RestOf<T extends IResponsavelTurno | NewResponsavelTurno> = Omit<T, 'de' | 'ate' | 'timestamp'> & {
  de?: string | null;
  ate?: string | null;
  timestamp?: string | null;
};

export type RestResponsavelTurno = RestOf<IResponsavelTurno>;

export type NewRestResponsavelTurno = RestOf<NewResponsavelTurno>;

export type PartialUpdateRestResponsavelTurno = RestOf<PartialUpdateResponsavelTurno>;

export type EntityResponseType = HttpResponse<IResponsavelTurno>;
export type EntityArrayResponseType = HttpResponse<IResponsavelTurno[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelTurnoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/responsavel-turnos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(responsavelTurno: NewResponsavelTurno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelTurno);
    return this.http
      .post<RestResponsavelTurno>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(responsavelTurno: IResponsavelTurno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelTurno);
    return this.http
      .put<RestResponsavelTurno>(`${this.resourceUrl}/${this.getResponsavelTurnoIdentifier(responsavelTurno)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(responsavelTurno: PartialUpdateResponsavelTurno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelTurno);
    return this.http
      .patch<RestResponsavelTurno>(`${this.resourceUrl}/${this.getResponsavelTurnoIdentifier(responsavelTurno)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResponsavelTurno>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResponsavelTurno[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResponsavelTurnoIdentifier(responsavelTurno: Pick<IResponsavelTurno, 'id'>): number {
    return responsavelTurno.id;
  }

  compareResponsavelTurno(o1: Pick<IResponsavelTurno, 'id'> | null, o2: Pick<IResponsavelTurno, 'id'> | null): boolean {
    return o1 && o2 ? this.getResponsavelTurnoIdentifier(o1) === this.getResponsavelTurnoIdentifier(o2) : o1 === o2;
  }

  addResponsavelTurnoToCollectionIfMissing<Type extends Pick<IResponsavelTurno, 'id'>>(
    responsavelTurnoCollection: Type[],
    ...responsavelTurnosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const responsavelTurnos: Type[] = responsavelTurnosToCheck.filter(isPresent);
    if (responsavelTurnos.length > 0) {
      const responsavelTurnoCollectionIdentifiers = responsavelTurnoCollection.map(
        responsavelTurnoItem => this.getResponsavelTurnoIdentifier(responsavelTurnoItem)!
      );
      const responsavelTurnosToAdd = responsavelTurnos.filter(responsavelTurnoItem => {
        const responsavelTurnoIdentifier = this.getResponsavelTurnoIdentifier(responsavelTurnoItem);
        if (responsavelTurnoCollectionIdentifiers.includes(responsavelTurnoIdentifier)) {
          return false;
        }
        responsavelTurnoCollectionIdentifiers.push(responsavelTurnoIdentifier);
        return true;
      });
      return [...responsavelTurnosToAdd, ...responsavelTurnoCollection];
    }
    return responsavelTurnoCollection;
  }

  protected convertDateFromClient<T extends IResponsavelTurno | NewResponsavelTurno | PartialUpdateResponsavelTurno>(
    responsavelTurno: T
  ): RestOf<T> {
    return {
      ...responsavelTurno,
      de: responsavelTurno.de?.format(DATE_FORMAT) ?? null,
      ate: responsavelTurno.ate?.format(DATE_FORMAT) ?? null,
      timestamp: responsavelTurno.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restResponsavelTurno: RestResponsavelTurno): IResponsavelTurno {
    return {
      ...restResponsavelTurno,
      de: restResponsavelTurno.de ? dayjs(restResponsavelTurno.de) : undefined,
      ate: restResponsavelTurno.ate ? dayjs(restResponsavelTurno.ate) : undefined,
      timestamp: restResponsavelTurno.timestamp ? dayjs(restResponsavelTurno.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResponsavelTurno>): HttpResponse<IResponsavelTurno> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResponsavelTurno[]>): HttpResponse<IResponsavelTurno[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
