import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICategoriaOcorrencia } from '../categoria-ocorrencia.model';
import { CategoriaOcorrenciaService } from '../service/categoria-ocorrencia.service';

import { CategoriaOcorrenciaRoutingResolveService } from './categoria-ocorrencia-routing-resolve.service';

describe('CategoriaOcorrencia routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CategoriaOcorrenciaRoutingResolveService;
  let service: CategoriaOcorrenciaService;
  let resultCategoriaOcorrencia: ICategoriaOcorrencia | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(CategoriaOcorrenciaRoutingResolveService);
    service = TestBed.inject(CategoriaOcorrenciaService);
    resultCategoriaOcorrencia = undefined;
  });

  describe('resolve', () => {
    it('should return ICategoriaOcorrencia returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCategoriaOcorrencia = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCategoriaOcorrencia).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCategoriaOcorrencia = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCategoriaOcorrencia).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICategoriaOcorrencia>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCategoriaOcorrencia = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCategoriaOcorrencia).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
