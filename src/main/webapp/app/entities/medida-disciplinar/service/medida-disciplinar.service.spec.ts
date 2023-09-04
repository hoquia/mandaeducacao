import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMedidaDisciplinar } from '../medida-disciplinar.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../medida-disciplinar.test-samples';

import { MedidaDisciplinarService } from './medida-disciplinar.service';

const requireRestSample: IMedidaDisciplinar = {
  ...sampleWithRequiredData,
};

describe('MedidaDisciplinar Service', () => {
  let service: MedidaDisciplinarService;
  let httpMock: HttpTestingController;
  let expectedResult: IMedidaDisciplinar | IMedidaDisciplinar[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MedidaDisciplinarService);
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

    it('should create a MedidaDisciplinar', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const medidaDisciplinar = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(medidaDisciplinar).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MedidaDisciplinar', () => {
      const medidaDisciplinar = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(medidaDisciplinar).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MedidaDisciplinar', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MedidaDisciplinar', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MedidaDisciplinar', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMedidaDisciplinarToCollectionIfMissing', () => {
      it('should add a MedidaDisciplinar to an empty array', () => {
        const medidaDisciplinar: IMedidaDisciplinar = sampleWithRequiredData;
        expectedResult = service.addMedidaDisciplinarToCollectionIfMissing([], medidaDisciplinar);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(medidaDisciplinar);
      });

      it('should not add a MedidaDisciplinar to an array that contains it', () => {
        const medidaDisciplinar: IMedidaDisciplinar = sampleWithRequiredData;
        const medidaDisciplinarCollection: IMedidaDisciplinar[] = [
          {
            ...medidaDisciplinar,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMedidaDisciplinarToCollectionIfMissing(medidaDisciplinarCollection, medidaDisciplinar);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MedidaDisciplinar to an array that doesn't contain it", () => {
        const medidaDisciplinar: IMedidaDisciplinar = sampleWithRequiredData;
        const medidaDisciplinarCollection: IMedidaDisciplinar[] = [sampleWithPartialData];
        expectedResult = service.addMedidaDisciplinarToCollectionIfMissing(medidaDisciplinarCollection, medidaDisciplinar);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(medidaDisciplinar);
      });

      it('should add only unique MedidaDisciplinar to an array', () => {
        const medidaDisciplinarArray: IMedidaDisciplinar[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const medidaDisciplinarCollection: IMedidaDisciplinar[] = [sampleWithRequiredData];
        expectedResult = service.addMedidaDisciplinarToCollectionIfMissing(medidaDisciplinarCollection, ...medidaDisciplinarArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const medidaDisciplinar: IMedidaDisciplinar = sampleWithRequiredData;
        const medidaDisciplinar2: IMedidaDisciplinar = sampleWithPartialData;
        expectedResult = service.addMedidaDisciplinarToCollectionIfMissing([], medidaDisciplinar, medidaDisciplinar2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(medidaDisciplinar);
        expect(expectedResult).toContain(medidaDisciplinar2);
      });

      it('should accept null and undefined values', () => {
        const medidaDisciplinar: IMedidaDisciplinar = sampleWithRequiredData;
        expectedResult = service.addMedidaDisciplinarToCollectionIfMissing([], null, medidaDisciplinar, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(medidaDisciplinar);
      });

      it('should return initial array if no MedidaDisciplinar is added', () => {
        const medidaDisciplinarCollection: IMedidaDisciplinar[] = [sampleWithRequiredData];
        expectedResult = service.addMedidaDisciplinarToCollectionIfMissing(medidaDisciplinarCollection, undefined, null);
        expect(expectedResult).toEqual(medidaDisciplinarCollection);
      });
    });

    describe('compareMedidaDisciplinar', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMedidaDisciplinar(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMedidaDisciplinar(entity1, entity2);
        const compareResult2 = service.compareMedidaDisciplinar(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMedidaDisciplinar(entity1, entity2);
        const compareResult2 = service.compareMedidaDisciplinar(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMedidaDisciplinar(entity1, entity2);
        const compareResult2 = service.compareMedidaDisciplinar(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
