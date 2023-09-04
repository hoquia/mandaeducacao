import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILongonkeloHistorico, NewLongonkeloHistorico } from '../longonkelo-historico.model';

export type PartialUpdateLongonkeloHistorico = Partial<ILongonkeloHistorico> & Pick<ILongonkeloHistorico, 'id'>;

type RestOf<T extends ILongonkeloHistorico | NewLongonkeloHistorico> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestLongonkeloHistorico = RestOf<ILongonkeloHistorico>;

export type NewRestLongonkeloHistorico = RestOf<NewLongonkeloHistorico>;

export type PartialUpdateRestLongonkeloHistorico = RestOf<PartialUpdateLongonkeloHistorico>;

export type EntityResponseType = HttpResponse<ILongonkeloHistorico>;
export type EntityArrayResponseType = HttpResponse<ILongonkeloHistorico[]>;

@Injectable({ providedIn: 'root' })
export class LongonkeloHistoricoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/longonkelo-historicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(longonkeloHistorico: NewLongonkeloHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(longonkeloHistorico);
    return this.http
      .post<RestLongonkeloHistorico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(longonkeloHistorico: ILongonkeloHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(longonkeloHistorico);
    return this.http
      .put<RestLongonkeloHistorico>(`${this.resourceUrl}/${this.getLongonkeloHistoricoIdentifier(longonkeloHistorico)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(longonkeloHistorico: PartialUpdateLongonkeloHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(longonkeloHistorico);
    return this.http
      .patch<RestLongonkeloHistorico>(`${this.resourceUrl}/${this.getLongonkeloHistoricoIdentifier(longonkeloHistorico)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestLongonkeloHistorico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestLongonkeloHistorico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLongonkeloHistoricoIdentifier(longonkeloHistorico: Pick<ILongonkeloHistorico, 'id'>): number {
    return longonkeloHistorico.id;
  }

  compareLongonkeloHistorico(o1: Pick<ILongonkeloHistorico, 'id'> | null, o2: Pick<ILongonkeloHistorico, 'id'> | null): boolean {
    return o1 && o2 ? this.getLongonkeloHistoricoIdentifier(o1) === this.getLongonkeloHistoricoIdentifier(o2) : o1 === o2;
  }

  addLongonkeloHistoricoToCollectionIfMissing<Type extends Pick<ILongonkeloHistorico, 'id'>>(
    longonkeloHistoricoCollection: Type[],
    ...longonkeloHistoricosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const longonkeloHistoricos: Type[] = longonkeloHistoricosToCheck.filter(isPresent);
    if (longonkeloHistoricos.length > 0) {
      const longonkeloHistoricoCollectionIdentifiers = longonkeloHistoricoCollection.map(
        longonkeloHistoricoItem => this.getLongonkeloHistoricoIdentifier(longonkeloHistoricoItem)!
      );
      const longonkeloHistoricosToAdd = longonkeloHistoricos.filter(longonkeloHistoricoItem => {
        const longonkeloHistoricoIdentifier = this.getLongonkeloHistoricoIdentifier(longonkeloHistoricoItem);
        if (longonkeloHistoricoCollectionIdentifiers.includes(longonkeloHistoricoIdentifier)) {
          return false;
        }
        longonkeloHistoricoCollectionIdentifiers.push(longonkeloHistoricoIdentifier);
        return true;
      });
      return [...longonkeloHistoricosToAdd, ...longonkeloHistoricoCollection];
    }
    return longonkeloHistoricoCollection;
  }

  protected convertDateFromClient<T extends ILongonkeloHistorico | NewLongonkeloHistorico | PartialUpdateLongonkeloHistorico>(
    longonkeloHistorico: T
  ): RestOf<T> {
    return {
      ...longonkeloHistorico,
      timestamp: longonkeloHistorico.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restLongonkeloHistorico: RestLongonkeloHistorico): ILongonkeloHistorico {
    return {
      ...restLongonkeloHistorico,
      timestamp: restLongonkeloHistorico.timestamp ? dayjs(restLongonkeloHistorico.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestLongonkeloHistorico>): HttpResponse<ILongonkeloHistorico> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestLongonkeloHistorico[]>): HttpResponse<ILongonkeloHistorico[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
