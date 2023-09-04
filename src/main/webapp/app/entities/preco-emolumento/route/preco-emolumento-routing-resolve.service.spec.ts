import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPrecoEmolumento } from '../preco-emolumento.model';
import { PrecoEmolumentoService } from '../service/preco-emolumento.service';

import { PrecoEmolumentoRoutingResolveService } from './preco-emolumento-routing-resolve.service';

describe('PrecoEmolumento routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PrecoEmolumentoRoutingResolveService;
  let service: PrecoEmolumentoService;
  let resultPrecoEmolumento: IPrecoEmolumento | null | undefined;

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
    routingResolveService = TestBed.inject(PrecoEmolumentoRoutingResolveService);
    service = TestBed.inject(PrecoEmolumentoService);
    resultPrecoEmolumento = undefined;
  });

  describe('resolve', () => {
    it('should return IPrecoEmolumento returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrecoEmolumento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrecoEmolumento).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrecoEmolumento = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPrecoEmolumento).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPrecoEmolumento>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPrecoEmolumento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPrecoEmolumento).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
