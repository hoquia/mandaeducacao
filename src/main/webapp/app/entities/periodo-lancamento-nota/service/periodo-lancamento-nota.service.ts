import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPeriodoLancamentoNota, NewPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';

export type PartialUpdatePeriodoLancamentoNota = Partial<IPeriodoLancamentoNota> & Pick<IPeriodoLancamentoNota, 'id'>;

type RestOf<T extends IPeriodoLancamentoNota | NewPeriodoLancamentoNota> = Omit<T, 'de' | 'ate' | 'timestamp'> & {
  de?: string | null;
  ate?: string | null;
  timestamp?: string | null;
};

export type RestPeriodoLancamentoNota = RestOf<IPeriodoLancamentoNota>;

export type NewRestPeriodoLancamentoNota = RestOf<NewPeriodoLancamentoNota>;

export type PartialUpdateRestPeriodoLancamentoNota = RestOf<PartialUpdatePeriodoLancamentoNota>;

export type EntityResponseType = HttpResponse<IPeriodoLancamentoNota>;
export type EntityArrayResponseType = HttpResponse<IPeriodoLancamentoNota[]>;

@Injectable({ providedIn: 'root' })
export class PeriodoLancamentoNotaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/periodo-lancamento-notas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(periodoLancamentoNota: NewPeriodoLancamentoNota): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodoLancamentoNota);
    return this.http
      .post<RestPeriodoLancamentoNota>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(periodoLancamentoNota: IPeriodoLancamentoNota): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodoLancamentoNota);
    return this.http
      .put<RestPeriodoLancamentoNota>(`${this.resourceUrl}/${this.getPeriodoLancamentoNotaIdentifier(periodoLancamentoNota)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(periodoLancamentoNota: PartialUpdatePeriodoLancamentoNota): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodoLancamentoNota);
    return this.http
      .patch<RestPeriodoLancamentoNota>(`${this.resourceUrl}/${this.getPeriodoLancamentoNotaIdentifier(periodoLancamentoNota)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPeriodoLancamentoNota>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPeriodoLancamentoNota[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPeriodoLancamentoNotaIdentifier(periodoLancamentoNota: Pick<IPeriodoLancamentoNota, 'id'>): number {
    return periodoLancamentoNota.id;
  }

  comparePeriodoLancamentoNota(o1: Pick<IPeriodoLancamentoNota, 'id'> | null, o2: Pick<IPeriodoLancamentoNota, 'id'> | null): boolean {
    return o1 && o2 ? this.getPeriodoLancamentoNotaIdentifier(o1) === this.getPeriodoLancamentoNotaIdentifier(o2) : o1 === o2;
  }

  addPeriodoLancamentoNotaToCollectionIfMissing<Type extends Pick<IPeriodoLancamentoNota, 'id'>>(
    periodoLancamentoNotaCollection: Type[],
    ...periodoLancamentoNotasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const periodoLancamentoNotas: Type[] = periodoLancamentoNotasToCheck.filter(isPresent);
    if (periodoLancamentoNotas.length > 0) {
      const periodoLancamentoNotaCollectionIdentifiers = periodoLancamentoNotaCollection.map(
        periodoLancamentoNotaItem => this.getPeriodoLancamentoNotaIdentifier(periodoLancamentoNotaItem)!
      );
      const periodoLancamentoNotasToAdd = periodoLancamentoNotas.filter(periodoLancamentoNotaItem => {
        const periodoLancamentoNotaIdentifier = this.getPeriodoLancamentoNotaIdentifier(periodoLancamentoNotaItem);
        if (periodoLancamentoNotaCollectionIdentifiers.includes(periodoLancamentoNotaIdentifier)) {
          return false;
        }
        periodoLancamentoNotaCollectionIdentifiers.push(periodoLancamentoNotaIdentifier);
        return true;
      });
      return [...periodoLancamentoNotasToAdd, ...periodoLancamentoNotaCollection];
    }
    return periodoLancamentoNotaCollection;
  }

  protected convertDateFromClient<T extends IPeriodoLancamentoNota | NewPeriodoLancamentoNota | PartialUpdatePeriodoLancamentoNota>(
    periodoLancamentoNota: T
  ): RestOf<T> {
    return {
      ...periodoLancamentoNota,
      de: periodoLancamentoNota.de?.toJSON() ?? null,
      ate: periodoLancamentoNota.ate?.toJSON() ?? null,
      timestamp: periodoLancamentoNota.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPeriodoLancamentoNota: RestPeriodoLancamentoNota): IPeriodoLancamentoNota {
    return {
      ...restPeriodoLancamentoNota,
      de: restPeriodoLancamentoNota.de ? dayjs(restPeriodoLancamentoNota.de) : undefined,
      ate: restPeriodoLancamentoNota.ate ? dayjs(restPeriodoLancamentoNota.ate) : undefined,
      timestamp: restPeriodoLancamentoNota.timestamp ? dayjs(restPeriodoLancamentoNota.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPeriodoLancamentoNota>): HttpResponse<IPeriodoLancamentoNota> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPeriodoLancamentoNota[]>): HttpResponse<IPeriodoLancamentoNota[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
