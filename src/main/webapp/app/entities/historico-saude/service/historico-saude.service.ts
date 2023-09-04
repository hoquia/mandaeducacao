import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHistoricoSaude, NewHistoricoSaude } from '../historico-saude.model';

export type PartialUpdateHistoricoSaude = Partial<IHistoricoSaude> & Pick<IHistoricoSaude, 'id'>;

type RestOf<T extends IHistoricoSaude | NewHistoricoSaude> = Omit<T, 'inicio' | 'fim' | 'timestamp'> & {
  inicio?: string | null;
  fim?: string | null;
  timestamp?: string | null;
};

export type RestHistoricoSaude = RestOf<IHistoricoSaude>;

export type NewRestHistoricoSaude = RestOf<NewHistoricoSaude>;

export type PartialUpdateRestHistoricoSaude = RestOf<PartialUpdateHistoricoSaude>;

export type EntityResponseType = HttpResponse<IHistoricoSaude>;
export type EntityArrayResponseType = HttpResponse<IHistoricoSaude[]>;

@Injectable({ providedIn: 'root' })
export class HistoricoSaudeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/historico-saudes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(historicoSaude: NewHistoricoSaude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicoSaude);
    return this.http
      .post<RestHistoricoSaude>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(historicoSaude: IHistoricoSaude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicoSaude);
    return this.http
      .put<RestHistoricoSaude>(`${this.resourceUrl}/${this.getHistoricoSaudeIdentifier(historicoSaude)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(historicoSaude: PartialUpdateHistoricoSaude): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historicoSaude);
    return this.http
      .patch<RestHistoricoSaude>(`${this.resourceUrl}/${this.getHistoricoSaudeIdentifier(historicoSaude)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestHistoricoSaude>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestHistoricoSaude[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHistoricoSaudeIdentifier(historicoSaude: Pick<IHistoricoSaude, 'id'>): number {
    return historicoSaude.id;
  }

  compareHistoricoSaude(o1: Pick<IHistoricoSaude, 'id'> | null, o2: Pick<IHistoricoSaude, 'id'> | null): boolean {
    return o1 && o2 ? this.getHistoricoSaudeIdentifier(o1) === this.getHistoricoSaudeIdentifier(o2) : o1 === o2;
  }

  addHistoricoSaudeToCollectionIfMissing<Type extends Pick<IHistoricoSaude, 'id'>>(
    historicoSaudeCollection: Type[],
    ...historicoSaudesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const historicoSaudes: Type[] = historicoSaudesToCheck.filter(isPresent);
    if (historicoSaudes.length > 0) {
      const historicoSaudeCollectionIdentifiers = historicoSaudeCollection.map(
        historicoSaudeItem => this.getHistoricoSaudeIdentifier(historicoSaudeItem)!
      );
      const historicoSaudesToAdd = historicoSaudes.filter(historicoSaudeItem => {
        const historicoSaudeIdentifier = this.getHistoricoSaudeIdentifier(historicoSaudeItem);
        if (historicoSaudeCollectionIdentifiers.includes(historicoSaudeIdentifier)) {
          return false;
        }
        historicoSaudeCollectionIdentifiers.push(historicoSaudeIdentifier);
        return true;
      });
      return [...historicoSaudesToAdd, ...historicoSaudeCollection];
    }
    return historicoSaudeCollection;
  }

  protected convertDateFromClient<T extends IHistoricoSaude | NewHistoricoSaude | PartialUpdateHistoricoSaude>(
    historicoSaude: T
  ): RestOf<T> {
    return {
      ...historicoSaude,
      inicio: historicoSaude.inicio?.toJSON() ?? null,
      fim: historicoSaude.fim?.toJSON() ?? null,
      timestamp: historicoSaude.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restHistoricoSaude: RestHistoricoSaude): IHistoricoSaude {
    return {
      ...restHistoricoSaude,
      inicio: restHistoricoSaude.inicio ? dayjs(restHistoricoSaude.inicio) : undefined,
      fim: restHistoricoSaude.fim ? dayjs(restHistoricoSaude.fim) : undefined,
      timestamp: restHistoricoSaude.timestamp ? dayjs(restHistoricoSaude.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestHistoricoSaude>): HttpResponse<IHistoricoSaude> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestHistoricoSaude[]>): HttpResponse<IHistoricoSaude[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
