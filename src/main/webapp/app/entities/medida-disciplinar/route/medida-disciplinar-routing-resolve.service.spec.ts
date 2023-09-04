import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IMedidaDisciplinar } from '../medida-disciplinar.model';
import { MedidaDisciplinarService } from '../service/medida-disciplinar.service';

import { MedidaDisciplinarRoutingResolveService } from './medida-disciplinar-routing-resolve.service';

describe('MedidaDisciplinar routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MedidaDisciplinarRoutingResolveService;
  let service: MedidaDisciplinarService;
  let resultMedidaDisciplinar: IMedidaDisciplinar | null | undefined;

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
    routingResolveService = TestBed.inject(MedidaDisciplinarRoutingResolveService);
    service = TestBed.inject(MedidaDisciplinarService);
    resultMedidaDisciplinar = undefined;
  });

  describe('resolve', () => {
    it('should return IMedidaDisciplinar returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMedidaDisciplinar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMedidaDisciplinar).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMedidaDisciplinar = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMedidaDisciplinar).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IMedidaDisciplinar>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMedidaDisciplinar = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMedidaDisciplinar).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
