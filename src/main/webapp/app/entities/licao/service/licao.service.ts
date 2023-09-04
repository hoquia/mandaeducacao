import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILicao, NewLicao } from '../licao.model';

export type PartialUpdateLicao = Partial<ILicao> & Pick<ILicao, 'id'>;

export type EntityResponseType = HttpResponse<ILicao>;
export type EntityArrayResponseType = HttpResponse<ILicao[]>;

@Injectable({ providedIn: 'root' })
export class LicaoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/licaos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(licao: NewLicao): Observable<EntityResponseType> {
    return this.http.post<ILicao>(this.resourceUrl, licao, { observe: 'response' });
  }

  update(licao: ILicao): Observable<EntityResponseType> {
    return this.http.put<ILicao>(`${this.resourceUrl}/${this.getLicaoIdentifier(licao)}`, licao, { observe: 'response' });
  }

  partialUpdate(licao: PartialUpdateLicao): Observable<EntityResponseType> {
    return this.http.patch<ILicao>(`${this.resourceUrl}/${this.getLicaoIdentifier(licao)}`, licao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILicao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILicao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLicaoIdentifier(licao: Pick<ILicao, 'id'>): number {
    return licao.id;
  }

  compareLicao(o1: Pick<ILicao, 'id'> | null, o2: Pick<ILicao, 'id'> | null): boolean {
    return o1 && o2 ? this.getLicaoIdentifier(o1) === this.getLicaoIdentifier(o2) : o1 === o2;
  }

  addLicaoToCollectionIfMissing<Type extends Pick<ILicao, 'id'>>(
    licaoCollection: Type[],
    ...licaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const licaos: Type[] = licaosToCheck.filter(isPresent);
    if (licaos.length > 0) {
      const licaoCollectionIdentifiers = licaoCollection.map(licaoItem => this.getLicaoIdentifier(licaoItem)!);
      const licaosToAdd = licaos.filter(licaoItem => {
        const licaoIdentifier = this.getLicaoIdentifier(licaoItem);
        if (licaoCollectionIdentifiers.includes(licaoIdentifier)) {
          return false;
        }
        licaoCollectionIdentifiers.push(licaoIdentifier);
        return true;
      });
      return [...licaosToAdd, ...licaoCollection];
    }
    return licaoCollection;
  }
}
