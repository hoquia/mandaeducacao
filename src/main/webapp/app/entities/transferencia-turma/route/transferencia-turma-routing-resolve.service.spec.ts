import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITransferenciaTurma } from '../transferencia-turma.model';
import { TransferenciaTurmaService } from '../service/transferencia-turma.service';

import { TransferenciaTurmaRoutingResolveService } from './transferencia-turma-routing-resolve.service';

describe('TransferenciaTurma routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TransferenciaTurmaRoutingResolveService;
  let service: TransferenciaTurmaService;
  let resultTransferenciaTurma: ITransferenciaTurma | null | undefined;

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
    routingResolveService = TestBed.inject(TransferenciaTurmaRoutingResolveService);
    service = TestBed.inject(TransferenciaTurmaService);
    resultTransferenciaTurma = undefined;
  });

  describe('resolve', () => {
    it('should return ITransferenciaTurma returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransferenciaTurma = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTransferenciaTurma).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransferenciaTurma = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTransferenciaTurma).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITransferenciaTurma>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransferenciaTurma = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTransferenciaTurma).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
