import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProvedorNotificacao } from '../provedor-notificacao.model';
import { ProvedorNotificacaoService } from '../service/provedor-notificacao.service';

import { ProvedorNotificacaoRoutingResolveService } from './provedor-notificacao-routing-resolve.service';

describe('ProvedorNotificacao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProvedorNotificacaoRoutingResolveService;
  let service: ProvedorNotificacaoService;
  let resultProvedorNotificacao: IProvedorNotificacao | null | undefined;

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
    routingResolveService = TestBed.inject(ProvedorNotificacaoRoutingResolveService);
    service = TestBed.inject(ProvedorNotificacaoService);
    resultProvedorNotificacao = undefined;
  });

  describe('resolve', () => {
    it('should return IProvedorNotificacao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProvedorNotificacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProvedorNotificacao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProvedorNotificacao = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProvedorNotificacao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProvedorNotificacao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProvedorNotificacao = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProvedorNotificacao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
