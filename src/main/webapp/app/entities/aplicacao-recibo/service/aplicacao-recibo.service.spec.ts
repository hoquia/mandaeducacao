import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAplicacaoRecibo } from '../aplicacao-recibo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../aplicacao-recibo.test-samples';

import { AplicacaoReciboService, RestAplicacaoRecibo } from './aplicacao-recibo.service';

const requireRestSample: RestAplicacaoRecibo = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('AplicacaoRecibo Service', () => {
  let service: AplicacaoReciboService;
  let httpMock: HttpTestingController;
  let expectedResult: IAplicacaoRecibo | IAplicacaoRecibo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AplicacaoReciboService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AplicacaoRecibo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const aplicacaoRecibo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(aplicacaoRecibo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AplicacaoRecibo', () => {
      const aplicacaoRecibo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(aplicacaoRecibo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AplicacaoRecibo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AplicacaoRecibo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AplicacaoRecibo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAplicacaoReciboToCollectionIfMissing', () => {
      it('should add a AplicacaoRecibo to an empty array', () => {
        const aplicacaoRecibo: IAplicacaoRecibo = sampleWithRequiredData;
        expectedResult = service.addAplicacaoReciboToCollectionIfMissing([], aplicacaoRecibo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(aplicacaoRecibo);
      });

      it('should not add a AplicacaoRecibo to an array that contains it', () => {
        const aplicacaoRecibo: IAplicacaoRecibo = sampleWithRequiredData;
        const aplicacaoReciboCollection: IAplicacaoRecibo[] = [
          {
            ...aplicacaoRecibo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAplicacaoReciboToCollectionIfMissing(aplicacaoReciboCollection, aplicacaoRecibo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AplicacaoRecibo to an array that doesn't contain it", () => {
        const aplicacaoRecibo: IAplicacaoRecibo = sampleWithRequiredData;
        const aplicacaoReciboCollection: IAplicacaoRecibo[] = [sampleWithPartialData];
        expectedResult = service.addAplicacaoReciboToCollectionIfMissing(aplicacaoReciboCollection, aplicacaoRecibo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(aplicacaoRecibo);
      });

      it('should add only unique AplicacaoRecibo to an array', () => {
        const aplicacaoReciboArray: IAplicacaoRecibo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const aplicacaoReciboCollection: IAplicacaoRecibo[] = [sampleWithRequiredData];
        expectedResult = service.addAplicacaoReciboToCollectionIfMissing(aplicacaoReciboCollection, ...aplicacaoReciboArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const aplicacaoRecibo: IAplicacaoRecibo = sampleWithRequiredData;
        const aplicacaoRecibo2: IAplicacaoRecibo = sampleWithPartialData;
        expectedResult = service.addAplicacaoReciboToCollectionIfMissing([], aplicacaoRecibo, aplicacaoRecibo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(aplicacaoRecibo);
        expect(expectedResult).toContain(aplicacaoRecibo2);
      });

      it('should accept null and undefined values', () => {
        const aplicacaoRecibo: IAplicacaoRecibo = sampleWithRequiredData;
        expectedResult = service.addAplicacaoReciboToCollectionIfMissing([], null, aplicacaoRecibo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(aplicacaoRecibo);
      });

      it('should return initial array if no AplicacaoRecibo is added', () => {
        const aplicacaoReciboCollection: IAplicacaoRecibo[] = [sampleWithRequiredData];
        expectedResult = service.addAplicacaoReciboToCollectionIfMissing(aplicacaoReciboCollection, undefined, null);
        expect(expectedResult).toEqual(aplicacaoReciboCollection);
      });
    });

    describe('compareAplicacaoRecibo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAplicacaoRecibo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAplicacaoRecibo(entity1, entity2);
        const compareResult2 = service.compareAplicacaoRecibo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAplicacaoRecibo(entity1, entity2);
        const compareResult2 = service.compareAplicacaoRecibo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAplicacaoRecibo(entity1, entity2);
        const compareResult2 = service.compareAplicacaoRecibo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
