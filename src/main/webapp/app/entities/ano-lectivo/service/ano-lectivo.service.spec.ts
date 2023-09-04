import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAnoLectivo } from '../ano-lectivo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ano-lectivo.test-samples';

import { AnoLectivoService, RestAnoLectivo } from './ano-lectivo.service';

const requireRestSample: RestAnoLectivo = {
  ...sampleWithRequiredData,
  inicio: sampleWithRequiredData.inicio?.format(DATE_FORMAT),
  fim: sampleWithRequiredData.fim?.format(DATE_FORMAT),
  timestam: sampleWithRequiredData.timestam?.toJSON(),
};

describe('AnoLectivo Service', () => {
  let service: AnoLectivoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnoLectivo | IAnoLectivo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AnoLectivoService);
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

    it('should create a AnoLectivo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const anoLectivo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anoLectivo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnoLectivo', () => {
      const anoLectivo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anoLectivo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnoLectivo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnoLectivo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnoLectivo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnoLectivoToCollectionIfMissing', () => {
      it('should add a AnoLectivo to an empty array', () => {
        const anoLectivo: IAnoLectivo = sampleWithRequiredData;
        expectedResult = service.addAnoLectivoToCollectionIfMissing([], anoLectivo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anoLectivo);
      });

      it('should not add a AnoLectivo to an array that contains it', () => {
        const anoLectivo: IAnoLectivo = sampleWithRequiredData;
        const anoLectivoCollection: IAnoLectivo[] = [
          {
            ...anoLectivo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnoLectivoToCollectionIfMissing(anoLectivoCollection, anoLectivo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnoLectivo to an array that doesn't contain it", () => {
        const anoLectivo: IAnoLectivo = sampleWithRequiredData;
        const anoLectivoCollection: IAnoLectivo[] = [sampleWithPartialData];
        expectedResult = service.addAnoLectivoToCollectionIfMissing(anoLectivoCollection, anoLectivo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anoLectivo);
      });

      it('should add only unique AnoLectivo to an array', () => {
        const anoLectivoArray: IAnoLectivo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const anoLectivoCollection: IAnoLectivo[] = [sampleWithRequiredData];
        expectedResult = service.addAnoLectivoToCollectionIfMissing(anoLectivoCollection, ...anoLectivoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anoLectivo: IAnoLectivo = sampleWithRequiredData;
        const anoLectivo2: IAnoLectivo = sampleWithPartialData;
        expectedResult = service.addAnoLectivoToCollectionIfMissing([], anoLectivo, anoLectivo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anoLectivo);
        expect(expectedResult).toContain(anoLectivo2);
      });

      it('should accept null and undefined values', () => {
        const anoLectivo: IAnoLectivo = sampleWithRequiredData;
        expectedResult = service.addAnoLectivoToCollectionIfMissing([], null, anoLectivo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anoLectivo);
      });

      it('should return initial array if no AnoLectivo is added', () => {
        const anoLectivoCollection: IAnoLectivo[] = [sampleWithRequiredData];
        expectedResult = service.addAnoLectivoToCollectionIfMissing(anoLectivoCollection, undefined, null);
        expect(expectedResult).toEqual(anoLectivoCollection);
      });
    });

    describe('compareAnoLectivo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnoLectivo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnoLectivo(entity1, entity2);
        const compareResult2 = service.compareAnoLectivo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnoLectivo(entity1, entity2);
        const compareResult2 = service.compareAnoLectivo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnoLectivo(entity1, entity2);
        const compareResult2 = service.compareAnoLectivo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
