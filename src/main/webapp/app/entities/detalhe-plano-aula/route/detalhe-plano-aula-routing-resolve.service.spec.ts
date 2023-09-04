import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDetalhePlanoAula } from '../detalhe-plano-aula.model';
import { DetalhePlanoAulaService } from '../service/detalhe-plano-aula.service';

import { DetalhePlanoAulaRoutingResolveService } from './detalhe-plano-aula-routing-resolve.service';

describe('DetalhePlanoAula routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DetalhePlanoAulaRoutingResolveService;
  let service: DetalhePlanoAulaService;
  let resultDetalhePlanoAula: IDetalhePlanoAula | null | undefined;

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
    routingResolveService = TestBed.inject(DetalhePlanoAulaRoutingResolveService);
    service = TestBed.inject(DetalhePlanoAulaService);
    resultDetalhePlanoAula = undefined;
  });

  describe('resolve', () => {
    it('should return IDetalhePlanoAula returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetalhePlanoAula = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetalhePlanoAula).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetalhePlanoAula = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDetalhePlanoAula).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDetalhePlanoAula>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDetalhePlanoAula = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDetalhePlanoAula).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
