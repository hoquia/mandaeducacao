import { IDocente } from './../../docente/docente.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDiscente, NewDiscente } from '../discente.model';

export type PartialUpdateDiscente = Partial<IDiscente> & Pick<IDiscente, 'id'>;

type RestOf<T extends IDiscente | NewDiscente> = Omit<T, 'nascimento' | 'documentoEmissao' | 'documentoValidade' | 'dataIngresso'> & {
  nascimento?: string | null;
  documentoEmissao?: string | null;
  documentoValidade?: string | null;
  dataIngresso?: string | null;
};

export type RestDiscente = RestOf<IDiscente>;

export type NewRestDiscente = RestOf<NewDiscente>;

export type PartialUpdateRestDiscente = RestOf<PartialUpdateDiscente>;

export type EntityResponseType = HttpResponse<IDiscente>;
export type EntityArrayResponseType = HttpResponse<IDiscente[]>;

@Injectable({ providedIn: 'root' })
export class DiscenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/discentes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(discente: NewDiscente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(discente);
    return this.http
      .post<RestDiscente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(discente: IDiscente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(discente);
    return this.http
      .put<RestDiscente>(`${this.resourceUrl}/${this.getDiscenteIdentifier(discente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(discente: PartialUpdateDiscente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(discente);
    return this.http
      .patch<RestDiscente>(`${this.resourceUrl}/${this.getDiscenteIdentifier(discente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDiscente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDiscente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDiscenteIdentifier(discente: Pick<IDiscente, 'id'>): number {
    return discente.id;
  }

  compareDiscente(o1: Pick<IDiscente, 'id'> | null, o2: Pick<IDiscente, 'id'> | null): boolean {
    return o1 && o2 ? this.getDiscenteIdentifier(o1) === this.getDiscenteIdentifier(o2) : o1 === o2;
  }

  addDiscenteToCollectionIfMissing<Type extends Pick<IDiscente, 'id'>>(
    discenteCollection: Type[],
    ...discentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const discentes: Type[] = discentesToCheck.filter(isPresent);
    if (discentes.length > 0) {
      const discenteCollectionIdentifiers = discenteCollection.map(discenteItem => this.getDiscenteIdentifier(discenteItem)!);
      const discentesToAdd = discentes.filter(discenteItem => {
        const discenteIdentifier = this.getDiscenteIdentifier(discenteItem);
        if (discenteCollectionIdentifiers.includes(discenteIdentifier)) {
          return false;
        }
        discenteCollectionIdentifiers.push(discenteIdentifier);
        return true;
      });
      return [...discentesToAdd, ...discenteCollection];
    }
    return discenteCollection;
  }

  protected convertDateFromClient<T extends IDiscente | NewDiscente | PartialUpdateDiscente>(discente: T): RestOf<T> {
    return {
      ...discente,
      nascimento: discente.nascimento?.format(DATE_FORMAT) ?? null,
      documentoEmissao: discente.documentoEmissao?.format(DATE_FORMAT) ?? null,
      documentoValidade: discente.documentoValidade?.format(DATE_FORMAT) ?? null,
      dataIngresso: discente.dataIngresso?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDiscente: RestDiscente): IDiscente {
    return {
      ...restDiscente,
      nascimento: restDiscente.nascimento ? dayjs(restDiscente.nascimento) : undefined,
      documentoEmissao: restDiscente.documentoEmissao ? dayjs(restDiscente.documentoEmissao) : undefined,
      documentoValidade: restDiscente.documentoValidade ? dayjs(restDiscente.documentoValidade) : undefined,
      dataIngresso: restDiscente.dataIngresso ? dayjs(restDiscente.dataIngresso) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDiscente>): HttpResponse<IDiscente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDiscente[]>): HttpResponse<IDiscente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  getDiscentes(): Observable<IDocente[]> {
    return this.http.get<IDocente[]>(`${this.resourceUrl}`);
  }
}
