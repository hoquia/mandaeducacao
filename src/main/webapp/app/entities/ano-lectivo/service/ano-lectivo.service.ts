import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnoLectivo, NewAnoLectivo } from '../ano-lectivo.model';

export type PartialUpdateAnoLectivo = Partial<IAnoLectivo> & Pick<IAnoLectivo, 'id'>;

type RestOf<T extends IAnoLectivo | NewAnoLectivo> = Omit<T, 'inicio' | 'fim' | 'timestam'> & {
  inicio?: string | null;
  fim?: string | null;
  timestam?: string | null;
};

export type RestAnoLectivo = RestOf<IAnoLectivo>;

export type NewRestAnoLectivo = RestOf<NewAnoLectivo>;

export type PartialUpdateRestAnoLectivo = RestOf<PartialUpdateAnoLectivo>;

export type EntityResponseType = HttpResponse<IAnoLectivo>;
export type EntityArrayResponseType = HttpResponse<IAnoLectivo[]>;

@Injectable({ providedIn: 'root' })
export class AnoLectivoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ano-lectivos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(anoLectivo: NewAnoLectivo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anoLectivo);
    return this.http
      .post<RestAnoLectivo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(anoLectivo: IAnoLectivo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anoLectivo);
    return this.http
      .put<RestAnoLectivo>(`${this.resourceUrl}/${this.getAnoLectivoIdentifier(anoLectivo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(anoLectivo: PartialUpdateAnoLectivo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anoLectivo);
    return this.http
      .patch<RestAnoLectivo>(`${this.resourceUrl}/${this.getAnoLectivoIdentifier(anoLectivo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAnoLectivo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAnoLectivo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnoLectivoIdentifier(anoLectivo: Pick<IAnoLectivo, 'id'>): number {
    return anoLectivo.id;
  }

  compareAnoLectivo(o1: Pick<IAnoLectivo, 'id'> | null, o2: Pick<IAnoLectivo, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnoLectivoIdentifier(o1) === this.getAnoLectivoIdentifier(o2) : o1 === o2;
  }

  addAnoLectivoToCollectionIfMissing<Type extends Pick<IAnoLectivo, 'id'>>(
    anoLectivoCollection: Type[],
    ...anoLectivosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anoLectivos: Type[] = anoLectivosToCheck.filter(isPresent);
    if (anoLectivos.length > 0) {
      const anoLectivoCollectionIdentifiers = anoLectivoCollection.map(anoLectivoItem => this.getAnoLectivoIdentifier(anoLectivoItem)!);
      const anoLectivosToAdd = anoLectivos.filter(anoLectivoItem => {
        const anoLectivoIdentifier = this.getAnoLectivoIdentifier(anoLectivoItem);
        if (anoLectivoCollectionIdentifiers.includes(anoLectivoIdentifier)) {
          return false;
        }
        anoLectivoCollectionIdentifiers.push(anoLectivoIdentifier);
        return true;
      });
      return [...anoLectivosToAdd, ...anoLectivoCollection];
    }
    return anoLectivoCollection;
  }

  protected convertDateFromClient<T extends IAnoLectivo | NewAnoLectivo | PartialUpdateAnoLectivo>(anoLectivo: T): RestOf<T> {
    return {
      ...anoLectivo,
      inicio: anoLectivo.inicio?.format(DATE_FORMAT) ?? null,
      fim: anoLectivo.fim?.format(DATE_FORMAT) ?? null,
      timestam: anoLectivo.timestam?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAnoLectivo: RestAnoLectivo): IAnoLectivo {
    return {
      ...restAnoLectivo,
      inicio: restAnoLectivo.inicio ? dayjs(restAnoLectivo.inicio) : undefined,
      fim: restAnoLectivo.fim ? dayjs(restAnoLectivo.fim) : undefined,
      timestam: restAnoLectivo.timestam ? dayjs(restAnoLectivo.timestam) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAnoLectivo>): HttpResponse<IAnoLectivo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAnoLectivo[]>): HttpResponse<IAnoLectivo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
