import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResponsavelDisciplina, NewResponsavelDisciplina } from '../responsavel-disciplina.model';

export type PartialUpdateResponsavelDisciplina = Partial<IResponsavelDisciplina> & Pick<IResponsavelDisciplina, 'id'>;

type RestOf<T extends IResponsavelDisciplina | NewResponsavelDisciplina> = Omit<T, 'de' | 'ate' | 'timestamp'> & {
  de?: string | null;
  ate?: string | null;
  timestamp?: string | null;
};

export type RestResponsavelDisciplina = RestOf<IResponsavelDisciplina>;

export type NewRestResponsavelDisciplina = RestOf<NewResponsavelDisciplina>;

export type PartialUpdateRestResponsavelDisciplina = RestOf<PartialUpdateResponsavelDisciplina>;

export type EntityResponseType = HttpResponse<IResponsavelDisciplina>;
export type EntityArrayResponseType = HttpResponse<IResponsavelDisciplina[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelDisciplinaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/responsavel-disciplinas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(responsavelDisciplina: NewResponsavelDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelDisciplina);
    return this.http
      .post<RestResponsavelDisciplina>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(responsavelDisciplina: IResponsavelDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelDisciplina);
    return this.http
      .put<RestResponsavelDisciplina>(`${this.resourceUrl}/${this.getResponsavelDisciplinaIdentifier(responsavelDisciplina)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(responsavelDisciplina: PartialUpdateResponsavelDisciplina): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(responsavelDisciplina);
    return this.http
      .patch<RestResponsavelDisciplina>(`${this.resourceUrl}/${this.getResponsavelDisciplinaIdentifier(responsavelDisciplina)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestResponsavelDisciplina>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResponsavelDisciplina[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResponsavelDisciplinaIdentifier(responsavelDisciplina: Pick<IResponsavelDisciplina, 'id'>): number {
    return responsavelDisciplina.id;
  }

  compareResponsavelDisciplina(o1: Pick<IResponsavelDisciplina, 'id'> | null, o2: Pick<IResponsavelDisciplina, 'id'> | null): boolean {
    return o1 && o2 ? this.getResponsavelDisciplinaIdentifier(o1) === this.getResponsavelDisciplinaIdentifier(o2) : o1 === o2;
  }

  addResponsavelDisciplinaToCollectionIfMissing<Type extends Pick<IResponsavelDisciplina, 'id'>>(
    responsavelDisciplinaCollection: Type[],
    ...responsavelDisciplinasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const responsavelDisciplinas: Type[] = responsavelDisciplinasToCheck.filter(isPresent);
    if (responsavelDisciplinas.length > 0) {
      const responsavelDisciplinaCollectionIdentifiers = responsavelDisciplinaCollection.map(
        responsavelDisciplinaItem => this.getResponsavelDisciplinaIdentifier(responsavelDisciplinaItem)!
      );
      const responsavelDisciplinasToAdd = responsavelDisciplinas.filter(responsavelDisciplinaItem => {
        const responsavelDisciplinaIdentifier = this.getResponsavelDisciplinaIdentifier(responsavelDisciplinaItem);
        if (responsavelDisciplinaCollectionIdentifiers.includes(responsavelDisciplinaIdentifier)) {
          return false;
        }
        responsavelDisciplinaCollectionIdentifiers.push(responsavelDisciplinaIdentifier);
        return true;
      });
      return [...responsavelDisciplinasToAdd, ...responsavelDisciplinaCollection];
    }
    return responsavelDisciplinaCollection;
  }

  protected convertDateFromClient<T extends IResponsavelDisciplina | NewResponsavelDisciplina | PartialUpdateResponsavelDisciplina>(
    responsavelDisciplina: T
  ): RestOf<T> {
    return {
      ...responsavelDisciplina,
      de: responsavelDisciplina.de?.format(DATE_FORMAT) ?? null,
      ate: responsavelDisciplina.ate?.format(DATE_FORMAT) ?? null,
      timestamp: responsavelDisciplina.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restResponsavelDisciplina: RestResponsavelDisciplina): IResponsavelDisciplina {
    return {
      ...restResponsavelDisciplina,
      de: restResponsavelDisciplina.de ? dayjs(restResponsavelDisciplina.de) : undefined,
      ate: restResponsavelDisciplina.ate ? dayjs(restResponsavelDisciplina.ate) : undefined,
      timestamp: restResponsavelDisciplina.timestamp ? dayjs(restResponsavelDisciplina.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResponsavelDisciplina>): HttpResponse<IResponsavelDisciplina> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResponsavelDisciplina[]>): HttpResponse<IResponsavelDisciplina[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
