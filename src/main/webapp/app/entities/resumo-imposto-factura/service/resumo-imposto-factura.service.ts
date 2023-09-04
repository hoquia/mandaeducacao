import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResumoImpostoFactura, NewResumoImpostoFactura } from '../resumo-imposto-factura.model';

export type PartialUpdateResumoImpostoFactura = Partial<IResumoImpostoFactura> & Pick<IResumoImpostoFactura, 'id'>;

export type EntityResponseType = HttpResponse<IResumoImpostoFactura>;
export type EntityArrayResponseType = HttpResponse<IResumoImpostoFactura[]>;

@Injectable({ providedIn: 'root' })
export class ResumoImpostoFacturaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resumo-imposto-facturas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resumoImpostoFactura: NewResumoImpostoFactura): Observable<EntityResponseType> {
    return this.http.post<IResumoImpostoFactura>(this.resourceUrl, resumoImpostoFactura, { observe: 'response' });
  }

  update(resumoImpostoFactura: IResumoImpostoFactura): Observable<EntityResponseType> {
    return this.http.put<IResumoImpostoFactura>(
      `${this.resourceUrl}/${this.getResumoImpostoFacturaIdentifier(resumoImpostoFactura)}`,
      resumoImpostoFactura,
      { observe: 'response' }
    );
  }

  partialUpdate(resumoImpostoFactura: PartialUpdateResumoImpostoFactura): Observable<EntityResponseType> {
    return this.http.patch<IResumoImpostoFactura>(
      `${this.resourceUrl}/${this.getResumoImpostoFacturaIdentifier(resumoImpostoFactura)}`,
      resumoImpostoFactura,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResumoImpostoFactura>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResumoImpostoFactura[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResumoImpostoFacturaIdentifier(resumoImpostoFactura: Pick<IResumoImpostoFactura, 'id'>): number {
    return resumoImpostoFactura.id;
  }

  compareResumoImpostoFactura(o1: Pick<IResumoImpostoFactura, 'id'> | null, o2: Pick<IResumoImpostoFactura, 'id'> | null): boolean {
    return o1 && o2 ? this.getResumoImpostoFacturaIdentifier(o1) === this.getResumoImpostoFacturaIdentifier(o2) : o1 === o2;
  }

  addResumoImpostoFacturaToCollectionIfMissing<Type extends Pick<IResumoImpostoFactura, 'id'>>(
    resumoImpostoFacturaCollection: Type[],
    ...resumoImpostoFacturasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const resumoImpostoFacturas: Type[] = resumoImpostoFacturasToCheck.filter(isPresent);
    if (resumoImpostoFacturas.length > 0) {
      const resumoImpostoFacturaCollectionIdentifiers = resumoImpostoFacturaCollection.map(
        resumoImpostoFacturaItem => this.getResumoImpostoFacturaIdentifier(resumoImpostoFacturaItem)!
      );
      const resumoImpostoFacturasToAdd = resumoImpostoFacturas.filter(resumoImpostoFacturaItem => {
        const resumoImpostoFacturaIdentifier = this.getResumoImpostoFacturaIdentifier(resumoImpostoFacturaItem);
        if (resumoImpostoFacturaCollectionIdentifiers.includes(resumoImpostoFacturaIdentifier)) {
          return false;
        }
        resumoImpostoFacturaCollectionIdentifiers.push(resumoImpostoFacturaIdentifier);
        return true;
      });
      return [...resumoImpostoFacturasToAdd, ...resumoImpostoFacturaCollection];
    }
    return resumoImpostoFacturaCollection;
  }
}
