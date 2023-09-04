import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILookupItem } from '../lookup-item.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../lookup-item.test-samples';

import { LookupItemService } from './lookup-item.service';

const requireRestSample: ILookupItem = {
  ...sampleWithRequiredData,
};

describe('LookupItem Service', () => {
  let service: LookupItemService;
  let httpMock: HttpTestingController;
  let expectedResult: ILookupItem | ILookupItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LookupItemService);
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

    it('should create a LookupItem', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const lookupItem = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(lookupItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LookupItem', () => {
      const lookupItem = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(lookupItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LookupItem', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LookupItem', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LookupItem', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLookupItemToCollectionIfMissing', () => {
      it('should add a LookupItem to an empty array', () => {
        const lookupItem: ILookupItem = sampleWithRequiredData;
        expectedResult = service.addLookupItemToCollectionIfMissing([], lookupItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lookupItem);
      });

      it('should not add a LookupItem to an array that contains it', () => {
        const lookupItem: ILookupItem = sampleWithRequiredData;
        const lookupItemCollection: ILookupItem[] = [
          {
            ...lookupItem,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLookupItemToCollectionIfMissing(lookupItemCollection, lookupItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LookupItem to an array that doesn't contain it", () => {
        const lookupItem: ILookupItem = sampleWithRequiredData;
        const lookupItemCollection: ILookupItem[] = [sampleWithPartialData];
        expectedResult = service.addLookupItemToCollectionIfMissing(lookupItemCollection, lookupItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lookupItem);
      });

      it('should add only unique LookupItem to an array', () => {
        const lookupItemArray: ILookupItem[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const lookupItemCollection: ILookupItem[] = [sampleWithRequiredData];
        expectedResult = service.addLookupItemToCollectionIfMissing(lookupItemCollection, ...lookupItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const lookupItem: ILookupItem = sampleWithRequiredData;
        const lookupItem2: ILookupItem = sampleWithPartialData;
        expectedResult = service.addLookupItemToCollectionIfMissing([], lookupItem, lookupItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lookupItem);
        expect(expectedResult).toContain(lookupItem2);
      });

      it('should accept null and undefined values', () => {
        const lookupItem: ILookupItem = sampleWithRequiredData;
        expectedResult = service.addLookupItemToCollectionIfMissing([], null, lookupItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lookupItem);
      });

      it('should return initial array if no LookupItem is added', () => {
        const lookupItemCollection: ILookupItem[] = [sampleWithRequiredData];
        expectedResult = service.addLookupItemToCollectionIfMissing(lookupItemCollection, undefined, null);
        expect(expectedResult).toEqual(lookupItemCollection);
      });
    });

    describe('compareLookupItem', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLookupItem(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLookupItem(entity1, entity2);
        const compareResult2 = service.compareLookupItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLookupItem(entity1, entity2);
        const compareResult2 = service.compareLookupItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLookupItem(entity1, entity2);
        const compareResult2 = service.compareLookupItem(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
