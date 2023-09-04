import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDissertacaoFinalCurso } from '../dissertacao-final-curso.model';
import { DissertacaoFinalCursoService } from '../service/dissertacao-final-curso.service';

import { DissertacaoFinalCursoRoutingResolveService } from './dissertacao-final-curso-routing-resolve.service';

describe('DissertacaoFinalCurso routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DissertacaoFinalCursoRoutingResolveService;
  let service: DissertacaoFinalCursoService;
  let resultDissertacaoFinalCurso: IDissertacaoFinalCurso | null | undefined;

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
    routingResolveService = TestBed.inject(DissertacaoFinalCursoRoutingResolveService);
    service = TestBed.inject(DissertacaoFinalCursoService);
    resultDissertacaoFinalCurso = undefined;
  });

  describe('resolve', () => {
    it('should return IDissertacaoFinalCurso returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDissertacaoFinalCurso = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDissertacaoFinalCurso).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDissertacaoFinalCurso = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDissertacaoFinalCurso).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDissertacaoFinalCurso>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDissertacaoFinalCurso = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDissertacaoFinalCurso).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
