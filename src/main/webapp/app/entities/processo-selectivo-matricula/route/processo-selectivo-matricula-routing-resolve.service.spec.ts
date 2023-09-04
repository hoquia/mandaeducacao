import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProcessoSelectivoMatricula } from '../processo-selectivo-matricula.model';
import { ProcessoSelectivoMatriculaService } from '../service/processo-selectivo-matricula.service';

import { ProcessoSelectivoMatriculaRoutingResolveService } from './processo-selectivo-matricula-routing-resolve.service';

describe('ProcessoSelectivoMatricula routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProcessoSelectivoMatriculaRoutingResolveService;
  let service: ProcessoSelectivoMatriculaService;
  let resultProcessoSelectivoMatricula: IProcessoSelectivoMatricula | null | undefined;

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
    routingResolveService = TestBed.inject(ProcessoSelectivoMatriculaRoutingResolveService);
    service = TestBed.inject(ProcessoSelectivoMatriculaService);
    resultProcessoSelectivoMatricula = undefined;
  });

  describe('resolve', () => {
    it('should return IProcessoSelectivoMatricula returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProcessoSelectivoMatricula = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProcessoSelectivoMatricula).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProcessoSelectivoMatricula = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProcessoSelectivoMatricula).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProcessoSelectivoMatricula>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProcessoSelectivoMatricula = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultProcessoSelectivoMatricula).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
