import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPeriodoHorario } from '../periodo-horario.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../periodo-horario.test-samples';

import { PeriodoHorarioService } from './periodo-horario.service';

const requireRestSample: IPeriodoHorario = {
  ...sampleWithRequiredData,
};

describe('PeriodoHorario Service', () => {
  let service: PeriodoHorarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IPeriodoHorario | IPeriodoHorario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PeriodoHorarioService);
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

    it('should create a PeriodoHorario', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const periodoHorario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(periodoHorario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PeriodoHorario', () => {
      const periodoHorario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(periodoHorario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PeriodoHorario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PeriodoHorario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PeriodoHorario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPeriodoHorarioToCollectionIfMissing', () => {
      it('should add a PeriodoHorario to an empty array', () => {
        const periodoHorario: IPeriodoHorario = sampleWithRequiredData;
        expectedResult = service.addPeriodoHorarioToCollectionIfMissing([], periodoHorario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(periodoHorario);
      });

      it('should not add a PeriodoHorario to an array that contains it', () => {
        const periodoHorario: IPeriodoHorario = sampleWithRequiredData;
        const periodoHorarioCollection: IPeriodoHorario[] = [
          {
            ...periodoHorario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPeriodoHorarioToCollectionIfMissing(periodoHorarioCollection, periodoHorario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PeriodoHorario to an array that doesn't contain it", () => {
        const periodoHorario: IPeriodoHorario = sampleWithRequiredData;
        const periodoHorarioCollection: IPeriodoHorario[] = [sampleWithPartialData];
        expectedResult = service.addPeriodoHorarioToCollectionIfMissing(periodoHorarioCollection, periodoHorario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(periodoHorario);
      });

      it('should add only unique PeriodoHorario to an array', () => {
        const periodoHorarioArray: IPeriodoHorario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const periodoHorarioCollection: IPeriodoHorario[] = [sampleWithRequiredData];
        expectedResult = service.addPeriodoHorarioToCollectionIfMissing(periodoHorarioCollection, ...periodoHorarioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const periodoHorario: IPeriodoHorario = sampleWithRequiredData;
        const periodoHorario2: IPeriodoHorario = sampleWithPartialData;
        expectedResult = service.addPeriodoHorarioToCollectionIfMissing([], periodoHorario, periodoHorario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(periodoHorario);
        expect(expectedResult).toContain(periodoHorario2);
      });

      it('should accept null and undefined values', () => {
        const periodoHorario: IPeriodoHorario = sampleWithRequiredData;
        expectedResult = service.addPeriodoHorarioToCollectionIfMissing([], null, periodoHorario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(periodoHorario);
      });

      it('should return initial array if no PeriodoHorario is added', () => {
        const periodoHorarioCollection: IPeriodoHorario[] = [sampleWithRequiredData];
        expectedResult = service.addPeriodoHorarioToCollectionIfMissing(periodoHorarioCollection, undefined, null);
        expect(expectedResult).toEqual(periodoHorarioCollection);
      });
    });

    describe('comparePeriodoHorario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePeriodoHorario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePeriodoHorario(entity1, entity2);
        const compareResult2 = service.comparePeriodoHorario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePeriodoHorario(entity1, entity2);
        const compareResult2 = service.comparePeriodoHorario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePeriodoHorario(entity1, entity2);
        const compareResult2 = service.comparePeriodoHorario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
