import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResponsavelCurso } from '../responsavel-curso.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../responsavel-curso.test-samples';

import { ResponsavelCursoService, RestResponsavelCurso } from './responsavel-curso.service';

const requireRestSample: RestResponsavelCurso = {
  ...sampleWithRequiredData,
  de: sampleWithRequiredData.de?.format(DATE_FORMAT),
  ate: sampleWithRequiredData.ate?.format(DATE_FORMAT),
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('ResponsavelCurso Service', () => {
  let service: ResponsavelCursoService;
  let httpMock: HttpTestingController;
  let expectedResult: IResponsavelCurso | IResponsavelCurso[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResponsavelCursoService);
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

    it('should create a ResponsavelCurso', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const responsavelCurso = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(responsavelCurso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ResponsavelCurso', () => {
      const responsavelCurso = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(responsavelCurso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ResponsavelCurso', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ResponsavelCurso', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ResponsavelCurso', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResponsavelCursoToCollectionIfMissing', () => {
      it('should add a ResponsavelCurso to an empty array', () => {
        const responsavelCurso: IResponsavelCurso = sampleWithRequiredData;
        expectedResult = service.addResponsavelCursoToCollectionIfMissing([], responsavelCurso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelCurso);
      });

      it('should not add a ResponsavelCurso to an array that contains it', () => {
        const responsavelCurso: IResponsavelCurso = sampleWithRequiredData;
        const responsavelCursoCollection: IResponsavelCurso[] = [
          {
            ...responsavelCurso,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResponsavelCursoToCollectionIfMissing(responsavelCursoCollection, responsavelCurso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ResponsavelCurso to an array that doesn't contain it", () => {
        const responsavelCurso: IResponsavelCurso = sampleWithRequiredData;
        const responsavelCursoCollection: IResponsavelCurso[] = [sampleWithPartialData];
        expectedResult = service.addResponsavelCursoToCollectionIfMissing(responsavelCursoCollection, responsavelCurso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelCurso);
      });

      it('should add only unique ResponsavelCurso to an array', () => {
        const responsavelCursoArray: IResponsavelCurso[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const responsavelCursoCollection: IResponsavelCurso[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelCursoToCollectionIfMissing(responsavelCursoCollection, ...responsavelCursoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const responsavelCurso: IResponsavelCurso = sampleWithRequiredData;
        const responsavelCurso2: IResponsavelCurso = sampleWithPartialData;
        expectedResult = service.addResponsavelCursoToCollectionIfMissing([], responsavelCurso, responsavelCurso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(responsavelCurso);
        expect(expectedResult).toContain(responsavelCurso2);
      });

      it('should accept null and undefined values', () => {
        const responsavelCurso: IResponsavelCurso = sampleWithRequiredData;
        expectedResult = service.addResponsavelCursoToCollectionIfMissing([], null, responsavelCurso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(responsavelCurso);
      });

      it('should return initial array if no ResponsavelCurso is added', () => {
        const responsavelCursoCollection: IResponsavelCurso[] = [sampleWithRequiredData];
        expectedResult = service.addResponsavelCursoToCollectionIfMissing(responsavelCursoCollection, undefined, null);
        expect(expectedResult).toEqual(responsavelCursoCollection);
      });
    });

    describe('compareResponsavelCurso', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResponsavelCurso(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareResponsavelCurso(entity1, entity2);
        const compareResult2 = service.compareResponsavelCurso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareResponsavelCurso(entity1, entity2);
        const compareResult2 = service.compareResponsavelCurso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareResponsavelCurso(entity1, entity2);
        const compareResult2 = service.compareResponsavelCurso(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
