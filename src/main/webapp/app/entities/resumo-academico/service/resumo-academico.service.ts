import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumoAcademico, NewResumoAcademico } from '../resumo-academico.model';

export type PartialUpdateResumoAcademico = Partial<IResumoAcademico> & Pick<IResumoAcademico, 'id'>;

export type EntityResponseType = HttpResponse<IResumoAcademico>;
export type EntityArrayResponseType = HttpResponse<IResumoAcademico[]>;

@Injectable({ providedIn: 'root' })
export class ResumoAcademicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resumo-academicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumoAcademico: NewResumoAcademico): Observable<EntityResponseType> {
    return this.http.post<IResumoAcademico>(this.resourceUrl, resumoAcademico, { observe: 'response' });
  }

  update(resumoAcademico: IResumoAcademico): Observable<EntityResponseType> {
    return this.http.put<IResumoAcademico>(`${this.resourceUrl}/${this.getResumoAcademicoIdentifier(resumoAcademico)}`, resumoAcademico, {
      observe: 'response',
    });
  }

  partialUpdate(resumoAcademico: PartialUpdateResumoAcademico): Observable<EntityResponseType> {
    return this.http.patch<IResumoAcademico>(`${this.resourceUrl}/${this.getResumoAcademicoIdentifier(resumoAcademico)}`, resumoAcademico, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumoAcademico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumoAcademico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResumoAcademicoIdentifier(resumoAcademico: Pick<IResumoAcademico, 'id'>): number {
    return resumoAcademico.id;
  }

  compareResumoAcademico(o1: Pick<IResumoAcademico, 'id'> | null, o2: Pick<IResumoAcademico, 'id'> | null): boolean {
    return o1 && o2 ? this.getResumoAcademicoIdentifier(o1) === this.getResumoAcademicoIdentifier(o2) : o1 === o2;
  }

  addResumoAcademicoToCollectionIfMissing<Type extends Pick<IResumoAcademico, 'id'>>(
    resumoAcademicoCollection: Type[],
    ...resumoAcademicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const resumoAcademicos: Type[] = resumoAcademicosToCheck.filter(isPresent);
    if (resumoAcademicos.length > 0) {
      const resumoAcademicoCollectionIdentifiers = resumoAcademicoCollection.map(
        resumoAcademicoItem => this.getResumoAcademicoIdentifier(resumoAcademicoItem)!
      );
      const resumoAcademicosToAdd = resumoAcademicos.filter(resumoAcademicoItem => {
        const resumoAcademicoIdentifier = this.getResumoAcademicoIdentifier(resumoAcademicoItem);
        if (resumoAcademicoCollectionIdentifiers.includes(resumoAcademicoIdentifier)) {
          return false;
        }
        resumoAcademicoCollectionIdentifiers.push(resumoAcademicoIdentifier);
        return true;
      });
      return [...resumoAcademicosToAdd, ...resumoAcademicoCollection];
    }
    return resumoAcademicoCollection;
  }
}
