import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoDiscente, NewAnexoDiscente } from '../anexo-discente.model';

export type PartialUpdateAnexoDiscente = Partial<IAnexoDiscente> & Pick<IAnexoDiscente, 'id'>;

type RestOf<T extends IAnexoDiscente | NewAnexoDiscente> = Omit<T, 'validade' | 'timestamp'> & {
  validade?: string | null;
  timestamp?: string | null;
};

export type RestAnexoDiscente = RestOf<IAnexoDiscente>;

export type NewRestAnexoDiscente = RestOf<NewAnexoDiscente>;

export type PartialUpdateRestAnexoDiscente = RestOf<PartialUpdateAnexoDiscente>;

export type EntityResponseType = HttpResponse<IAnexoDiscente>;
export type EntityArrayResponseType = HttpResponse<IAnexoDiscente[]>;

@Injectable({ providedIn: 'root' })
export class AnexoDiscenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-discentes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(anexoDiscente: NewAnexoDiscente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoDiscente);
    return this.http
      .post<RestAnexoDiscente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(anexoDiscente: IAnexoDiscente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoDiscente);
    return this.http
      .put<RestAnexoDiscente>(`${this.resourceUrl}/${this.getAnexoDiscenteIdentifier(anexoDiscente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(anexoDiscente: PartialUpdateAnexoDiscente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoDiscente);
    return this.http
      .patch<RestAnexoDiscente>(`${this.resourceUrl}/${this.getAnexoDiscenteIdentifier(anexoDiscente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAnexoDiscente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAnexoDiscente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoDiscenteIdentifier(anexoDiscente: Pick<IAnexoDiscente, 'id'>): number {
    return anexoDiscente.id;
  }

  compareAnexoDiscente(o1: Pick<IAnexoDiscente, 'id'> | null, o2: Pick<IAnexoDiscente, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnexoDiscenteIdentifier(o1) === this.getAnexoDiscenteIdentifier(o2) : o1 === o2;
  }

  addAnexoDiscenteToCollectionIfMissing<Type extends Pick<IAnexoDiscente, 'id'>>(
    anexoDiscenteCollection: Type[],
    ...anexoDiscentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoDiscentes: Type[] = anexoDiscentesToCheck.filter(isPresent);
    if (anexoDiscentes.length > 0) {
      const anexoDiscenteCollectionIdentifiers = anexoDiscenteCollection.map(
        anexoDiscenteItem => this.getAnexoDiscenteIdentifier(anexoDiscenteItem)!
      );
      const anexoDiscentesToAdd = anexoDiscentes.filter(anexoDiscenteItem => {
        const anexoDiscenteIdentifier = this.getAnexoDiscenteIdentifier(anexoDiscenteItem);
        if (anexoDiscenteCollectionIdentifiers.includes(anexoDiscenteIdentifier)) {
          return false;
        }
        anexoDiscenteCollectionIdentifiers.push(anexoDiscenteIdentifier);
        return true;
      });
      return [...anexoDiscentesToAdd, ...anexoDiscenteCollection];
    }
    return anexoDiscenteCollection;
  }

  protected convertDateFromClient<T extends IAnexoDiscente | NewAnexoDiscente | PartialUpdateAnexoDiscente>(anexoDiscente: T): RestOf<T> {
    return {
      ...anexoDiscente,
      validade: anexoDiscente.validade?.format(DATE_FORMAT) ?? null,
      timestamp: anexoDiscente.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAnexoDiscente: RestAnexoDiscente): IAnexoDiscente {
    return {
      ...restAnexoDiscente,
      validade: restAnexoDiscente.validade ? dayjs(restAnexoDiscente.validade) : undefined,
      timestamp: restAnexoDiscente.timestamp ? dayjs(restAnexoDiscente.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAnexoDiscente>): HttpResponse<IAnexoDiscente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAnexoDiscente[]>): HttpResponse<IAnexoDiscente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
