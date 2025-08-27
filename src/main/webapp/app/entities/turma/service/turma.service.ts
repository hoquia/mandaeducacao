/* eslint-disable @typescript-eslint/member-ordering */
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITurma, NewTurma } from '../turma.model';

export type PartialUpdateTurma = Partial<ITurma> & Pick<ITurma, 'id'>;

type RestOf<T extends ITurma | NewTurma> = Omit<T, 'abertura' | 'encerramento'> & {
  abertura?: string | null;
  encerramento?: string | null;
};

export type RestTurma = RestOf<ITurma>;

export type NewRestTurma = RestOf<NewTurma>;

export type PartialUpdateRestTurma = RestOf<PartialUpdateTurma>;

export type EntityResponseType = HttpResponse<ITurma>;
export type EntityArrayResponseType = HttpResponse<ITurma[]>;

@Injectable({ providedIn: 'root' })
export class TurmaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/turmas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(turma: NewTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(turma);
    return this.http.post<RestTurma>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(turma: ITurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(turma);
    return this.http
      .put<RestTurma>(`${this.resourceUrl}/${this.getTurmaIdentifier(turma)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(turma: PartialUpdateTurma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(turma);
    return this.http
      .patch<RestTurma>(`${this.resourceUrl}/${this.getTurmaIdentifier(turma)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTurma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTurma[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTurmaIdentifier(turma: Pick<ITurma, 'id'>): number {
    return turma.id;
  }

  compareTurma(o1: Pick<ITurma, 'id'> | null, o2: Pick<ITurma, 'id'> | null): boolean {
    return o1 && o2 ? this.getTurmaIdentifier(o1) === this.getTurmaIdentifier(o2) : o1 === o2;
  }

  addTurmaToCollectionIfMissing<Type extends Pick<ITurma, 'id'>>(
    turmaCollection: Type[],
    ...turmasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const turmas: Type[] = turmasToCheck.filter(isPresent);
    if (turmas.length > 0) {
      const turmaCollectionIdentifiers = turmaCollection.map(turmaItem => this.getTurmaIdentifier(turmaItem)!);
      const turmasToAdd = turmas.filter(turmaItem => {
        const turmaIdentifier = this.getTurmaIdentifier(turmaItem);
        if (turmaCollectionIdentifiers.includes(turmaIdentifier)) {
          return false;
        }
        turmaCollectionIdentifiers.push(turmaIdentifier);
        return true;
      });
      return [...turmasToAdd, ...turmaCollection];
    }
    return turmaCollection;
  }

  protected convertDateFromClient<T extends ITurma | NewTurma | PartialUpdateTurma>(turma: T): RestOf<T> {
    return {
      ...turma,
      abertura: turma.abertura?.format(DATE_FORMAT) ?? null,
      encerramento: turma.encerramento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTurma: RestTurma): ITurma {
    return {
      ...restTurma,
      abertura: restTurma.abertura ? dayjs(restTurma.abertura) : undefined,
      encerramento: restTurma.encerramento ? dayjs(restTurma.encerramento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTurma>): HttpResponse<ITurma> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTurma[]>): HttpResponse<ITurma[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type, @typescript-eslint/member-ordering
  downloadMinipautaPdf(turmaID: number, periodoID: number) {
    let headers = new HttpHeaders();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    headers = headers.set('Accept', 'application/pdf');
    return this.http.get(`${this.resourceUrl}/mini-pauta/${turmaID}/${periodoID}`, { headers, responseType: 'blob' });
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  downloadListaPresencaPdf(turmaID: number) {
    let headers = new HttpHeaders();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    headers = headers.set('Accept', 'application/pdf');
    return this.http.get(`${this.resourceUrl}/lista-presenca/${turmaID}`, { headers, responseType: 'blob' });
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  downloadHorarioDiscentePdf(turmaID: number) {
    let headers = new HttpHeaders();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    headers = headers.set('Accept', 'application/pdf');
    return this.http.get(`${this.resourceUrl}/horario-discente/${turmaID}`, { headers, responseType: 'blob' });
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  downloadListaPagoNaoPagoPdf(turmaID: number, emolumentoSelecionadoID: number) {
    let headers = new HttpHeaders();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    headers = headers.set('Accept', 'application/pdf');
    return this.http.get(`${this.resourceUrl}/lista-pago-nao-pago/${turmaID}/${emolumentoSelecionadoID}`, {
      headers,
      responseType: 'blob',
    });
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  downloadEstratoFinanceiro(turmaID: number, emolumentoSelecionadoEstrato: number) {
    let headers = new HttpHeaders();
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    headers = headers.set('Accept', 'application/pdf');
    return this.http.get(`${this.resourceUrl}/estrato-financeiro/${turmaID}/${emolumentoSelecionadoEstrato}`, {
      headers,
      responseType: 'blob',
    });
  }

  getTurmas(): Observable<ITurma[]> {
    return this.http.get<ITurma[]>(`${this.resourceUrl}`);
  }
}
