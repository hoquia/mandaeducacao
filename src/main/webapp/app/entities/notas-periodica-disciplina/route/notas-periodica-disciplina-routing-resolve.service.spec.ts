import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { INotasPeriodicaDisciplina } from '../notas-periodica-disciplina.model';
import { NotasPeriodicaDisciplinaService } from '../service/notas-periodica-disciplina.service';

import { NotasPeriodicaDisciplinaRoutingResolveService } from './notas-periodica-disciplina-routing-resolve.service';

describe('NotasPeriodicaDisciplina routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NotasPeriodicaDisciplinaRoutingResolveService;
  let service: NotasPeriodicaDisciplinaService;
  let resultNotasPeriodicaDisciplina: INotasPeriodicaDisciplina | null | undefined;

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
    routingResolveService = TestBed.inject(NotasPeriodicaDisciplinaRoutingResolveService);
    service = TestBed.inject(NotasPeriodicaDisciplinaService);
    resultNotasPeriodicaDisciplina = undefined;
  });

  describe('resolve', () => {
    it('should return INotasPeriodicaDisciplina returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNotasPeriodicaDisciplina = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNotasPeriodicaDisciplina).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNotasPeriodicaDisciplina = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNotasPeriodicaDisciplina).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<INotasPeriodicaDisciplina>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNotasPeriodicaDisciplina = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNotasPeriodicaDisciplina).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
