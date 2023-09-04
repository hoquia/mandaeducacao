import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IResponsavelTurma } from '../responsavel-turma.model';
import { ResponsavelTurmaService } from '../service/responsavel-turma.service';

import { ResponsavelTurmaRoutingResolveService } from './responsavel-turma-routing-resolve.service';

describe('ResponsavelTurma routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ResponsavelTurmaRoutingResolveService;
  let service: ResponsavelTurmaService;
  let resultResponsavelTurma: IResponsavelTurma | null | undefined;

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
    routingResolveService = TestBed.inject(ResponsavelTurmaRoutingResolveService);
    service = TestBed.inject(ResponsavelTurmaService);
    resultResponsavelTurma = undefined;
  });

  describe('resolve', () => {
    it('should return IResponsavelTurma returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelTurma = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResponsavelTurma).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelTurma = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultResponsavelTurma).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IResponsavelTurma>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultResponsavelTurma = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultResponsavelTurma).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
