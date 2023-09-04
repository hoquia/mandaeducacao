import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDisciplinaCurricular } from '../disciplina-curricular.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../disciplina-curricular.test-samples';

import { DisciplinaCurricularService } from './disciplina-curricular.service';

const requireRestSample: IDisciplinaCurricular = {
  ...sampleWithRequiredData,
};

describe('DisciplinaCurricular Service', () => {
  let service: DisciplinaCurricularService;
  let httpMock: HttpTestingController;
  let expectedResult: IDisciplinaCurricular | IDisciplinaCurricular[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DisciplinaCurricularService);
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

    it('should create a DisciplinaCurricular', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const disciplinaCurricular = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(disciplinaCurricular).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DisciplinaCurricular', () => {
      const disciplinaCurricular = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(disciplinaCurricular).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DisciplinaCurricular', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DisciplinaCurricular', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DisciplinaCurricular', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDisciplinaCurricularToCollectionIfMissing', () => {
      it('should add a DisciplinaCurricular to an empty array', () => {
        const disciplinaCurricular: IDisciplinaCurricular = sampleWithRequiredData;
        expectedResult = service.addDisciplinaCurricularToCollectionIfMissing([], disciplinaCurricular);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disciplinaCurricular);
      });

      it('should not add a DisciplinaCurricular to an array that contains it', () => {
        const disciplinaCurricular: IDisciplinaCurricular = sampleWithRequiredData;
        const disciplinaCurricularCollection: IDisciplinaCurricular[] = [
          {
            ...disciplinaCurricular,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDisciplinaCurricularToCollectionIfMissing(disciplinaCurricularCollection, disciplinaCurricular);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DisciplinaCurricular to an array that doesn't contain it", () => {
        const disciplinaCurricular: IDisciplinaCurricular = sampleWithRequiredData;
        const disciplinaCurricularCollection: IDisciplinaCurricular[] = [sampleWithPartialData];
        expectedResult = service.addDisciplinaCurricularToCollectionIfMissing(disciplinaCurricularCollection, disciplinaCurricular);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disciplinaCurricular);
      });

      it('should add only unique DisciplinaCurricular to an array', () => {
        const disciplinaCurricularArray: IDisciplinaCurricular[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const disciplinaCurricularCollection: IDisciplinaCurricular[] = [sampleWithRequiredData];
        expectedResult = service.addDisciplinaCurricularToCollectionIfMissing(disciplinaCurricularCollection, ...disciplinaCurricularArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const disciplinaCurricular: IDisciplinaCurricular = sampleWithRequiredData;
        const disciplinaCurricular2: IDisciplinaCurricular = sampleWithPartialData;
        expectedResult = service.addDisciplinaCurricularToCollectionIfMissing([], disciplinaCurricular, disciplinaCurricular2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(disciplinaCurricular);
        expect(expectedResult).toContain(disciplinaCurricular2);
      });

      it('should accept null and undefined values', () => {
        const disciplinaCurricular: IDisciplinaCurricular = sampleWithRequiredData;
        expectedResult = service.addDisciplinaCurricularToCollectionIfMissing([], null, disciplinaCurricular, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(disciplinaCurricular);
      });

      it('should return initial array if no DisciplinaCurricular is added', () => {
        const disciplinaCurricularCollection: IDisciplinaCurricular[] = [sampleWithRequiredData];
        expectedResult = service.addDisciplinaCurricularToCollectionIfMissing(disciplinaCurricularCollection, undefined, null);
        expect(expectedResult).toEqual(disciplinaCurricularCollection);
      });
    });

    describe('compareDisciplinaCurricular', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDisciplinaCurricular(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDisciplinaCurricular(entity1, entity2);
        const compareResult2 = service.compareDisciplinaCurricular(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDisciplinaCurricular(entity1, entity2);
        const compareResult2 = service.compareDisciplinaCurricular(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDisciplinaCurricular(entity1, entity2);
        const compareResult2 = service.compareDisciplinaCurricular(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
