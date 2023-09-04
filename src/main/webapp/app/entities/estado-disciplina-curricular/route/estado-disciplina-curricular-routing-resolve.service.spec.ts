import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEstadoDisciplinaCurricular } from '../estado-disciplina-curricular.model';
import { EstadoDisciplinaCurricularService } from '../service/estado-disciplina-curricular.service';

import { EstadoDisciplinaCurricularRoutingResolveService } from './estado-disciplina-curricular-routing-resolve.service';

describe('EstadoDisciplinaCurricular routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EstadoDisciplinaCurricularRoutingResolveService;
  let service: EstadoDisciplinaCurricularService;
  let resultEstadoDisciplinaCurricular: IEstadoDisciplinaCurricular | null | undefined;

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
    routingResolveService = TestBed.inject(EstadoDisciplinaCurricularRoutingResolveService);
    service = TestBed.inject(EstadoDisciplinaCurricularService);
    resultEstadoDisciplinaCurricular = undefined;
  });

  describe('resolve', () => {
    it('should return IEstadoDisciplinaCurricular returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstadoDisciplinaCurricular = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEstadoDisciplinaCurricular).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstadoDisciplinaCurricular = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEstadoDisciplinaCurricular).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEstadoDisciplinaCurricular>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEstadoDisciplinaCurricular = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEstadoDisciplinaCurricular).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
