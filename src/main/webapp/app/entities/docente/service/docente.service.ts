import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocente, NewDocente } from '../docente.model';

export type PartialUpdateDocente = Partial<IDocente> & Pick<IDocente, 'id'>;

type RestOf<T extends IDocente | NewDocente> = Omit<
  T,
  'nascimento' | 'documentoEmissao' | 'documentoValidade' | 'dataInicioFuncoes' | 'timestamp'
> & {
  nascimento?: string | null;
  documentoEmissao?: string | null;
  documentoValidade?: string | null;
  dataInicioFuncoes?: string | null;
  timestamp?: string | null;
};

export type RestDocente = RestOf<IDocente>;

export type NewRestDocente = RestOf<NewDocente>;

export type PartialUpdateRestDocente = RestOf<PartialUpdateDocente>;

export type EntityResponseType = HttpResponse<IDocente>;
export type EntityArrayResponseType = HttpResponse<IDocente[]>;

@Injectable({ providedIn: 'root' })
export class DocenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/docentes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(docente: NewDocente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docente);
    return this.http
      .post<RestDocente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(docente: IDocente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docente);
    return this.http
      .put<RestDocente>(`${this.resourceUrl}/${this.getDocenteIdentifier(docente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(docente: PartialUpdateDocente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docente);
    return this.http
      .patch<RestDocente>(`${this.resourceUrl}/${this.getDocenteIdentifier(docente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDocente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDocente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocenteIdentifier(docente: Pick<IDocente, 'id'>): number {
    return docente.id;
  }

  compareDocente(o1: Pick<IDocente, 'id'> | null, o2: Pick<IDocente, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocenteIdentifier(o1) === this.getDocenteIdentifier(o2) : o1 === o2;
  }

  addDocenteToCollectionIfMissing<Type extends Pick<IDocente, 'id'>>(
    docenteCollection: Type[],
    ...docentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const docentes: Type[] = docentesToCheck.filter(isPresent);
    if (docentes.length > 0) {
      const docenteCollectionIdentifiers = docenteCollection.map(docenteItem => this.getDocenteIdentifier(docenteItem)!);
      const docentesToAdd = docentes.filter(docenteItem => {
        const docenteIdentifier = this.getDocenteIdentifier(docenteItem);
        if (docenteCollectionIdentifiers.includes(docenteIdentifier)) {
          return false;
        }
        docenteCollectionIdentifiers.push(docenteIdentifier);
        return true;
      });
      return [...docentesToAdd, ...docenteCollection];
    }
    return docenteCollection;
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  downloadHorarioDocentePdf(docenteID: number) {
    let headers = new HttpHeaders();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    headers = headers.set('Accept', 'application/pdf');
    return this.http.get(`${this.resourceUrl}/horario-docente/${docenteID}`, { headers, responseType: 'blob' });
  }

  protected convertDateFromClient<T extends IDocente | NewDocente | PartialUpdateDocente>(docente: T): RestOf<T> {
    return {
      ...docente,
      nascimento: docente.nascimento?.format(DATE_FORMAT) ?? null,
      documentoEmissao: docente.documentoEmissao?.format(DATE_FORMAT) ?? null,
      documentoValidade: docente.documentoValidade?.format(DATE_FORMAT) ?? null,
      dataInicioFuncoes: docente.dataInicioFuncoes?.format(DATE_FORMAT) ?? null,
      timestamp: docente.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDocente: RestDocente): IDocente {
    return {
      ...restDocente,
      nascimento: restDocente.nascimento ? dayjs(restDocente.nascimento) : undefined,
      documentoEmissao: restDocente.documentoEmissao ? dayjs(restDocente.documentoEmissao) : undefined,
      documentoValidade: restDocente.documentoValidade ? dayjs(restDocente.documentoValidade) : undefined,
      dataInicioFuncoes: restDocente.dataInicioFuncoes ? dayjs(restDocente.dataInicioFuncoes) : undefined,
      timestamp: restDocente.timestamp ? dayjs(restDocente.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDocente>): HttpResponse<IDocente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDocente[]>): HttpResponse<IDocente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
