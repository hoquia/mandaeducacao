import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResponsavelAreaFormacao } from '../responsavel-area-formacao.model';
import { ResponsavelAreaFormacaoService } from '../service/responsavel-area-formacao.service';

import { ResponsavelAreaFormacaoRoutingResolveService } from './responsavel-area-formacao-routing-resolve.service';

describe('ResponsavelAreaFormacao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResponsavelAreaFormacaoRoutingResolveService;
  let service: ResponsavelAreaFormacaoService;
  let resultResponsavelAreaFormacao: IResponsavelAreaFormacao | null | undefined;

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
    routingResolveService = TestBed.inject(ResponsavelAreaFormacaoRoutingResolveService);
    service = TestBed.inject(ResponsavelAreaFormacaoService);
    resultResponsavelAreaFormacao = undefined;
  });

  describe('resolve', () => {
    it('should return IResponsavelAreaFormacao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelAreaFormacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResponsavelAreaFormacao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelAreaFormacao = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResponsavelAreaFormacao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IResponsavelAreaFormacao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelAreaFormacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResponsavelAreaFormacao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
