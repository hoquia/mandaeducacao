import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDissertacaoFinalCurso } from '../dissertacao-final-curso.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../dissertacao-final-curso.test-samples';

import { DissertacaoFinalCursoService, RestDissertacaoFinalCurso } from './dissertacao-final-curso.service';

const requireRestSample: RestDissertacaoFinalCurso = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
  data: sampleWithRequiredData.data?.format(DATE_FORMAT),
};

describe('DissertacaoFinalCurso Service', () => {
  let service: DissertacaoFinalCursoService;
  let httpMock: HttpTestingController;
  let expectedResult: IDissertacaoFinalCurso | IDissertacaoFinalCurso[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DissertacaoFinalCursoService);
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

    it('should create a DissertacaoFinalCurso', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dissertacaoFinalCurso = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dissertacaoFinalCurso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DissertacaoFinalCurso', () => {
      const dissertacaoFinalCurso = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dissertacaoFinalCurso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DissertacaoFinalCurso', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DissertacaoFinalCurso', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DissertacaoFinalCurso', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDissertacaoFinalCursoToCollectionIfMissing', () => {
      it('should add a DissertacaoFinalCurso to an empty array', () => {
        const dissertacaoFinalCurso: IDissertacaoFinalCurso = sampleWithRequiredData;
        expectedResult = service.addDissertacaoFinalCursoToCollectionIfMissing([], dissertacaoFinalCurso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dissertacaoFinalCurso);
      });

      it('should not add a DissertacaoFinalCurso to an array that contains it', () => {
        const dissertacaoFinalCurso: IDissertacaoFinalCurso = sampleWithRequiredData;
        const dissertacaoFinalCursoCollection: IDissertacaoFinalCurso[] = [
          {
            ...dissertacaoFinalCurso,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDissertacaoFinalCursoToCollectionIfMissing(dissertacaoFinalCursoCollection, dissertacaoFinalCurso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DissertacaoFinalCurso to an array that doesn't contain it", () => {
        const dissertacaoFinalCurso: IDissertacaoFinalCurso = sampleWithRequiredData;
        const dissertacaoFinalCursoCollection: IDissertacaoFinalCurso[] = [sampleWithPartialData];
        expectedResult = service.addDissertacaoFinalCursoToCollectionIfMissing(dissertacaoFinalCursoCollection, dissertacaoFinalCurso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dissertacaoFinalCurso);
      });

      it('should add only unique DissertacaoFinalCurso to an array', () => {
        const dissertacaoFinalCursoArray: IDissertacaoFinalCurso[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dissertacaoFinalCursoCollection: IDissertacaoFinalCurso[] = [sampleWithRequiredData];
        expectedResult = service.addDissertacaoFinalCursoToCollectionIfMissing(
          dissertacaoFinalCursoCollection,
          ...dissertacaoFinalCursoArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dissertacaoFinalCurso: IDissertacaoFinalCurso = sampleWithRequiredData;
        const dissertacaoFinalCurso2: IDissertacaoFinalCurso = sampleWithPartialData;
        expectedResult = service.addDissertacaoFinalCursoToCollectionIfMissing([], dissertacaoFinalCurso, dissertacaoFinalCurso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dissertacaoFinalCurso);
        expect(expectedResult).toContain(dissertacaoFinalCurso2);
      });

      it('should accept null and undefined values', () => {
        const dissertacaoFinalCurso: IDissertacaoFinalCurso = sampleWithRequiredData;
        expectedResult = service.addDissertacaoFinalCursoToCollectionIfMissing([], null, dissertacaoFinalCurso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dissertacaoFinalCurso);
      });

      it('should return initial array if no DissertacaoFinalCurso is added', () => {
        const dissertacaoFinalCursoCollection: IDissertacaoFinalCurso[] = [sampleWithRequiredData];
        expectedResult = service.addDissertacaoFinalCursoToCollectionIfMissing(dissertacaoFinalCursoCollection, undefined, null);
        expect(expectedResult).toEqual(dissertacaoFinalCursoCollection);
      });
    });

    describe('compareDissertacaoFinalCurso', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDissertacaoFinalCurso(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDissertacaoFinalCurso(entity1, entity2);
        const compareResult2 = service.compareDissertacaoFinalCurso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDissertacaoFinalCurso(entity1, entity2);
        const compareResult2 = service.compareDissertacaoFinalCurso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDissertacaoFinalCurso(entity1, entity2);
        const compareResult2 = service.compareDissertacaoFinalCurso(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
