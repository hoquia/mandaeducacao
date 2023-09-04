import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILongonkeloHistorico } from '../longonkelo-historico.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../longonkelo-historico.test-samples';

import { LongonkeloHistoricoService, RestLongonkeloHistorico } from './longonkelo-historico.service';

const requireRestSample: RestLongonkeloHistorico = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('LongonkeloHistorico Service', () => {
  let service: LongonkeloHistoricoService;
  let httpMock: HttpTestingController;
  let expectedResult: ILongonkeloHistorico | ILongonkeloHistorico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LongonkeloHistoricoService);
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

    it('should create a LongonkeloHistorico', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const longonkeloHistorico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(longonkeloHistorico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LongonkeloHistorico', () => {
      const longonkeloHistorico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(longonkeloHistorico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LongonkeloHistorico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LongonkeloHistorico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LongonkeloHistorico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLongonkeloHistoricoToCollectionIfMissing', () => {
      it('should add a LongonkeloHistorico to an empty array', () => {
        const longonkeloHistorico: ILongonkeloHistorico = sampleWithRequiredData;
        expectedResult = service.addLongonkeloHistoricoToCollectionIfMissing([], longonkeloHistorico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(longonkeloHistorico);
      });

      it('should not add a LongonkeloHistorico to an array that contains it', () => {
        const longonkeloHistorico: ILongonkeloHistorico = sampleWithRequiredData;
        const longonkeloHistoricoCollection: ILongonkeloHistorico[] = [
          {
            ...longonkeloHistorico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLongonkeloHistoricoToCollectionIfMissing(longonkeloHistoricoCollection, longonkeloHistorico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LongonkeloHistorico to an array that doesn't contain it", () => {
        const longonkeloHistorico: ILongonkeloHistorico = sampleWithRequiredData;
        const longonkeloHistoricoCollection: ILongonkeloHistorico[] = [sampleWithPartialData];
        expectedResult = service.addLongonkeloHistoricoToCollectionIfMissing(longonkeloHistoricoCollection, longonkeloHistorico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(longonkeloHistorico);
      });

      it('should add only unique LongonkeloHistorico to an array', () => {
        const longonkeloHistoricoArray: ILongonkeloHistorico[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const longonkeloHistoricoCollection: ILongonkeloHistorico[] = [sampleWithRequiredData];
        expectedResult = service.addLongonkeloHistoricoToCollectionIfMissing(longonkeloHistoricoCollection, ...longonkeloHistoricoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const longonkeloHistorico: ILongonkeloHistorico = sampleWithRequiredData;
        const longonkeloHistorico2: ILongonkeloHistorico = sampleWithPartialData;
        expectedResult = service.addLongonkeloHistoricoToCollectionIfMissing([], longonkeloHistorico, longonkeloHistorico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(longonkeloHistorico);
        expect(expectedResult).toContain(longonkeloHistorico2);
      });

      it('should accept null and undefined values', () => {
        const longonkeloHistorico: ILongonkeloHistorico = sampleWithRequiredData;
        expectedResult = service.addLongonkeloHistoricoToCollectionIfMissing([], null, longonkeloHistorico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(longonkeloHistorico);
      });

      it('should return initial array if no LongonkeloHistorico is added', () => {
        const longonkeloHistoricoCollection: ILongonkeloHistorico[] = [sampleWithRequiredData];
        expectedResult = service.addLongonkeloHistoricoToCollectionIfMissing(longonkeloHistoricoCollection, undefined, null);
        expect(expectedResult).toEqual(longonkeloHistoricoCollection);
      });
    });

    describe('compareLongonkeloHistorico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLongonkeloHistorico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLongonkeloHistorico(entity1, entity2);
        const compareResult2 = service.compareLongonkeloHistorico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLongonkeloHistorico(entity1, entity2);
        const compareResult2 = service.compareLongonkeloHistorico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLongonkeloHistorico(entity1, entity2);
        const compareResult2 = service.compareLongonkeloHistorico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
