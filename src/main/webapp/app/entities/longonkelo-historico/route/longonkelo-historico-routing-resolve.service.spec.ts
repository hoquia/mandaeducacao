import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ILongonkeloHistorico } from '../longonkelo-historico.model';
import { LongonkeloHistoricoService } from '../service/longonkelo-historico.service';

import { LongonkeloHistoricoRoutingResolveService } from './longonkelo-historico-routing-resolve.service';

describe('LongonkeloHistorico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: LongonkeloHistoricoRoutingResolveService;
  let service: LongonkeloHistoricoService;
  let resultLongonkeloHistorico: ILongonkeloHistorico | null | undefined;

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
    routingResolveService = TestBed.inject(LongonkeloHistoricoRoutingResolveService);
    service = TestBed.inject(LongonkeloHistoricoService);
    resultLongonkeloHistorico = undefined;
  });

  describe('resolve', () => {
    it('should return ILongonkeloHistorico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLongonkeloHistorico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLongonkeloHistorico).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLongonkeloHistorico = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultLongonkeloHistorico).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ILongonkeloHistorico>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultLongonkeloHistorico = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultLongonkeloHistorico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
