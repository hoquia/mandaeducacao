import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOcorrencia } from '../ocorrencia.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ocorrencia.test-samples';

import { OcorrenciaService, RestOcorrencia } from './ocorrencia.service';

const requireRestSample: RestOcorrencia = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('Ocorrencia Service', () => {
  let service: OcorrenciaService;
  let httpMock: HttpTestingController;
  let expectedResult: IOcorrencia | IOcorrencia[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OcorrenciaService);
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

    it('should create a Ocorrencia', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const ocorrencia = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ocorrencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Ocorrencia', () => {
      const ocorrencia = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ocorrencia).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Ocorrencia', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Ocorrencia', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Ocorrencia', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOcorrenciaToCollectionIfMissing', () => {
      it('should add a Ocorrencia to an empty array', () => {
        const ocorrencia: IOcorrencia = sampleWithRequiredData;
        expectedResult = service.addOcorrenciaToCollectionIfMissing([], ocorrencia);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ocorrencia);
      });

      it('should not add a Ocorrencia to an array that contains it', () => {
        const ocorrencia: IOcorrencia = sampleWithRequiredData;
        const ocorrenciaCollection: IOcorrencia[] = [
          {
            ...ocorrencia,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOcorrenciaToCollectionIfMissing(ocorrenciaCollection, ocorrencia);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Ocorrencia to an array that doesn't contain it", () => {
        const ocorrencia: IOcorrencia = sampleWithRequiredData;
        const ocorrenciaCollection: IOcorrencia[] = [sampleWithPartialData];
        expectedResult = service.addOcorrenciaToCollectionIfMissing(ocorrenciaCollection, ocorrencia);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ocorrencia);
      });

      it('should add only unique Ocorrencia to an array', () => {
        const ocorrenciaArray: IOcorrencia[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ocorrenciaCollection: IOcorrencia[] = [sampleWithRequiredData];
        expectedResult = service.addOcorrenciaToCollectionIfMissing(ocorrenciaCollection, ...ocorrenciaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ocorrencia: IOcorrencia = sampleWithRequiredData;
        const ocorrencia2: IOcorrencia = sampleWithPartialData;
        expectedResult = service.addOcorrenciaToCollectionIfMissing([], ocorrencia, ocorrencia2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ocorrencia);
        expect(expectedResult).toContain(ocorrencia2);
      });

      it('should accept null and undefined values', () => {
        const ocorrencia: IOcorrencia = sampleWithRequiredData;
        expectedResult = service.addOcorrenciaToCollectionIfMissing([], null, ocorrencia, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ocorrencia);
      });

      it('should return initial array if no Ocorrencia is added', () => {
        const ocorrenciaCollection: IOcorrencia[] = [sampleWithRequiredData];
        expectedResult = service.addOcorrenciaToCollectionIfMissing(ocorrenciaCollection, undefined, null);
        expect(expectedResult).toEqual(ocorrenciaCollection);
      });
    });

    describe('compareOcorrencia', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOcorrencia(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOcorrencia(entity1, entity2);
        const compareResult2 = service.compareOcorrencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOcorrencia(entity1, entity2);
        const compareResult2 = service.compareOcorrencia(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOcorrencia(entity1, entity2);
        const compareResult2 = service.compareOcorrencia(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
