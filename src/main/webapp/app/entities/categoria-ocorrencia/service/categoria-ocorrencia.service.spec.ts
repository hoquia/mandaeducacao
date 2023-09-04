import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategoriaOcorrencia } from '../categoria-ocorrencia.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../categoria-ocorrencia.test-samples';

import { CategoriaOcorrenciaService } from './categoria-ocorrencia.service';

const requireRestSample: ICategoriaOcorrencia = {
  ...sampleWithRequiredData,
};

describe('CategoriaOcorrencia Service', () => {
  let service: CategoriaOcorrenciaService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoriaOcorrencia | ICategoriaOcorrencia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategoriaOcorrenciaService);
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

    it('should create a CategoriaOcorrencia', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categoriaOcorrencia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoriaOcorrencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoriaOcorrencia', () => {
      const categoriaOcorrencia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoriaOcorrencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoriaOcorrencia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoriaOcorrencia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoriaOcorrencia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoriaOcorrenciaToCollectionIfMissing', () => {
      it('should add a CategoriaOcorrencia to an empty array', () => {
        const categoriaOcorrencia: ICategoriaOcorrencia = sampleWithRequiredData;
        expectedResult = service.addCategoriaOcorrenciaToCollectionIfMissing([], categoriaOcorrencia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaOcorrencia);
      });

      it('should not add a CategoriaOcorrencia to an array that contains it', () => {
        const categoriaOcorrencia: ICategoriaOcorrencia = sampleWithRequiredData;
        const categoriaOcorrenciaCollection: ICategoriaOcorrencia[] = [
          {
            ...categoriaOcorrencia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoriaOcorrenciaToCollectionIfMissing(categoriaOcorrenciaCollection, categoriaOcorrencia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoriaOcorrencia to an array that doesn't contain it", () => {
        const categoriaOcorrencia: ICategoriaOcorrencia = sampleWithRequiredData;
        const categoriaOcorrenciaCollection: ICategoriaOcorrencia[] = [sampleWithPartialData];
        expectedResult = service.addCategoriaOcorrenciaToCollectionIfMissing(categoriaOcorrenciaCollection, categoriaOcorrencia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaOcorrencia);
      });

      it('should add only unique CategoriaOcorrencia to an array', () => {
        const categoriaOcorrenciaArray: ICategoriaOcorrencia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoriaOcorrenciaCollection: ICategoriaOcorrencia[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaOcorrenciaToCollectionIfMissing(categoriaOcorrenciaCollection, ...categoriaOcorrenciaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoriaOcorrencia: ICategoriaOcorrencia = sampleWithRequiredData;
        const categoriaOcorrencia2: ICategoriaOcorrencia = sampleWithPartialData;
        expectedResult = service.addCategoriaOcorrenciaToCollectionIfMissing([], categoriaOcorrencia, categoriaOcorrencia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriaOcorrencia);
        expect(expectedResult).toContain(categoriaOcorrencia2);
      });

      it('should accept null and undefined values', () => {
        const categoriaOcorrencia: ICategoriaOcorrencia = sampleWithRequiredData;
        expectedResult = service.addCategoriaOcorrenciaToCollectionIfMissing([], null, categoriaOcorrencia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriaOcorrencia);
      });

      it('should return initial array if no CategoriaOcorrencia is added', () => {
        const categoriaOcorrenciaCollection: ICategoriaOcorrencia[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriaOcorrenciaToCollectionIfMissing(categoriaOcorrenciaCollection, undefined, null);
        expect(expectedResult).toEqual(categoriaOcorrenciaCollection);
      });
    });

    describe('compareCategoriaOcorrencia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoriaOcorrencia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategoriaOcorrencia(entity1, entity2);
        const compareResult2 = service.compareCategoriaOcorrencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategoriaOcorrencia(entity1, entity2);
        const compareResult2 = service.compareCategoriaOcorrencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategoriaOcorrencia(entity1, entity2);
        const compareResult2 = service.compareCategoriaOcorrencia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
