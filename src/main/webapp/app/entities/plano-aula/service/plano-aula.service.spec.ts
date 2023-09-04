import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlanoAula } from '../plano-aula.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plano-aula.test-samples';

import { PlanoAulaService } from './plano-aula.service';

const requireRestSample: IPlanoAula = {
  ...sampleWithRequiredData,
};

describe('PlanoAula Service', () => {
  let service: PlanoAulaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoAula | IPlanoAula[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoAulaService);
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

    it('should create a PlanoAula', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const planoAula = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoAula).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoAula', () => {
      const planoAula = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoAula).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoAula', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoAula', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoAula', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoAulaToCollectionIfMissing', () => {
      it('should add a PlanoAula to an empty array', () => {
        const planoAula: IPlanoAula = sampleWithRequiredData;
        expectedResult = service.addPlanoAulaToCollectionIfMissing([], planoAula);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoAula);
      });

      it('should not add a PlanoAula to an array that contains it', () => {
        const planoAula: IPlanoAula = sampleWithRequiredData;
        const planoAulaCollection: IPlanoAula[] = [
          {
            ...planoAula,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoAulaToCollectionIfMissing(planoAulaCollection, planoAula);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoAula to an array that doesn't contain it", () => {
        const planoAula: IPlanoAula = sampleWithRequiredData;
        const planoAulaCollection: IPlanoAula[] = [sampleWithPartialData];
        expectedResult = service.addPlanoAulaToCollectionIfMissing(planoAulaCollection, planoAula);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoAula);
      });

      it('should add only unique PlanoAula to an array', () => {
        const planoAulaArray: IPlanoAula[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planoAulaCollection: IPlanoAula[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoAulaToCollectionIfMissing(planoAulaCollection, ...planoAulaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoAula: IPlanoAula = sampleWithRequiredData;
        const planoAula2: IPlanoAula = sampleWithPartialData;
        expectedResult = service.addPlanoAulaToCollectionIfMissing([], planoAula, planoAula2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoAula);
        expect(expectedResult).toContain(planoAula2);
      });

      it('should accept null and undefined values', () => {
        const planoAula: IPlanoAula = sampleWithRequiredData;
        expectedResult = service.addPlanoAulaToCollectionIfMissing([], null, planoAula, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoAula);
      });

      it('should return initial array if no PlanoAula is added', () => {
        const planoAulaCollection: IPlanoAula[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoAulaToCollectionIfMissing(planoAulaCollection, undefined, null);
        expect(expectedResult).toEqual(planoAulaCollection);
      });
    });

    describe('comparePlanoAula', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoAula(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoAula(entity1, entity2);
        const compareResult2 = service.comparePlanoAula(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoAula(entity1, entity2);
        const compareResult2 = service.comparePlanoAula(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoAula(entity1, entity2);
        const compareResult2 = service.comparePlanoAula(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
