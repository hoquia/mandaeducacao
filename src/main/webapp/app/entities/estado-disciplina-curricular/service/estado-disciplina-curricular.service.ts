import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstadoDisciplinaCurricular, NewEstadoDisciplinaCurricular } from '../estado-disciplina-curricular.model';

export type PartialUpdateEstadoDisciplinaCurricular = Partial<IEstadoDisciplinaCurricular> & Pick<IEstadoDisciplinaCurricular, 'id'>;

export type EntityResponseType = HttpResponse<IEstadoDisciplinaCurricular>;
export type EntityArrayResponseType = HttpResponse<IEstadoDisciplinaCurricular[]>;

@Injectable({ providedIn: 'root' })
export class EstadoDisciplinaCurricularService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estado-disciplina-curriculars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(estadoDisciplinaCurricular: NewEstadoDisciplinaCurricular): Observable<EntityResponseType> {
    return this.http.post<IEstadoDisciplinaCurricular>(this.resourceUrl, estadoDisciplinaCurricular, { observe: 'response' });
  }

  update(estadoDisciplinaCurricular: IEstadoDisciplinaCurricular): Observable<EntityResponseType> {
    return this.http.put<IEstadoDisciplinaCurricular>(
      `${this.resourceUrl}/${this.getEstadoDisciplinaCurricularIdentifier(estadoDisciplinaCurricular)}`,
      estadoDisciplinaCurricular,
      { observe: 'response' }
    );
  }

  partialUpdate(estadoDisciplinaCurricular: PartialUpdateEstadoDisciplinaCurricular): Observable<EntityResponseType> {
    return this.http.patch<IEstadoDisciplinaCurricular>(
      `${this.resourceUrl}/${this.getEstadoDisciplinaCurricularIdentifier(estadoDisciplinaCurricular)}`,
      estadoDisciplinaCurricular,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoDisciplinaCurricular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoDisciplinaCurricular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEstadoDisciplinaCurricularIdentifier(estadoDisciplinaCurricular: Pick<IEstadoDisciplinaCurricular, 'id'>): number {
    return estadoDisciplinaCurricular.id;
  }

  compareEstadoDisciplinaCurricular(
    o1: Pick<IEstadoDisciplinaCurricular, 'id'> | null,
    o2: Pick<IEstadoDisciplinaCurricular, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getEstadoDisciplinaCurricularIdentifier(o1) === this.getEstadoDisciplinaCurricularIdentifier(o2) : o1 === o2;
  }

  addEstadoDisciplinaCurricularToCollectionIfMissing<Type extends Pick<IEstadoDisciplinaCurricular, 'id'>>(
    estadoDisciplinaCurricularCollection: Type[],
    ...estadoDisciplinaCurricularsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const estadoDisciplinaCurriculars: Type[] = estadoDisciplinaCurricularsToCheck.filter(isPresent);
    if (estadoDisciplinaCurriculars.length > 0) {
      const estadoDisciplinaCurricularCollectionIdentifiers = estadoDisciplinaCurricularCollection.map(
        estadoDisciplinaCurricularItem => this.getEstadoDisciplinaCurricularIdentifier(estadoDisciplinaCurricularItem)!
      );
      const estadoDisciplinaCurricularsToAdd = estadoDisciplinaCurriculars.filter(estadoDisciplinaCurricularItem => {
        const estadoDisciplinaCurricularIdentifier = this.getEstadoDisciplinaCurricularIdentifier(estadoDisciplinaCurricularItem);
        if (estadoDisciplinaCurricularCollectionIdentifiers.includes(estadoDisciplinaCurricularIdentifier)) {
          return false;
        }
        estadoDisciplinaCurricularCollectionIdentifiers.push(estadoDisciplinaCurricularIdentifier);
        return true;
      });
      return [...estadoDisciplinaCurricularsToAdd, ...estadoDisciplinaCurricularCollection];
    }
    return estadoDisciplinaCurricularCollection;
  }
}
