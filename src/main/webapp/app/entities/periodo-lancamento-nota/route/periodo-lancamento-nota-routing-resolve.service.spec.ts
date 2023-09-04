import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPeriodoLancamentoNota } from '../periodo-lancamento-nota.model';
import { PeriodoLancamentoNotaService } from '../service/periodo-lancamento-nota.service';

import { PeriodoLancamentoNotaRoutingResolveService } from './periodo-lancamento-nota-routing-resolve.service';

describe('PeriodoLancamentoNota routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PeriodoLancamentoNotaRoutingResolveService;
  let service: PeriodoLancamentoNotaService;
  let resultPeriodoLancamentoNota: IPeriodoLancamentoNota | null | undefined;

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
    routingResolveService = TestBed.inject(PeriodoLancamentoNotaRoutingResolveService);
    service = TestBed.inject(PeriodoLancamentoNotaService);
    resultPeriodoLancamentoNota = undefined;
  });

  describe('resolve', () => {
    it('should return IPeriodoLancamentoNota returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPeriodoLancamentoNota = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPeriodoLancamentoNota).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPeriodoLancamentoNota = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPeriodoLancamentoNota).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPeriodoLancamentoNota>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPeriodoLancamentoNota = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPeriodoLancamentoNota).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
