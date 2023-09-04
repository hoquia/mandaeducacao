import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormacaoDocente, NewFormacaoDocente } from '../formacao-docente.model';

export type PartialUpdateFormacaoDocente = Partial<IFormacaoDocente> & Pick<IFormacaoDocente, 'id'>;

type RestOf<T extends IFormacaoDocente | NewFormacaoDocente> = Omit<T, 'inicio' | 'fim'> & {
  inicio?: string | null;
  fim?: string | null;
};

export type RestFormacaoDocente = RestOf<IFormacaoDocente>;

export type NewRestFormacaoDocente = RestOf<NewFormacaoDocente>;

export type PartialUpdateRestFormacaoDocente = RestOf<PartialUpdateFormacaoDocente>;

export type EntityResponseType = HttpResponse<IFormacaoDocente>;
export type EntityArrayResponseType = HttpResponse<IFormacaoDocente[]>;

@Injectable({ providedIn: 'root' })
export class FormacaoDocenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/formacao-docentes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(formacaoDocente: NewFormacaoDocente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formacaoDocente);
    return this.http
      .post<RestFormacaoDocente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(formacaoDocente: IFormacaoDocente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formacaoDocente);
    return this.http
      .put<RestFormacaoDocente>(`${this.resourceUrl}/${this.getFormacaoDocenteIdentifier(formacaoDocente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(formacaoDocente: PartialUpdateFormacaoDocente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(formacaoDocente);
    return this.http
      .patch<RestFormacaoDocente>(`${this.resourceUrl}/${this.getFormacaoDocenteIdentifier(formacaoDocente)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFormacaoDocente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFormacaoDocente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFormacaoDocenteIdentifier(formacaoDocente: Pick<IFormacaoDocente, 'id'>): number {
    return formacaoDocente.id;
  }

  compareFormacaoDocente(o1: Pick<IFormacaoDocente, 'id'> | null, o2: Pick<IFormacaoDocente, 'id'> | null): boolean {
    return o1 && o2 ? this.getFormacaoDocenteIdentifier(o1) === this.getFormacaoDocenteIdentifier(o2) : o1 === o2;
  }

  addFormacaoDocenteToCollectionIfMissing<Type extends Pick<IFormacaoDocente, 'id'>>(
    formacaoDocenteCollection: Type[],
    ...formacaoDocentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const formacaoDocentes: Type[] = formacaoDocentesToCheck.filter(isPresent);
    if (formacaoDocentes.length > 0) {
      const formacaoDocenteCollectionIdentifiers = formacaoDocenteCollection.map(
        formacaoDocenteItem => this.getFormacaoDocenteIdentifier(formacaoDocenteItem)!
      );
      const formacaoDocentesToAdd = formacaoDocentes.filter(formacaoDocenteItem => {
        const formacaoDocenteIdentifier = this.getFormacaoDocenteIdentifier(formacaoDocenteItem);
        if (formacaoDocenteCollectionIdentifiers.includes(formacaoDocenteIdentifier)) {
          return false;
        }
        formacaoDocenteCollectionIdentifiers.push(formacaoDocenteIdentifier);
        return true;
      });
      return [...formacaoDocentesToAdd, ...formacaoDocenteCollection];
    }
    return formacaoDocenteCollection;
  }

  protected convertDateFromClient<T extends IFormacaoDocente | NewFormacaoDocente | PartialUpdateFormacaoDocente>(
    formacaoDocente: T
  ): RestOf<T> {
    return {
      ...formacaoDocente,
      inicio: formacaoDocente.inicio?.format(DATE_FORMAT) ?? null,
      fim: formacaoDocente.fim?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFormacaoDocente: RestFormacaoDocente): IFormacaoDocente {
    return {
      ...restFormacaoDocente,
      inicio: restFormacaoDocente.inicio ? dayjs(restFormacaoDocente.inicio) : undefined,
      fim: restFormacaoDocente.fim ? dayjs(restFormacaoDocente.fim) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFormacaoDocente>): HttpResponse<IFormacaoDocente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFormacaoDocente[]>): HttpResponse<IFormacaoDocente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
