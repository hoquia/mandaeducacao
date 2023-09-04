import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDisciplinaCurricular, NewDisciplinaCurricular } from '../disciplina-curricular.model';

export type PartialUpdateDisciplinaCurricular = Partial<IDisciplinaCurricular> & Pick<IDisciplinaCurricular, 'id'>;

export type EntityResponseType = HttpResponse<IDisciplinaCurricular>;
export type EntityArrayResponseType = HttpResponse<IDisciplinaCurricular[]>;

@Injectable({ providedIn: 'root' })
export class DisciplinaCurricularService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/disciplina-curriculars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(disciplinaCurricular: NewDisciplinaCurricular): Observable<EntityResponseType> {
    return this.http.post<IDisciplinaCurricular>(this.resourceUrl, disciplinaCurricular, { observe: 'response' });
  }

  update(disciplinaCurricular: IDisciplinaCurricular): Observable<EntityResponseType> {
    return this.http.put<IDisciplinaCurricular>(
      `${this.resourceUrl}/${this.getDisciplinaCurricularIdentifier(disciplinaCurricular)}`,
      disciplinaCurricular,
      { observe: 'response' }
    );
  }

  partialUpdate(disciplinaCurricular: PartialUpdateDisciplinaCurricular): Observable<EntityResponseType> {
    return this.http.patch<IDisciplinaCurricular>(
      `${this.resourceUrl}/${this.getDisciplinaCurricularIdentifier(disciplinaCurricular)}`,
      disciplinaCurricular,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDisciplinaCurricular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDisciplinaCurricular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDisciplinaCurricularIdentifier(disciplinaCurricular: Pick<IDisciplinaCurricular, 'id'>): number {
    return disciplinaCurricular.id;
  }

  compareDisciplinaCurricular(o1: Pick<IDisciplinaCurricular, 'id'> | null, o2: Pick<IDisciplinaCurricular, 'id'> | null): boolean {
    return o1 && o2 ? this.getDisciplinaCurricularIdentifier(o1) === this.getDisciplinaCurricularIdentifier(o2) : o1 === o2;
  }

  addDisciplinaCurricularToCollectionIfMissing<Type extends Pick<IDisciplinaCurricular, 'id'>>(
    disciplinaCurricularCollection: Type[],
    ...disciplinaCurricularsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const disciplinaCurriculars: Type[] = disciplinaCurricularsToCheck.filter(isPresent);
    if (disciplinaCurriculars.length > 0) {
      const disciplinaCurricularCollectionIdentifiers = disciplinaCurricularCollection.map(
        disciplinaCurricularItem => this.getDisciplinaCurricularIdentifier(disciplinaCurricularItem)!
      );
      const disciplinaCurricularsToAdd = disciplinaCurriculars.filter(disciplinaCurricularItem => {
        const disciplinaCurricularIdentifier = this.getDisciplinaCurricularIdentifier(disciplinaCurricularItem);
        if (disciplinaCurricularCollectionIdentifiers.includes(disciplinaCurricularIdentifier)) {
          return false;
        }
        disciplinaCurricularCollectionIdentifiers.push(disciplinaCurricularIdentifier);
        return true;
      });
      return [...disciplinaCurricularsToAdd, ...disciplinaCurricularCollection];
    }
    return disciplinaCurricularCollection;
  }
}
