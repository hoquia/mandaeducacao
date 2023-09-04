import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlanoCurricular } from '../plano-curricular.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plano-curricular.test-samples';

import { PlanoCurricularService } from './plano-curricular.service';

const requireRestSample: IPlanoCurricular = {
  ...sampleWithRequiredData,
};

describe('PlanoCurricular Service', () => {
  let service: PlanoCurricularService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoCurricular | IPlanoCurricular[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoCurricularService);
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

    it('should create a PlanoCurricular', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const planoCurricular = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoCurricular).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoCurricular', () => {
      const planoCurricular = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoCurricular).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoCurricular', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoCurricular', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoCurricular', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoCurricularToCollectionIfMissing', () => {
      it('should add a PlanoCurricular to an empty array', () => {
        const planoCurricular: IPlanoCurricular = sampleWithRequiredData;
        expectedResult = service.addPlanoCurricularToCollectionIfMissing([], planoCurricular);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoCurricular);
      });

      it('should not add a PlanoCurricular to an array that contains it', () => {
        const planoCurricular: IPlanoCurricular = sampleWithRequiredData;
        const planoCurricularCollection: IPlanoCurricular[] = [
          {
            ...planoCurricular,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoCurricularToCollectionIfMissing(planoCurricularCollection, planoCurricular);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoCurricular to an array that doesn't contain it", () => {
        const planoCurricular: IPlanoCurricular = sampleWithRequiredData;
        const planoCurricularCollection: IPlanoCurricular[] = [sampleWithPartialData];
        expectedResult = service.addPlanoCurricularToCollectionIfMissing(planoCurricularCollection, planoCurricular);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoCurricular);
      });

      it('should add only unique PlanoCurricular to an array', () => {
        const planoCurricularArray: IPlanoCurricular[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planoCurricularCollection: IPlanoCurricular[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoCurricularToCollectionIfMissing(planoCurricularCollection, ...planoCurricularArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoCurricular: IPlanoCurricular = sampleWithRequiredData;
        const planoCurricular2: IPlanoCurricular = sampleWithPartialData;
        expectedResult = service.addPlanoCurricularToCollectionIfMissing([], planoCurricular, planoCurricular2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoCurricular);
        expect(expectedResult).toContain(planoCurricular2);
      });

      it('should accept null and undefined values', () => {
        const planoCurricular: IPlanoCurricular = sampleWithRequiredData;
        expectedResult = service.addPlanoCurricularToCollectionIfMissing([], null, planoCurricular, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoCurricular);
      });

      it('should return initial array if no PlanoCurricular is added', () => {
        const planoCurricularCollection: IPlanoCurricular[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoCurricularToCollectionIfMissing(planoCurricularCollection, undefined, null);
        expect(expectedResult).toEqual(planoCurricularCollection);
      });
    });

    describe('comparePlanoCurricular', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoCurricular(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoCurricular(entity1, entity2);
        const compareResult2 = service.comparePlanoCurricular(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoCurricular(entity1, entity2);
        const compareResult2 = service.comparePlanoCurricular(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoCurricular(entity1, entity2);
        const compareResult2 = service.comparePlanoCurricular(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
