import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICampoActuacaoDissertacao } from '../campo-actuacao-dissertacao.model';
import { CampoActuacaoDissertacaoService } from '../service/campo-actuacao-dissertacao.service';

import { CampoActuacaoDissertacaoRoutingResolveService } from './campo-actuacao-dissertacao-routing-resolve.service';

describe('CampoActuacaoDissertacao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CampoActuacaoDissertacaoRoutingResolveService;
  let service: CampoActuacaoDissertacaoService;
  let resultCampoActuacaoDissertacao: ICampoActuacaoDissertacao | null | undefined;

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
    routingResolveService = TestBed.inject(CampoActuacaoDissertacaoRoutingResolveService);
    service = TestBed.inject(CampoActuacaoDissertacaoService);
    resultCampoActuacaoDissertacao = undefined;
  });

  describe('resolve', () => {
    it('should return ICampoActuacaoDissertacao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCampoActuacaoDissertacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCampoActuacaoDissertacao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCampoActuacaoDissertacao = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCampoActuacaoDissertacao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICampoActuacaoDissertacao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCampoActuacaoDissertacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCampoActuacaoDissertacao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
