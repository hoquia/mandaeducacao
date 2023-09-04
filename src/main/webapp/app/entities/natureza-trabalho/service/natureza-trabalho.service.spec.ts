import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INaturezaTrabalho } from '../natureza-trabalho.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../natureza-trabalho.test-samples';

import { NaturezaTrabalhoService } from './natureza-trabalho.service';

const requireRestSample: INaturezaTrabalho = {
  ...sampleWithRequiredData,
};

describe('NaturezaTrabalho Service', () => {
  let service: NaturezaTrabalhoService;
  let httpMock: HttpTestingController;
  let expectedResult: INaturezaTrabalho | INaturezaTrabalho[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NaturezaTrabalhoService);
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

    it('should create a NaturezaTrabalho', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const naturezaTrabalho = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(naturezaTrabalho).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NaturezaTrabalho', () => {
      const naturezaTrabalho = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(naturezaTrabalho).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NaturezaTrabalho', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NaturezaTrabalho', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NaturezaTrabalho', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNaturezaTrabalhoToCollectionIfMissing', () => {
      it('should add a NaturezaTrabalho to an empty array', () => {
        const naturezaTrabalho: INaturezaTrabalho = sampleWithRequiredData;
        expectedResult = service.addNaturezaTrabalhoToCollectionIfMissing([], naturezaTrabalho);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(naturezaTrabalho);
      });

      it('should not add a NaturezaTrabalho to an array that contains it', () => {
        const naturezaTrabalho: INaturezaTrabalho = sampleWithRequiredData;
        const naturezaTrabalhoCollection: INaturezaTrabalho[] = [
          {
            ...naturezaTrabalho,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNaturezaTrabalhoToCollectionIfMissing(naturezaTrabalhoCollection, naturezaTrabalho);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NaturezaTrabalho to an array that doesn't contain it", () => {
        const naturezaTrabalho: INaturezaTrabalho = sampleWithRequiredData;
        const naturezaTrabalhoCollection: INaturezaTrabalho[] = [sampleWithPartialData];
        expectedResult = service.addNaturezaTrabalhoToCollectionIfMissing(naturezaTrabalhoCollection, naturezaTrabalho);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(naturezaTrabalho);
      });

      it('should add only unique NaturezaTrabalho to an array', () => {
        const naturezaTrabalhoArray: INaturezaTrabalho[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const naturezaTrabalhoCollection: INaturezaTrabalho[] = [sampleWithRequiredData];
        expectedResult = service.addNaturezaTrabalhoToCollectionIfMissing(naturezaTrabalhoCollection, ...naturezaTrabalhoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const naturezaTrabalho: INaturezaTrabalho = sampleWithRequiredData;
        const naturezaTrabalho2: INaturezaTrabalho = sampleWithPartialData;
        expectedResult = service.addNaturezaTrabalhoToCollectionIfMissing([], naturezaTrabalho, naturezaTrabalho2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(naturezaTrabalho);
        expect(expectedResult).toContain(naturezaTrabalho2);
      });

      it('should accept null and undefined values', () => {
        const naturezaTrabalho: INaturezaTrabalho = sampleWithRequiredData;
        expectedResult = service.addNaturezaTrabalhoToCollectionIfMissing([], null, naturezaTrabalho, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(naturezaTrabalho);
      });

      it('should return initial array if no NaturezaTrabalho is added', () => {
        const naturezaTrabalhoCollection: INaturezaTrabalho[] = [sampleWithRequiredData];
        expectedResult = service.addNaturezaTrabalhoToCollectionIfMissing(naturezaTrabalhoCollection, undefined, null);
        expect(expectedResult).toEqual(naturezaTrabalhoCollection);
      });
    });

    describe('compareNaturezaTrabalho', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNaturezaTrabalho(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNaturezaTrabalho(entity1, entity2);
        const compareResult2 = service.compareNaturezaTrabalho(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNaturezaTrabalho(entity1, entity2);
        const compareResult2 = service.compareNaturezaTrabalho(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNaturezaTrabalho(entity1, entity2);
        const compareResult2 = service.compareNaturezaTrabalho(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
