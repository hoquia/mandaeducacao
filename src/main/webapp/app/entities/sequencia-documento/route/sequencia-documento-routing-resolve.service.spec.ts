import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISequenciaDocumento } from '../sequencia-documento.model';
import { SequenciaDocumentoService } from '../service/sequencia-documento.service';

import { SequenciaDocumentoRoutingResolveService } from './sequencia-documento-routing-resolve.service';

describe('SequenciaDocumento routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SequenciaDocumentoRoutingResolveService;
  let service: SequenciaDocumentoService;
  let resultSequenciaDocumento: ISequenciaDocumento | null | undefined;

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
    routingResolveService = TestBed.inject(SequenciaDocumentoRoutingResolveService);
    service = TestBed.inject(SequenciaDocumentoService);
    resultSequenciaDocumento = undefined;
  });

  describe('resolve', () => {
    it('should return ISequenciaDocumento returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSequenciaDocumento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSequenciaDocumento).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSequenciaDocumento = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSequenciaDocumento).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISequenciaDocumento>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSequenciaDocumento = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSequenciaDocumento).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
