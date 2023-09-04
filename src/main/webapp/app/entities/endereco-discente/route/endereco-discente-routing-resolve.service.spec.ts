import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEnderecoDiscente } from '../endereco-discente.model';
import { EnderecoDiscenteService } from '../service/endereco-discente.service';

import { EnderecoDiscenteRoutingResolveService } from './endereco-discente-routing-resolve.service';

describe('EnderecoDiscente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EnderecoDiscenteRoutingResolveService;
  let service: EnderecoDiscenteService;
  let resultEnderecoDiscente: IEnderecoDiscente | null | undefined;

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
    routingResolveService = TestBed.inject(EnderecoDiscenteRoutingResolveService);
    service = TestBed.inject(EnderecoDiscenteService);
    resultEnderecoDiscente = undefined;
  });

  describe('resolve', () => {
    it('should return IEnderecoDiscente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEnderecoDiscente = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEnderecoDiscente).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEnderecoDiscente = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEnderecoDiscente).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEnderecoDiscente>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEnderecoDiscente = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEnderecoDiscente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
