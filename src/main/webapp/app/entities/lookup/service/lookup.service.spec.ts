import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILookup } from '../lookup.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../lookup.test-samples';

import { LookupService } from './lookup.service';

const requireRestSample: ILookup = {
  ...sampleWithRequiredData,
};

describe('Lookup Service', () => {
  let service: LookupService;
  let httpMock: HttpTestingController;
  let expectedResult: ILookup | ILookup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LookupService);
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

    it('should create a Lookup', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const lookup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(lookup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Lookup', () => {
      const lookup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(lookup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Lookup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Lookup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Lookup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLookupToCollectionIfMissing', () => {
      it('should add a Lookup to an empty array', () => {
        const lookup: ILookup = sampleWithRequiredData;
        expectedResult = service.addLookupToCollectionIfMissing([], lookup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lookup);
      });

      it('should not add a Lookup to an array that contains it', () => {
        const lookup: ILookup = sampleWithRequiredData;
        const lookupCollection: ILookup[] = [
          {
            ...lookup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLookupToCollectionIfMissing(lookupCollection, lookup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Lookup to an array that doesn't contain it", () => {
        const lookup: ILookup = sampleWithRequiredData;
        const lookupCollection: ILookup[] = [sampleWithPartialData];
        expectedResult = service.addLookupToCollectionIfMissing(lookupCollection, lookup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lookup);
      });

      it('should add only unique Lookup to an array', () => {
        const lookupArray: ILookup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const lookupCollection: ILookup[] = [sampleWithRequiredData];
        expectedResult = service.addLookupToCollectionIfMissing(lookupCollection, ...lookupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const lookup: ILookup = sampleWithRequiredData;
        const lookup2: ILookup = sampleWithPartialData;
        expectedResult = service.addLookupToCollectionIfMissing([], lookup, lookup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lookup);
        expect(expectedResult).toContain(lookup2);
      });

      it('should accept null and undefined values', () => {
        const lookup: ILookup = sampleWithRequiredData;
        expectedResult = service.addLookupToCollectionIfMissing([], null, lookup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lookup);
      });

      it('should return initial array if no Lookup is added', () => {
        const lookupCollection: ILookup[] = [sampleWithRequiredData];
        expectedResult = service.addLookupToCollectionIfMissing(lookupCollection, undefined, null);
        expect(expectedResult).toEqual(lookupCollection);
      });
    });

    describe('compareLookup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLookup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLookup(entity1, entity2);
        const compareResult2 = service.compareLookup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLookup(entity1, entity2);
        const compareResult2 = service.compareLookup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLookup(entity1, entity2);
        const compareResult2 = service.compareLookup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
