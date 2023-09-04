import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlanoMulta } from '../plano-multa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plano-multa.test-samples';

import { PlanoMultaService } from './plano-multa.service';

const requireRestSample: IPlanoMulta = {
  ...sampleWithRequiredData,
};

describe('PlanoMulta Service', () => {
  let service: PlanoMultaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoMulta | IPlanoMulta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoMultaService);
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

    it('should create a PlanoMulta', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const planoMulta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoMulta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoMulta', () => {
      const planoMulta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoMulta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoMulta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoMulta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoMulta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoMultaToCollectionIfMissing', () => {
      it('should add a PlanoMulta to an empty array', () => {
        const planoMulta: IPlanoMulta = sampleWithRequiredData;
        expectedResult = service.addPlanoMultaToCollectionIfMissing([], planoMulta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoMulta);
      });

      it('should not add a PlanoMulta to an array that contains it', () => {
        const planoMulta: IPlanoMulta = sampleWithRequiredData;
        const planoMultaCollection: IPlanoMulta[] = [
          {
            ...planoMulta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoMultaToCollectionIfMissing(planoMultaCollection, planoMulta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoMulta to an array that doesn't contain it", () => {
        const planoMulta: IPlanoMulta = sampleWithRequiredData;
        const planoMultaCollection: IPlanoMulta[] = [sampleWithPartialData];
        expectedResult = service.addPlanoMultaToCollectionIfMissing(planoMultaCollection, planoMulta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoMulta);
      });

      it('should add only unique PlanoMulta to an array', () => {
        const planoMultaArray: IPlanoMulta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planoMultaCollection: IPlanoMulta[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoMultaToCollectionIfMissing(planoMultaCollection, ...planoMultaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoMulta: IPlanoMulta = sampleWithRequiredData;
        const planoMulta2: IPlanoMulta = sampleWithPartialData;
        expectedResult = service.addPlanoMultaToCollectionIfMissing([], planoMulta, planoMulta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoMulta);
        expect(expectedResult).toContain(planoMulta2);
      });

      it('should accept null and undefined values', () => {
        const planoMulta: IPlanoMulta = sampleWithRequiredData;
        expectedResult = service.addPlanoMultaToCollectionIfMissing([], null, planoMulta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoMulta);
      });

      it('should return initial array if no PlanoMulta is added', () => {
        const planoMultaCollection: IPlanoMulta[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoMultaToCollectionIfMissing(planoMultaCollection, undefined, null);
        expect(expectedResult).toEqual(planoMultaCollection);
      });
    });

    describe('comparePlanoMulta', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoMulta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoMulta(entity1, entity2);
        const compareResult2 = service.comparePlanoMulta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoMulta(entity1, entity2);
        const compareResult2 = service.comparePlanoMulta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoMulta(entity1, entity2);
        const compareResult2 = service.comparePlanoMulta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
