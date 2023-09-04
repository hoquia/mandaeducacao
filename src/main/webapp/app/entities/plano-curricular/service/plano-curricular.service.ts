import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoCurricular, NewPlanoCurricular } from '../plano-curricular.model';

export type PartialUpdatePlanoCurricular = Partial<IPlanoCurricular> & Pick<IPlanoCurricular, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoCurricular>;
export type EntityArrayResponseType = HttpResponse<IPlanoCurricular[]>;

@Injectable({ providedIn: 'root' })
export class PlanoCurricularService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-curriculars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(planoCurricular: NewPlanoCurricular): Observable<EntityResponseType> {
    return this.http.post<IPlanoCurricular>(this.resourceUrl, planoCurricular, { observe: 'response' });
  }

  update(planoCurricular: IPlanoCurricular): Observable<EntityResponseType> {
    return this.http.put<IPlanoCurricular>(`${this.resourceUrl}/${this.getPlanoCurricularIdentifier(planoCurricular)}`, planoCurricular, {
      observe: 'response',
    });
  }

  partialUpdate(planoCurricular: PartialUpdatePlanoCurricular): Observable<EntityResponseType> {
    return this.http.patch<IPlanoCurricular>(`${this.resourceUrl}/${this.getPlanoCurricularIdentifier(planoCurricular)}`, planoCurricular, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoCurricular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoCurricular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoCurricularIdentifier(planoCurricular: Pick<IPlanoCurricular, 'id'>): number {
    return planoCurricular.id;
  }

  comparePlanoCurricular(o1: Pick<IPlanoCurricular, 'id'> | null, o2: Pick<IPlanoCurricular, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanoCurricularIdentifier(o1) === this.getPlanoCurricularIdentifier(o2) : o1 === o2;
  }

  addPlanoCurricularToCollectionIfMissing<Type extends Pick<IPlanoCurricular, 'id'>>(
    planoCurricularCollection: Type[],
    ...planoCurricularsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoCurriculars: Type[] = planoCurricularsToCheck.filter(isPresent);
    if (planoCurriculars.length > 0) {
      const planoCurricularCollectionIdentifiers = planoCurricularCollection.map(
        planoCurricularItem => this.getPlanoCurricularIdentifier(planoCurricularItem)!
      );
      const planoCurricularsToAdd = planoCurriculars.filter(planoCurricularItem => {
        const planoCurricularIdentifier = this.getPlanoCurricularIdentifier(planoCurricularItem);
        if (planoCurricularCollectionIdentifiers.includes(planoCurricularIdentifier)) {
          return false;
        }
        planoCurricularCollectionIdentifiers.push(planoCurricularIdentifier);
        return true;
      });
      return [...planoCurricularsToAdd, ...planoCurricularCollection];
    }
    return planoCurricularCollection;
  }
}
