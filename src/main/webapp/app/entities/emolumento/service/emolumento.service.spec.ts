import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmolumento } from '../emolumento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../emolumento.test-samples';

import { EmolumentoService } from './emolumento.service';

const requireRestSample: IEmolumento = {
  ...sampleWithRequiredData,
};

describe('Emolumento Service', () => {
  let service: EmolumentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmolumento | IEmolumento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmolumentoService);
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

    it('should create a Emolumento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const emolumento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(emolumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Emolumento', () => {
      const emolumento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(emolumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Emolumento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Emolumento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Emolumento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmolumentoToCollectionIfMissing', () => {
      it('should add a Emolumento to an empty array', () => {
        const emolumento: IEmolumento = sampleWithRequiredData;
        expectedResult = service.addEmolumentoToCollectionIfMissing([], emolumento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emolumento);
      });

      it('should not add a Emolumento to an array that contains it', () => {
        const emolumento: IEmolumento = sampleWithRequiredData;
        const emolumentoCollection: IEmolumento[] = [
          {
            ...emolumento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmolumentoToCollectionIfMissing(emolumentoCollection, emolumento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Emolumento to an array that doesn't contain it", () => {
        const emolumento: IEmolumento = sampleWithRequiredData;
        const emolumentoCollection: IEmolumento[] = [sampleWithPartialData];
        expectedResult = service.addEmolumentoToCollectionIfMissing(emolumentoCollection, emolumento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emolumento);
      });

      it('should add only unique Emolumento to an array', () => {
        const emolumentoArray: IEmolumento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const emolumentoCollection: IEmolumento[] = [sampleWithRequiredData];
        expectedResult = service.addEmolumentoToCollectionIfMissing(emolumentoCollection, ...emolumentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emolumento: IEmolumento = sampleWithRequiredData;
        const emolumento2: IEmolumento = sampleWithPartialData;
        expectedResult = service.addEmolumentoToCollectionIfMissing([], emolumento, emolumento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emolumento);
        expect(expectedResult).toContain(emolumento2);
      });

      it('should accept null and undefined values', () => {
        const emolumento: IEmolumento = sampleWithRequiredData;
        expectedResult = service.addEmolumentoToCollectionIfMissing([], null, emolumento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emolumento);
      });

      it('should return initial array if no Emolumento is added', () => {
        const emolumentoCollection: IEmolumento[] = [sampleWithRequiredData];
        expectedResult = service.addEmolumentoToCollectionIfMissing(emolumentoCollection, undefined, null);
        expect(expectedResult).toEqual(emolumentoCollection);
      });
    });

    describe('compareEmolumento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmolumento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmolumento(entity1, entity2);
        const compareResult2 = service.compareEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmolumento(entity1, entity2);
        const compareResult2 = service.compareEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmolumento(entity1, entity2);
        const compareResult2 = service.compareEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
