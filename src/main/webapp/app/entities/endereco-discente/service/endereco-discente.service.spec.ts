import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEnderecoDiscente } from '../endereco-discente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../endereco-discente.test-samples';

import { EnderecoDiscenteService } from './endereco-discente.service';

const requireRestSample: IEnderecoDiscente = {
  ...sampleWithRequiredData,
};

describe('EnderecoDiscente Service', () => {
  let service: EnderecoDiscenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IEnderecoDiscente | IEnderecoDiscente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EnderecoDiscenteService);
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

    it('should create a EnderecoDiscente', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const enderecoDiscente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(enderecoDiscente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EnderecoDiscente', () => {
      const enderecoDiscente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(enderecoDiscente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EnderecoDiscente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EnderecoDiscente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EnderecoDiscente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEnderecoDiscenteToCollectionIfMissing', () => {
      it('should add a EnderecoDiscente to an empty array', () => {
        const enderecoDiscente: IEnderecoDiscente = sampleWithRequiredData;
        expectedResult = service.addEnderecoDiscenteToCollectionIfMissing([], enderecoDiscente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoDiscente);
      });

      it('should not add a EnderecoDiscente to an array that contains it', () => {
        const enderecoDiscente: IEnderecoDiscente = sampleWithRequiredData;
        const enderecoDiscenteCollection: IEnderecoDiscente[] = [
          {
            ...enderecoDiscente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEnderecoDiscenteToCollectionIfMissing(enderecoDiscenteCollection, enderecoDiscente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EnderecoDiscente to an array that doesn't contain it", () => {
        const enderecoDiscente: IEnderecoDiscente = sampleWithRequiredData;
        const enderecoDiscenteCollection: IEnderecoDiscente[] = [sampleWithPartialData];
        expectedResult = service.addEnderecoDiscenteToCollectionIfMissing(enderecoDiscenteCollection, enderecoDiscente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoDiscente);
      });

      it('should add only unique EnderecoDiscente to an array', () => {
        const enderecoDiscenteArray: IEnderecoDiscente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const enderecoDiscenteCollection: IEnderecoDiscente[] = [sampleWithRequiredData];
        expectedResult = service.addEnderecoDiscenteToCollectionIfMissing(enderecoDiscenteCollection, ...enderecoDiscenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enderecoDiscente: IEnderecoDiscente = sampleWithRequiredData;
        const enderecoDiscente2: IEnderecoDiscente = sampleWithPartialData;
        expectedResult = service.addEnderecoDiscenteToCollectionIfMissing([], enderecoDiscente, enderecoDiscente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enderecoDiscente);
        expect(expectedResult).toContain(enderecoDiscente2);
      });

      it('should accept null and undefined values', () => {
        const enderecoDiscente: IEnderecoDiscente = sampleWithRequiredData;
        expectedResult = service.addEnderecoDiscenteToCollectionIfMissing([], null, enderecoDiscente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enderecoDiscente);
      });

      it('should return initial array if no EnderecoDiscente is added', () => {
        const enderecoDiscenteCollection: IEnderecoDiscente[] = [sampleWithRequiredData];
        expectedResult = service.addEnderecoDiscenteToCollectionIfMissing(enderecoDiscenteCollection, undefined, null);
        expect(expectedResult).toEqual(enderecoDiscenteCollection);
      });
    });

    describe('compareEnderecoDiscente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEnderecoDiscente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEnderecoDiscente(entity1, entity2);
        const compareResult2 = service.compareEnderecoDiscente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEnderecoDiscente(entity1, entity2);
        const compareResult2 = service.compareEnderecoDiscente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEnderecoDiscente(entity1, entity2);
        const compareResult2 = service.compareEnderecoDiscente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
