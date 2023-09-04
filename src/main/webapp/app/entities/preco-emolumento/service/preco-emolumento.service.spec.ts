import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrecoEmolumento } from '../preco-emolumento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../preco-emolumento.test-samples';

import { PrecoEmolumentoService } from './preco-emolumento.service';

const requireRestSample: IPrecoEmolumento = {
  ...sampleWithRequiredData,
};

describe('PrecoEmolumento Service', () => {
  let service: PrecoEmolumentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrecoEmolumento | IPrecoEmolumento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrecoEmolumentoService);
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

    it('should create a PrecoEmolumento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const precoEmolumento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(precoEmolumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrecoEmolumento', () => {
      const precoEmolumento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(precoEmolumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrecoEmolumento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrecoEmolumento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrecoEmolumento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrecoEmolumentoToCollectionIfMissing', () => {
      it('should add a PrecoEmolumento to an empty array', () => {
        const precoEmolumento: IPrecoEmolumento = sampleWithRequiredData;
        expectedResult = service.addPrecoEmolumentoToCollectionIfMissing([], precoEmolumento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(precoEmolumento);
      });

      it('should not add a PrecoEmolumento to an array that contains it', () => {
        const precoEmolumento: IPrecoEmolumento = sampleWithRequiredData;
        const precoEmolumentoCollection: IPrecoEmolumento[] = [
          {
            ...precoEmolumento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrecoEmolumentoToCollectionIfMissing(precoEmolumentoCollection, precoEmolumento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrecoEmolumento to an array that doesn't contain it", () => {
        const precoEmolumento: IPrecoEmolumento = sampleWithRequiredData;
        const precoEmolumentoCollection: IPrecoEmolumento[] = [sampleWithPartialData];
        expectedResult = service.addPrecoEmolumentoToCollectionIfMissing(precoEmolumentoCollection, precoEmolumento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(precoEmolumento);
      });

      it('should add only unique PrecoEmolumento to an array', () => {
        const precoEmolumentoArray: IPrecoEmolumento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const precoEmolumentoCollection: IPrecoEmolumento[] = [sampleWithRequiredData];
        expectedResult = service.addPrecoEmolumentoToCollectionIfMissing(precoEmolumentoCollection, ...precoEmolumentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const precoEmolumento: IPrecoEmolumento = sampleWithRequiredData;
        const precoEmolumento2: IPrecoEmolumento = sampleWithPartialData;
        expectedResult = service.addPrecoEmolumentoToCollectionIfMissing([], precoEmolumento, precoEmolumento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(precoEmolumento);
        expect(expectedResult).toContain(precoEmolumento2);
      });

      it('should accept null and undefined values', () => {
        const precoEmolumento: IPrecoEmolumento = sampleWithRequiredData;
        expectedResult = service.addPrecoEmolumentoToCollectionIfMissing([], null, precoEmolumento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(precoEmolumento);
      });

      it('should return initial array if no PrecoEmolumento is added', () => {
        const precoEmolumentoCollection: IPrecoEmolumento[] = [sampleWithRequiredData];
        expectedResult = service.addPrecoEmolumentoToCollectionIfMissing(precoEmolumentoCollection, undefined, null);
        expect(expectedResult).toEqual(precoEmolumentoCollection);
      });
    });

    describe('comparePrecoEmolumento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrecoEmolumento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrecoEmolumento(entity1, entity2);
        const compareResult2 = service.comparePrecoEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrecoEmolumento(entity1, entity2);
        const compareResult2 = service.comparePrecoEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrecoEmolumento(entity1, entity2);
        const compareResult2 = service.comparePrecoEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
