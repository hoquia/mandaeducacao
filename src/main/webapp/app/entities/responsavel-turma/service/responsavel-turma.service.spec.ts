import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResponsavelTurma } from '../responsavel-turma.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../responsavel-turma.test-samples';

import { ResponsavelTurmaService, RestResponsavelTurma } from './responsavel-turma.service';

const requireRestSample: RestResponsavelTurma = {
  ...sampleWithRequiredData,
  de: sampleWithRequiredData.de?.format(DATE_FORMAT),
  ate: sampleWithRequiredData.ate?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('ResponsavelTurma Service', () => {
  let service: ResponsavelTurmaService;
  let httpMock: HttpTestingController;
  let expectedResult: IResponsavelTurma | IResponsavelTurma[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResponsavelTurmaService);
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

    it('should create a ResponsavelTurma', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const responsavelTurma = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(responsavelTurma).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResponsavelTurma', () => {
      const responsavelTurma = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(responsavelTurma).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResponsavelTurma', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResponsavelTurma', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResponsavelTurma', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResponsavelTurmaToCollectionIfMissing', () => {
      it('should add a ResponsavelTurma to an empty array', () => {
        const responsavelTurma: IResponsavelTurma = sampleWithRequiredData;
        expectedResult = service.addResponsavelTurmaToCollectionIfMissing([], responsavelTurma);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelTurma);
      });

      it('should not add a ResponsavelTurma to an array that contains it', () => {
        const responsavelTurma: IResponsavelTurma = sampleWithRequiredData;
        const responsavelTurmaCollection: IResponsavelTurma[] = [
          {
            ...responsavelTurma,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResponsavelTurmaToCollectionIfMissing(responsavelTurmaCollection, responsavelTurma);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResponsavelTurma to an array that doesn't contain it", () => {
        const responsavelTurma: IResponsavelTurma = sampleWithRequiredData;
        const responsavelTurmaCollection: IResponsavelTurma[] = [sampleWithPartialData];
        expectedResult = service.addResponsavelTurmaToCollectionIfMissing(responsavelTurmaCollection, responsavelTurma);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelTurma);
      });

      it('should add only unique ResponsavelTurma to an array', () => {
        const responsavelTurmaArray: IResponsavelTurma[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const responsavelTurmaCollection: IResponsavelTurma[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelTurmaToCollectionIfMissing(responsavelTurmaCollection, ...responsavelTurmaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const responsavelTurma: IResponsavelTurma = sampleWithRequiredData;
        const responsavelTurma2: IResponsavelTurma = sampleWithPartialData;
        expectedResult = service.addResponsavelTurmaToCollectionIfMissing([], responsavelTurma, responsavelTurma2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelTurma);
        expect(expectedResult).toContain(responsavelTurma2);
      });

      it('should accept null and undefined values', () => {
        const responsavelTurma: IResponsavelTurma = sampleWithRequiredData;
        expectedResult = service.addResponsavelTurmaToCollectionIfMissing([], null, responsavelTurma, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelTurma);
      });

      it('should return initial array if no ResponsavelTurma is added', () => {
        const responsavelTurmaCollection: IResponsavelTurma[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelTurmaToCollectionIfMissing(responsavelTurmaCollection, undefined, null);
        expect(expectedResult).toEqual(responsavelTurmaCollection);
      });
    });

    describe('compareResponsavelTurma', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResponsavelTurma(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResponsavelTurma(entity1, entity2);
        const compareResult2 = service.compareResponsavelTurma(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResponsavelTurma(entity1, entity2);
        const compareResult2 = service.compareResponsavelTurma(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResponsavelTurma(entity1, entity2);
        const compareResult2 = service.compareResponsavelTurma(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
