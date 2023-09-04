import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRecibo } from '../recibo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../recibo.test-samples';

import { ReciboService, RestRecibo } from './recibo.service';

const requireRestSample: RestRecibo = {
  ...sampleWithRequiredData,
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
  vencimento: sampleWithRequiredData.vencimento?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('Recibo Service', () => {
  let service: ReciboService;
  let httpMock: HttpTestingController;
  let expectedResult: IRecibo | IRecibo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReciboService);
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

    it('should create a Recibo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const recibo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(recibo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Recibo', () => {
      const recibo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(recibo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Recibo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Recibo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Recibo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addReciboToCollectionIfMissing', () => {
      it('should add a Recibo to an empty array', () => {
        const recibo: IRecibo = sampleWithRequiredData;
        expectedResult = service.addReciboToCollectionIfMissing([], recibo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recibo);
      });

      it('should not add a Recibo to an array that contains it', () => {
        const recibo: IRecibo = sampleWithRequiredData;
        const reciboCollection: IRecibo[] = [
          {
            ...recibo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReciboToCollectionIfMissing(reciboCollection, recibo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Recibo to an array that doesn't contain it", () => {
        const recibo: IRecibo = sampleWithRequiredData;
        const reciboCollection: IRecibo[] = [sampleWithPartialData];
        expectedResult = service.addReciboToCollectionIfMissing(reciboCollection, recibo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recibo);
      });

      it('should add only unique Recibo to an array', () => {
        const reciboArray: IRecibo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const reciboCollection: IRecibo[] = [sampleWithRequiredData];
        expectedResult = service.addReciboToCollectionIfMissing(reciboCollection, ...reciboArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const recibo: IRecibo = sampleWithRequiredData;
        const recibo2: IRecibo = sampleWithPartialData;
        expectedResult = service.addReciboToCollectionIfMissing([], recibo, recibo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(recibo);
        expect(expectedResult).toContain(recibo2);
      });

      it('should accept null and undefined values', () => {
        const recibo: IRecibo = sampleWithRequiredData;
        expectedResult = service.addReciboToCollectionIfMissing([], null, recibo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(recibo);
      });

      it('should return initial array if no Recibo is added', () => {
        const reciboCollection: IRecibo[] = [sampleWithRequiredData];
        expectedResult = service.addReciboToCollectionIfMissing(reciboCollection, undefined, null);
        expect(expectedResult).toEqual(reciboCollection);
      });
    });

    describe('compareRecibo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRecibo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRecibo(entity1, entity2);
        const compareResult2 = service.compareRecibo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRecibo(entity1, entity2);
        const compareResult2 = service.compareRecibo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRecibo(entity1, entity2);
        const compareResult2 = service.compareRecibo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
