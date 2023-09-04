import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITurno } from '../turno.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../turno.test-samples';

import { TurnoService } from './turno.service';

const requireRestSample: ITurno = {
  ...sampleWithRequiredData,
};

describe('Turno Service', () => {
  let service: TurnoService;
  let httpMock: HttpTestingController;
  let expectedResult: ITurno | ITurno[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TurnoService);
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

    it('should create a Turno', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const turno = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(turno).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Turno', () => {
      const turno = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(turno).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Turno', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Turno', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Turno', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTurnoToCollectionIfMissing', () => {
      it('should add a Turno to an empty array', () => {
        const turno: ITurno = sampleWithRequiredData;
        expectedResult = service.addTurnoToCollectionIfMissing([], turno);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(turno);
      });

      it('should not add a Turno to an array that contains it', () => {
        const turno: ITurno = sampleWithRequiredData;
        const turnoCollection: ITurno[] = [
          {
            ...turno,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTurnoToCollectionIfMissing(turnoCollection, turno);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Turno to an array that doesn't contain it", () => {
        const turno: ITurno = sampleWithRequiredData;
        const turnoCollection: ITurno[] = [sampleWithPartialData];
        expectedResult = service.addTurnoToCollectionIfMissing(turnoCollection, turno);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(turno);
      });

      it('should add only unique Turno to an array', () => {
        const turnoArray: ITurno[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const turnoCollection: ITurno[] = [sampleWithRequiredData];
        expectedResult = service.addTurnoToCollectionIfMissing(turnoCollection, ...turnoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const turno: ITurno = sampleWithRequiredData;
        const turno2: ITurno = sampleWithPartialData;
        expectedResult = service.addTurnoToCollectionIfMissing([], turno, turno2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(turno);
        expect(expectedResult).toContain(turno2);
      });

      it('should accept null and undefined values', () => {
        const turno: ITurno = sampleWithRequiredData;
        expectedResult = service.addTurnoToCollectionIfMissing([], null, turno, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(turno);
      });

      it('should return initial array if no Turno is added', () => {
        const turnoCollection: ITurno[] = [sampleWithRequiredData];
        expectedResult = service.addTurnoToCollectionIfMissing(turnoCollection, undefined, null);
        expect(expectedResult).toEqual(turnoCollection);
      });
    });

    describe('compareTurno', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTurno(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTurno(entity1, entity2);
        const compareResult2 = service.compareTurno(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTurno(entity1, entity2);
        const compareResult2 = service.compareTurno(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTurno(entity1, entity2);
        const compareResult2 = service.compareTurno(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
