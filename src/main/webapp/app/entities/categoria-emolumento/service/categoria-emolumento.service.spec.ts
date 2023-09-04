import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategoriaEmolumento } from '../categoria-emolumento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../categoria-emolumento.test-samples';

import { CategoriaEmolumentoService } from './categoria-emolumento.service';

const requireRestSample: ICategoriaEmolumento = {
  ...sampleWithRequiredData,
};

describe('CategoriaEmolumento Service', () => {
  let service: CategoriaEmolumentoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoriaEmolumento | ICategoriaEmolumento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategoriaEmolumentoService);
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

    it('should create a CategoriaEmolumento', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categoriaEmolumento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoriaEmolumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoriaEmolumento', () => {
      const categoriaEmolumento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoriaEmolumento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoriaEmolumento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoriaEmolumento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoriaEmolumento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoriaEmolumentoToCollectionIfMissing', () => {
      it('should add a CategoriaEmolumento to an empty array', () => {
        const categoriaEmolumento: ICategoriaEmolumento = sampleWithRequiredData;
        expectedResult = service.addCategoriaEmolumentoToCollectionIfMissing([], categoriaEmolumento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaEmolumento);
      });

      it('should not add a CategoriaEmolumento to an array that contains it', () => {
        const categoriaEmolumento: ICategoriaEmolumento = sampleWithRequiredData;
        const categoriaEmolumentoCollection: ICategoriaEmolumento[] = [
          {
            ...categoriaEmolumento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoriaEmolumentoToCollectionIfMissing(categoriaEmolumentoCollection, categoriaEmolumento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoriaEmolumento to an array that doesn't contain it", () => {
        const categoriaEmolumento: ICategoriaEmolumento = sampleWithRequiredData;
        const categoriaEmolumentoCollection: ICategoriaEmolumento[] = [sampleWithPartialData];
        expectedResult = service.addCategoriaEmolumentoToCollectionIfMissing(categoriaEmolumentoCollection, categoriaEmolumento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaEmolumento);
      });

      it('should add only unique CategoriaEmolumento to an array', () => {
        const categoriaEmolumentoArray: ICategoriaEmolumento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoriaEmolumentoCollection: ICategoriaEmolumento[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaEmolumentoToCollectionIfMissing(categoriaEmolumentoCollection, ...categoriaEmolumentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoriaEmolumento: ICategoriaEmolumento = sampleWithRequiredData;
        const categoriaEmolumento2: ICategoriaEmolumento = sampleWithPartialData;
        expectedResult = service.addCategoriaEmolumentoToCollectionIfMissing([], categoriaEmolumento, categoriaEmolumento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaEmolumento);
        expect(expectedResult).toContain(categoriaEmolumento2);
      });

      it('should accept null and undefined values', () => {
        const categoriaEmolumento: ICategoriaEmolumento = sampleWithRequiredData;
        expectedResult = service.addCategoriaEmolumentoToCollectionIfMissing([], null, categoriaEmolumento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaEmolumento);
      });

      it('should return initial array if no CategoriaEmolumento is added', () => {
        const categoriaEmolumentoCollection: ICategoriaEmolumento[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaEmolumentoToCollectionIfMissing(categoriaEmolumentoCollection, undefined, null);
        expect(expectedResult).toEqual(categoriaEmolumentoCollection);
      });
    });

    describe('compareCategoriaEmolumento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoriaEmolumento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategoriaEmolumento(entity1, entity2);
        const compareResult2 = service.compareCategoriaEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategoriaEmolumento(entity1, entity2);
        const compareResult2 = service.compareCategoriaEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategoriaEmolumento(entity1, entity2);
        const compareResult2 = service.compareCategoriaEmolumento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
